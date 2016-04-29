package the_fireplace.commbind;

import net.minecraft.client.gui.GuiOptions;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * @author The_Fireplace
 */
public class ClientEvents {
    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
        if(eventArgs.getModID().equals(CommBind.MODID)) {
            CommBind.syncConfig();
            CommBind.keyHandler.setNeedsRestart();
        }
    }
    @SubscribeEvent
    public void guiScreenEvent(GuiScreenEvent event){
        if(event.getGui() instanceof GuiOptions)
            CommBind.keyHandler.saveBindings();
    }
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event){
        if(CommBind.keyHandler.keyTimer > 0)
            CommBind.keyHandler.keyTimer--;
    }
}