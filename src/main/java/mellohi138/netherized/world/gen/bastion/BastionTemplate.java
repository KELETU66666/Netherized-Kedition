package mellohi138.netherized.world.gen.bastion;

import mellohi138.netherized.Netherized;
import mellohi138.netherized.objects.entity.neutral.EntityHoglin;
import mellohi138.netherized.objects.entity.neutral.EntityPigman;
import mellohi138.netherized.objects.entity.neutral.EntityPigmanBrute;
import mellohi138.netherized.util.ModRand;
import mellohi138.netherized.util.config.NetherizedGeneralConfig;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.Random;

public class BastionTemplate extends NetherStructureTemplate {

    private static final ResourceLocation LOOT_TOWERS = new ResourceLocation(Netherized.MODID, "pig_towers");
    private static final ResourceLocation LOOT_STRONGHOLD = new ResourceLocation(Netherized.MODID, "bastion_hold");

    private static final ResourceLocation LOOT_STRONGHOLD_FUTUREMC = new ResourceLocation(Netherized.MODID, "bastion_hold_futuremc");
    private static final ResourceLocation LOOT_TREASURE = new ResourceLocation(Netherized.MODID, "bastion_treasure");

    private static final ResourceLocation LOOT_TREASURE_FUTUREMC = new ResourceLocation(Netherized.MODID, "bastion_treasure_futuremc");

    public BastionTemplate(TemplateManager manager, String type, BlockPos pos, Rotation rot, int distance, boolean overWriteIn) {
        super(manager, type, pos,distance, rot, overWriteIn);

    }

    @Override
    protected void handleDataMarker(String function, BlockPos pos, World world, Random rand, StructureBoundingBox sbb) {
        //Loot tables and chests
        if(function.startsWith("chest_hold")) {
            BlockPos blockPos = pos.down();
            if(generateHoldRegular() && sbb.isVecInside(blockPos)) {

                TileEntity tileEntity = world.getTileEntity(blockPos);
                world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                if (tileEntity instanceof TileEntityChest) {
                    TileEntityChest chest = (TileEntityChest) tileEntity;
                    chest.setLootTable(LOOT_STRONGHOLD, rand.nextLong());
                }
            } else {
                world.setBlockToAir(pos);
                world.setBlockToAir(pos.down());
            }
        } else if (function.startsWith("chest_house")){
            BlockPos blockPos = pos.down();
            if(generateHoldRegular() && sbb.isVecInside(blockPos)) {

                TileEntity tileEntity = world.getTileEntity(blockPos);
                world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                if (tileEntity instanceof TileEntityChest) {
                    TileEntityChest chest = (TileEntityChest) tileEntity;
                    chest.setLootTable(LOOT_STRONGHOLD, rand.nextLong());
                }
            } else {
                world.setBlockToAir(pos);
                world.setBlockToAir(pos.down());
            }
        } else if (function.startsWith("chest")) {
            BlockPos blockPos = pos.down();
            if(generateRegularChest() && sbb.isVecInside(blockPos)) {

                TileEntity tileEntity = world.getTileEntity(blockPos);
                world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                if (tileEntity instanceof TileEntityChest) {
                    TileEntityChest chest = (TileEntityChest) tileEntity;
                    chest.setLootTable(LOOT_TOWERS, rand.nextLong());
                }
            } else {
                world.setBlockToAir(pos);
                world.setBlockToAir(pos.down());
            }
        } else if (function.startsWith("treasure")) {
            BlockPos blockPos = pos.down();
            if(sbb.isVecInside(blockPos)) {
                TileEntity tileEntity = world.getTileEntity(blockPos);
                world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                if (tileEntity instanceof TileEntityChest) {
                    TileEntityChest chest = (TileEntityChest) tileEntity;
                    chest.setLootTable(LOOT_TREASURE, rand.nextLong());
                }
            } else {
                world.setBlockToAir(pos);
                world.setBlockToAir(pos.down());
            }
        }
        //End Chests

        //Mobs
        if(function.startsWith("hoglin")) {
            EntityHoglin hoglin = new EntityHoglin(world);
            hoglin.setInsideBastion(true);
            world.setBlockToAir(pos);
            hoglin.setPosition(pos.getX(), pos.getY() + 1, pos.getZ());
            world.spawnEntity(hoglin);
            //Piglins and Brutes
        } else if (function.startsWith("mob")) {
            if(generateMobSpawn()) {
                //Spawn Brute
                if (world.rand.nextInt(5) == 0) {
                    EntityPigmanBrute brute = new EntityPigmanBrute(world);
                    brute.setInsideBastion(true);
                    world.setBlockToAir(pos);
                    brute.setPosition(pos.getX(), pos.getY() + 1, pos.getZ());
                    world.spawnEntity(brute);
                } else {
                    //Spawn Piglin
                    EntityPigman piglin = new EntityPigman(world);
                    piglin.isNoDespawnRequired();
                    world.setBlockToAir(pos);
                    piglin.setPosition(pos.getX(), pos.getY() + 1, pos.getZ());
                    world.spawnEntity(piglin);
                }
            } else {
                world.setBlockToAir(pos);
            }
        }

    }

    private boolean generateMobSpawn() {
        int random = ModRand.range(1, 11);
        if(random >= NetherizedGeneralConfig.bastionSpawnRate) {
            return true;
        }
        return false;
    }

    private boolean generateRegularChest() {
        int random = ModRand.range(1, 10);
        if(random >= NetherizedGeneralConfig.bastionregularChestChance) {
            return true;
        }
        return false;
    }

    private boolean generateHoldRegular() {
        int random = ModRand.range(1, 10);
        if(random >= NetherizedGeneralConfig.bastionHoldChestChance) {
            return true;
        }
        return false;
    }

    @Override
    public String templateLocation() {
        return "bastion";
    }
}