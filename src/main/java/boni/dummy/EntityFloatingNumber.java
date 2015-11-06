package boni.dummy;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityFloatingNumber extends Entity implements IEntityAdditionalSpawnData {
    protected int age;
    public float damage;
    protected int speed;

    public EntityFloatingNumber(World world) {
        super(world);
    }

    public EntityFloatingNumber(World world, float damage, double x, double y, double z) {
        super(world);

        this.damage = damage;
        this.lastTickPosX = this.posX = x;
        this.lastTickPosY = this.posY = y;
        this.lastTickPosZ = this.posZ = z;
    }

    @Override
    protected void entityInit() {
        age = 0;
        speed = 500;
    }

    @Override
    public void onEntityUpdate() {
        if(this.age++ > 50)
            this.setDead();

        this.posY += speed/500d;

        if(speed > 1)
            speed /= 2;
        else if(speed == 1)
            speed = 0;
    }

    @Override
    public void moveEntity(double p_70091_1_, double p_70091_3_, double p_70091_5_) {

    }

    public void reSet(float damage) {
        this.damage = damage;
        this.age = 0;
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {

    }

    @Override
    public void writeSpawnData(ByteBuf buffer) {
        buffer.writeFloat(this.damage);
    }

    @Override
    public void readSpawnData(ByteBuf additionalData) {
        this.damage = additionalData.readFloat();
    }
}
