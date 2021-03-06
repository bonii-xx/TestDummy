package boni.dummy.client;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;

public class LayerDummyArmor extends LayerBipedArmor {

  public LayerDummyArmor(RenderLivingBase<?> rendererIn) {
    super(rendererIn);
  }

  @Override
  protected void initArmor() {
    this.modelLeggings = new ModelDummy(0.5F, 0, 64, 32);
    this.modelArmor = new ModelDummy(1.0F, 0, 64, 32);

    ((ModelDummy)modelArmor).standPlate.showModel = false;
    ((ModelDummy)modelLeggings).standPlate.showModel = false;
  }

  @Override
  protected void setModelVisible(ModelBiped model) {
    super.setModelVisible(model);
    // right leg is always invisible
    model.bipedRightLeg.showModel = false;
  }
}
