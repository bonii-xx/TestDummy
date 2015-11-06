package boni.dummy;

import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

@Mod(modid = TestDummyMod.MODID, version = TestDummyMod.VERSION, name = "MmmMmmMmmMmm")
public class TestDummyMod
{
    public static final String MODID = "testdummy";
    public static final String VERSION = "1.0";

    @Mod.Instance
    public static TestDummyMod instance;

    @SidedProxy(clientSide = "boni.dummy.ClientProxy", serverSide = "boni.dummy.CommonProxy")
    public static CommonProxy proxy;

    public static Item itemDummy = new ItemDummy();

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        EntityRegistry.registerModEntity(EntityDummy.class, "Dummy", 0, TestDummyMod.instance, 32, 10, false);
        EntityRegistry.registerModEntity(EntityFloatingNumber.class, "FloatingNumber", 1, TestDummyMod.instance, 32, 1, false);
        EntityRegistry.registerModEntity(EntityDpsFloatingNumber.class, "FloatingNumberDPS", 2, TestDummyMod.instance, 32, 1, false);

        GameRegistry.registerItem(itemDummy, "Dummy");

        GameRegistry.addRecipe(new ShapedOreRecipe(itemDummy,
                " B ",
                "HWH",
                " P ",
                'B', Item.getItemFromBlock(Blocks.hay_block),
                'H', Items.wheat,
                'W', new ItemStack(Blocks.wool, 0, OreDictionary.WILDCARD_VALUE),
                'P', "plankWood"));

        proxy.init();
    }
}
