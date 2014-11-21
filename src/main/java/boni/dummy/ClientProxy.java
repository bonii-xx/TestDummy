package boni.dummy;


import boni.dummy.network.DamageMessage;
import boni.dummy.network.SyncEquipmentMessage;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy extends CommonProxy {
    @Override
    public void init() {
        super.init(); // init network

        RenderingRegistry.registerEntityRenderingHandler(EntityDummy.class, new RenderDummy());
        RenderingRegistry.registerEntityRenderingHandler(EntityFloatingNumber.class, new RenderFloatingNumber());
    }
}
