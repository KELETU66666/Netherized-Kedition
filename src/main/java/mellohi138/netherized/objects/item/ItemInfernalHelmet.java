package mellohi138.netherized.objects.item;

import com.google.common.collect.Multimap;
import mellohi138.netherized.Netherized;
import mellohi138.netherized.client.model.armor.ModelInfernalHelmet;
import mellohi138.netherized.util.config.NetherizedItemConfig;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemInfernalHelmet extends ItemArmor {
	private final AttributeModifier knockbackResistance;
	
	public ItemInfernalHelmet(String name, ArmorMaterial materialIn, CreativeTabs tab) {
		super(materialIn, 1, EntityEquipmentSlot.HEAD);
		this.setTranslationKey(name);
		this.setRegistryName(Netherized.MODID, name);
		this.setCreativeTab(tab);
		
		this.knockbackResistance = new AttributeModifier("infernoKnockbackResistance", NetherizedItemConfig.infernalConfig.infernoKnockbackResistance, 0);
	}
	
	@Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
        if (equipmentSlot == EntityEquipmentSlot.HEAD) {
            multimap.put(SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(), this.knockbackResistance);
        }
        return multimap;
    }
    
	@Override
    @Nullable
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped defaultModel) {
		return new ModelInfernalHelmet();
    }
	
	@Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        return Netherized.MODID + ":textures/models/armor/infernal_helmet.png";
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(TextFormatting.BLUE + I18n.format("item.inferno_helmet.desc"));
		tooltip.add(TextFormatting.BLUE + I18n.format("description.wip"));
    }
}