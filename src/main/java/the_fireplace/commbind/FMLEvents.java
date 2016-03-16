package the_fireplace.commbind;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import the_fireplace.commbind.config.ConfigValues;

/**
 * @author The_Fireplace
 */
public class FMLEvents {
    public static final int COMMONE = 0;
    public static final int COMMTWO = 1;
    private static final String[] desc = {"key.commbind1.desc", "key.commbind2.desc"};
    private static final int[] keyValues = {Keyboard.KEY_F, Keyboard.KEY_G};
    private final KeyBinding[] keys;
    public FMLEvents(){
        keys = new KeyBinding[desc.length];
        for(int i = 0; i < desc.length; ++i){
            keys[i] = new KeyBinding(desc[i], keyValues[i], "key.commbind.category");
            ClientRegistry.registerKeyBinding(keys[i]);
        }
    }
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onKeyInput(KeyInputEvent event){
        if(keys[COMMONE].isPressed()){
            command(ConfigValues.COMMAND_ONE);
        }
        if(keys[COMMTWO].isPressed()){
            command(ConfigValues.COMMAND_TWO);
        }
    }

    public void command(String command){
        if(Minecraft.getMinecraft() != null)
            if(Minecraft.getMinecraft().inGameHasFocus)
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/"+command);
    }
    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
        if(eventArgs.modID.equals(CommBind.MODID))
            CommBind.syncConfig();
    }
}