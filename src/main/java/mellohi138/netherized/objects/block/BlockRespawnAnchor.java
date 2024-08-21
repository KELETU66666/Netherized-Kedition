package mellohi138.netherized.objects.block;

import mellohi138.netherized.Netherized;
import mellohi138.netherized.enums.EnumNetherizedParticles;
import mellohi138.netherized.init.NetherizedSounds;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockRespawnAnchor extends BlockBase {
	public static final PropertyInteger CHARGE = PropertyInteger.create("charges", 0, 4);
			
	public BlockRespawnAnchor(String name, Material material, MapColor color, String usedTool, int toolStrength, SoundType soundType, CreativeTabs tab) {
		super(name, material, color, usedTool, toolStrength, soundType, tab, false);
		
        this.setDefaultState(this.blockState.getBaseState().withProperty(CHARGE, Integer.valueOf(0)));
	}
	
	@Override
    public IBlockState getStateFromMeta(int meta) {
    	return this.getDefaultState().withProperty(CHARGE, Integer.valueOf(MathHelper.clamp(meta, 0, 4)));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
    	return ((Integer)state.getValue(CHARGE)).intValue();
    }
    
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {	
        		CHARGE
        });
    }
    
	@Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
    	if (stateIn.getValue(CHARGE) != 0) {
            if (rand.nextInt(100) == 0) {
               worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, NetherizedSounds.BLOCK_RESPAWN_ANCHOR_AMBIENCE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            }
            
            double d0 = (double)pos.getX() + 0.5D + (0.5D - rand.nextDouble());
            double d1 = (double)pos.getY() + 1.0D;
            double d2 = (double)pos.getZ() + 0.5D + (0.5D - rand.nextDouble());
            double d3 = (double)rand.nextFloat() * 0.04D;
            Netherized.PROXY.spawnParticle(EnumNetherizedParticles.REVERSED_PORTAL, worldIn, d0, d1, d2, 0.0D, d3, 0.0D);
    	}
    }
	
    @Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = playerIn.getHeldItem(hand);
		
		if(playerIn.isSneaking() && state.getValue(CHARGE) != 0) {
			if(!playerIn.isCreative()) playerIn.inventory.addItemStackToInventory(new ItemStack(Blocks.GLOWSTONE));
			worldIn.setBlockState(pos, state.withProperty(CHARGE, Integer.valueOf(state.getValue(CHARGE) - 1)), 3);
			worldIn.playSound((EntityPlayer)null, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1.0F, 1.0F);
			return true;
		} else if(state.getValue(CHARGE) < 4 && stack.getItem() == Item.getItemFromBlock(Blocks.GLOWSTONE)) {
			worldIn.setBlockState(pos, state.cycleProperty(CHARGE));
			worldIn.playSound((EntityPlayer)null, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, NetherizedSounds.BLOCK_RESPAWN_ANCHOR_CHARGE, SoundCategory.BLOCKS, 1.0F, 1.0F);
			if (!playerIn.isCreative()) stack.shrink(1); 
			return true;
		} else if(state.getValue(CHARGE) != 0) {
			if(worldIn.provider.getDimension() == DimensionType.NETHER.getId()) {
				playerIn.setSpawnDimension(DimensionType.NETHER.getId());
				playerIn.setSpawnPoint(pos.offset(facing), true);
				worldIn.setBlockState(pos, state.withProperty(CHARGE, Integer.valueOf(state.getValue(CHARGE) - 1)));
				worldIn.playSound((EntityPlayer)null, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, NetherizedSounds.BLOCK_RESPAWN_ANCHOR_SPAWN_SET, SoundCategory.BLOCKS, 1.0F, 1.0F);
				return true;
			} else {
				worldIn.destroyBlock(pos, false);
				worldIn.newExplosion((Entity)null, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, 5.0F, true, true);
				worldIn.playSound((EntityPlayer)null, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, NetherizedSounds.BLOCK_RESPAWN_ANCHOR_DEPLETE, SoundCategory.BLOCKS, 1.0F, 1.0F);
				return true;
			}
		}
		
		return false;
	}
}