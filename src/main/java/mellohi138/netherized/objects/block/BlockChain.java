package mellohi138.netherized.objects.block;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockChain extends BlockRotatedPillarBase {
    private static final AxisAlignedBB AXIS_SHAPE_X = new AxisAlignedBB(0.0D, 0.40625D, 0.40625D, 1D, 0.59375D, 0.59375D);
    private static final AxisAlignedBB AXIS_SHAPE_Y = new AxisAlignedBB(0.40625D, 0.0D, 0.40625D, 0.59375D, 1D, 0.59375D);
    private static final AxisAlignedBB AXIS_SHAPE_Z = new AxisAlignedBB(0.40625D, 0.40625D, 0.0D, 0.59375D, 0.59375D, 1D);
	
	public BlockChain(String name, Material material, MapColor color, String usedTool, int toolStrength, SoundType soundType, CreativeTabs tab) {
		super(name, material, color, usedTool, toolStrength, soundType, tab);
	}
	
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    	switch(state.getValue(BlockRotatedPillar.AXIS)) {
		case X:
			return AXIS_SHAPE_X;
		case Y:
			return AXIS_SHAPE_Y;
		case Z:
			return AXIS_SHAPE_Z;
    	}
		return AXIS_SHAPE_X;
    }

    @Override
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
    	switch(blockState.getValue(BlockRotatedPillar.AXIS)) {
		case X:
			return AXIS_SHAPE_X;
		case Y:
			return AXIS_SHAPE_Y;
		case Z:
			return AXIS_SHAPE_Z;
    	}
		return AXIS_SHAPE_X;
    }
    
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
}
