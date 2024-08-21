package mellohi138.netherized.util;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BlockSplatter extends WorldGenerator
{
    private Block splotchBlock;
    private int splotchBlockMeta;
    private int numberOfBlocks;
    private List<Block> blockList;
    
    public BlockSplatter(final Block generatedBlock, final int BlockMeta, final int numberOfBlocks, final Block... blockList) {
        super(true);
        this.splotchBlock = generatedBlock;
        this.splotchBlockMeta = BlockMeta;
        this.numberOfBlocks = numberOfBlocks;
        this.blockList = Arrays.asList(blockList);
    }
    
    public boolean generate(final World world, final Random random, BlockPos pos) {
        for (int l = 0; l < this.numberOfBlocks; ++l) {
            final int i1 = pos.getX() + random.nextInt(6) - random.nextInt(6);
            final int j1 = pos.getY() + random.nextInt(4) - random.nextInt(4);
            final int k1 = pos.getZ() + random.nextInt(6) - random.nextInt(6);
            if (world.isAirBlock(new BlockPos(i1, j1, k1)) && this.blockList.contains(world.getBlockState(new BlockPos(i1, j1 - 1, k1)).getBlock())) {
                world.setBlockState(new BlockPos(i1, j1 - 1, k1), this.splotchBlock.getStateFromMeta(this.splotchBlockMeta), 2);
            }
        }
        return true;
    }
}
