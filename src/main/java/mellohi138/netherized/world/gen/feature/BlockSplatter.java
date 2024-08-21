// Decompiled with: Procyon 0.6.0
// Class Version: 8
package mellohi138.netherized.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BlockSplatter extends WorldGenerator {
    private Block splotchBlock;
    private int splotchBlockMeta;
    private int numberOfBlocks;
    private List blockList;

    public BlockSplatter(final Block generatedBlock, final int BlockMeta, final int numberOfBlocks, final Block... blockList) {
        super(true);
        this.splotchBlock = generatedBlock;
        this.splotchBlockMeta = BlockMeta;
        this.numberOfBlocks = numberOfBlocks;
        this.blockList = Arrays.asList(blockList);
    }

    public boolean generate(final World world, final Random random, BlockPos pos) {
        for (int l = 0; l < this.numberOfBlocks; ++l) {
            BlockPos pos1 = pos.add(random.nextInt(6) - random.nextInt(6), random.nextInt(4) - random.nextInt(4), random.nextInt(6) - random.nextInt(6));

            if (world.isAirBlock(pos1) && this.blockList.contains(world.getBlockState(pos.down()).getBlock())) {
                world.setBlockState(pos.down(), this.splotchBlock.getStateFromMeta(this.splotchBlockMeta), 2);
            }
        }
        return true;
    }
}
