package boni.dummy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DummyEventHandler {

  @SubscribeEvent
  public void onPlayerLeftClick(LivingAttackEvent event) {
    if(event.getSource() == null || event.getEntity() == null || !(event.getEntity() instanceof EntityDummy)) {
      return;
    }

    if(!event.getSource().damageType.equals("player")) {
      return;
    }
    if(!(event.getSource().getEntity() instanceof EntityPlayer)) {
      return;
    }

    EntityPlayer player = (EntityPlayer) event.getSource().getEntity();

    // shift-leftclick with empty hand dismantles
    if(player.isSneaking() && player.getHeldItemMainhand() == null) {
      ((EntityDummy) event.getEntity()).dismantle();
    }
  }
}
