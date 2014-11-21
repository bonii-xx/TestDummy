package boni.dummy;

import boni.dummy.network.DamageMessage;
import boni.dummy.network.SyncEquipmentMessage;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class CommonProxy {
    public SimpleNetworkWrapper network;

    public void init() {
        network = NetworkRegistry.INSTANCE.newSimpleChannel("TestDummy");

        this.network.registerMessage(DamageMessage.MessageHandlerClient.class, DamageMessage.class, 0, Side.CLIENT);
        this.network.registerMessage(SyncEquipmentMessage.MessageHandlerClient.class, SyncEquipmentMessage.class, 1, Side.CLIENT);
    }
}
