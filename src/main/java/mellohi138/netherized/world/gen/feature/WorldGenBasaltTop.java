package mellohi138.netherized.world.gen.feature;

import git.jbredwards.nether_api.mod.common.config.NetherAPIConfig;
import mellohi138.netherized.init.NetherizedBlocks;
import mellohi138.netherized.util.ModRand;
import mellohi138.netherized.world.NetherizedWorldGen;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldGenBasaltTop extends NetherizedWorldGen {

    public WorldGenBasaltTop(String structureName) {
        super("basalt/" + structureName);
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        int yVar = getNetherSurfaceHeight(worldIn, position.add(3, 1, 3), 32, NetherAPIConfig.tallNether ? 240 : 120);
        if(yVar != 0) {
            replaceandBuildPillar(worldIn, new BlockPos(position.getX() + 3, position.getY() + 1, position.getZ() + 3), rand, yVar);
               return super.generate(worldIn, rand, new BlockPos(position.getX(), 31, position.getZ()));
        }
        return false;
    }


    private void replaceandBuildPillar(World world, BlockPos pos, Random rand, double highYHeight) {
        int isDouble = ModRand.range(1, 5);
        int isTriple = ModRand.range(1, 6);
        for(int i = pos.getY(); i <= highYHeight; i++) {
            BlockPos modifiedPos = new BlockPos(pos.getX(), i, pos.getZ());
            world.setBlockState(modifiedPos, NetherizedBlocks.BASALT.getDefaultState());

            if(isTriple == 5) {
                world.setBlockState(modifiedPos.add(1, 0,0), NetherizedBlocks.BASALT.getDefaultState());
            }
            if(isDouble < 3) {
                world.setBlockState(modifiedPos.add(0,0,-1), NetherizedBlocks.BASALT.getDefaultState());
            }
        }

    }


    private int getNetherSurfaceHeight(World world, BlockPos pos, int min, int max)
    {
        int maxY = max;
        int minY = min;
        int currentY = minY;

        while(currentY <= maxY)
        {
            if(!world.isAirBlock(pos.add(0, currentY, 0)))
                return currentY;
            currentY++;
        }
        return 0;
    }
}