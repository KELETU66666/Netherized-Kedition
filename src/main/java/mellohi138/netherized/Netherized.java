package mellohi138.netherized;

import org.apache.logging.log4j.Logger;

import mellohi138.netherized.util.NetherizedCreativeTabs;
import mellohi138.netherized.util.RegistryHandler;
import mellohi138.netherized.util.interfaces.IProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Netherized.MODID, name = Netherized.MODNAME, version = Netherized.VERSION)
public class Netherized {
	@Instance
	public static Netherized INSTANCE;
	
	@SidedProxy(clientSide = Netherized.CLIENT_PROXY, serverSide = Netherized.SERVER_PROXY)
	public static IProxy PROXY;
	
    public static Logger LOGGER;
	
    public static final String MODID = "netherized";
    public static final String MODNAME = "Netherized";
    public static final String VERSION = "0.0.5-A";
    
    public static final String CLIENT_PROXY = "mellohi138.netherized.proxy.ClientProxy";
    public static final String SERVER_PROXY = "mellohi138.netherized.proxy.CommonProxy";
	
	public static final CreativeTabs NETHERIZED_ITEMS = new NetherizedCreativeTabs(0);
	public static final CreativeTabs NETHERIZED_BLOCKS = new NetherizedCreativeTabs(1);
	    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	LOGGER = event.getModLog();
    	RegistryHandler.registerModEvents();
    	
    	PROXY.preInit();
    } 
    
	@EventHandler
    public void init(FMLInitializationEvent event) {
    	RegistryHandler.registerOreDict();
    	RegistryHandler.registerBlockSounds();
    	RegistryHandler.registerMobUtils();
    	
		PROXY.init();
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	PROXY.postInit();
    }
}
