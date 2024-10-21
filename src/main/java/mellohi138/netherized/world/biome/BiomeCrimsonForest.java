package mellohi138.netherized.world.biome;

import mellohi138.netherized.init.NetherizedBlocks;
import mellohi138.netherized.objects.entity.neutral.EntityHoglin;
import mellohi138.netherized.objects.entity.neutral.EntityPigman;
import mellohi138.netherized.objects.entity.passive.EntityStrider;
import mellohi138.netherized.world.biome.decorator.DecoratorCrimsonForest;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class BiomeCrimsonForest extends BiomeBase {
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
}
