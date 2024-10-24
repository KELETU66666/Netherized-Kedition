package mellohi138.netherized.world.gen.bastion;

import mellohi138.netherized.util.config.NetherizedGeneralConfig;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureStart;

import java.util.Random;

public class WorldGenBastion extends WorldGenerator {
    private int spacing = 0;

    public WorldGenBastion() {

    }



    @Override
    public boolean generate(World world, Random random, BlockPos pos) {
        /**
         * MAKE THIS CONFIGURABLE, SPACING IS THE AMOUNT OF EACH TIME IT IS CAPAPABLE OF GENERATING
         */
        //old 525
        if((spacing / 6) > NetherizedGeneralConfig.bastionFrequency) {
            getStructureStart(world, pos.getX() >> 4, pos.getZ() >> 4, random)
                    .generateStructure(world, random, new StructureBoundingBox(pos.getX() - 150, pos.getZ() - 150, pos.getX() + 150, pos.getZ() + 150));
            return true;
        }
        spacing++;
        return false;
    }


    protected StructureStart getStructureStart(World world, int chunkX, int chunkZ, Random rand) {
        spacing = 0;
        return new WorldGenBastion.Start(world, rand , chunkX, chunkZ);
    }


    public static class Start extends StructureStart {

        public Start() {

        }

        public Start(World worldIn, Random rand, int chunkX, int chunkZ) {
            super(chunkX, chunkZ);
            this.createChambers(worldIn, rand, chunkX, chunkZ);
        }


        protected void createChambers(World world, Random rand, int chunkX, int chunkZ) {
            Random random = new Random(chunkX + chunkZ * 10387313L);
            int rand2 = random.nextInt(Rotation.values().length);
            BlockPos posI = new BlockPos(chunkX * 16 + 8, 0, chunkZ * 16 + 8);

            //You can add more additional parameters or second checks to things before it starts the physical structure
            for(int i = 0; i < 4; i++) {
                Rotation rotation = Rotation.values()[(rand2 + i) % Rotation.values().length];
                components.clear();
                //Set IAW with the best Position to spawn the first layer of the chambers at
                BlockPos blockpos = posI.add(0, NetherizedGeneralConfig.bastionYLevel, 0);
                BastionRemnants bastion = new BastionRemnants(world, world.getSaveHandler().getStructureTemplateManager(), components);
                //Starts the first room within the Trial Chambers
                bastion.startBastion(blockpos, rotation);
                this.updateBoundingBox();

                if (this.isSizeableStructure()) {

                    break;
                }
            }
        }


        @Override
        public void generateStructure(World worldIn, Random rand, StructureBoundingBox structurebb)
        {
            super.generateStructure(worldIn, rand, structurebb);
        }

        //Here is where you specify the size of the structure in terms of rooms, I guess you could make this configurable if you want
        @Override
        public boolean isSizeableStructure() {
            return components.size() > 2;
        }
    }
}