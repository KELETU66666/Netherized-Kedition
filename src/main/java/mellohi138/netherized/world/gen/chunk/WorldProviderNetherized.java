package mellohi138.netherized.world.gen.chunk;

import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.gen.IChunkGenerator;

public class WorldProviderNetherized extends WorldProviderHell
{
    @Override
    public void init()
    {
        this.biomeProvider = new BiomeProviderNetherized(this.world);
        this.doesWaterVaporize = true;
        this.nether = true;
    }

    @Override
    public IChunkGenerator createChunkGenerator()
    {
        return new ChunkGeneratorNetherized(this.world, this.world.getWorldInfo().isMapFeaturesEnabled(), this.world.getSeed());
    }
}