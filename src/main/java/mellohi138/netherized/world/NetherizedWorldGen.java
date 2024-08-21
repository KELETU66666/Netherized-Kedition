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

	private WorldGenerator ANCIENT_DEBRIS, ANCIENT_DEBRIS2;

	public NetherizedWorldGen() {
		ANCIENT_DEBRIS = new WorldGenMinable(NetherizedBlocks.ANCIENT_DEBRIS.getDefaultState(), 4, BlockMatcher.forBlock(Blocks.NETHERRACK));
		ANCIENT_DEBRIS2 = new WorldGenMinable(NetherizedBlocks.ANCIENT_DEBRIS.getDefaultState(), 2, BlockMatcher.forBlock(Blocks.NETHERRACK));
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		switch (world.provider.getDimension()) {
			case -1: //Nether
				runGenerator(ANCIENT_DEBRIS, world, random, chunkX, chunkZ, 1, 8, 22);
				runGenerator(ANCIENT_DEBRIS2, world, random, chunkX, chunkZ, 1, 8, 119);
				break;
			case 0: //Overworld
				break;
			case 1: //End
				break;
		}
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