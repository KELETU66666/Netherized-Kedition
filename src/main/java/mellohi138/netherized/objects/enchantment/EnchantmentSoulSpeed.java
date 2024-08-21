package mellohi138.netherized.objects.enchantment;

import mellohi138.netherized.Netherized;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

import java.util.UUID;

public class EnchantmentSoulSpeed extends Enchantment {
	public static final UUID SOUL_SPEED_MODIFIER = UUID.fromString("0483aa4a-af8d-36a2-8693-22bec9caa265");
	
	public EnchantmentSoulSpeed(String nameIn, Rarity rarityIn, EnumEnchantmentType typeIn) {
		super(rarityIn, typeIn, new EntityEquipmentSlot[] {EntityEquipmentSlot.FEET});
		this.setRegistryName(Netherized.MODID, nameIn);
		this.setName(nameIn);
	}
	
    public int getMinEnchantability(int enchantmentLevel) {
        return enchantmentLevel * 10;
    }

    public int getMaxEnchantability(int enchantmentLevel) {
        return this.getMinEnchantability(enchantmentLevel) + 15;
    }
	
    public boolean isTreasureEnchantment() {
        return true;
    }
    
    public int getMaxLevel() {
        return 3;
    }
}
