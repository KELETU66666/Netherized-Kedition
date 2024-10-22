package mellohi138.netherized.world.gen.chunk;

import net.minecraft.init.Biomes;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.*;

public class BiomeProviderNetherized extends BiomeProvider {

    public BiomeProviderNetherized(World world) {
        super();

        GenLayer[] genlayers = setBiomeGeneration(world.getSeed());
        this.genBiomes = genlayers[0];
        this.biomeIndexLayer = genlayers[1];
    }

    @Override
    public Biome[] getBiomesForGeneration(Biome[] biomes, int x, int z, int width, int height) {
        IntCache.resetIntCache();

        if (biomes.length < width * height) {
            biomes = new Biome[width * height];
        }

        int[] aint = this.genBiomes.getInts(x, z, width, height);

        for (int i = 0; i < width * height; ++i) {
            biomes[i] = Biome.getBiome(aint[i], Biomes.HELL);
        }

        return biomes;
    }

    public static GenLayer allocateBiomes() {
        GenLayer biomesLayer = new NetherizedGenLayer(200L);

        biomesLayer = new GenLayerZoom(1000L, biomesLayer);

        biomesLayer = new GenLayerZoom(1000L, biomesLayer);

        return biomesLayer;
    }


    public static GenLayer[] setBiomeGeneration(long worldSeed) {
        int biomeSize = 3;

        GenLayer mainBranch = allocateBiomes();

        for (int i = 0; i < biomeSize; ++i) {
            mainBranch = new GenLayerZoom(1000 + i, mainBranch);
        }

        mainBranch = new GenLayerSmooth(1000L, mainBranch);

        GenLayer biomesFinal = new GenLayerVoronoiZoom(10L, mainBranch);

        mainBranch.initWorldGenSeed(worldSeed);
        biomesFinal.initWorldGenSeed(worldSeed);
        return new GenLayer[]{mainBranch, biomesFinal};
    }
}