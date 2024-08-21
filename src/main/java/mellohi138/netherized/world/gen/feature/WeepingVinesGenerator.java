package mellohi138.netherized.world.gen.feature;

import mellohi138.netherized.init.NetherizedBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WeepingVinesGenerator extends WorldGenerator
{
    public boolean generate(final World world, final Random random, BlockPos pos) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        BlockPos pos1 = new BlockPos(x, y, z);
        while (world.isAirBlock(pos1)) {
            ++y;
        }
        final Block block = world.getBlockState(new BlockPos(x, y + 1, z)).getBlock();
        if (!NetherizedBlocks.WEEPING_VINES.canPlaceBlockAt(world, pos1) || block == Blocks.NETHER_WART_BLOCK) {
            return false;
        }
        for (int iy = 1; iy <= random.nextInt(6) + 1 && world.isAirBlock(pos1.add(0, - iy - 1, 0)); ++iy) {
            world.setBlockState(pos1.add(0, - iy, 0), NetherizedBlocks.WEEPING_VINES.getStateFromMeta(1), 2);
            world.setBlockState(pos1.add(0, - iy - 1, 0), NetherizedBlocks.WEEPING_VINES.getStateFromMeta(0), 2);
        }
        return true;
    }
    
    public static boolean generateFungi(final World world, final Random random, BlockPos pos) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        BlockPos pos1 = new BlockPos(x, y, z);
        while (world.isAirBlock(pos1)) {
            ++y;
        }
        final Block block = world.getBlockState(pos1.add(0, 1, 0)).getBlock();
        for (int iy = 1; iy <= random.nextInt(6) + 1 && world.isAirBlock(pos1.add(0, - iy - 1, 0)); ++iy) {
            world.setBlockState(pos1.add(0, - iy, 0), NetherizedBlocks.WEEPING_VINES.getStateFromMeta(1), 2);
            world.setBlockState(pos1.add(0, - iy - 1, 0), NetherizedBlocks.WEEPING_VINES.getStateFromMeta(0), 2);
        }
        return true;
    }
}
