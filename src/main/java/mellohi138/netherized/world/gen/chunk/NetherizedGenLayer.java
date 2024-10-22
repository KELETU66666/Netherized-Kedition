package mellohi138.netherized.world.gen.chunk;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class NetherizedGenLayer extends GenLayer {
    public NetherizedGenLayer(long seed) {
        super(seed);
    }

    @Override
    public int nextInt(int a) {
        return super.nextInt(a);
    }

    @Override
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
        int[] out = IntCache.getIntCache(areaWidth * areaHeight);

        for (int x = 0; x < areaHeight; ++x) {
            for (int z = 0; z < areaWidth; ++z) {
                int index = z + x * areaWidth;
                this.initChunkSeed(z + areaX, x + areaY);
                out[index] = Biome.getIdForBiome(SpecialBiomes.getRandomBiome(this));
            }
        }

        return out;
    }
}