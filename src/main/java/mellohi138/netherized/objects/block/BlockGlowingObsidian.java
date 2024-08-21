package mellohi138.netherized.objects.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockGlowingObsidian extends BlockBase {
	public BlockGlowingObsidian(String name, Material material, MapColor color, String usedTool, int toolStrength, SoundType soundType, CreativeTabs tab) {
		super(name, material, color, usedTool, toolStrength, soundType, tab, false);
	}
	
    public boolean isFireSource(World world, BlockPos pos, EnumFacing side) {
    	return true;
    }
	
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    	return Item.getItemFromBlock(Blocks.OBSIDIAN);
    }
    
	@Override
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		EnumFacing side = EnumFacing.random(rand);
		BlockPos blockpos = pos.offset(side);
		IBlockState blockstate = worldIn.getBlockState(blockpos);
		if (!stateIn.isSideSolid(worldIn, blockpos, EnumFacing.UP) || !blockstate.isSideSolid(worldIn, blockpos, side.getOpposite())) {
			double d0 = side.getXOffset() == 0 ? rand.nextDouble() : 0.5D + (double)side.getXOffset() * 0.6D;
            double d1 = side.getYOffset() == 0 ? rand.nextDouble() : 0.5D + (double)side.getYOffset() * 0.6D;
            double d2 = side.getZOffset() == 0 ? rand.nextDouble() : 0.5D + (double)side.getZOffset() * 0.6D;
			worldIn.spawnParticle(EnumParticleTypes.FLAME, (double)pos.getX() + d0, (double)pos.getY() + d1, (double)pos.getZ() + d2, 0.0D, 0.0D, 0.0D);
    	}
    }
}