package mellohi138.netherized.init;

import mellohi138.netherized.Netherized;
import mellohi138.netherized.objects.item.*;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Netherized.MODID)
public class NetherizedItems {
	public static final List<Item> ITEM_LIST = new ArrayList<Item>();
	
	public static final Item NETHERITE_INGOT = addItem(new ItemBase("netherite_ingot", Netherized.NETHERIZED_ITEMS, true));
	public static final Item NETHERITE_SCRAP = addItem(new ItemBase("netherite_scrap", Netherized.NETHERIZED_ITEMS));
	
	public static final Item NETHERITE_SWORD = addItem(new ItemNetheriteSword("netherite_sword", NetherizedMaterials.NETHERITE_TOOL_MATERIAL, Netherized.NETHERIZED_ITEMS));
    public static final Item NETHERITE_SHOVEL = addItem(new ItemNetheriteShovel("netherite_shovel", NetherizedMaterials.NETHERITE_TOOL_MATERIAL, Netherized.NETHERIZED_ITEMS));
    public static final Item NETHERITE_PICKAXE = addItem(new ItemNetheritePickaxe("netherite_pickaxe", NetherizedMaterials.NETHERITE_TOOL_MATERIAL, Netherized.NETHERIZED_ITEMS));
    public static final Item NETHERITE_AXE = addItem(new ItemNetheriteAxe("netherite_axe", NetherizedMaterials.NETHERITE_TOOL_MATERIAL, Netherized.NETHERIZED_ITEMS));
    public static final Item NETHERITE_HOE = addItem(new ItemNetheriteHoe("netherite_hoe", NetherizedMaterials.NETHERITE_TOOL_MATERIAL, Netherized.NETHERIZED_ITEMS));
    
    public static final Item NETHERITE_HELMET = addItem(new ItemNetheriteArmor("netherite_helmet", NetherizedMaterials.NETHERITE_ARMOR_MATERIAL, 1, EntityEquipmentSlot.HEAD, Netherized.NETHERIZED_ITEMS));
    public static final Item NETHERITE_CHESTPLATE = addItem(new ItemNetheriteArmor("netherite_chestplate", NetherizedMaterials.NETHERITE_ARMOR_MATERIAL, 1, EntityEquipmentSlot.CHEST, Netherized.NETHERIZED_ITEMS));
    public static final Item NETHERITE_LEGGINGS = addItem(new ItemNetheriteArmor("netherite_leggings", NetherizedMaterials.NETHERITE_ARMOR_MATERIAL, 2, EntityEquipmentSlot.LEGS, Netherized.NETHERIZED_ITEMS));
    public static final Item NETHERITE_BOOTS = addItem(new ItemNetheriteArmor("netherite_boots", NetherizedMaterials.NETHERITE_ARMOR_MATERIAL, 1, EntityEquipmentSlot.FEET, Netherized.NETHERIZED_ITEMS));
    
    public static final Item NETHERITE_HORSE_ARMOR = addItem(new ItemNetheriteHorseArmor("netherite_horse_armor", NetherizedMaterials.NETHERITE_HORSE_ARMOR, Netherized.NETHERIZED_ITEMS));
    
    public static final Item BRUTE_AXE = addItem(new ItemBruteAxe("brute_axe", ToolMaterial.GOLD, Netherized.NETHERIZED_ITEMS));
    public static final Item INFERNAL_HELMET = addItem(new ItemInfernalHelmet("inferno_helmet", NetherizedMaterials.NETHERITE_ARMOR_MATERIAL, Netherized.NETHERIZED_ITEMS));
    public static final Item INFERNAL_SHIELD = addItem(new ItemInfernalShield("inferno_shield", Netherized.NETHERIZED_ITEMS));
    
	public static final Item LODESTONE_COMPASS = addItem(new ItemLodestoneCompass("lodestone_compass"));
    public static final Item WARPED_FUNGUS_ON_A_STICK = addItem(new ItemWarpedFungusOnAStick("warped_fungus_on_a_stick", Netherized.NETHERIZED_ITEMS));
    public static final Item RECORD_PIGSTEP = addItem(new ItemRecordBase("record_pigstep", NetherizedSounds.RECORD_PIGSTEP, Netherized.NETHERIZED_ITEMS));
    
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
		for(Item itemIn : ITEM_LIST) {
			event.getRegistry().register(itemIn);
		}
    }
    
	private static Item addItem(Item item) {
		ITEM_LIST.add(item);
		return item;
	}
}
