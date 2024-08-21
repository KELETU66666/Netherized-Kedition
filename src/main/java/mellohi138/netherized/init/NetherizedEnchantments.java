package mellohi138.netherized.init;

import mellohi138.netherized.Netherized;
import mellohi138.netherized.objects.enchantment.EnchantmentSoulSpeed;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Netherized.MODID)
public class NetherizedEnchantments {
	public static final List<Enchantment> ENCHANTMENT_LIST = new ArrayList<Enchantment>();
	
	public static final Enchantment SOUL_SPEED = addEnchantment(new EnchantmentSoulSpeed("soul_speed", Rarity.VERY_RARE, EnumEnchantmentType.ARMOR_FEET));

	private static Enchantment addEnchantment(Enchantment enchantmentIn) {
		NetherizedEnchantments.ENCHANTMENT_LIST.add(enchantmentIn);
		return enchantmentIn;
	}
	
	@SubscribeEvent
	public static void registerEnchantments(RegistryEvent.Register<Enchantment> event) {
		for(Enchantment enchantmentIn : ENCHANTMENT_LIST) {
			event.getRegistry().register(enchantmentIn);
		}
	}
}