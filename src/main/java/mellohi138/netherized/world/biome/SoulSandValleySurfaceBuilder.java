package mellohi138.netherized.world.biome;

import mellohi138.netherized.init.NetherizedBlocks;
import mellohi138.netherized.world.CustomValleySurfaceBuilder;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class SoulSandValleySurfaceBuilder extends CustomValleySurfaceBuilder {
    private static final IBlockState field_237180_a_ = Blocks.SOUL_SAND.getDefaultState();
    private static final IBlockState field_237181_b_ = NetherizedBlocks.SOUL_SOIL.getDefaultState();
    private static final IBlockState field_237182_c_ = Blocks.GRAVEL.getDefaultState();
    private static final IBlockState[] field_237183_d_ = new IBlockState[]{field_237180_a_, field_237181_b_};

    public SoulSandValleySurfaceBuilder(World world, boolean generateStructures, long seed) {
        super(world, generateStructures, seed);
    }

    protected IBlockState[] getTopBlocks() {
        return field_237183_d_;
    }

    protected IBlockState[] getFillBlocks() {
        return field_237183_d_;
    }

    protected IBlockState getSpecialBlockState() {
        return field_237182_c_;
    }
}