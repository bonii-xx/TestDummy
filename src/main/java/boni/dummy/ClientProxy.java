package boni.dummy;


import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

    @Override
    public void preinit() {

        final String loc = TestDummyMod.MODID + ":Dummy";
        ModelBakery.addVariantName(TestDummyMod.itemDummy, loc);
        ModelLoader.setCustomModelResourceLocation(TestDummyMod.itemDummy, 0, new ModelResourceLocation(loc, "inventory"));
    }

    @Override
    public void init() {
        super.init(); // init network

        RenderingRegistry.registerEntityRenderingHandler(EntityDummy.class, new RenderDummy(Minecraft.getMinecraft().getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler(EntityFloatingNumber.class, new RenderFloatingNumber(Minecraft.getMinecraft().getRenderManager()));
    }
}
