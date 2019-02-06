package com.kreezcraft.bonsaicrops.proxy;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import com.kreezcraft.bonsaicrops.init.Blockss;
import com.kreezcraft.bonsaicrops.init.Itemss;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        Blockss.initModels();
        Itemss.initModels();
    }
}
