package mellohi138.netherized.events;

import mellohi138.netherized.Netherized;
import mellohi138.netherized.init.NetherizedItems;
import mellohi138.netherized.objects.item.ItemBruteAxe;
import mellohi138.netherized.objects.item.ItemInfernalShield;
import mellohi138.netherized.recipe.ArmorUpgradeRecipe;
import mellohi138.netherized.util.ModUtils;
import mellohi138.netherized.util.config.NetherizedGeneralConfig;
import mellohi138.netherized.util.config.NetherizedItemConfig;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = Netherized.MODID)
public class ItemEvents {

	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> evt) {
		IForgeRegistry<IRecipe> r = evt.getRegistry();

		r.register(new ArmorUpgradeRecipe(NetherizedItems.NETHERITE_HELMET, Items.DIAMOND_HELMET)
				.setRegistryName(Netherized.MODID, "netherite_helmet"));
		r.register(new ArmorUpgradeRecipe(NetherizedItems.NETHERITE_CHESTPLATE, Items.DIAMOND_CHESTPLATE)
				.setRegistryName(Netherized.MODID, "netherite_chestplate"));
		r.register(new ArmorUpgradeRecipe(NetherizedItems.NETHERITE_LEGGINGS, Items.DIAMOND_LEGGINGS)
				.setRegistryName(Netherized.MODID, "netherite_leggings"));
		r.register(new ArmorUpgradeRecipe(NetherizedItems.NETHERITE_BOOTS, Items.DIAMOND_BOOTS)
				.setRegistryName(Netherized.MODID, "netherite_boots"));
		r.register(new ArmorUpgradeRecipe(NetherizedItems.NETHERITE_SWORD, Items.DIAMOND_SWORD)
				.setRegistryName(Netherized.MODID, "netherite_sword"));
		r.register(new ArmorUpgradeRecipe(NetherizedItems.NETHERITE_PICKAXE, Items.DIAMOND_PICKAXE)
				.setRegistryName(Netherized.MODID, "netherite_pickaxe"));
		r.register(new ArmorUpgradeRecipe(NetherizedItems.NETHERITE_AXE, Items.DIAMOND_AXE)
				.setRegistryName(Netherized.MODID, "netherite_axe"));
		r.register(new ArmorUpgradeRecipe(NetherizedItems.NETHERITE_SHOVEL, Items.DIAMOND_SHOVEL)
				.setRegistryName(Netherized.MODID, "netherite_shovel"));
		r.register(new ArmorUpgradeRecipe(NetherizedItems.NETHERITE_HOE, Items.DIAMOND_HOE)
				.setRegistryName(Netherized.MODID, "netherite_hoe"));
	}

	@SubscribeEvent
	public static void addBruteAxeDamage(LivingHurtEvent event) {
		Entity source = event.getSource().getImmediateSource();
		EntityLivingBase target = event.getEntityLiving();
		
		if(source != null) {
			if(!target.world.isRemote && !target.isDead) {
				float damage = event.getAmount();
				
				if(source instanceof EntityLiving) {
					EntityLiving attacker = (EntityLiving)source;
					ItemStack weapon = attacker.getHeldItemMainhand();
					if(!weapon.isEmpty() && weapon.getItem() instanceof ItemBruteAxe) {
						damage += ModUtils.calculateValueWithPrecentage(target.getHealth(), NetherizedItemConfig.bruteAxeConfig.bruteAxeDamage);
					}
				} else if(source instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer)source;
					ItemStack weapon = player.getHeldItemMainhand();
					
					if(!weapon.isEmpty() && weapon.getItem() instanceof ItemBruteAxe) {
						if(!player.getCooldownTracker().hasCooldown(weapon.getItem())) {								
							//Copied and edited from EntityPlayer.attackTargetEntityWithCurrentItem(Entity entityIn)
							boolean isCritical = player.swingProgress == 0 && player.fallDistance > 0.0F && !player.onGround && !player.isOnLadder() && !player.isInWater() && !player.isPotionActive(MobEffects.BLINDNESS) && !player.isRiding() && !player.isSprinting();
							
							if(isCritical) {
								damage += ModUtils.calculateValueWithPrecentage(target.getHealth(), NetherizedItemConfig.bruteAxeConfig.bruteAxeDamage * 1.5F);
								
								target.knockBack(target, 0.5F, player.posX - target.posX, player.posZ - target.posZ);
								player.getCooldownTracker().setCooldown(weapon.getItem(), 300);
							} else {
								float swingProgress = 1.0F - player.swingProgress;
								damage += ModUtils.calculateValueWithPrecentage(target.getHealth(), swingProgress * NetherizedItemConfig.bruteAxeConfig.bruteAxeDamage);
								
								player.getCooldownTracker().setCooldown(weapon.getItem(), (int) (swingProgress * 200));
							}
						}
					}
				}
				event.setAmount(damage);
			}
		}
	}
	
	@SubscribeEvent
	public static void addInfernalShieldBlock(LivingAttackEvent event) {
		Entity source = event.getSource().getImmediateSource();
		EntityLivingBase target = event.getEntityLiving();
		
		//TODO: Make it so that this is called upon a successful block instead of checking if it's blocking with this shield
		if(target.getActiveItemStack().getItem() instanceof ItemInfernalShield && target.isHandActive()) {
			if(!source.isImmuneToFire()) source.setFire(8);
			
			if(source instanceof EntityLivingBase) {
				EntityLivingBase attacker = (EntityLivingBase)source;
				attacker.knockBack(attacker, 0.25F, target.posX - attacker.posX, target.posZ - attacker.posZ);
			}
		}
	}
	
	@SubscribeEvent
	public static void addInfernalHelmetDamageReduction(LivingHurtEvent event) {
		EntityLivingBase entityIn = event.getEntityLiving();
		if(entityIn.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == NetherizedItems.INFERNAL_HELMET) {
			DamageSource source = event.getSource();
			if(source.isFireDamage()) {
				float amount = event.getAmount();
				event.setAmount(amount / NetherizedItemConfig.infernalConfig.infernalHelmetDamageReduction);
			}
		}
	}
	
	@SubscribeEvent
	public static void addHoeHarvesting(PlayerEvent.BreakSpeed event) {
		EntityPlayer playerIn = event.getEntityPlayer();
		ItemStack stack = playerIn.getHeldItem(EnumHand.MAIN_HAND);
		
		if(stack.getItem() instanceof ItemHoe) {
			IBlockState state = event.getState();
			if(ModUtils.isHoeWhitelisted(state.getBlock())) {
				ItemHoe hoe = (ItemHoe)playerIn.getHeldItem(EnumHand.MAIN_HAND).getItem();
				ToolMaterial material = ToolMaterial.valueOf(hoe.getMaterialName());
				event.setNewSpeed(material.getEfficiency());
			}
		}
	}
	
	@SubscribeEvent
	public static void damageHoeUponHarvest(BlockEvent.BreakEvent event) {
		EntityPlayer player = event.getPlayer();
		ItemStack stack = player.getHeldItem(EnumHand.MAIN_HAND);
		
		if(stack.getItem() instanceof ItemHoe) {
			IBlockState state = event.getState();
			if(ModUtils.isHoeWhitelisted(state.getBlock()) && !player.world.isRemote) {
				player.getHeldItem(EnumHand.MAIN_HAND).damageItem(1, player);
			}
		}
	}
	
	@SubscribeEvent
	public static void addNetheriteAnvilRecipes(AnvilUpdateEvent event) {
		if(NetherizedGeneralConfig.netheriteAnvilRecipes) {
			ItemStack leftInput = event.getLeft();
			ItemStack rightInput = event.getRight();
			ItemStack output = event.getOutput();
			
			if(rightInput.getItem() == NetherizedItems.NETHERITE_INGOT) {
				Item[] diamondTools = new Item[] {Items.DIAMOND_SWORD, Items.DIAMOND_SHOVEL, Items.DIAMOND_PICKAXE, Items.DIAMOND_AXE, Items.DIAMOND_HOE, Items.DIAMOND_HELMET, Items.DIAMOND_CHESTPLATE, Items.DIAMOND_LEGGINGS, Items.DIAMOND_BOOTS, Items.DIAMOND_HORSE_ARMOR};
				
				for(int i = 0; i < diamondTools.length; i++) {
					if(leftInput.getItem() == diamondTools[i]) {
						Item[] netheriteTools = new Item[] {NetherizedItems.NETHERITE_SWORD, NetherizedItems.NETHERITE_SHOVEL, NetherizedItems.NETHERITE_PICKAXE, NetherizedItems.NETHERITE_AXE, NetherizedItems.NETHERITE_HOE, NetherizedItems.NETHERITE_HELMET, NetherizedItems.NETHERITE_CHESTPLATE, NetherizedItems.NETHERITE_LEGGINGS, NetherizedItems.NETHERITE_BOOTS, NetherizedItems.NETHERITE_HORSE_ARMOR};
						output = new ItemStack(netheriteTools[i]);
						NBTTagCompound tags = leftInput.getTagCompound();
						output.setTagCompound(tags);
						int itemDamage = (int)ModUtils.findPrecentageOf(leftInput.getMaxDamage(), leftInput.getItemDamage());
						int calculatedDamage = (int)ModUtils.calculateValueWithPrecentage(output.getMaxDamage(), itemDamage);
						output.setItemDamage(calculatedDamage);
						event.setOutput(output);
						event.setMaterialCost(1);
						event.setCost(3);
					}
				}
			}
 		}
	}
}
