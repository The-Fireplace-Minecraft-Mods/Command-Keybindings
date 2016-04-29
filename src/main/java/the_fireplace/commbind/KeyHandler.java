package the_fireplace.commbind;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.input.Keyboard;
import the_fireplace.commbind.config.ConfigValues;

/**
 * @author The_Fireplace
 */
public class KeyHandler {
    private static final String desc = I18n.translateToLocal("key.comm");
    private KeyBinding[] keys;
    private boolean needsRestart;
    public byte keyTimer = 0;
    public KeyHandler(){
        keys = new KeyBinding[ConfigValues.COMMANDS.length];
        for(int i = 0; i < ConfigValues.COMMANDS.length; ++i){
            while(ConfigValues.BINDINGSTORAGE.length<ConfigValues.COMMANDS.length){
                ConfigValues.BINDINGSTORAGE = ArrayUtils.add(ConfigValues.BINDINGSTORAGE, Keyboard.KEY_NONE);
            }
            keys[i] = new KeyBinding(String.format(desc, ConfigValues.COMMANDS[i]), ConfigValues.BINDINGSTORAGE[i], "key.commbind.category");
            ClientRegistry.registerKeyBinding(keys[i]);
        }
        needsRestart=false;
    }
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event){
        if(keyTimer <= 0)
        for(int i=0;i<ConfigValues.COMMANDS.length;++i){
            if(i < keys.length) {
                if (keys[i].isPressed())
                    if (!needsRestart)
                        command(ConfigValues.COMMANDS[i]);
                    else
                        Minecraft.getMinecraft().thePlayer.addChatMessage(new TextComponentString(I18n.translateToLocal("commbind.restart")));
            }else{
                Minecraft.getMinecraft().thePlayer.addChatMessage(new TextComponentString(I18n.translateToLocal("commbind.restart")));
            }
        }
    }

    public void command(String command){
        if(Minecraft.getMinecraft() != null)
            if(Minecraft.getMinecraft().inGameHasFocus) {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/" + command);
                this.keyTimer = 127;
            }
    }

    public void setNeedsRestart(){
        needsRestart=true;
    }

    public void saveBindings(){
        int[] temp = CommBind.BINDINGS.getIntList();
        while(temp.length<keys.length){
            temp = ArrayUtils.add(temp, Keyboard.KEY_NONE);
        }
        for(int i=0;i<keys.length;++i){
            temp[i]=keys[i].getKeyCode();
        }
        CommBind.BINDINGS.set(temp);
        CommBind.syncConfig();
    }
}
