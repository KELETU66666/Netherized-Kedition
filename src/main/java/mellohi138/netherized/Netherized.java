package mellohi138.netherized;

import mellohi138.netherized.init.NetherizedBlocks;
import mellohi138.netherized.init.NetherizedItems;
import mellohi138.netherized.util.NetherizedCreativeTabs;
import mellohi138.netherized.util.RegistryHandler;
import mellohi138.netherized.util.interfaces.IProxy;
import mellohi138.netherized.world.StructureHandler;
import mellohi138.netherized.world.gen.chunk.WorldProviderNetherized;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Logger;

@Mod(modid = Netherized.MODID, name = Netherized.MODNAME, version = Netherized.VERSION)
public class Netherized {
    @Instance
    public static Netherized INSTANCE;

    @SidedProxy(clientSide = Netherized.CLIENT_PROXY, serverSide = Netherized.SERVER_PROXY)
    public static IProxy PROXY;

    public static Logger LOGGER;

    public static final String MODID = "netherized";
    public static final String MODNAME = "Netherized";
    public static final String VERSION = "0.0.6-A";

    public static final String CLIENT_PROXY = "mellohi138.netherized.proxy.ClientProxy";
    public static final String SERVER_PROXY = "mellohi138.netherized.proxy.CommonProxy";

    public static final CreativeTabs NETHERIZED_ITEMS = new NetherizedCreativeTabs(0);
    public static final CreativeTabs NETHERIZED_BLOCKS = new NetherizedCreativeTabs(1);

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER = event.getModLog();
        RegistryHandler.registerWorldGenerators();
        RegistryHandler.registerBlockSounds();
        RegistryHandler.registerMobUtils();
        registerNetherOverride();
        PROXY.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        RegistryHandler.registerOreDict();

        GameRegistry.addSmelting(NetherizedBlocks.ANCIENT_DEBRIS, new ItemStack(NetherizedItems.NETHERITE_SCRAP, 1), 2.0F);
        StructureHandler.handleStructureRegistries();

        PROXY.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        PROXY.postInit();
    }

    public static <MSG extends IMessage> void sendMSGToAll(MSG message) {

        //  for(EntityPlayerMP playerMP : Minecraft.getMinecraft().) {
        //  sendNonLocal(message, playerMP);
        //  }
        //network.sendToAll(message);
    }

    public static void registerNetherOverride()
    {
        DimensionManager.unregisterDimension(-1);
        DimensionManager.registerDimension(-1, DimensionType.register("Nether", "_nether", -1, WorldProviderNetherized.class, false));
    }
}
