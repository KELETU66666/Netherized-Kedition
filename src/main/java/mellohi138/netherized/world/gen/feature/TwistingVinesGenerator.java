package mellohi138.netherized.world.gen.feature;

import mellohi138.netherized.init.NetherizedBlocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class TwistingVinesGenerator extends WorldGenerator
{
    public boolean generate(final World world, final Random random, BlockPos pos) {
        final int var999 = random.nextInt(4);
        if (var999 == 0) {
            for (int l = 0; l < 10; ++l) {
                final BlockPos pos1 = pos.add(random.nextInt(8) - random.nextInt(8), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(8));
                if ((world.isAirBlock(pos1) && world.getBlockState(pos1.down()).getBlock() == NetherizedBlocks.WARPED_NYLIUM) || (world.isAirBlock(pos1) && world.getBlockState(pos1.down()).getBlock() == NetherizedBlocks.WARPED_WART_BLOCK)) {
                    world.setBlockState(pos1, NetherizedBlocks.TWISTING_VINES.getDefaultState(), 2);
                }
            }
        }
        if (var999 == 1) {
            for (int l = 0; l < 10; ++l) {
                final BlockPos pos1 = pos.add(random.nextInt(8) - random.nextInt(8), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(8));
                if ((world.isAirBlock(pos1) && world.isAirBlock(pos1.up()) && world.getBlockState(pos1.down()).getBlock() == NetherizedBlocks.WARPED_NYLIUM) || (world.isAirBlock(pos1) && world.isAirBlock(pos1.up()) && world.getBlockState(pos1.down()).getBlock() == NetherizedBlocks.WARPED_WART_BLOCK)) {
                    world.setBlockState(pos1, NetherizedBlocks.TWISTING_VINES.getStateFromMeta(1), 2);
                    world.setBlockState(pos1.up(), NetherizedBlocks.TWISTING_VINES.getDefaultState(), 2);
                }
            }
        }
        if (var999 == 2) {
            for (int l = 0; l < 10; ++l) {
                final BlockPos pos1 = pos.add(random.nextInt(8) - random.nextInt(8), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(8));
                if ((world.isAirBlock(pos1) && world.isAirBlock(pos1.up()) && world.isAirBlock(pos1.up(2)) && world.getBlockState(pos1.down()).getBlock() == NetherizedBlocks.WARPED_NYLIUM) || (world.isAirBlock(pos1) && world.isAirBlock(pos1.up()) && world.isAirBlock(pos1.up(2)) && world.getBlockState(pos1.down()).getBlock() == NetherizedBlocks.WARPED_WART_BLOCK)) {
                    world.setBlockState(pos1, NetherizedBlocks.TWISTING_VINES.getStateFromMeta(1), 2);
                    world.setBlockState(pos1.up(), NetherizedBlocks.TWISTING_VINES.getStateFromMeta(1), 2);
                    world.setBlockState(pos1.up(2), NetherizedBlocks.TWISTING_VINES.getDefaultState(), 2);
                }
            }
        }
        else {
            for (int l = 0; l < 10; ++l) {
                final BlockPos pos1 = pos.add(random.nextInt(8) - random.nextInt(8), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(8));
                if ((world.isAirBlock(pos1) && world.isAirBlock(pos1.up()) && world.isAirBlock(pos1.up(2)) && world.isAirBlock(pos1.up(3)) && world.getBlockState(pos1.down()).getBlock() == NetherizedBlocks.WARPED_NYLIUM) || (world.isAirBlock(pos1) && world.isAirBlock(pos1.up()) && world.isAirBlock(pos1.up(2)) && world.isAirBlock(pos1.up(3)) && world.getBlockState(pos1.down()).getBlock() == NetherizedBlocks.WARPED_WART_BLOCK)) {
                    world.setBlockState(pos1, NetherizedBlocks.TWISTING_VINES.getStateFromMeta(1), 2);
                    world.setBlockState(pos1.up(), NetherizedBlocks.TWISTING_VINES.getStateFromMeta(1), 2);
                    world.setBlockState(pos1.up(2), NetherizedBlocks.TWISTING_VINES.getStateFromMeta(1), 2);
                    world.setBlockState(pos1.up(3), NetherizedBlocks.TWISTING_VINES.getDefaultState(), 2);
                }
            }
        }
        return true;
    }
}
