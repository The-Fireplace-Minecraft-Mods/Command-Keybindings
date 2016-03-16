package the_fireplace.commbind.config;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;
import the_fireplace.commbind.CommBind;

/**
 *
 * @author The_Fireplace
 *
 */
public class CommBindConfigGui extends GuiConfig {

    public CommBindConfigGui(GuiScreen parentScreen) {
        super(parentScreen, new ConfigElement(CommBind.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), CommBind.MODID, false,
                false, StatCollector.translateToLocal("comm.tooltip"));
    }

}