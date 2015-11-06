package boni.dummy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.text.DecimalFormat;

public class RenderFloatingNumber extends Render {
    private static FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
    private static DecimalFormat df = new DecimalFormat("#.##");

    @Override
    public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
        doRender((EntityFloatingNumber)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_);
    }

    public void doRender(EntityFloatingNumber entity, double x, double y, double z)
    {
        boolean dps = entity instanceof EntityDpsFloatingNumber;

        GL11.glPushMatrix();
        GL11.glTranslated(x,y,z);

        // make scale dependant on distance
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        double xd = player.posX - entity.posX;
        double yd = player.posY - entity.posY;
        double zd = player.posZ - entity.posZ;

        double l = Math.sqrt(xd*xd + yd*yd + zd*zd);
        double scale = 0.01*l;

        if(dps) scale += 0.03f;

        GL11.glScaled(-scale, -scale, scale);
        // also move it up a bit depending on distance
        GL11.glTranslated(0, -l/10d, 0);

        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDepthMask(false);

        // rotate it towards the player
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GL11.glRotatef(this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);

        // draw it
        String s = df.format(entity.damage/2f);
        if(dps) s = "DPS: " + s;
        GL11.glTranslated(-fontRenderer.getStringWidth(s)/2,0,0);
        fontRenderer.drawString(s, 0, 0, 0xffffffff, true);
        GL11.glTranslated(fontRenderer.getStringWidth(s)/2,0,0);

        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return null;
    }
}
