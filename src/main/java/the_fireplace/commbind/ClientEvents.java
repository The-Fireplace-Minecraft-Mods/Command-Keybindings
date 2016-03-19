package the_fireplace.commbind;

import net.minecraft.client.gui.GuiOptions;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author The_Fireplace
 */
public class ClientEvents {
    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
        if(eventArgs.modID.equals(CommBind.MODID)) {
            CommBind.syncConfig();
            CommBind.keyHandler.setNeedsRestart();
        }
    }
    @SubscribeEvent
    public void guiScreenEvent(GuiScreenEvent event){
        if(event.gui instanceof GuiOptions)
            CommBind.keyHandler.saveBindings();
    }
}