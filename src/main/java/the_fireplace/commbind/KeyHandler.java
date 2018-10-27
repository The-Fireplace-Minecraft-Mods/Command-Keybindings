package the_fireplace.commbind;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.input.Keyboard;
import the_fireplace.commbind.config.ConfigValues;

import static the_fireplace.commbind.config.ConfigValues.BINDINGSTORAGE;
import static the_fireplace.commbind.config.ConfigValues.MODIFIERS;

/**
 * @author The_Fireplace
 */
public class KeyHandler {
    private static final String desc = "key.comm";
    private KeyBinding[] keys;
    byte[] keyTimer;
    private boolean isLoaded;
    public KeyHandler(){
        keys = new KeyBinding[ConfigValues.COMMANDS.length];
        keyTimer = new byte[keys.length];
        for(int i = 0; i < ConfigValues.COMMANDS.length; ++i){
            while(BINDINGSTORAGE.length<ConfigValues.COMMANDS.length){
                BINDINGSTORAGE = ArrayUtils.add(BINDINGSTORAGE, Keyboard.KEY_NONE);
            }
            while(MODIFIERS.length<ConfigValues.COMMANDS.length){
                MODIFIERS = ArrayUtils.add(MODIFIERS, KeyModifier.NONE.ordinal());
            }
            keys[i] = new KeyBinding(I18n.format(desc, ConfigValues.COMMANDS[i]), BINDINGSTORAGE[i], "key.commbind.category");
            keys[i].setKeyModifierAndCode(KeyModifier.values()[MODIFIERS[i]], BINDINGSTORAGE[i]);
            ClientRegistry.registerKeyBinding(keys[i]);
        }
        isLoaded = true;
    }
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event){
        for(int i=0;i<ConfigValues.COMMANDS.length;++i){
            if(i < keys.length && i < keyTimer.length) {
                if(keyTimer[i] <= 0)
                    if (keys[i].isPressed() && (KeyModifier.values()[MODIFIERS[i]].equals(KeyModifier.NONE) || KeyModifier.values()[MODIFIERS[i]].isActive(KeyConflictContext.IN_GAME)))
                        command(ConfigValues.COMMANDS[i]);
            }
        }
    }

    public void command(String command){
        if(Minecraft.getMinecraft().inGameHasFocus) {
            Minecraft.getMinecraft().player.sendChatMessage("/" + command);
            this.keyTimer[ArrayUtils.indexOf(ConfigValues.COMMANDS, command)] = 20;
        }
    }

    public void saveBindings(){
        int[] updatedBindings = CommBind.BINDINGS.getIntList();
        int[] updatedModifiers = CommBind.MODIFIERS.getIntList();
        //Create lists of bindings and modifiers set to none
        while(updatedBindings.length<keys.length)
            updatedBindings = ArrayUtils.add(updatedBindings, Keyboard.KEY_NONE);
        while(updatedModifiers.length<keys.length)
            updatedModifiers = ArrayUtils.add(updatedModifiers, KeyModifier.NONE.ordinal());
        //Update all the bindings and modifiers
        for(int i=0;i<keys.length;++i){
            updatedBindings[i]=keys[i].getKeyCode();
            updatedModifiers[i]=keys[i].getKeyModifier().ordinal();
        }
        CommBind.BINDINGS.set(updatedBindings);
        CommBind.MODIFIERS.set(updatedModifiers);
        CommBind.syncConfig();
    }

    public void reload(){
        if(isLoaded) {
            for (KeyBinding key : keys)
                Minecraft.getMinecraft().gameSettings.keyBindings = ArrayUtils.remove(Minecraft.getMinecraft().gameSettings.keyBindings, ArrayUtils.indexOf(Minecraft.getMinecraft().gameSettings.keyBindings, key));

            keys = new KeyBinding[ConfigValues.COMMANDS.length];
            keyTimer = new byte[keys.length];
            for (int i = 0; i < ConfigValues.COMMANDS.length; ++i) {
                while (BINDINGSTORAGE.length < ConfigValues.COMMANDS.length) {
                    BINDINGSTORAGE = ArrayUtils.add(BINDINGSTORAGE, Keyboard.KEY_NONE);
                }
                while (MODIFIERS.length < ConfigValues.COMMANDS.length) {
                    MODIFIERS = ArrayUtils.add(MODIFIERS, KeyModifier.NONE.ordinal());
                }
                keys[i] = new KeyBinding(I18n.format(desc, ConfigValues.COMMANDS[i]), BINDINGSTORAGE[i], "key.commbind.category");
                keys[i].setKeyModifierAndCode(KeyModifier.values()[MODIFIERS[i]], BINDINGSTORAGE[i]);
                ClientRegistry.registerKeyBinding(keys[i]);
            }
        }
    }
}
