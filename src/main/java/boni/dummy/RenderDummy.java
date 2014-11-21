package boni.dummy;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderDummy extends RenderBiped {
    private static final ResourceLocation texture = new ResourceLocation("testdummy" ,"textures/entity/dummy.png");
    public static final ModelDummy model = new ModelDummy(0.0f, 0.0F);

    private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");

    public RenderDummy() {
        super(model, 0.125f);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityLiving p_110775_1_) {
        return texture;
    }

    @Override
    protected void func_82421_b()
    {
        this.field_82423_g = new ModelDummy(1.0F, 0f, 64, 32);
        this.field_82425_h = new ModelDummy(0.5F, 0f, 64, 32);

        ((ModelDummy)this.field_82423_g).standPlate.showModel = false;
        ((ModelDummy)this.field_82425_h).standPlate.showModel = false;
    }
}
