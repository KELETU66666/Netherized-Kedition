package mellohi138.netherized.world.gen.feature;

import mellohi138.netherized.world.NetherizedWorldGen;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenFossils extends NetherizedWorldGen {

    public WorldGenFossils(String structureName) {
        super("fossils/" + structureName);
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        if(!worldIn.isAirBlock(position.add(4, -1, 4))) {
            return super.generate(worldIn, rand, position);
        }
        return false;
    }
}
