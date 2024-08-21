package mellohi138.netherized.world.gen.feature;

import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class NetherPlantGroup extends WorldGenerator
{
    private Block Plant;
    private int PlantMeta;
    private int GroupSize;
    
    public NetherPlantGroup(final Block block, final int meta, final int size) {
        this.Plant = block;
        this.PlantMeta = meta;
        this.GroupSize = size;
    }
    
    public boolean generate(final World world, final Random random, BlockPos pos) {
        for (int l = 0; l < this.GroupSize; ++l) {
            BlockPos pos1 = pos.add(random.nextInt(8) - random.nextInt(8), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(8));
            if (world.isAirBlock(pos1) && this.Plant.canPlaceBlockOnSide(world, pos1, EnumFacing.UP)) {
                world.setBlockState(pos1, this.Plant.getStateFromMeta(this.PlantMeta), 2);
            }
        }
        return true;
    }
}
