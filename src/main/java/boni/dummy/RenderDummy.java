package boni.dummy;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderDummy extends RenderBiped {
    private static final ResourceLocation texture = new ResourceLocation("testdummy" ,"textures/entity/dummy.png");
    public static final ModelDummy model = new ModelDummy(0.0f, 0.0F);

    private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");

    public RenderDummy(RenderManager renderManager) {
        super(renderManager, model, 0.125f);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityLiving p_110775_1_) {
        return texture;
    }

    //@Override
    protected void func_82421_b()
    {
        //this.field_82423_g = new ModelDummy(1.0F, 0f, 64, 32);
        //this.field_82425_h = new ModelDummy(0.5F, 0f, 64, 32);

        //((ModelDummy)this.field_82423_g).standPlate.showModel = false;
        //((ModelDummy)this.field_82425_h).standPlate.showModel = false;
    }
}
