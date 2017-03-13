package the_fireplace.commbind;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import the_fireplace.commbind.config.ConfigValues;

/**
 * @author The_Fireplace
 */
@Mod(modid=CommBind.MODID, name=CommBind.MODNAME, clientSideOnly=true, guiFactory="the_fireplace.commbind.config.CommBindGuiFactory", updateJSON = "http://thefireplace.bitnamiapp.com/jsons/commbind.json", acceptedMinecraftVersions = "[1.11,)")
public class CommBind {
    public static final String MODID="commandkeybindings";
    public static final String MODNAME="Command Keybindings";
    public static Configuration config;

    public static KeyHandler keyHandler;

    public static Property COMMANDS;
    public static Property BINDINGS;
    public static Property MODIFIERS;

    public static void syncConfig(){
        ConfigValues.COMMANDS = COMMANDS.getStringList();
        ConfigValues.BINDINGSTORAGE = BINDINGS.getIntList();
        ConfigValues.MODIFIERS = MODIFIERS.getIntList();
        if(config.hasChanged())
            config.save();
        if(keyHandler != null)
            keyHandler.reload();
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        COMMANDS = config.get(Configuration.CATEGORY_GENERAL, ConfigValues.COMMANDS_NAME, ConfigValues.COMMANDS_DEFAULT, I18n.format(ConfigValues.COMMANDS_NAME+".tooltip"));
        BINDINGS = config.get("hidden", ConfigValues.BINDINGSTORAGE_NAME, ConfigValues.BINDINGSTORAGE_DEFAULT);
        MODIFIERS = config.get("hidden", ConfigValues.MODIFIERS_NAME, ConfigValues.MODIFIERS_DEFAULT);
        syncConfig();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        keyHandler = new KeyHandler();
        MinecraftForge.EVENT_BUS.register(new ClientEvents());
        MinecraftForge.EVENT_BUS.register(keyHandler);
    }
}
