package mellohi138.netherized.world.gen.feature;

import mellohi138.netherized.init.NetherizedBlocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WarpedGrassGroup extends WorldGenerator
{
    private int GroupSize;

    public WarpedGrassGroup(final int size) {
        this.GroupSize = size;
    }

    public boolean generate(final World world, final Random random, BlockPos pos) {
        for (int l = 0; l < this.GroupSize; ++l) {
            BlockPos pos1 = pos.add(random.nextInt(8) - random.nextInt(8), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(8));
            if (world.isAirBlock(pos1) && NetherizedBlocks.WARPED_SPROUTS.canPlaceBlockOnSide(world, pos1, EnumFacing.UP)) {
                world.setBlockState(pos1, NetherizedBlocks.WARPED_SPROUTS.getDefaultState(), 2);
            }
        }
        return true;
    }
}
