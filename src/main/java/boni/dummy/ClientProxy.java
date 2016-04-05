package boni.dummy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import boni.dummy.client.RenderDummy;
import boni.dummy.client.RenderFloatingNumber;

public class ClientProxy extends CommonProxy {

  @Override
  public void preinit() {

    final String loc = TestDummyMod.MODID + ":dummy";
    ModelLoader.setCustomModelResourceLocation(TestDummyMod.itemDummy, 0, new ModelResourceLocation(loc, "inventory"));

    RenderingRegistry.registerEntityRenderingHandler(EntityDummy.class, new IRenderFactory<EntityDummy>() {
      @Override
      public Render<? super EntityDummy> createRenderFor(RenderManager manager) {
        return new RenderDummy(manager);
      }
    });
    RenderingRegistry.registerEntityRenderingHandler(EntityFloatingNumber.class, new IRenderFactory<EntityFloatingNumber>() {
      @Override
      public Render<? super EntityFloatingNumber> createRenderFor(RenderManager manager) {
        return new RenderFloatingNumber(manager);
      }
    });
  }
}
