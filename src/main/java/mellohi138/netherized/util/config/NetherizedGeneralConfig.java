package mellohi138.netherized.util.config;

import mellohi138.netherized.Netherized;
import net.minecraftforge.common.config.Config;

@Config(modid = Netherized.MODID, name = "Netherized/General_Config")
public class NetherizedGeneralConfig {

	@Config.Name("Bastion Remnants Spawn Frequency")
	@Config.Comment("Change the spacing between Bastion Remnants, lower means more frequent, higher means less (Block spacing / 6 > frequency)")
	@Config.RequiresMcRestart
	public static int bastionFrequency = 100;

	@Config.Name("Bastion Remnants Enable/Disable")
	@Config.Comment("When set to false, this will disable Bastion Remnants from spawning")
	@Config.RequiresMcRestart
	public static boolean bastion_enabled = true;

	@Config.Name("Bastion Remnants Y-level")
	@Config.Comment("Change the Y-level the Bastion Remnants spawn at, NOTE: this is the level that bridges for will spawn at")
	@Config.RequiresMcRestart
	public static double bastionYLevel = 41;

	@Config.Name("Bastion Remnants Mob Spawns")
	@Config.Comment("Change the spawns alloted in the Bastion Remnants, lower means more mobs, higher means less")
	@Config.RangeInt(min = 1, max = 10)
	@Config.RequiresMcRestart
	public static int bastionSpawnRate = 5;

	@Config.Name("Bastion Remnants Regular Chest Spawn Chance")
	@Config.Comment("Change the chest spawn chance through general Bastion areas, lower means more likely chance")
	@Config.RangeInt(min = 1, max = 10)
	@Config.RequiresMcRestart
	public static int bastionregularChestChance = 7;

	@Config.Name("Bastion Remnants Hold Chest Spawn Chance")
	@Config.Comment("Change the chest spawn chance through the stronghold parts of the Bastion, lower means more likely chance")
	@Config.RangeInt(min = 1, max = 10)
	@Config.RequiresMcRestart
	public static int bastionHoldChestChance = 7;

	@Config.Name("Bastion Remnants & Nether Portal Ruins Biomes Blacklist")
	@Config.Comment("This list acts as a black list for the remnants & nether portal ruins to NOT spawn in")
	@Config.RequiresMcRestart
	public static String[] remnantsBiomesNotAllowed = {};
	@Config.RequiresMcRestart
	@Config.Comment(value = "Whether or not allow netherite anvil recipes to be used")
	public static boolean netheriteAnvilRecipes = true;
	
	@Config.RequiresMcRestart
	@Config.Comment(value = "Whether or not soul speed will override soul sands mechanic of slowing you down, keep in mind enabling this may potentially break the game.")
    public static boolean overrideSoulSandWithSoulSpeed = false;
	
	@Config.RequiresMcRestart
	@Config.Comment(value = "This will replace the current knockback system with the one from 1.16")
	public static boolean newKnockback = true;
	
	@Config.RequiresMcRestart
	@Config.Comment(value = "Which items will be fireproof. Works with 'modID:itemName', does not work with metadatas.")
    public static String[] fireproofItemList = new String[] {
    		Netherized.MODID + ":ancient_debris",
    		Netherized.MODID + ":netherite_scrap",
    		Netherized.MODID + ":netherite_ingot",
    		Netherized.MODID + ":netherite_sword",
    		Netherized.MODID + ":netherite_shovel",
    		Netherized.MODID + ":netherite_pickaxe",
    		Netherized.MODID + ":netherite_axe",
    		Netherized.MODID + ":netherite_hoe",
    		Netherized.MODID + ":netherite_helmet",
    		Netherized.MODID + ":netherite_chestplate",
    		Netherized.MODID + ":netherite_leggings",
    		Netherized.MODID + ":netherite_boots",
    		Netherized.MODID + ":netherite_horse_armor",
    		Netherized.MODID + ":netherite_block"
    };
	
	@Config.RequiresMcRestart
	@Config.Comment(value = "Enabling this will make reverse the logic of the fireproof item list. Items that are in the list will be normal and the ones that aren't will be fireproof.")
	public static boolean fireproofItemBlacklist = false;
	
	@Config.RequiresMcRestart
	@Config.Comment(value = "Which blocks can be harvested with a hoe. Works with 'modID:blockName'. Every leaf block in the game is already in the list, and cannot be removed.")
    public static String[] hoeWhitelistedBlocks = new String[] {
    		"minecraft:hay_block",
    		"minecraft:nether_wart_block",
    		"minecraft:sponge",
    		Netherized.MODID + ":crimson_wart_block",
    		Netherized.MODID + ":warped_wart_block",
    		Netherized.MODID + ":shroomlight"
    };
	
	@Config.RequiresMcRestart
	@Config.Comment(value = "Which blocks soul speed and soul fire will work on. Works with 'modID:blockName'.")
    public static String[] soulBlocks = new String[] {
    		"minecraft:soul_sand",
    		Netherized.MODID + ":soul_soil"
    };

	@Config.Name("Nether Backport Global Attack Damage Modifier")
	@Config.Comment("Modify Attack Damage globally of all mobs added in this mod, only affects Piglin, Piglin Brute, Hoglin, Zoglin, Zombified Piglin")
	@Config.RequiresMcRestart
	public static double attackDamageScale = 1;

	@Config.Name("Nether Backport Global Health Modifier")
	@Config.Comment("Modify Health globally of all mobs added in this mod")
	@Config.RequiresMcRestart
	public static double healthScale = 1;
}