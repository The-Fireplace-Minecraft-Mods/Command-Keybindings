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
        if(eventArgs.getModID().equals(CommBind.MODID))
            CommBind.syncConfig();
    }
    @SubscribeEvent
    public void guiScreenEvent(GuiScreenEvent event){
        if(event.getGui() instanceof GuiOptions)
            CommBind.keyHandler.saveBindings();
    }
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event){
        for(int i=0;i<CommBind.keyHandler.keyTimer.length;i++)
            if(CommBind.keyHandler.keyTimer[i] > 0)
                CommBind.keyHandler.keyTimer[i]--;
    }
}