package mellohi138.netherized.world.gen.chunk;

import mellohi138.netherized.init.NetherizedBiomes;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager.BiomeType;

import java.util.ArrayList;
import java.util.Iterator;

public class SpecialBiomes {
    public final BiomeType biomeType;
    private static int totalBiomesWeight;

    private static  ArrayList<WeightedBiomeEntry> landBiomes = new ArrayList<>();

    SpecialBiomes(BiomeType biomeType)
    {
        this.biomeType = biomeType;
    }

    public static void addBiome(int weight, Biome biome)
    {
        addBiome(new WeightedBiomeEntry(weight, biome));
    }

    public static void addBiome(WeightedBiomeEntry biomeEntry)
    {
        totalBiomesWeight += biomeEntry.weight;
        landBiomes.add(biomeEntry);
    }

    public static Biome getRandomBiome(NetherizedGenLayer layer)
    {
        int weight = layer.nextInt(totalBiomesWeight);
        Iterator<WeightedBiomeEntry> iterator = landBiomes.iterator();
        WeightedBiomeEntry item;
        do
        {
            item = iterator.next();
            weight -= item.weight;
        }
        while (weight >= 0);
        return item.biome;
    }

    static
    {
        addBiome(10, Biomes.HELL);
        addBiome(10, NetherizedBiomes.CRIMSON_FOREST);
        addBiome(10, NetherizedBiomes.WARPED_FOREST);
        addBiome(5, NetherizedBiomes.SOUL_SAND_VALLEY);
    }

    public static class WeightedBiomeEntry
    {
        public final int weight;
        public final Biome biome;

        public WeightedBiomeEntry(int weight, Biome biome)
        {
            this.weight = weight;
            this.biome = biome;
        }
    }
}