package mellohi138.netherized.objects.block;

import mellohi138.netherized.enums.EnumNetherForestType;
import mellohi138.netherized.util.ModUtils;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockNetherNylium extends BlockBase implements IGrowable {
	private final EnumNetherForestType forestType;
	
	public BlockNetherNylium(String name, Material material, MapColor color, String usedTool, int toolStrength, EnumNetherForestType forestTypeIn, SoundType soundType, CreativeTabs tab) {
		super(name, material, color, usedTool, toolStrength, soundType, tab, false);
		
		this.forestType = forestTypeIn;
	}
	
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    	return Item.getItemFromBlock(Blocks.NETHERRACK);
    }

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return true;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return true;
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {	
		ModUtils.growNetherVegetation(worldIn, rand, pos.up(), this.forestType);
		if(this.forestType == EnumNetherForestType.WARPED) {
			
		}
	}
}