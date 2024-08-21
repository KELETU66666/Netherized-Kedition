package mellohi138.netherized.objects.block;

import mellohi138.netherized.Netherized;
import mellohi138.netherized.enums.EnumNetherForestType;
import mellohi138.netherized.init.NetherizedBlocks;
import mellohi138.netherized.world.gen.feature.FeatureHugeFungus;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.EnumPlantType;

import java.util.Random;

public class BlockNetherFungus extends BlockBush implements IGrowable {
	private static final AxisAlignedBB FUNGUS_AABB = new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 0.5625D, 0.75D);
	private final EnumNetherForestType forestType;
	
	public BlockNetherFungus(String name, Material material, MapColor color, EnumNetherForestType forestTypeIn, SoundType soundType, CreativeTabs tab) {
		super(material, color);
		this.setTranslationKey(name);
		this.setRegistryName(Netherized.MODID, name);
		this.setCreativeTab(tab);
        this.setSoundType(soundType);
		
		this.forestType = forestTypeIn;
	}
	
	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
        return FUNGUS_AABB;
    }
	
    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
    	return true;
    }
	
	@Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return (worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos) && worldIn.getBlockState(pos).getBlock() != this) && this.canBlockStay(worldIn, pos, this.getDefaultState());
    }
	
	@Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
        if (state.getBlock() == this) {
            IBlockState soil = worldIn.getBlockState(pos.down());
            
            return soil.getBlock() == Blocks.GRASS || soil.getBlock() == Blocks.DIRT || soil.getBlock() == Blocks.FARMLAND || soil.getBlock() == Blocks.MYCELIUM || soil.getBlock() == NetherizedBlocks.CRIMSON_NYLIUM || soil.getBlock() == NetherizedBlocks.WARPED_NYLIUM;
        }
        return false;
    }
	
	@Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
		return EnumPlantType.Nether;
    }
    
	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		IBlockState soil = worldIn.getBlockState(pos.down());
		return soil.getBlock() == forestType.getVegetationBlocks("nylium");
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return (double)rand.nextFloat() < 0.4D;
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        WorldGenerator generator = new FeatureHugeFungus(this.forestType);

        if (!generator.generate(worldIn, rand, pos)) {
        	worldIn.setBlockState(pos, state, 4);
        }
	}
}
