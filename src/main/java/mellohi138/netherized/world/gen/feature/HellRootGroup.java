package mellohi138.netherized.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class HellRootGroup extends WorldGenerator
{
    private Block block;
    private int GroupSize;

    public HellRootGroup(Block block, final int size) {
        this.GroupSize = size;
        this.block = block;
    }

    public boolean generate(final World world, final Random random, BlockPos pos) {
        for (int l = 0; l < this.GroupSize; ++l) {
            BlockPos pos1 = pos.add(random.nextInt(8) - random.nextInt(8), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(8));
            if (world.isAirBlock(pos1) && block.canPlaceBlockOnSide(world, pos1, EnumFacing.UP)) {
                world.setBlockState(pos1, block.getDefaultState(), 2);
            }
        }
        return true;
    }
}
