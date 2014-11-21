package boni.dummy;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class ModelDummy extends ModelBiped {
    public ModelRenderer standPlate;

    public ModelDummy(float size, float p_i1149_2_)
    {
        this(size, p_i1149_2_, 64, 64);
    }

    public ModelDummy(float size, float p_i1149_2_, int xw, int yw)
    {
        super(size, p_i1149_2_, xw, yw);

        this.bipedRightArm = new ModelRenderer(this, 40, 16);
        this.bipedRightArm.addBox(-3.01F, 1.0F, -2.01F, 4, 8, 4, size);
        this.bipedRightArm.setRotationPoint(-2.5F, 2.0F + p_i1149_2_, 0.0F);
        this.bipedLeftArm = new ModelRenderer(this, 40, 16);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-1.01F, 1.0F, -2.01F, 4, 8, 4, size);
        this.bipedLeftArm.setRotationPoint(2.5F, 2.0F + p_i1149_2_, 0.0F);

        // left leg == stand
        this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
        this.bipedLeftLeg.addBox(-2.0F, -12.0F, -2.0F, 4, 12, 4, size);
        this.bipedLeftLeg.setRotationPoint(0F, 24.0F + p_i1149_2_, 0.0F);

        this.bipedRightLeg = new ModelRenderer(this, 0,0);

        this.standPlate = new ModelRenderer(this, 0, 32);
        this.standPlate.addBox(-8.0F, 11.5F, -8.0F, 16, 1, 16, size);
        this.standPlate.setRotationPoint(0F, 12F + p_i1149_2_, 0.0F);

        this.bipedBody = new ModelRenderer(this, 16, 16);
        this.bipedBody.addBox(-4.0F, -24.0F, -2.0F, 8, 12, 4, size);
        this.bipedBody.setRotationPoint(0.0F, 24.0F + p_i1149_2_, 0.0F);


        //this.bipedLeftLeg.addChild(this.bipedBody);
        //this.bipedBody.addChild(this.bipedHead);
        //this.bipedBody.addChild(this.bipedLeftArm);
        //this.bipedBody.addChild(this.bipedRightArm);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_)
    {
        this.setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);

        if (this.isChild)
        {
            float f6 = 2.0F;
            GL11.glPushMatrix();
            GL11.glScalef(1.5F / f6, 1.5F / f6, 1.5F / f6);
            GL11.glTranslatef(0.0F, 16.0F * p_78088_7_, 0.0F);
            this.bipedHead.render(p_78088_7_);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(1.0F / f6, 1.0F / f6, 1.0F / f6);
            GL11.glTranslatef(0.0F, 24.0F * p_78088_7_, 0.0F);
            this.bipedBody.render(p_78088_7_);
            this.bipedRightArm.render(p_78088_7_);
            this.bipedLeftArm.render(p_78088_7_);

            this.standPlate.render(p_78088_7_);
            this.bipedLeftLeg.render(p_78088_7_);
            GL11.glPopMatrix();
        }
        else
        {
            this.bipedHead.render(p_78088_7_);
            this.bipedBody.render(p_78088_7_);
            this.bipedRightArm.render(p_78088_7_);
            this.bipedLeftArm.render(p_78088_7_);
            this.standPlate.render(p_78088_7_);
            this.bipedLeftLeg.render(p_78088_7_);
        }
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_)
    {
        //this.bipedBody.rotateAngleY = ((EntityDummy)p_78087_7_).customRotation;

        this.bipedHead.rotateAngleY = p_78087_4_ / (180F / (float)Math.PI);
        this.bipedHead.rotateAngleX = p_78087_5_ / (180F / (float)Math.PI);
        this.bipedHeadwear.rotateAngleY = this.bipedHead.rotateAngleY;
        this.bipedHeadwear.rotateAngleX = this.bipedHead.rotateAngleX;
        //this.bipedRightArm.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F + (float) Math.PI) * 2.0F * p_78087_2_ * 0.5F;
        //this.bipedLeftArm.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F) * 2.0F * p_78087_2_ * 0.5F;
        this.bipedRightArm.rotateAngleZ = 0.0F;
        this.bipedLeftArm.rotateAngleZ = 0.0F;
        //this.bipedRightLeg.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F) * 1.4F * p_78087_2_;
        //this.bipedLeftLeg.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F + (float)Math.PI) * 1.4F * p_78087_2_;
        //this.bipedRightLeg.rotateAngleY = 0.0F;
        //this.bipedLeftLeg.rotateAngleY = 0.0F;

        this.bipedLeftArm.rotateAngleX = 0;
        this.bipedRightArm.rotateAngleX = 0;

        if (this.heldItemLeft != 0)
        {
            //this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5F - ((float)Math.PI / 10F) * (float)this.heldItemLeft;
        }

        if (this.heldItemRight != 0)
        {
            //this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5F - ((float)Math.PI / 10F) * (float)this.heldItemRight;
        }

        this.bipedRightArm.rotateAngleY = 0.0F;
        this.bipedLeftArm.rotateAngleY = 0.0F;
        float f6;
        float f7;

        /*
        if (this.onGround > -9990.0F)
        {
            f6 = this.onGround;
            this.bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt_float(f6) * (float)Math.PI * 2.0F) * 0.2F;
            this.bipedRightArm.rotationPointZ = MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F;
            this.bipedRightArm.rotationPointX = -MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F;
            this.bipedLeftArm.rotationPointZ = -MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F;
            this.bipedLeftArm.rotationPointX = MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F;
            this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY;
            this.bipedLeftArm.rotateAngleY += this.bipedBody.rotateAngleY;
            this.bipedLeftArm.rotateAngleX += this.bipedBody.rotateAngleY;
            f6 = 1.0F - this.onGround;
            f6 *= f6;
            f6 *= f6;
            f6 = 1.0F - f6;
            f7 = MathHelper.sin(f6 * (float)Math.PI);
            float f8 = MathHelper.sin(this.onGround * (float)Math.PI) * -(this.bipedHead.rotateAngleX - 0.7F) * 0.75F;
            this.bipedRightArm.rotateAngleX = (float)((double)this.bipedRightArm.rotateAngleX - ((double)f7 * 1.2D + (double)f8));
            this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY * 2.0F;
            this.bipedRightArm.rotateAngleZ = MathHelper.sin(this.onGround * (float)Math.PI) * -0.4F;
        }
*/
        this.bipedBody.rotateAngleX = 0.0F;
        //this.bipedRightLeg.rotationPointZ = 0.1F;
        //this.bipedLeftLeg.rotationPointZ = 0.1F;
        //this.bipedRightLeg.rotationPointY = 12.0F;
        //this.bipedLeftLeg.rotationPointY = 12.0F;
        this.bipedHead.rotationPointY = 0.0F;
        this.bipedHeadwear.rotationPointY = 0.0F;
/*
        this.bipedRightArm.rotateAngleZ += MathHelper.cos(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
        this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
        this.bipedRightArm.rotateAngleX += MathHelper.sin(p_78087_3_ * 0.067F) * 0.05F;
        this.bipedLeftArm.rotateAngleX -= MathHelper.sin(p_78087_3_ * 0.067F) * 0.05F;
        */

        //this.bipedLeftLeg.rotateAngleY = (float)-Math.PI/2f;

        this.bipedRightArm.rotateAngleZ = (float)Math.PI/2f;
        this.bipedLeftArm.rotateAngleZ = -(float)Math.PI/2f;

        float phase = ((EntityDummy)p_78087_7_).shakeAnimation;
        float shake = ((EntityDummy)p_78087_7_).shake;
        float r = 0, r2 = 0;
        if(shake > 0) {
            r = (float) -(MathHelper.sin(phase) * Math.PI / 100f * shake);
            r2 = (float) (MathHelper.cos(phase) * Math.PI / 20f);
        }

        this.bipedLeftLeg.rotateAngleX = r / 2; // z instead of x because it's rotated 90°
        this.bipedBody.rotateAngleX = r / 2;
        this.bipedLeftArm.rotateAngleX = r * 1.1f;
        this.bipedRightArm.rotateAngleX = r * 1.1f;
        this.bipedHead.rotateAngleX = -r;
        this.bipedHead.rotateAngleZ = r2;
    }
}
