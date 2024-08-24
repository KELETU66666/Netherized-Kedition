package mellohi138.netherized.world;

import mellohi138.netherized.init.NetherizedBlocks;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class NetherizedWorldGen implements IWorldGenerator {

	private WorldGenerator ANCIENT_DEBRIS_BIG, ANCIENT_DEBRIS_SMALL, NETHER_GOLDORE, BLACKSTONE_CLUSTER;

	public NetherizedWorldGen() {
		ANCIENT_DEBRIS_BIG = new WorldGenMinable(NetherizedBlocks.ANCIENT_DEBRIS.getDefaultState(), 3, BlockMatcher.forBlock(Blocks.NETHERRACK));
		ANCIENT_DEBRIS_SMALL = new WorldGenMinable(NetherizedBlocks.ANCIENT_DEBRIS.getDefaultState(), 2, BlockMatcher.forBlock(Blocks.NETHERRACK));
		NETHER_GOLDORE = new WorldGenMinable(NetherizedBlocks.NETHER_GOLD_ORE.getDefaultState(), 12, BlockMatcher.forBlock(Blocks.NETHERRACK));
		BLACKSTONE_CLUSTER = new WorldGenMinable(NetherizedBlocks.BLACKSTONE.getDefaultState(), 33, BlockMatcher.forBlock(Blocks.NETHERRACK));
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		if (!(world.provider instanceof net.minecraft.world.WorldProviderHell))
			return;

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
}