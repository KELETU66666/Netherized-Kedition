package mellohi138.netherized.world.biome;

import mellohi138.netherized.init.NetherizedBlocks;
import mellohi138.netherized.world.biome.decorator.DecoratorWarpedForest;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class BiomeSoulSandValley extends BiomeBase {
	public BiomeSoulSandValley(String nameIn, BiomeProperties properties) {
		super(nameIn, properties);

		this.topBlock = NetherizedBlocks.SOUL_SOIL.getDefaultState();
		this.fillerBlock = Blocks.SOUL_SAND.getDefaultState();

		//this.spawnableCreatureList.add(new SpawnListEntry(EntityStrider.class, 33, 1, 2));
        //this.spawnableMonsterList.add(new SpawnListEntry(EntityEnderman.class, 200, 1, 4));
	}
	
    @Override
    public BiomeDecorator createBiomeDecorator() {
    	return new DecoratorWarpedForest();
    }

	@Override
	public void setTypes() {
		BiomeDictionary.addTypes(this, Type.NETHER);
	}
}
