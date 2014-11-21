package boni.dummy.network;

import boni.dummy.EntityDummy;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SyncEquipmentMessage implements IMessage {
    private int entityID;
    private int slotId;
    private ItemStack itemstack;

    public SyncEquipmentMessage() {
    }

    public SyncEquipmentMessage(int entityId, int slotId, ItemStack itemstack) {
        this.entityID = entityId;
        this.slotId = slotId;
        this.itemstack = itemstack == null ? null : itemstack.copy();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.entityID = buf.readInt();
        this.slotId = buf.readInt();
        //this.itemstack = null;

        // let's try it like this since identification fails with null for some reason
        //if(buf.readBoolean())
            this.itemstack = ByteBufUtils.readItemStack(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(entityID);
        buf.writeInt(slotId);
        //buf.writeBoolean(itemstack != null);
        //if(itemstack != null)
            ByteBufUtils.writeItemStack(buf, itemstack);
    }

    public static class MessageHandlerClient implements IMessageHandler<SyncEquipmentMessage, IMessage> {
        @Override
        public SyncEquipmentMessage onMessage(SyncEquipmentMessage message, MessageContext ctx) {
            Entity entity = Minecraft.getMinecraft().theWorld.getEntityByID(message.entityID);
            if(entity != null && entity instanceof EntityDummy)
            {
                entity.setCurrentItemOrArmor(message.slotId, message.itemstack);
            }
            return null;
        }
    }
}
