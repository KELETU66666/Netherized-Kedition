package mellohi138.netherized.objects.item;

import mellohi138.netherized.Netherized;
import mellohi138.netherized.client.render.item.RenderInfernalShield;
import mellohi138.netherized.util.interfaces.ITEISRModel;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemInfernalShield extends ItemShield implements ITEISRModel {
	public ItemInfernalShield(String name, CreativeTabs tab) {
		super();
		this.setTranslationKey(name);
		this.setRegistryName(Netherized.MODID, name);
		this.setCreativeTab(tab);
        this.setMaxDamage(823);
	}

	@Override
    public boolean isShield(ItemStack stack, @Nullable EntityLivingBase entity) {
        return true;
    }
	
	@Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return false;
    }
	
	@Override
	@SuppressWarnings("deprecation")
	public String getItemStackDisplayName(ItemStack stack) {
        return net.minecraft.util.text.translation.I18n.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name").trim();
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(TextFormatting.BLUE + I18n.format("item.inferno_shield.desc"));
		tooltip.add(TextFormatting.BLUE + I18n.format("description.wip"));
    }

	@Override
	@SideOnly(Side.CLIENT)
	public void registerTEISRModel() {
		RenderInfernalShield.render();
	}
}
