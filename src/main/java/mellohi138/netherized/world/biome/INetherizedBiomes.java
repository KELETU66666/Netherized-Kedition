package mellohi138.netherized.world.biome;

import mellohi138.netherized.world.gen.chunk.ChunkGeneratorNetherized;
import net.minecraft.world.chunk.ChunkPrimer;

import javax.annotation.Nonnull;

public interface INetherizedBiomes {
    int getBiomeColor(int paramInt1, int paramInt2, int paramInt3);
    void buildSurface(@Nonnull ChunkGeneratorNetherized chunkGenerator, int chunkX, int chunkZ, @Nonnull ChunkPrimer primer, int x, int z, double[] soulSandNoise, double[] gravelNoise, double[] depthBuffer);
}
