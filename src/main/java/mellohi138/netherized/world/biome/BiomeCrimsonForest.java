package mellohi138.netherized.world.biome;

import mellohi138.netherized.init.NetherizedBlocks;
import mellohi138.netherized.objects.entity.neutral.EntityHoglin;
import mellohi138.netherized.objects.entity.neutral.EntityPigman;
import mellohi138.netherized.objects.entity.passive.EntityStrider;
import mellohi138.netherized.world.biome.decorator.DecoratorCrimsonForest;
import mellohi138.netherized.world.gen.chunk.ChunkGeneratorNetherized;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import javax.annotation.Nonnull;

public class BiomeCrimsonForest extends BiomeBase implements INetherizedBiomes {
	public BiomeCrimsonForest(String nameIn, BiomeProperties properties) {
		super(nameIn, properties);

		this.topBlock = NetherizedBlocks.CRIMSON_NYLIUM.getDefaultState();
		this.fillerBlock = Blocks.NETHERRACK.getDefaultState();

		this.spawnableMonsterList.add(new SpawnListEntry(EntityPigman.class, 20, 2, 4));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityPigZombie.class, 4, 4, 4));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityStrider.class, 20, 1, 2));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityHoglin.class, 40, 3, 4));
	}
	
    @Override
    public BiomeDecorator createBiomeDecorator() {
    	return new DecoratorCrimsonForest();
    }

	@Override
	public void setTypes() {
		BiomeDictionary.addTypes(this, Type.NETHER);
	}

	@Override
	public int getBiomeColor(int paramInt1, int paramInt2, int paramInt3) {
		return 11339784;
	}

	@Nonnull
	@Override
	public int getSkyColorByTemp(float currentTemperature) {
		return 11339784;
	}

	@Override
	public void buildSurface(@Nonnull ChunkGeneratorNetherized chunkGenerator, int chunkX, int chunkZ, @Nonnull ChunkPrimer primer, int x, int z, double[] soulSandNoise, double[] gravelNoise, double[] depthBuffer) {
		int currDepth = -1;
		for(int y = chunkGenerator.getWorld().getActualHeight() - 1; y >= 0; --y) {
			final IBlockState here = primer.getBlockState(x, y, z);
			if(here.getMaterial() == Material.AIR) currDepth = -1;
			else if(here.getBlock() == Blocks.NETHERRACK) {
				if(currDepth == -1) {
					currDepth = 40 + chunkGenerator.getWorld().rand.nextInt(2);
					primer.setBlockState(x, y, z, topBlock);
				}
				else if(currDepth > 0) {
					--currDepth;
					fillerBlock = Blocks.NETHERRACK.getDefaultState();
					primer.setBlockState(x, y, z, fillerBlock);
				}
			}
		}
	}
}
