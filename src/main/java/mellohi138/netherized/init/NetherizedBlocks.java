package mellohi138.netherized.init;

import mellohi138.netherized.Netherized;
import mellohi138.netherized.enums.EnumNetherForestType;
import mellohi138.netherized.objects.block.*;
import mellohi138.netherized.objects.block.reregistered.BlockNewSoulSand;
import mellohi138.netherized.objects.block.tileentity.TileEntityInfernoReactor;
import mellohi138.netherized.objects.item.block.ItemDoorBase;
import mellohi138.netherized.objects.item.block.ItemSlabBase;
import mellohi138.netherized.util.config.NetherizedGeneralConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Netherized.MODID)
public class NetherizedBlocks {
	public static final List<Block> BLOCK_LIST = new ArrayList<Block>();
	
	//Blocks
	public static final Block BASALT = addBlock(new BlockRotatedPillarBase("basalt", Material.ROCK, MapColor.BLACK, "pickaxe", 0, NetherizedSounds.SOUND_TYPE_BASALT, Netherized.NETHERIZED_BLOCKS).setHardness(1.25F).setResistance(4.2F));
	public static final Block BLACKSTONE = addBlock(new BlockBase("blackstone", Material.ROCK, MapColor.BLACK, "pickaxe", 0, SoundType.STONE, Netherized.NETHERIZED_BLOCKS).setHardness(1.5F).setResistance(6.0F));
	
	public static final Block CRYING_OBSIDIAN = addBlock(new BlockCryingObsidian("crying_obsidian", Material.ROCK, MapColor.BLACK, "pickaxe", 0, SoundType.STONE, Netherized.NETHERIZED_BLOCKS).setHardness(50.0F).setResistance(2000.0F).setLightLevel(0.75F));
	public static final Block GLOWING_OBSIDIAN = addBlock(new BlockGlowingObsidian("glowing_obsidian", Material.ROCK, MapColor.RED, "pickaxe", 0, SoundType.STONE, Netherized.NETHERIZED_BLOCKS).setHardness(50.0F).setResistance(2000.0F).setLightLevel(1F));
	
	public static final Block CHAIN = addBlock(new BlockChain("chain", Material.IRON, MapColor.AIR, "pickaxe", 0, NetherizedSounds.SOUND_TYPE_CHAIN, Netherized.NETHERIZED_BLOCKS).setHardness(5.0F).setResistance(6.0F));
	
	public static final Block NETHERITE_BLOCK = addBlock(new BlockBase("netherite_block", Material.ROCK, MapColor.BLACK, "pickaxe", 3, NetherizedSounds.SOUND_TYPE_NETHERITE_BLOCK, Netherized.NETHERIZED_BLOCKS, true).setHardness(50.0F).setResistance(2000.0F));
	
	//Soul Fire
	public static final Block SOUL_TORCH = addBlock(new BlockSoulTorch("soul_torch", SoundType.WOOD, Netherized.NETHERIZED_BLOCKS).setLightLevel(0.7F));
	public static final Block SOUL_FIRE = addBlock(new BlockSoulFire("soul_fire", Material.FIRE, MapColor.LIGHT_BLUE, NetherizedSounds.SOUND_TYPE_FIRE).setLightLevel(0.7F), true);
	public static final Block SOUL_SOIL = addBlock(new BlockBase("soul_soil", Material.GROUND, MapColor.BROWN, "shovel", 0, NetherizedSounds.SOUND_TYPE_SOUL_SOIL, Netherized.NETHERIZED_BLOCKS).setHardness(0.5F));
	
	//Ores
	public static final Block WITHERED_REMAINS = addBlock(new BlockNetherOre("withered_remains", Material.ROCK, MapColor.BROWN, "pickaxe", 0, NetherizedSounds.SOUND_TYPE_SOUL_SOIL, Netherized.NETHERIZED_BLOCKS, 2).setHardness(1.5F).setResistance(1.0F));
	public static final Block IRON_RICH_BASALT = addBlock(new BlockNetherOre("iron_rich_basalt", Material.ROCK, MapColor.BLACK, "pickaxe", 1, NetherizedSounds.SOUND_TYPE_NETHER_ORE, Netherized.NETHERIZED_BLOCKS, 0).setHardness(4.0F).setResistance(4.2F));
	public static final Block NETHER_GOLD_ORE = addBlock(new BlockNetherOre("nether_gold_ore", Material.ROCK, MapColor.NETHERRACK, "pickaxe", 0, NetherizedSounds.SOUND_TYPE_NETHER_ORE, Netherized.NETHERIZED_BLOCKS, 1).setHardness(3.0F).setResistance(3.0F));
	public static final Block GILDED_BLACKSTONE = addBlock(new BlockNetherOre("gilded_blackstone", Material.ROCK, MapColor.BLACK, "pickaxe", 3, NetherizedSounds.SOUND_TYPE_NETHER_ORE, Netherized.NETHERIZED_BLOCKS, 1).setHardness(1.5F).setResistance(6.0F));
	public static final Block ANCIENT_DEBRIS = addBlock(new BlockBase("ancient_debris", Material.IRON, MapColor.BLACK, "pickaxe", 3, NetherizedSounds.SOUND_TYPE_ANCIENT_DEBRIS, Netherized.NETHERIZED_BLOCKS).setHardness(30.0F).setResistance(2000.0F));
	
	//Mechanical Blocks
	public static final Block LODESTONE = addBlock(new BlockLodestone("lodestone", Material.ANVIL, MapColor.BLACK, "pickaxe", 0, NetherizedSounds.SOUND_TYPE_LODESTONE, Netherized.NETHERIZED_BLOCKS).setHardness(3.5F).setResistance(3.5F));
	public static final Block INFERNO_REACTOR = addBlock(new BlockInfernoReactor("inferno_reactor", Material.ANVIL, MapColor.GOLD, "pickaxe", 3, SoundType.STONE, Netherized.NETHERIZED_BLOCKS).setHardness(50.0F).setResistance(2000.0F));;
	public static final Block RESPAWN_ANCHOR = addBlock(new BlockRespawnAnchor("respawn_anchor", Material.ANVIL, MapColor.BLACK, "pickaxe", 3, SoundType.STONE, Netherized.NETHERIZED_BLOCKS).setHardness(50.0F).setResistance(2000.0F).setLightLevel(1.0F));
	public static final Block POLISHED_BLACKSTONE_PRESSURE_PLATE = addBlock(new BlockPressurePlateBase("polished_blackstone_pressure_plate", Material.ROCK, MapColor.BLACK, Netherized.NETHERIZED_BLOCKS));
	public static final Block POLISHED_BLACKSTONE_BUTTON = addBlock(new BlockButtonBase("polished_blackstone_button", false, Netherized.NETHERIZED_BLOCKS));
	
	//Nether Vegetation	
	public static final Block CRIMSON_NYLIUM = addBlock(new BlockNetherNylium("crimson_nylium", Material.ROCK, MapColor.RED_STAINED_HARDENED_CLAY, "pickaxe", 0, EnumNetherForestType.CRIMSON, NetherizedSounds.SOUND_TYPE_NETHER_NYLIUM, Netherized.NETHERIZED_BLOCKS).setHardness(0.4F));
	public static final Block WARPED_NYLIUM = addBlock(new BlockNetherNylium("warped_nylium", Material.ROCK, MapColor.GREEN_STAINED_HARDENED_CLAY, "pickaxe", 0, EnumNetherForestType.WARPED, NetherizedSounds.SOUND_TYPE_NETHER_NYLIUM, Netherized.NETHERIZED_BLOCKS).setHardness(0.4F));
	
	public static final Block CRIMSON_ROOTS = addBlock(new BlockNetherRoots("crimson_roots", Material.PLANTS, MapColor.RED_STAINED_HARDENED_CLAY, EnumNetherForestType.CRIMSON, NetherizedSounds.SOUND_TYPE_NETHER_ROOTS, Netherized.NETHERIZED_BLOCKS));
	public static final Block WARPED_ROOTS = addBlock(new BlockNetherRoots("warped_roots", Material.PLANTS, MapColor.GREEN_STAINED_HARDENED_CLAY, EnumNetherForestType.WARPED, NetherizedSounds.SOUND_TYPE_NETHER_ROOTS, Netherized.NETHERIZED_BLOCKS));
	public static final Block WARPED_SPROUTS = addBlock(new BlockNetherRoots("warped_sprouts", Material.PLANTS, MapColor.GREEN_STAINED_HARDENED_CLAY, EnumNetherForestType.WARPED, NetherizedSounds.SOUND_TYPE_WARPED_SPROUTS, Netherized.NETHERIZED_BLOCKS));
	
	public static final Block CRIMSON_FUNGUS = addBlock(new BlockNetherFungus("crimson_fungus", Material.PLANTS, MapColor.NETHERRACK, EnumNetherForestType.CRIMSON, NetherizedSounds.SOUND_TYPE_NETHER_FUNGUS, Netherized.NETHERIZED_BLOCKS));
	public static final Block WARPED_FUNGUS = addBlock(new BlockNetherFungus("warped_fungus", Material.PLANTS, MapColor.CYAN, EnumNetherForestType.WARPED, NetherizedSounds.SOUND_TYPE_NETHER_FUNGUS, Netherized.NETHERIZED_BLOCKS));
	
	public static final Block WEEPING_VINES = addBlock(new BlockNetherVines("weeping_vines", Material.PLANTS, MapColor.RED, EnumNetherForestType.CRIMSON, EnumFacing.DOWN, SoundType.PLANT, Netherized.NETHERIZED_BLOCKS));
	public static final Block TWISTING_VINES = addBlock(new BlockNetherVines("twisting_vines", Material.PLANTS, MapColor.CYAN, EnumNetherForestType.WARPED, EnumFacing.UP, SoundType.PLANT, Netherized.NETHERIZED_BLOCKS));
	
	public static final Block WEEPING_VINES_END = addBlock(new BlockNetherVines.BlockNetherVinesEnd("weeping_vines_end", Material.PLANTS, MapColor.NETHERRACK, EnumNetherForestType.CRIMSON, EnumFacing.DOWN, SoundType.PLANT), true);
	public static final Block TWISTING_VINES_END = addBlock(new BlockNetherVines.BlockNetherVinesEnd("twisting_vines_end", Material.PLANTS, MapColor.CYAN, EnumNetherForestType.WARPED, EnumFacing.UP, SoundType.PLANT), true); 
	
	public static final Block CRIMSON_STEM = addBlock(new BlockLogBase("crimson_stem", NetherizedMaterials.NETHER_WOOD, MapColor.RED, "axe", 0, NetherizedSounds.SOUND_TYPE_NETHER_STEM, Netherized.NETHERIZED_BLOCKS).setResistance(2.0F));
	public static final Block WARPED_STEM = addBlock(new BlockLogBase("warped_stem", NetherizedMaterials.NETHER_WOOD, MapColor.CYAN, "axe", 0, NetherizedSounds.SOUND_TYPE_NETHER_STEM, Netherized.NETHERIZED_BLOCKS).setResistance(2.0F));
	
	public static final Block CRIMSON_WART_BLOCK = addBlock(new BlockBase("crimson_wart_block", Material.GRASS, MapColor.RED, null, 0, NetherizedSounds.SOUND_TYPE_NETHER_WART, Netherized.NETHERIZED_BLOCKS).setHardness(1.0F));
	public static final Block WARPED_WART_BLOCK = addBlock(new BlockBase("warped_wart_block", Material.GRASS, MapColor.CYAN, null, 0, NetherizedSounds.SOUND_TYPE_NETHER_WART, Netherized.NETHERIZED_BLOCKS).setHardness(1.0F));
	public static final Block SHROOMLIGHT = addBlock(new BlockBase("shroomlight", Material.GRASS, MapColor.RED, "axe", 0, NetherizedSounds.SOUND_TYPE_SHROOMLIGHT, Netherized.NETHERIZED_BLOCKS).setHardness(1.0F).setLightLevel(1F));
	
	//Decoration Blocks
	public static final Block CRIMSON_PLANKS = addBlock(new BlockBase("crimson_planks", NetherizedMaterials.NETHER_WOOD, MapColor.RED, "axe", 0, SoundType.WOOD, Netherized.NETHERIZED_BLOCKS).setHardness(2.0F).setResistance(3.0F));
	public static final Block WARPED_PLANKS = addBlock(new BlockBase("warped_planks", NetherizedMaterials.NETHER_WOOD, MapColor.CYAN, "axe", 0, SoundType.WOOD, Netherized.NETHERIZED_BLOCKS).setHardness(2.0F).setResistance(3.0F));
	
	public static final Block CRIMSON_STAIRS = addBlock(new BlockStairsBase("crimson_stairs", CRIMSON_PLANKS.getDefaultState()).setHardness(2.0F).setResistance(3.0F));
	public static final Block WARPED_STAIRS = addBlock(new BlockStairsBase("warped_stairs", WARPED_PLANKS.getDefaultState()).setHardness(2.0F).setResistance(3.0F));
	
	public static final Block CRIMSON_SLABS = addSlab(new BlockSlabBase("crimson_slab", CRIMSON_PLANKS.getDefaultState(), false), new BlockSlabBase("crimson_slab", CRIMSON_PLANKS.getDefaultState(), true));
	public static final Block WARPED_SLABS = addSlab(new BlockSlabBase("warped_slab", WARPED_PLANKS.getDefaultState(), false), new BlockSlabBase("warped_slab", WARPED_PLANKS.getDefaultState(), true));
	
	public static final Block CRIMSON_FENCE = addBlock(new BlockFenceBase("crimson_fence", NetherizedMaterials.NETHER_WOOD, MapColor.RED, Netherized.NETHERIZED_BLOCKS));
	public static final Block WARPED_FENCE = addBlock(new BlockFenceBase("warped_fence", NetherizedMaterials.NETHER_WOOD, MapColor.CYAN, Netherized.NETHERIZED_BLOCKS));
	
	public static final Block CRIMSON_FENCE_GATE = addBlock(new BlockFenceGateBase("crimson_fence_gate", NetherizedMaterials.NETHER_WOOD, MapColor.RED, Netherized.NETHERIZED_BLOCKS));
	public static final Block WARPED_FENCE_GATE = addBlock(new BlockFenceGateBase("warped_fence_gate", NetherizedMaterials.NETHER_WOOD, MapColor.CYAN, Netherized.NETHERIZED_BLOCKS));
	
	public static final Block CRIMSON_DOOR = addDoor(new BlockDoorBase("crimson_door", NetherizedMaterials.NETHER_WOOD, MapColor.RED, Netherized.NETHERIZED_BLOCKS));
	public static final Block WARPED_DOOR = addDoor(new BlockDoorBase("warped_door", NetherizedMaterials.NETHER_WOOD, MapColor.CYAN, Netherized.NETHERIZED_BLOCKS));
	
	public static final Block BLACKSTONE_STAIRS = addBlock(new BlockStairsBase(BLACKSTONE.getDefaultState()));
	public static final Block BLACKSTONE_WALLS = addBlock(new BlockWallBase(BLACKSTONE.getDefaultState()));
	public static final Block BLACKSTONE_SLABS = addSlab(new BlockSlabBase(BLACKSTONE.getDefaultState(), false), new BlockSlabBase(BLACKSTONE.getDefaultState(), true));
	
	public static final Block POLISHED_BLACKSTONE = addBlock(new BlockBase("polished_blackstone", Material.ROCK, MapColor.BLACK, "pickaxe", 0, SoundType.STONE, Netherized.NETHERIZED_BLOCKS).setHardness(2.0F).setResistance(6.0F));
	public static final Block CHISELED_POLISHED_BLACKSTONE = addBlock(new BlockBase("chiseled_polished_blackstone", Material.ROCK, MapColor.BLACK, "pickaxe", 0, SoundType.STONE, Netherized.NETHERIZED_BLOCKS).setHardness(1.5F).setResistance(6.0F));
	public static final Block POLISHED_BLACKSTONE_STAIRS = addBlock(new BlockStairsBase(POLISHED_BLACKSTONE.getDefaultState()));
	public static final Block POLISHED_BLACKSTONE_WALLS = addBlock(new BlockWallBase(POLISHED_BLACKSTONE.getDefaultState()));
	public static final Block POLISHED_BLACKSTONE_SLABS = addSlab(new BlockSlabBase(POLISHED_BLACKSTONE.getDefaultState(), false), new BlockSlabBase(POLISHED_BLACKSTONE.getDefaultState(), true));
	
	public static final Block POLISHED_BLACKSTONE_BRICKS = addBlock(new BlockBase("polished_blackstone_bricks", Material.ROCK, MapColor.BLACK, "pickaxe", 0, SoundType.STONE, Netherized.NETHERIZED_BLOCKS).setHardness(1.5F).setResistance(6.0F));
	public static final Block CRACKED_POLISHED_BLACKSTONE_BRICKS = addBlock(new BlockBase("cracked_polished_blackstone_bricks", Material.ROCK, MapColor.BLACK, "pickaxe", 0, SoundType.STONE, Netherized.NETHERIZED_BLOCKS).setHardness(1.5F).setResistance(6.0F));
	public static final Block POLISHED_BLACKSTONE_BRICK_STAIRS = addBlock(new BlockStairsBase("polished_blackstone_brick_stairs", POLISHED_BLACKSTONE_BRICKS.getDefaultState()));
	public static final Block POLISHED_BLACKSTONE_BRICK_WALLS = addBlock(new BlockWallBase("polished_blackstone_brick_wall", POLISHED_BLACKSTONE_BRICKS.getDefaultState()));
	public static final Block POLISHED_BLACKSTONE_BRICK_SLABS = addSlab(new BlockSlabBase("polished_blackstone_brick_slab", POLISHED_BLACKSTONE_BRICKS.getDefaultState(), false), new BlockSlabBase("polished_blackstone_brick_slab", POLISHED_BLACKSTONE_BRICKS.getDefaultState(), true));
	
	public static final Block QUARTZ_BRICKS = addBlock(new BlockBase("quartz_bricks", Material.ROCK, MapColor.QUARTZ, "pickaxe", 0, SoundType.STONE, Netherized.NETHERIZED_BLOCKS));
	public static final Block POLISHED_BASALT = addBlock(new BlockRotatedPillarBase("polished_basalt", Material.ROCK, MapColor.BLACK, "pickaxe", 0, NetherizedSounds.SOUND_TYPE_BASALT, Netherized.NETHERIZED_BLOCKS).setHardness(1.25F).setResistance(4.2F));
	public static final Block CHISELED_NETHER_BRICKS = addBlock(new BlockBase("chiseled_nether_bricks", Material.ROCK, MapColor.NETHERRACK, "pickaxe", 0, NetherizedSounds.SOUND_TYPE_NETHER_BRICKS, Netherized.NETHERIZED_BLOCKS).setHardness(2.0F).setResistance(10.0F));
	public static final Block CRACKED_NETHER_BRICKS = addBlock(new BlockBase("cracked_nether_bricks", Material.ROCK, MapColor.NETHERRACK, "pickaxe", 0, NetherizedSounds.SOUND_TYPE_NETHER_BRICKS, Netherized.NETHERIZED_BLOCKS).setHardness(2.0F).setResistance(10.0F));
	
	@SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {	
		for(Block blockIn : BLOCK_LIST) {
			event.getRegistry().register(blockIn);
		}
		if(NetherizedGeneralConfig.overrideSoulSandWithSoulSpeed) {
			event.getRegistry().register(new BlockNewSoulSand());
		}
    }
    
    private static Block addBlock(Block blockIn, boolean blockOnly) {
    	BLOCK_LIST.add(blockIn);
		if(!blockOnly) {
	    	NetherizedItems.ITEM_LIST.add(new ItemBlock(blockIn).setRegistryName(blockIn.getRegistryName()));
		}
    	return blockIn;
    }
    
    private static Block addBlock(Block blockIn) {
    	return addBlock(blockIn, false);
    }
    
    private static Block addSlab(BlockSlab halfSlab, BlockSlab doubleSlab) {
		NetherizedItems.ITEM_LIST.add(new ItemSlabBase(halfSlab, doubleSlab));
		BLOCK_LIST.add(doubleSlab);
    	return addBlock(halfSlab, true);
    }
    
    private static Block addDoor(BlockDoor door) {
		NetherizedItems.ITEM_LIST.add(new ItemDoorBase(door));
    	return addBlock(door, true);
    }
    
	public static void registerTileEntities() {
    	GameRegistry.registerTileEntity(TileEntityInfernoReactor.class, new ResourceLocation(Netherized.MODID, "inferno_reactor"));
    }
}