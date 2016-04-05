package boni.dummy.network;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import boni.dummy.EntityDummy;
import boni.dummy.EntityFloatingNumber;
import io.netty.buffer.ByteBuf;

public class DamageMessage implements IMessage {

  public float damage;
  public float shakeAmount;
  public int entityID;
  public int nrID;

  public DamageMessage() {
  }

  public DamageMessage(float damage, float shakeAmount, EntityDummy entity, EntityFloatingNumber e2) {
    this.damage = damage;
    this.shakeAmount = shakeAmount;
    this.entityID = entity.getEntityId();
    this.nrID = e2 != null ? e2.getEntityId() : -1;
  }

  @Override
  public void fromBytes(ByteBuf buf) {
    this.damage = buf.readFloat();
    this.shakeAmount = buf.readFloat();
    this.entityID = buf.readInt();
    this.nrID = buf.readInt();
  }

  @Override
  public void toBytes(ByteBuf buf) {
    buf.writeFloat(damage);
    buf.writeFloat(shakeAmount);
    buf.writeInt(entityID);
    buf.writeInt(nrID);
  }

  public static class MessageHandlerClient implements IMessageHandler<DamageMessage, IMessage> {

    @Override
    public DamageMessage onMessage(final DamageMessage message, MessageContext ctx) {
      FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(new Runnable() {
        @Override
        public void run() {
          Entity entity = Minecraft.getMinecraft().theWorld.getEntityByID(message.entityID);
          if(entity != null && entity instanceof EntityDummy) {
            EntityDummy dummy = (EntityDummy) entity;
            dummy.shake = message.shakeAmount;
            dummy.setCustomNameTag(String.valueOf(message.damage / 2f));
          }
          if(message.nrID > 0) {
            entity = Minecraft.getMinecraft().theWorld.getEntityByID(message.nrID);
            if(entity != null && entity instanceof EntityFloatingNumber) {
              ((EntityFloatingNumber) entity).reSet(message.damage);
            }
          }
        }
      });
      return null;
    }
  }
}
