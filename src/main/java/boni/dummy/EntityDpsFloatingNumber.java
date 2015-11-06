package boni.dummy;

import net.minecraft.world.World;

public class EntityDpsFloatingNumber extends EntityFloatingNumber {

  public EntityDpsFloatingNumber(World world) {
    super(world);
  }

  public EntityDpsFloatingNumber(World world, float damage, double x, double y, double z) {
    super(world, damage, x, y, z);
  }

  @Override
  protected void entityInit() {
    age = 0;
    speed = 100;
  }

  @Override
  public void onEntityUpdate() {
    if(this.age++ > 150)
      this.setDead();

    this.posY += speed/500d;

    if(speed > 1)
      speed /= 2;
    else if(speed == 1)
      speed = 0;
  }
}
