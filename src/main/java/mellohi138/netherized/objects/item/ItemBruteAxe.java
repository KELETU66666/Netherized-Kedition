package mellohi138.netherized.objects.item;

import mellohi138.netherized.Netherized;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBruteAxe extends ItemAxe {
	public ItemBruteAxe(String name, ToolMaterial materialIn, CreativeTabs tab) {
		super(materialIn, materialIn.getAttackDamage() + 9F, -3.0F);
		this.setTranslationKey(name);
		this.setRegistryName(Netherized.MODID, name);
		this.setCreativeTab(tab);
		this.setMaxDamage(materialIn.getMaxUses() * 20);
	}
	
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack itemStack, Enchantment enchantment) {
    	if(enchantment == Enchantments.SWEEPING) {
    		return false;
    	}
    	
        return enchantment.type == EnumEnchantmentType.WEAPON || enchantment.type == EnumEnchantmentType.DIGGER || enchantment.type == EnumEnchantmentType.BREAKABLE;
    }
	
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(TextFormatting.BLUE + I18n.format("item.brute_axe.desc"));
    }
}
