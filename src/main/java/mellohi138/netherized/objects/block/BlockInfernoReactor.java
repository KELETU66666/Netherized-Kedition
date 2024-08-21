package mellohi138.netherized.objects.block;

import mellohi138.netherized.Netherized;
import mellohi138.netherized.init.NetherizedBlocks;
import mellohi138.netherized.objects.block.tileentity.TileEntityInfernoReactor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockInfernoReactor extends BlockContainer {
    public static final PropertyBool IS_ACTIVE = PropertyBool.create("is_active");
    
	public BlockInfernoReactor(String name, Material materialIn, MapColor color, String usedTool, int toolStrength, SoundType soundType, CreativeTabs tab) {
		super(materialIn, color);
		this.setTranslationKey(name);
		this.setRegistryName(Netherized.MODID, name);
		this.setCreativeTab(tab);
        this.setHarvestLevel(usedTool, toolStrength);
        this.setSoundType(soundType);
        
        this.setDefaultState(this.blockState.getBaseState().withProperty(IS_ACTIVE, Boolean.valueOf(false)));
	}
	
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(IS_ACTIVE, Boolean.valueOf(meta > 0));
    }

    public int getMetaFromState(IBlockState state) {
        return state.getValue(IS_ACTIVE).booleanValue() ? 1 : 0;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {
        		IS_ACTIVE
        });
    }
    
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }
    
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(NetherizedBlocks.INFERNO_REACTOR);
    }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityInfernoReactor();
	}
	
	@Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        
        if(tileEntity instanceof TileEntityInfernoReactor) {
        	TileEntityInfernoReactor reactor = ((TileEntityInfernoReactor)tileEntity);
        	reactor.update();
        }
    }
}