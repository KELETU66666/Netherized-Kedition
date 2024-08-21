package mellohi138.netherized.init;

import mellohi138.netherized.Netherized;
import mellohi138.netherized.world.biome.BiomeBase;
import mellohi138.netherized.world.biome.BiomeCrimsonForest;
import mellohi138.netherized.world.biome.BiomeWarpedForest;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Netherized.MODID)
public class NetherizedBiomes {
	public static final List<BiomeBase> BIOME_LIST = new ArrayList<BiomeBase>();

	public static BiomeBase CRIMSON_FOREST = registerBiome(new BiomeCrimsonForest("crimson_forest", new BiomeProperties("Crimson Forest").setTemperature(2.0F).setRainfall(0.0F).setRainDisabled().setWaterColor(4159204)));
	public static BiomeBase WARPED_FOREST = registerBiome(new BiomeWarpedForest("warped_forest", new BiomeProperties("Warped Forest").setTemperature(2.0F).setRainfall(0.0F).setRainDisabled().setWaterColor(4159204)));
	
	private static BiomeBase registerBiome(BiomeBase biomeIn, Type... types) {
		BIOME_LIST.add(biomeIn);
		return biomeIn;
	}

	@SubscribeEvent
	public static void registerBiomes(RegistryEvent.Register<Biome> event) {
		for(BiomeBase biomeIn : BIOME_LIST) {
			event.getRegistry().register(biomeIn);
			biomeIn.setTypes();
			BiomeManager.addSpawnBiome(biomeIn);
		}
	}
}
