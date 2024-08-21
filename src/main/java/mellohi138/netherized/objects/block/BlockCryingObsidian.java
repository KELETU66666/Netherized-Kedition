package mellohi138.netherized.objects.block;

import mellohi138.netherized.Netherized;
import mellohi138.netherized.enums.EnumNetherizedParticles;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockCryingObsidian extends BlockBase {
	public BlockCryingObsidian(String name, Material material, MapColor color, String usedTool, int toolStrength, SoundType soundType, CreativeTabs tab) {
		super(name, material, color, usedTool, toolStrength, soundType, tab, false);
	}
	
	/*
	 * Code taken from 1.16 Forge to make it as accurate as possible
	 */
	@Override
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (rand.nextInt(5) == 0) {
			EnumFacing side = EnumFacing.random(rand);
			if (side != EnumFacing.UP) {
				BlockPos blockPos = pos.offset(side);
				IBlockState blockstate = worldIn.getBlockState(blockPos);
				if (!stateIn.isSideSolid(worldIn, blockPos, EnumFacing.UP) || !blockstate.isSideSolid(worldIn, blockPos, side.getOpposite())) {
					double d0 = side.getXOffset() == 0 ? rand.nextDouble() : 0.5D + (double)side.getXOffset() * 0.6D;
	                double d1 = side.getYOffset() == 0 ? rand.nextDouble() : 0.5D + (double)side.getYOffset() * 0.6D;
	                double d2 = side.getZOffset() == 0 ? rand.nextDouble() : 0.5D + (double)side.getZOffset() * 0.6D;
	                Netherized.PROXY.spawnParticle(EnumNetherizedParticles.CRYING_OBSIDIAN_TEAR, worldIn, (double)pos.getX() + d0, (double)pos.getY() + d1, (double)pos.getZ() + d2, 0.0D, 0.0D, 0.0D);
				}
			}   
		}
    }
}