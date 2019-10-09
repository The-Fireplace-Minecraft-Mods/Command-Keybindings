package the_fireplace.commbind;

import com.google.common.collect.Lists;
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

import java.util.Arrays;
import java.util.List;

import static the_fireplace.commbind.config.ConfigValues.*;

/**
 * @author The_Fireplace
 */
public class KeyHandler {
    private static final String desc = "key.comm";
    private KeyBinding[] keys;
    byte[] keyTimer;
    private boolean isLoaded;
    public KeyHandler(){
        keys = new KeyBinding[COMMANDS.length];
        keyTimer = new byte[keys.length];
        while(BINDINGSTORAGE.length<COMMANDS.length)
            BINDINGSTORAGE = ArrayUtils.add(BINDINGSTORAGE, Keyboard.KEY_NONE);
        while(MODIFIERS.length<COMMANDS.length)
            MODIFIERS = ArrayUtils.add(MODIFIERS, KeyModifier.NONE.ordinal());
        for(int i = 0; i < COMMANDS.length; ++i){
            keys[i] = new KeyBinding(I18n.format(desc, COMMANDS[i]), BINDINGSTORAGE[i], "key.commbind.category");
            keys[i].setKeyModifierAndCode(KeyModifier.values()[MODIFIERS[i]], BINDINGSTORAGE[i]);
            ClientRegistry.registerKeyBinding(keys[i]);
        }
        isLoaded = true;
    }
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event){
        for(int i=0;i<COMMANDS.length;++i) {
            if(i < keys.length && i < keyTimer.length) {
                if(keyTimer[i] <= 0)
                    if (Keyboard.isKeyDown(keys[i].getKeyCode()) && (KeyModifier.values()[MODIFIERS[i]].equals(KeyModifier.NONE) || KeyModifier.values()[MODIFIERS[i]].isActive(KeyConflictContext.IN_GAME)))
                        command(COMMANDS[i]);
            }
        }
    }

    public void command(String command){
        if(Minecraft.getMinecraft().inGameHasFocus) {
            Minecraft.getMinecraft().player.sendChatMessage("/" + command);
            this.keyTimer[ArrayUtils.indexOf(COMMANDS, command)] = (byte)ConfigValues.COOLDOWN;
        }
    }

    /**
     * Writes all the key bindings to the config
     */
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

    /**
     * Perform a reload after config changes. This is called when the config initially loads and after something changes in the config gui
     */
    public void reload() {
        //Don't do this if it hasn't been loaded yet. This will be true if something was changed from the config gui and false if the game is loading.
        if(isLoaded) {
            for (KeyBinding key : keys)
                Minecraft.getMinecraft().gameSettings.keyBindings = ArrayUtils.remove(Minecraft.getMinecraft().gameSettings.keyBindings, ArrayUtils.indexOf(Minecraft.getMinecraft().gameSettings.keyBindings, key));

            keys = new KeyBinding[ConfigValues.COMMANDS.length];
            keyTimer = new byte[keys.length];
            while (BINDINGSTORAGE.length < ConfigValues.COMMANDS.length)
                BINDINGSTORAGE = ArrayUtils.add(BINDINGSTORAGE, Keyboard.KEY_NONE);
            while (MODIFIERS.length < ConfigValues.COMMANDS.length)
                MODIFIERS = ArrayUtils.add(MODIFIERS, KeyModifier.NONE.ordinal());
            for (int i = 0; i < ConfigValues.COMMANDS.length; ++i) {
                keys[i] = new KeyBinding(I18n.format(desc, ConfigValues.COMMANDS[i]), BINDINGSTORAGE[i], "key.commbind.category");
                keys[i].setKeyModifierAndCode(KeyModifier.values()[MODIFIERS[i]], BINDINGSTORAGE[i]);
                ClientRegistry.registerKeyBinding(keys[i]);
            }
        }
    }
}
