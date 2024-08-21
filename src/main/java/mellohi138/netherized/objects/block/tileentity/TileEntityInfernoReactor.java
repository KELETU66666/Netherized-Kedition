package mellohi138.netherized.objects.block.tileentity;

import mellohi138.netherized.init.NetherizedBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class TileEntityInfernoReactor extends TileEntity implements ITickable {
	@Override
	public void update() {
        if (this.world.getTotalWorldTime() % 80L == 0L) {
    		this.updateReactor();
        }
	}
	
	protected void updateReactor() {
        if (this.world != null && !this.world.isRemote) {
        	if(this.isAssembled()) {
        		this.setReactor(true);
        		this.addEffectsToPlayers();
        	} else {
        		this.setReactor(false);
        	}
        }
	}
	
	@SuppressWarnings("deprecation")
	private void setReactor(boolean active) {
		IBlockState state = NetherizedBlocks.INFERNO_REACTOR.getStateFromMeta(active ? 1 : 0);
		if(world.getBlockState(pos) != state) {
			world.setBlockState(pos, state);
		}
	}
    
    private void addEffectsToPlayers() {
        if (!this.world.isRemote) {
            int x = this.pos.getX();
            int y = this.pos.getY();
            int z = this.pos.getZ();
            AxisAlignedBB axisAlignedBB = new AxisAlignedBB(x, y, z, (x + 1), (y + 1), (z + 1)).grow(48F).expand(0.0F, this.world.getHeight(), 0.0F);
            List<EntityPlayer> list = this.world.<EntityPlayer>getEntitiesWithinAABB(EntityPlayer.class, axisAlignedBB);

            for (EntityPlayer player : list) {
            	player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 140, 0, true, true));
            }
        }
    }
    
    private boolean checkCorners(Block block, BlockPos pos) {
        return this.world.getBlockState(pos.north().east()).getBlock() == block &&
        		this.world.getBlockState(pos.north().west()).getBlock() == block &&
        		this.world.getBlockState(pos.south().east()).getBlock() == block &&		
        		this.world.getBlockState(pos.south().west()).getBlock() == block;
    }
    
    private boolean checkSides(Block block, BlockPos pos) {
        return this.world.getBlockState(pos.north()).getBlock() == block &&
        		this.world.getBlockState(pos.east()).getBlock() == block &&
        		this.world.getBlockState(pos.west()).getBlock() == block &&	
                this.world.getBlockState(pos.south()).getBlock() == block &&	
                this.world.getBlockState(pos).getBlock() == block;
    }
    
    private boolean checkSides(Block block, Block reactor, BlockPos pos) {
        return this.world.getBlockState(pos.north()).getBlock() == block &&
        		this.world.getBlockState(pos.east()).getBlock() == block &&
        		this.world.getBlockState(pos.west()).getBlock() == block &&	
                this.world.getBlockState(pos.south()).getBlock() == block &&	
                this.world.getBlockState(pos).getBlock() == reactor;
    }

    public boolean isAssembled() {
    	return this.checkCorners(Blocks.GOLD_BLOCK, pos.down()) && this.checkCorners(Blocks.OBSIDIAN, pos) && this.checkCorners(Blocks.GOLD_BLOCK, pos.up()) &&
    			this.checkSides(Blocks.OBSIDIAN, pos.down()) && this.checkSides(Blocks.AIR, NetherizedBlocks.INFERNO_REACTOR, pos) && this.checkSides(Blocks.OBSIDIAN, pos.up());
    }
}
