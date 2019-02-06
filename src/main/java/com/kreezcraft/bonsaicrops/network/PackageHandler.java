package com.kreezcraft.bonsaicrops.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import com.kreezcraft.bonsaicrops.BonsaiTrees;

public class PackageHandler {
    public static final SimpleNetworkWrapper instance = NetworkRegistry.INSTANCE.newSimpleChannel(BonsaiTrees.MODID);

    public static void init() {
        instance.registerMessage(MessageReloadTreesHandler.class, MessageReloadTrees.class, 1, Side.CLIENT);
    }
}
