package boni.dummy;

import boni.dummy.network.DamageMessage;
import boni.dummy.network.SyncEquipmentMessage;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S04PacketEntityEquipment;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class EntityDummy extends EntityLiving implements IEntityAdditionalSpawnData {
    private float customRotation;
    public float shake;
    public float shakeAnimation; // used to have an independent start for the animation, otherwhise the phase of the animation depends ont he damage dealt
    // used to calculate the whole damage in one tick, in case there are multiple sources
    public float lastDamage;
    public int lastDamageTick;
    public int firstDamageTick; // indicates when we started taking damage and if != 0 it also means that we are currently recording damage taken
    public float damageTaken;

    public EntityFloatingNumber myLittleNumber;

    public EntityDummy(World world) {
        super(world);

        for(int i = 0; i < equipmentDropChances.length; i++)
            equipmentDropChances[i] = 1.1f;
    }

    public void setPlacementRotation(Vec3 lookVector, int side)
    {
        int r = 0;
        // up/down == depend on look vector
        if(side == 0 || side == 1) {
            r = (int) (Math.atan2(lookVector.zCoord, lookVector.xCoord) * 360 / (2*Math.PI));
            r += 90;
        }
        else if(side == 2)
            r = 180;
        else if(side == 3)
            r = 0;
        else if(side == 4)
            r = 90;
        else if(side == 5)
            r = 270;

        customRotation = r;
        setCustomRotationStuff();
    }

    private void setCustomRotationStuff()
    {
        float r = customRotation;
        prevRotationYawHead = rotationYawHead = r;
        prevRotationYaw = rotationYaw = r;
        newRotationYaw = r;
        prevRenderYawOffset = renderYawOffset = r;
        randomYawVelocity = 0;
    }

    // dress it up! :D
    @Override
    protected boolean interact(EntityPlayer player) {
        ItemStack stack = player.getCurrentEquippedItem();
        if(stack == null) {
            if(!player.isSneaking())
                return false;
            // taking armor off?
            for(int i = 0; i < 4; i++) {
                ItemStack armor = getEquipmentInSlot(4-i);
                if(armor == null)
                    continue;
                if(!this.worldObj.isRemote) {
                    if(!player.capabilities.isCreativeMode)
                        this.entityDropItem(armor, 1.0f);
                    // send update because for some reason that stuff is not synced
                    TestDummyMod.proxy.network.sendToAllAround(new SyncEquipmentMessage(this.getEntityId(), 4-i, null), new NetworkRegistry.TargetPoint(dimension, posX, posY, posZ, 20));
                }

                // also do it locally, but it'll be overwritten by the update packet above anyway
                this.setCurrentItemOrArmor(4-i, null);
                return true;
            }
            return false;
        }

        Item item = stack.getItem();
        for(int i = 0; i < 4; i++)
            if(item.isValidArmor(stack, i, player)) {
                ItemStack armor = getEquipmentInSlot(4-i);
                if(armor != null && !this.worldObj.isRemote)
                    this.entityDropItem(armor, 1.0f);

                armor = stack.copy();
                armor.stackSize = 1;

                // send update
                if(!this.worldObj.isRemote)
                    TestDummyMod.proxy.network.sendToAllAround(new SyncEquipmentMessage(this.getEntityId(), 4-i, armor), new NetworkRegistry.TargetPoint(dimension, posX, posY, posZ, 20));

                // also do it locally, but it'll be overwritten by the update packet above anyway
                setCurrentItemOrArmor(4-i, armor);

                stack.stackSize--;

                return true;
            }

        return false;
    }

    public void dismantle()
    {
        if(!worldObj.isRemote) {
            dropEquipment(true, 999);
            dropItem(TestDummyMod.itemDummy, 1);
        }
        this.setDead();
    }


    @Override
    public boolean attackEntityFrom(DamageSource source, float damage) {
        // dismantling
        if(source.damageType.equals("player"))
        {
            EntityPlayer player = (EntityPlayer) source.getEntity();

            // shift-leftclick with empty hand dismantles
            if(player.isSneaking() && player.getCurrentEquippedItem() == null) {
                dismantle();
                return false;
            }
        }


        if ((float)this.hurtResistantTime > (float)this.maxHurtResistantTime / 2.0F)
        {
            if (damage <= this.lastDamage)
            {
                return false;
            }

            this.lastDamage = damage;
        }
        else
        {
            this.lastDamage = damage;
            this.hurtResistantTime = this.maxHurtResistantTime;
        }

        // calculate the ACTUAL damage done after armor n stuff
        if (!this.isEntityInvulnerable())
        {
            damage = ForgeHooks.onLivingHurt(this, source, damage);
            if (damage > 0) {
                damage = this.applyArmorCalculations(source, damage);
                damage = this.applyPotionDamageCalculations(source, damage);
                float f1 = damage;
                damage = Math.max(damage - this.getAbsorptionAmount(), 0.0F);
                this.setAbsorptionAmount(this.getAbsorptionAmount() - (f1 - damage));
            }
        }

        // damage in the same tick, add it
        if(lastDamageTick == this.ticksExisted)
        {
            lastDamage += damage;
            shake += damage;
            shake = Math.min(shake, 30);
        }
        else {
            // OUCH :(
            shake = Math.min(damage, 30);
            lastDamage = damage;
            lastDamageTick = this.ticksExisted;
        }

        // visual effect
        this.hurtTime = this.maxHurtTime = 10;

        if(!this.worldObj.isRemote) {
            if(myLittleNumber != null && !myLittleNumber.isDead)
                myLittleNumber.setDead();

            // damage numebrssss
            EntityFloatingNumber number = new EntityFloatingNumber(worldObj, damage, this.posX, this.posY+2, this.posZ);
            myLittleNumber = number;
            worldObj.spawnEntityInWorld(number);

            TestDummyMod.proxy.network.sendToAllAround(new DamageMessage(lastDamage, shake, this, myLittleNumber), new NetworkRegistry.TargetPoint(dimension, posX, posY, posZ, 20));

            this.damageTaken += damage;
            if(firstDamageTick == 0)
                firstDamageTick = this.ticksExisted;
        }

        return true;
    }

    @Override
    protected void updateAITasks() {

    }

    @Override
    public void onUpdate() {
        if(shake > 0) {
            shakeAnimation++;
            shake -= 0.8f;
            if (shake <= 0) {
                shakeAnimation = 0;
                shake = 0;
            }
        }

        if (this.hurtTime > 0)
            --this.hurtTime;
        if (this.hurtResistantTime > 0)
            --this.hurtResistantTime;

        // handle fire
        if (this.worldObj.isRemote)
            this.extinguish();

        // DPS!
        if(!this.worldObj.isRemote && this.damageTaken > 0 && this.ticksExisted - lastDamageTick > 30) {
            if(firstDamageTick < lastDamageTick) {
                // it's not actual DPS but "damage per tick scaled to seconds".. but meh.
                float seconds = (lastDamageTick - firstDamageTick) / 20f + 1;
                float dps = damageTaken / seconds;
                EntityFloatingNumber number = new EntityDpsFloatingNumber(worldObj, dps, this.posX, this.posY + 3, this.posZ);
                worldObj.spawnEntityInWorld(number);
            }

            this.damageTaken = 0;
            this.firstDamageTick = 0;
        }
    }

    @Override
    public void onEntityUpdate() {
        //super.onEntityUpdate();
    }

    @Override
    protected boolean isMovementBlocked() {
        return true;
    }

    @Override
    public void onLivingUpdate() {
        //super.onLivingUpdate();
    }



    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Override
    public boolean isEntityAlive() {
        return false;
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public void writeSpawnData(ByteBuf buffer) {
        buffer.writeFloat(customRotation);
    }

    @Override
    public void readSpawnData(ByteBuf additionalData) {
        customRotation = additionalData.readFloat();
        setCustomRotationStuff();
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);
        tag.setFloat("customRotation", customRotation);
        tag.setFloat("shake", shake);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);
        customRotation = tag.getFloat("customRotation");
        shake = tag.getFloat("shake");
    }
}
