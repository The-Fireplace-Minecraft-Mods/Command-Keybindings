package the_fireplace.commbind.config;

import net.minecraftforge.client.settings.KeyModifier;
import org.lwjgl.input.Keyboard;

/**
 * @author The_Fireplace
 */
public class ConfigValues {
    public static String[] COMMANDS;
    public static final String[] COMMANDS_DEFAULT = new String[]{"help", "weather thunder"};
    public static final String COMMANDS_NAME = "commlist";

    public static int COOLDOWN;
    public static final int COOLDOWN_DEFAULT = 20;
    public static final String COOLDOWN_NAME = "cooldown";

    public static int[] BINDINGSTORAGE;
    public static final int[] BINDINGSTORAGE_DEFAULT = new int[]{Keyboard.KEY_NONE, Keyboard.KEY_NONE};
    public static final String BINDINGSTORAGE_NAME = "bindingstorage";

    public static int[] MODIFIERS;
    public static final int[] MODIFIERS_DEFAULT = new int[]{KeyModifier.NONE.ordinal(), KeyModifier.NONE.ordinal()};
    public static final String MODIFIERS_NAME = "modifiers";
}
