package mellohi138.netherized.world.gen.feature;

import mellohi138.netherized.enums.EnumNetherForestType;
import mellohi138.netherized.init.NetherizedBlocks;
import mellohi138.netherized.objects.block.BlockNetherVines;
import mellohi138.netherized.util.ModUtils;
import net.minecraft.block.BlockLog;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

/**
 * Code, again, taken from 1.16. No rights owned to it.
 */
public class FeatureHugeFungus extends WorldGenerator {
	private final EnumNetherForestType forestType;
	
	public FeatureHugeFungus(EnumNetherForestType forestTypeIn) {
		this(forestTypeIn, true);
	}
	
	public FeatureHugeFungus(EnumNetherForestType forestTypeIn, boolean notify) {
		super(notify);
		
		this.forestType = forestTypeIn;
	}
	
    private boolean isReplaceable(World world, BlockPos pos, boolean isStem) {
        IBlockState state = world.getBlockState(pos);
        return state.getMaterial().isReplaceable() || isStem && state.getMaterial() == Material.PLANTS;
    }
	
	@Override
	public boolean generate(World worldIn, Random rand, BlockPos pos) {      
		if (worldIn.getBlockState(pos.down()).getBlock() == this.forestType.getVegetationBlocks("nylium") && worldIn.isAirBlock(pos)) {
			int i = ModUtils.nextInt(rand, 4, 13);
			if (rand.nextInt(12) == 0) {
				i *= 2;
			}
			
			if(pos.getY() + i + 1 < worldIn.getHeight()) {         
				boolean flag = rand.nextFloat() < 0.06F;
				if(flag) {
					for(BlockPos blockPos : BlockPos.getAllInBox(pos.down().add(-1, 0, -1), pos.down().add(1, 0, 1))) {
						if(worldIn.getBlockState(blockPos).getBlock() != this.forestType.getVegetationBlocks("nylium")) {
							flag = false;
						}
					}
				}
				worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 4);
				this.generateStems(worldIn, rand, pos, i, flag);
				this.generateFungusHat(worldIn, rand, pos, i, flag);
				return true;
			}
		}
		return false;
	}

	private void generateStems(World worldIn, Random rand, BlockPos pos, int nextInt, boolean randFlag) {
		IBlockState state = this.forestType.getVegetationBlocks("stem").getDefaultState().withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Y);
		int i = randFlag ? 1 : 0;
		
		for(int j = -i; j <= i; ++j) {
			for(int k = -i; k <= i; ++k) {
				boolean flag = randFlag && MathHelper.abs(j) == i && MathHelper.abs(k) == i;
				for(int l = 0; l < nextInt; ++l) {
					BlockPos.MutableBlockPos mutablePos = ModUtils.createMutablePosOffset(pos, j, l, k);
					if (this.isReplaceable(worldIn, mutablePos, true)) {
						worldIn.setBlockState(mutablePos, state, 3);
						if (flag) {
							if (rand.nextFloat() < 0.1F) {
								worldIn.setBlockState(mutablePos, state);
							}
						} else {
							worldIn.setBlockState(mutablePos, state);
						}
					}
				}
			}
		}
	}

	private void generateFungusHat(World worldIn, Random rand, BlockPos pos, int nextInt, boolean randFlag) {
		boolean flag = this.forestType == EnumNetherForestType.CRIMSON;
		int i = Math.min(rand.nextInt(1 + nextInt / 3) + 5, nextInt);
		int j = nextInt - i;
	      
		for(int k = j; k <= nextInt; ++k) {
			int l = k < nextInt - rand.nextInt(3) ? 2 : 1;
			if (i > 8 && k < j + 4) {
				l = 3;
			}
			
			if (randFlag) {
				++l;
			}
			
			for(int i1 = -l; i1 <= l; ++i1) {
				for(int j1 = -l; j1 <= l; ++j1) {
					boolean flag1 = i1 == -l || i1 == l;
					boolean flag2 = j1 == -l || j1 == l;
					boolean flag3 = !flag1 && !flag2 && k != nextInt;
					boolean flag4 = flag1 && flag2;
					boolean flag5 = k < j + 3;
					BlockPos.MutableBlockPos mutablePos = ModUtils.createMutablePosOffset(pos, i1, k, j1);
					
					if (this.isReplaceable(worldIn, mutablePos, false)) {
						if (!worldIn.isAirBlock(mutablePos.down())) {
							worldIn.destroyBlock(mutablePos, true);
						}
						if (flag5) {
							if (!flag3) {
								this.generateHatWithVines(worldIn, rand, mutablePos, flag);
							}
						} else if (flag3) {
							this.generateFungus(worldIn, rand, mutablePos, 0.1F, 0.2F, flag ? 0.1F : 0.0F);
						} else if (flag4) {
							this.generateFungus(worldIn, rand, mutablePos, 0.01F, 0.7F, flag ? 0.083F : 0.0F);
						} else {
							this.generateFungus(worldIn, rand, mutablePos, 5.0E-4F, 0.98F, flag ? 0.07F : 0.0F);
						}
					}
				}
			}
		}
	}

	private void generateFungus(World worldIn, Random rand, BlockPos.MutableBlockPos mutablePos, float f, float g, float h) {
		if (rand.nextFloat() < f) {
			worldIn.setBlockState(mutablePos, NetherizedBlocks.SHROOMLIGHT.getDefaultState());
		} else if (rand.nextFloat() < g) {
			worldIn.setBlockState(mutablePos, this.forestType.getVegetationBlocks("wart").getDefaultState());
			if (rand.nextFloat() < h) {
				placeWeepingVine(mutablePos, worldIn, rand);
			}
		}
	}

	private void generateHatWithVines(World worldIn, Random rand, BlockPos pos, boolean isCrimson) {  
		if (worldIn.getBlockState(pos.down()) == this.forestType.getVegetationBlocks("wart").getDefaultState()) {
			worldIn.setBlockState(pos, this.forestType.getVegetationBlocks("wart").getDefaultState());
		} else if ((double)rand.nextFloat() < 0.15D) {
			worldIn.setBlockState(pos, this.forestType.getVegetationBlocks("wart").getDefaultState());
			if (isCrimson && rand.nextInt(11) == 0) {
				placeWeepingVine(pos, worldIn, rand);
			}
		}
	}
	
	private static void placeWeepingVine(BlockPos pos, World worldIn, Random rand) {  
		BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos(pos).move(EnumFacing.DOWN);
		if (worldIn.isAirBlock(mutablePos)) {
			int i = ModUtils.nextInt(rand, 1, 5);
			
			if (rand.nextInt(7) == 0) {
				i *= 2;
			}
			
			for(int e = 0; e <= i; ++e) {
				if (e == i || !worldIn.isAirBlock(mutablePos.down())) {
					worldIn.setBlockState(mutablePos, NetherizedBlocks.WEEPING_VINES_END.getDefaultState().withProperty(BlockNetherVines.BlockNetherVinesEnd.AGE, Integer.valueOf(ModUtils.nextInt(rand, 13, 15))));
					break;
				}
				
				worldIn.setBlockState(mutablePos, NetherizedBlocks.WEEPING_VINES.getDefaultState(), 2);
				mutablePos.move(EnumFacing.DOWN);
			}
		}
	}
}
