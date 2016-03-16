package the_fireplace.commbind;

import net.minecraft.util.StatCollector;
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
@Mod(modid=CommBind.MODID, name=CommBind.MODNAME, clientSideOnly=true, guiFactory="the_fireplace.commbind.config.CommBindGuiFactory")
public class CommBind {
    public static final String MODID="commbind";
    public static final String MODNAME="Command Keybindings";
    public static String VERSION;
    public static final String curseCode = "242907-command-keybindings";
    public static Configuration config;

    public static Property COMMAND_ONE;
    public static Property COMMAND_TWO;

    public static void syncConfig(){
        ConfigValues.COMMAND_ONE = COMMAND_ONE.getString();
        ConfigValues.COMMAND_TWO = COMMAND_TWO.getString();
        if(config.hasChanged())
            config.save();
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        String[] version = event.getModMetadata().version.split("\\.");
        if(version[3].equals("BUILDNUMBER"))//Dev environment
            VERSION = event.getModMetadata().version.replace("BUILDNUMBER", "9001");
        else//Released build
            VERSION = event.getModMetadata().version;
        config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        COMMAND_ONE = config.get(Configuration.CATEGORY_GENERAL, ConfigValues.COMMAND_ONE_NAME, ConfigValues.COMMAND_ONE_DEFAULT, StatCollector.translateToLocal(ConfigValues.COMMAND_ONE_NAME+".tooltip"));
        COMMAND_TWO = config.get(Configuration.CATEGORY_GENERAL, ConfigValues.COMMAND_TWO_NAME, ConfigValues.COMMAND_TWO_DEFAULT, StatCollector.translateToLocal(ConfigValues.COMMAND_TWO_NAME+".tooltip"));
        syncConfig();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        MinecraftForge.EVENT_BUS.register(new FMLEvents());
    }
}
