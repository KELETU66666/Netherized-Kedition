package mellohi138.netherized.util;

import mellohi138.netherized.init.NetherizedBlocks;
import mellohi138.netherized.init.NetherizedItems;
import mellohi138.netherized.init.NetherizedSounds;
import mellohi138.netherized.world.NetherizedOreWorldGen;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class RegistryHandler {
	public static void registerBlockSounds() {
		Blocks.NETHERRACK.setSoundType(NetherizedSounds.SOUND_TYPE_NETHERRACK);
		Blocks.SOUL_SAND.setSoundType(NetherizedSounds.SOUND_TYPE_SOUL_SAND);
		Blocks.NETHER_BRICK.setSoundType(NetherizedSounds.SOUND_TYPE_NETHER_BRICKS);
		Blocks.NETHER_BRICK_FENCE.setSoundType(NetherizedSounds.SOUND_TYPE_NETHER_BRICKS);
		Blocks.NETHER_BRICK_STAIRS.setSoundType(NetherizedSounds.SOUND_TYPE_NETHER_BRICKS);
		Blocks.QUARTZ_ORE.setSoundType(NetherizedSounds.SOUND_TYPE_NETHER_ORE);
		Blocks.NETHER_WART.setSoundType(NetherizedSounds.SOUND_TYPE_NETHER_WART);
		Blocks.NETHER_WART_BLOCK.setSoundType(NetherizedSounds.SOUND_TYPE_NETHER_WART);
		Blocks.BONE_BLOCK.setSoundType(NetherizedSounds.SOUND_TYPE_BONE_BLOCK);
	}
	
	public static void registerMobUtils() {
		EntityEnderman.setCarriable(NetherizedBlocks.CRIMSON_NYLIUM, true);
		EntityEnderman.setCarriable(NetherizedBlocks.WARPED_NYLIUM, true);
		EntityEnderman.setCarriable(NetherizedBlocks.SHROOMLIGHT, true);
	}
	
	public static void registerWorldGenerators() {
		GameRegistry.registerWorldGenerator(new NetherizedOreWorldGen(), 0);
	}

	public static void registerOreDict() {
		OreDictionary.registerOre("ingotNetherite", NetherizedItems.NETHERITE_INGOT);
		OreDictionary.registerOre("blockNetherite", NetherizedBlocks.NETHERITE_BLOCK);
		OreDictionary.registerOre("toolAxe", NetherizedItems.NETHERITE_AXE);
		OreDictionary.registerOre("materialStoneTool", NetherizedBlocks.BLACKSTONE);
		
		OreDictionary.registerOre("oreCoal", NetherizedBlocks.WITHERED_REMAINS);
		OreDictionary.registerOre("oreIron", NetherizedBlocks.IRON_RICH_BASALT);
		OreDictionary.registerOre("oreGold", NetherizedBlocks.NETHER_GOLD_ORE);
		OreDictionary.registerOre("oreGold", NetherizedBlocks.GILDED_BLACKSTONE);
		OreDictionary.registerOre("oreDebris", NetherizedBlocks.ANCIENT_DEBRIS);
	}
}
