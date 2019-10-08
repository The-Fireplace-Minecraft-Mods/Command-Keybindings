package the_fireplace.commbind.config;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;
import the_fireplace.commbind.CommBind;

/**
 * @author The_Fireplace
 */
public class CommBindConfigGui extends GuiConfig {

    public CommBindConfigGui(GuiScreen parentScreen) {
        super(parentScreen, new ConfigElement(CommBind.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), CommBind.MODID, true,
                true, I18n.format("comm.tooltip"));
    }

}