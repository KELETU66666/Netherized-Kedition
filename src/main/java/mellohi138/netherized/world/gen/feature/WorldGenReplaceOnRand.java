package mellohi138.netherized.world.gen.feature;

import mellohi138.netherized.util.ModRand;
import mellohi138.netherized.util.ModUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenReplaceOnRand extends WorldGenerator {

    public WorldGenReplaceOnRand() {

    }
    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        ModUtils.replaceBlocksInRandCircle(worldIn, 5 + ModRand.range(3, 9), position.getX(), position.getY(), position.getZ());
        return true;
    }

}
