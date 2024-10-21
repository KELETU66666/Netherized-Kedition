package mellohi138.netherized.world;

import com.google.common.collect.Lists;
import mellohi138.netherized.Netherized;
import mellohi138.netherized.init.NetherizedBlocks;
import mellohi138.netherized.util.config.NetherizedGeneralConfig;
import mellohi138.netherized.world.gen.bastion.WorldGenBastion;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.List;
import java.util.Random;

public class NetherizedOreWorldGen implements IWorldGenerator {

	private static List<Biome> spawnBiomesRemnants;
	private static final WorldGenBastion bastion = new WorldGenBastion();

	private WorldGenerator ANCIENT_DEBRIS_BIG, ANCIENT_DEBRIS_SMALL, NETHER_GOLDORE, BLACKSTONE_CLUSTER;

	public NetherizedOreWorldGen() {
		ANCIENT_DEBRIS_BIG = new WorldGenMinable(NetherizedBlocks.ANCIENT_DEBRIS.getDefaultState(), 3, BlockMatcher.forBlock(Blocks.NETHERRACK));
		ANCIENT_DEBRIS_SMALL = new WorldGenMinable(NetherizedBlocks.ANCIENT_DEBRIS.getDefaultState(), 2, BlockMatcher.forBlock(Blocks.NETHERRACK));
		NETHER_GOLDORE = new WorldGenMinable(NetherizedBlocks.NETHER_GOLD_ORE.getDefaultState(), 12, BlockMatcher.forBlock(Blocks.NETHERRACK));
		BLACKSTONE_CLUSTER = new WorldGenMinable(NetherizedBlocks.BLACKSTONE.getDefaultState(), 33, BlockMatcher.forBlock(Blocks.NETHERRACK));
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		if (!(world.provider instanceof net.minecraft.world.WorldProviderHell))
			return;

		int x = chunkX * 16;
		int z = chunkZ * 16;
		BlockPos pos = new BlockPos(x + 8, 0, z + 8);

		if(world.provider.getDimension() == -1) {
			//This is the connect between the bastion spawn rules and a signal to tell it to try it here
			//WorldGenBastion handles the actual spawn rules

			if (world.provider.getBiomeForCoords(pos) != getSpawnBiomesRemnarts().iterator()) {
				//Bastion Remnants
				if (NetherizedGeneralConfig.bastion_enabled) {
					bastion.generate(world, random, pos);
				}
			}
		}

		runGenerator(ANCIENT_DEBRIS_BIG, world, random, chunkX, chunkZ, 2, 8, 23);
		runGenerator(ANCIENT_DEBRIS_SMALL, world, random, chunkX, chunkZ, 3, 8, 118);
		runGenerator(NETHER_GOLDORE, world, random, chunkX, chunkZ, 10, 1, 116);
		runGenerator(BLACKSTONE_CLUSTER, world, random, chunkX, chunkZ, 2, 5, 27);

	}

	private void runGenerator(WorldGenerator gen, World world, Random rand, int chunkX, int chunkZ, int chancesToSpawn, int minHeight, int maxHeight) {
		if(minHeight > maxHeight || minHeight < 0 || maxHeight > 256) throw new IllegalArgumentException("Illegal Height Arguments for WorldGenerator");

		int heightDiff = maxHeight - minHeight + 1;
		for(int i = 0; i < chancesToSpawn; i++) {
			int x = chunkX * 16 + rand.nextInt(16);
			int y = minHeight + rand.nextInt(heightDiff);
			int z = chunkZ * 16 + rand.nextInt(16);

			gen.generate(world, rand, new BlockPos(x, y, z));
		}
	}

	/**
	 * Credit goes to SmileyCorps for Biomes read from a config
	 * @return
	 */
	public static List<Biome> getSpawnBiomesRemnarts() {
		if (spawnBiomesRemnants == null) {
			spawnBiomesRemnants = Lists.newArrayList();
			for (String str : NetherizedGeneralConfig.remnantsBiomesNotAllowed) {
				try {
					Biome biome = Biome.REGISTRY.getObject(new ResourceLocation(str));
					if (biome != null) spawnBiomesRemnants.add(biome);
					else Netherized.LOGGER.error("Biome " + str + " is not registered", new NullPointerException());
				} catch (Exception e) {
					Netherized.LOGGER.error(str + " is not a valid registry name", e);
				}
			}
		}
		return spawnBiomesRemnants;
	}
}