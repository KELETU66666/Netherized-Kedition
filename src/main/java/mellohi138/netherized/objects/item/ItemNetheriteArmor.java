package mellohi138.netherized.objects.item;

import com.google.common.collect.Multimap;
import mellohi138.netherized.Netherized;
import mellohi138.netherized.util.config.NetherizedItemConfig;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;

public class ItemNetheriteArmor extends ItemArmor {
	private final AttributeModifier knockbackResistance;

	public ItemNetheriteArmor(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, CreativeTabs tab) {
		super(materialIn, renderIndexIn, equipmentSlotIn);
		this.setTranslationKey(name);
		this.setRegistryName(Netherized.MODID, name);
		this.setCreativeTab(tab);
		
		this.knockbackResistance = new AttributeModifier("netheriteKnockbackResistance", NetherizedItemConfig.netheriteConfig.netheriteKnockbackResistance, 0);
	}
	
	@Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
        if (equipmentSlot == this.armorType) {
            multimap.put(SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(), this.knockbackResistance);
        }
        return multimap;
    }
}
