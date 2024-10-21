package mellohi138.netherized.world.gen.feature;

import mellohi138.netherized.init.NetherizedBlocks;
import mellohi138.netherized.objects.block.BlockSoulFire;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenSoulFire extends WorldGenerator {
    private final IBlockState plantState;
    private final BlockSoulFire plantBlock;

    public WorldGenSoulFire(IBlockState plant)
    {
        this.plantState = plant;
        this.plantBlock = (BlockSoulFire) plant.getBlock();
    }

    public boolean generate(World world, Random rand, BlockPos pos)
    {
        if(world.getBlockState(pos) == NetherizedBlocks.SOUL_SOIL.getDefaultState()) {
            world.setBlockState(pos.up(), plantState);
        }
        return true;
    }
}
