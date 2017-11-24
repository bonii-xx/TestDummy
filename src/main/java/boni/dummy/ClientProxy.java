package boni.dummy;

import boni.dummy.client.RenderDummy;
import boni.dummy.client.RenderFloatingNumber;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientProxy extends CommonProxy {

  @Override
  public void preinit() {
	  MinecraftForge.EVENT_BUS.register(this);

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
  
  @SubscribeEvent
  public void models(ModelRegistryEvent e) {
	    final String loc = TestDummyMod.MODID + ":dummy";
	    ModelLoader.setCustomModelResourceLocation(TestDummyMod.itemDummy, 0, new ModelResourceLocation(loc, "inventory"));
  }
}
