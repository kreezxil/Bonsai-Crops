package com.kreezcraft.bonsaicrops;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import com.kreezcraft.bonsaicrops.command.CommandBonsaiTrees;
import com.kreezcraft.bonsaicrops.integration.IntegrationRegistry;
import com.kreezcraft.bonsaicrops.misc.ConfigurationHandler;
import com.kreezcraft.bonsaicrops.misc.RenderTickCounter;
import com.kreezcraft.bonsaicrops.network.PackageHandler;
import com.kreezcraft.bonsaicrops.proxy.CommonProxy;
import com.kreezcraft.bonsaicrops.render.PotColorizer;
import com.kreezcraft.bonsaicrops.soils.BonsaiSoilRegistry;
import com.kreezcraft.bonsaicrops.soils.SoilCompatibility;
import com.kreezcraft.bonsaicrops.trees.TreeEvents;
import com.kreezcraft.bonsaicrops.trees.TreeShapeRegistry;
import com.kreezcraft.bonsaicrops.trees.TreeTypeRegistry;
import com.kreezcraft.bonsaicrops.utility.Logz;


@Mod(
        modid = BonsaiTrees.MODID,
        version = BonsaiTrees.VERSION,
        guiFactory = BonsaiTrees.GUI_FACTORY,
        acceptedMinecraftVersions = "[1.12,1.13)"
)
public class BonsaiTrees {
    public static final String MODID = "bonsaitrees";
    public static final String VERSION = "1.1.2";
    public static final String GUI_FACTORY = "com.kreezcraft.bonsaicrops.misc.ConfigGuiFactory";

    @Mod.Instance(BonsaiTrees.MODID)
    public static BonsaiTrees instance;

    @SidedProxy(clientSide = "com.kreezcraft.bonsaicrops.proxy.ClientProxy", serverSide = "com.kreezcraft.bonsaicrops.proxy.ServerProxy")
    public static CommonProxy proxy;

    public BonsaiSoilRegistry soilRegistry;
    public TreeTypeRegistry typeRegistry;
    public SoilCompatibility soilCompatibility;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ConfigurationHandler.init(event.getSuggestedConfigurationFile());

        IntegrationRegistry.loadBonsaiIntegrations(event.getAsmData());

        MinecraftForge.EVENT_BUS.register(ConfigurationHandler.class);
        MinecraftForge.EVENT_BUS.register(RenderTickCounter.class);
        MinecraftForge.EVENT_BUS.register(TreeEvents.class);
        MinecraftForge.EVENT_BUS.register(PotColorizer.class);

        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        PackageHandler.init();

        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        soilRegistry = new BonsaiSoilRegistry();
        typeRegistry = new TreeTypeRegistry();
        soilCompatibility = new SoilCompatibility();

        IntegrationRegistry.registerTreeIntegrations();
        Logz.info("Registered %d tree types", typeRegistry.getAllTypes().size());

        IntegrationRegistry.registerSoilIntegrations();
        Logz.info("Registered %d soil types", soilRegistry.getAllSoils().size());

        soilCompatibility.updateCompatibility(soilRegistry, typeRegistry);

        TreeShapeRegistry.init();
        typeRegistry.checkMissingShapes();

        proxy.postInit(event);
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandBonsaiTrees());
    }
}
