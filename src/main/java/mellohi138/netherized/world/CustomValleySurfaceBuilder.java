package mellohi138.netherized.world;

import git.jbredwards.nether_api.mod.common.world.gen.ChunkGeneratorNether;
import mellohi138.netherized.init.NetherizedBiomes;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.ChunkGeneratorHell;
import net.minecraft.world.gen.NoiseGeneratorOctaves;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public abstract class CustomValleySurfaceBuilder extends ChunkGeneratorHell {
    private long seed;
    private Map<IBlockState, NoiseGeneratorOctaves> blockNoiseMap = new HashMap<>();
    private Map<IBlockState, NoiseGeneratorOctaves> fluidNoiseMap = new HashMap<>();
    private NoiseGeneratorOctaves valleyNoise;
    private World world;

    public CustomValleySurfaceBuilder(World world, boolean generateStructures, long seed) {
        super(world, generateStructures, seed);
        this.seed = seed;
        this.valleyNoise = new NoiseGeneratorOctaves(new Random(seed), 4);
    }

    @Override
    public void buildSurfaces(int x, int z, ChunkPrimer primer) {
        int k = x * 16;
        int l = z * 16;
        Biome biome = this.world.getBiome(new BlockPos(k + 16, 0, l + 16));

        int seaLevel = this.world.getSeaLevel();
        Random random = new Random(this.seed);
        double noiseScale = 0.03125D;

        double[] noiseValues = new double[1];

        noiseValues = this.valleyNoise.generateNoiseOctaves(noiseValues, x, z, 1, 1, noiseScale, noiseScale, 1.0D);

        double noise = noiseValues[0] * 75.0D + random.nextDouble();
        boolean flag = noise > 0.0D;

        IBlockState topBlock = this.getMaxNoiseBlockState(x, seaLevel, z, blockNoiseMap);
        IBlockState fillBlock = this.getMaxNoiseBlockState(x, seaLevel, z, fluidNoiseMap);

        BlockPos.MutableBlockPos blockpos = new BlockPos.MutableBlockPos();

        for (int y = 127; y >= 0; --y) {
            blockpos.setPos(x & 15, y, z & 15);
            IBlockState blockState = primer.getBlockState(blockpos.getX(), blockpos.getY(), blockpos.getZ());

            if (blockState.getBlock() == Blocks.NETHERRACK) {
                if (flag && y >= seaLevel - 4 && y <= seaLevel + 1) {
                    primer.setBlockState(blockpos.getX(), blockpos.getY(), blockpos.getZ(), this.getSpecialBlockState());
                } else {
                    primer.setBlockState(blockpos.getX(), blockpos.getY(), blockpos.getZ(), topBlock);
                }
            } else if (blockState.getBlock() == Blocks.LAVA) {
                primer.setBlockState(blockpos.getX(), blockpos.getY(), blockpos.getZ(), fillBlock);
            }
        }
    }

    private IBlockState getMaxNoiseBlockState(int x, int y, int z, Map<IBlockState, NoiseGeneratorOctaves> noiseMap) {
        return noiseMap.entrySet().stream()
                .max((entry1, entry2) -> {
                    double[] noiseArray1 = new double[1];
                    double[] noiseArray2 = new double[1];

                    noiseArray1 = entry1.getValue().generateNoiseOctaves(noiseArray1, x, z, 1, 1, 1.0, 1.0, 1.0);
                    noiseArray2 = entry2.getValue().generateNoiseOctaves(noiseArray2, x, z, 1, 1, 1.0, 1.0, 1.0);

                    return Double.compare(noiseArray1[0], noiseArray2[0]);
                })
                .get()
                .getKey();
    }

    public void populate(int x, int z) {

    }

    protected abstract IBlockState[] getTopBlocks();

    protected abstract IBlockState[] getFillBlocks();

    protected abstract IBlockState getSpecialBlockState();
}
