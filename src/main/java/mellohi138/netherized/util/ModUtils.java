package mellohi138.netherized.util;

import mellohi138.netherized.enums.EnumNetherForestType;
import mellohi138.netherized.init.NetherizedEnchantments;
import mellohi138.netherized.util.config.NetherizedGeneralConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.Random;

public class ModUtils {
	/**
	 * Immunity checker. Code copied and edited from IaF ROtN Edition's Myrmex Workers. Credit goes to SandwichHorror, Democat, and Asterixxx
	*/
	public static boolean isFireproof(Item item) {
		for (String itemName : NetherizedGeneralConfig.fireproofItemList) {
			if (ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName)) == item) {
				return !NetherizedGeneralConfig.fireproofItemBlacklist;
			}
		}
		return NetherizedGeneralConfig.fireproofItemBlacklist;
	}
	
	public static boolean isHoeWhitelisted(Block block) {
		for (String blockName : NetherizedGeneralConfig.hoeWhitelistedBlocks) {
			if (ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockName)) == block || block instanceof BlockLeaves) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean getSoulBlocks(Block block) {
		for (String blockName : NetherizedGeneralConfig.soulBlocks) {
			if (ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockName)) == block) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean hasSoulSpeed(EntityLivingBase entityIn) {
		return EnchantmentHelper.getMaxEnchantmentLevel(NetherizedEnchantments.SOUL_SPEED, entityIn) > 0;
	}
	
	/**
	 * Calculates a value with the given variable and precentage.
	 * <p>
	 * 1st value is the value you want to take the precentage of.
	 * <p>
	 * 2nd value is how much precentage you want to use. This value cannot exceed 100.
	 */
	public static float calculateValueWithPrecentage(float precentageOf, float precentageVal) {
		return (precentageOf * Math.min(precentageVal, 100.0F)) / 100.0F;
	}
	
	/**
	 * Takes two values and calcuates a precentage with them. Cannot exceed 100.
	 */
	public static float findPrecentageOf(float precentageOf, float precentageVal) {
		return Math.min((precentageVal * 100.0F) / precentageOf, 100.0F);
	}
	
	/**
	 * This piece of code is copied from 1.16 Forge, I own no rights to it.
	 */
	public static int getPlantGrowthAmount(Random rand) {
		double d0 = 1.0D;
		int i;
		for(i = 0; rand.nextDouble() < d0; ++i) {
			d0 *= 0.826D;
		} 
		return i;
	}

	/*
	 * Extra MathHelper function, taken from 1.16 forge.
	 */
	public static int nextInt(Random random, int minimum, int maximum) {    
		return minimum >= maximum ? minimum : random.nextInt(maximum - minimum + 1) + minimum;
	}
	
	public static BlockPos.MutableBlockPos createMutablePosOffset(BlockPos pos, int x, int y, int z) {    
		return new BlockPos.MutableBlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
	}
	
	/*
	 * Grows Nether vegetation, code taken from 1.16 Forge
	 */
	public static boolean growNetherVegetation(World worldIn, Random rand, BlockPos pos, EnumNetherForestType forestType ) {
		Block block = worldIn.getBlockState(pos.down()).getBlock();
		if(block == forestType.getVegetationBlocks("nylium")) {
			int i = pos.getY();
			if(i >= 1 && i + 1 < worldIn.getHeight()) {
				int j = 0;
				
				for(int k = 0; k < 9; ++k) {
					BlockPos newPos = pos.add(rand.nextInt(3) - rand.nextInt(3), rand.nextInt(1), rand.nextInt(3) - rand.nextInt(3));
					IBlockState newState = null;
					if(rand.nextInt(23) == 0) {
						newState = forestType.getOpposite().getVegetationBlocks("fungus").getDefaultState();
					} else if(rand.nextInt(11) == 0) {
						newState = forestType.getVegetationBlocks("fungus").getDefaultState();
					} else if(rand.nextInt(3) == 0) {
						newState = forestType.getVegetationBlocks("roots").getDefaultState();
					}
					
					if(newState != null) {
						if(worldIn.isAirBlock(newPos) && newPos.getY() > 0 && worldIn.getBlockState(newPos.down()).getBlock() == forestType.getVegetationBlocks("nylium")) {
							worldIn.setBlockState(newPos, newState);
							++j;
						}
					}
				}
				return j > 0;
			}
		}
		return false;
	}
}