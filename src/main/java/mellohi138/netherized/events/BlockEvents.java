package mellohi138.netherized.events;

import mellohi138.netherized.Netherized;
import mellohi138.netherized.init.NetherizedBlocks;
import mellohi138.netherized.util.ModUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

@Mod.EventBusSubscriber(modid = Netherized.MODID)
public class BlockEvents {
	@SubscribeEvent
	public static void convertFireToSoulFire(BlockEvent event) {
		World worldIn = event.getWorld();
		BlockPos pos = event.getPos();
		
		if(event.getState().getBlock() == Blocks.FIRE) {
	    	IBlockState soil = worldIn.getBlockState(pos.down());
	    	if(ModUtils.getSoulBlocks(soil.getBlock())) {
	    		worldIn.setBlockState(pos, NetherizedBlocks.SOUL_FIRE.getDefaultState());
	    	}
		}
	}
	
	@SubscribeEvent
	public static void convertSoulFireToFire(BlockEvent event) {
		World worldIn = event.getWorld();
		BlockPos pos = event.getPos();
		
		if(event.getState().getBlock() == NetherizedBlocks.SOUL_FIRE) {
	    	IBlockState soil = worldIn.getBlockState(pos.down());
	    	if(!ModUtils.getSoulBlocks(soil.getBlock())) {
	    		worldIn.setBlockState(pos, Blocks.FIRE.getDefaultState());
	    	}
		}
	}
	
	@SubscribeEvent
	public static void addNetherrackBonemeal(BonemealEvent event) {
		if(event.getBlock() == Blocks.NETHERRACK.getDefaultState()) {
			BlockPos pos = event.getPos();
			World worldIn = event.getWorld();
			Random rand = worldIn.rand;
			
			if(!worldIn.getBlockState(pos.up()).isSideSolid(worldIn, pos, EnumFacing.UP)) {
				for(BlockPos blockPos : BlockPos.getAllInBoxMutable(pos.add(-1, -1, -1), pos.add(1, 1, 1))) {
					if (worldIn.getBlockState(blockPos).getBlock() == NetherizedBlocks.CRIMSON_NYLIUM || worldIn.getBlockState(blockPos).getBlock() == NetherizedBlocks.WARPED_NYLIUM) {
					      boolean crimsonFlag = false;
					      boolean warpedFlag = false;
					      
				    	  IBlockState blockState = worldIn.getBlockState(blockPos);
				    	  if(blockState.getBlock() == NetherizedBlocks.CRIMSON_NYLIUM) {
				    		  crimsonFlag = true;
				    	  }
				    	  
				    	  if(blockState.getBlock() == NetherizedBlocks.WARPED_NYLIUM) {
				    		  warpedFlag = true;
				    	  }
				    	  
				    	  if(crimsonFlag && warpedFlag) {
				    		  break;
				    	  }
				    	  
					      if (crimsonFlag && warpedFlag) {
					    	  worldIn.setBlockState(pos, rand.nextBoolean() ? NetherizedBlocks.CRIMSON_NYLIUM.getDefaultState() : NetherizedBlocks.WARPED_NYLIUM.getDefaultState());
					      } else if (crimsonFlag) {
					    	  worldIn.setBlockState(pos, NetherizedBlocks.CRIMSON_NYLIUM.getDefaultState());
					      } else if (warpedFlag) {
					    	  worldIn.setBlockState(pos, NetherizedBlocks.WARPED_NYLIUM.getDefaultState());
					      }
				    	  event.setResult(Result.ALLOW);
					}
				}
			}
		}
	}
}
