package mellohi138.netherized.util;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import mellohi138.netherized.enums.EnumNetherForestType;
import mellohi138.netherized.init.NetherizedBlocks;
import mellohi138.netherized.init.NetherizedEnchantments;
import mellohi138.netherized.util.config.NetherizedEntitiesConfig;
import mellohi138.netherized.util.config.NetherizedGeneralConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class ModUtils {

    public static boolean getItemsForBarter(Item item) {
        for(String itemName : NetherizedEntitiesConfig.itemsForBarter) {
            if(ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName)) == item) {
                return true;
            }
        }
        return false;
    }


    public static Item getOffhandItem() {
        for(String itemName : NetherizedEntitiesConfig.itemsForBarter) {
            return ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName));
        }
        return Items.GOLD_INGOT;
    }

    public static void handleAreaImpact(float radius, Function<Entity, Float> maxDamage, Entity source, Vec3d pos, DamageSource damageSource,
                                        float knockbackFactor, int fireFactor, boolean damageDecay) {
        if (source == null) {
            return;
        }

        List<Entity> list = source.world.getEntitiesWithinAABBExcludingEntity(source, new AxisAlignedBB(pos.x, pos.y, pos.z, pos.x, pos.y, pos.z).grow(radius));

        Predicate<Entity> isInstance = i -> i instanceof EntityLivingBase || i instanceof MultiPartEntityPart || i.canBeCollidedWith();
        double radiusSq = Math.pow(radius, 2);

        list.stream().filter(isInstance).forEach((entity) -> {

            // Get the hitbox size of the entity because otherwise explosions are less
            // effective against larger mobs
            double avgEntitySize = entity.getEntityBoundingBox().getAverageEdgeLength() * 0.75;

            // Choose the closest distance from the center or the head to encourage
            // headshots
            double distance = Math.min(Math.min(getCenter(entity.getEntityBoundingBox()).distanceTo(pos),
                            entity.getPositionVector().add(ModUtils.yVec(entity.getEyeHeight())).distanceTo(pos)),
                    entity.getPositionVector().distanceTo(pos));

            // Subtracting the average size makes it so that the full damage can be dealt
            // with a direct hit
            double adjustedDistance = Math.max(distance - avgEntitySize, 0);
            double adjustedDistanceSq = Math.pow(adjustedDistance, 2);
            double damageFactor = damageDecay ? Math.max(0, Math.min(1, (radiusSq - adjustedDistanceSq) / radiusSq)) : 1;

            // Damage decays by the square to make missed impacts less powerful
            double damageFactorSq = Math.pow(damageFactor, 2);
            double damage = maxDamage.apply(entity) * damageFactorSq;
            if (damage > 0 && adjustedDistanceSq < radiusSq) {
                entity.setFire((int) (fireFactor * damageFactorSq));
                if (entity.attackEntityFrom(damageSource, (float) damage)) {
                    double entitySizeFactor = avgEntitySize == 0 ? 1 : Math.max(0.5, Math.min(1, 1 / avgEntitySize));
                    double entitySizeFactorSq = Math.pow(entitySizeFactor, 2);

                    // Velocity depends on the entity's size and the damage dealt squared
                    Vec3d velocity = getCenter(entity.getEntityBoundingBox()).subtract(pos).normalize().scale(damageFactorSq).scale(knockbackFactor).scale(entitySizeFactorSq);
                    entity.addVelocity(velocity.x, velocity.y, velocity.z);
                }
            }
        });
    }

    public static Vec3d yVec(double heightAboveGround) {
        return new Vec3d(0, heightAboveGround, 0);
    }

    private static Vec3d getCenter(AxisAlignedBB box) {
        return new Vec3d(box.minX + (box.maxX - box.minX) * 0.5D, box.minY + (box.maxY - box.minY) * 0.5D, box.minZ + (box.maxZ - box.minZ) * 0.5D);
    }

    public static Vec3d getVectorForRotation(float pitch, float yaw) {
        float f = MathHelper.cos(-yaw * 0.017453292F - (float) Math.PI);
        float f1 = MathHelper.sin(-yaw * 0.017453292F - (float) Math.PI);
        float f2 = -MathHelper.cos(-pitch * 0.017453292F);
        float f3 = MathHelper.sin(-pitch * 0.017453292F);
        return new Vec3d(f1 * f2, f3, f * f2);
    }

    public static Vec3d getRelativeOffset(EntityLivingBase actor, Vec3d offset) {
        Vec3d look = ModUtils.getVectorForRotation(0, actor.renderYawOffset);
        Vec3d side = look.rotateYaw((float) Math.PI * 0.5f);
        return look.scale(offset.x).add(yVec((float) offset.y)).add(side.scale(offset.z));
    }

    public static BlockPos searchForBlocks(AxisAlignedBB box, World world, Entity entity, IBlockState block) {
        int i = MathHelper.floor(box.minX);
        int j = MathHelper.floor(box.minY);
        int k = MathHelper.floor(box.minZ);
        int l = MathHelper.floor(box.maxX);
        int i1 = MathHelper.floor(box.maxY);
        int j1 = MathHelper.floor(box.maxZ);
        for (int x = i; x <= l; ++x) {
            for (int y = j; y <= i1; ++y) {
                for (int z = k; z <= j1; ++z) {
                    BlockPos blockpos = new BlockPos(x, y, z);
                    IBlockState iblockstate = world.getBlockState(blockpos);


                    if (iblockstate == block) {

                        return blockpos;
                    }
                }
            }
        }

        return null;
    }

    /**
     * Immunity checker. Code copied and edited from IaF ROtN Edition's Myrmex Workers. Credit goes to SandwichHorror, Democat, and Asterixxx
     */
    public static boolean isFireproof(Item item) {
        for (String itemName : NetherizedGeneralConfig.fireproofItemList) {
            if (ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName)) == item) {
                return !NetherizedGeneralConfig.fireproofItemBlacklist;
            }
        }
        return NetherizedGeneralConfig.fireproofItemBlacklist;
    }

    public static boolean isHoeWhitelisted(Block block) {
        for (String blockName : NetherizedGeneralConfig.hoeWhitelistedBlocks) {
            if (ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockName)) == block || block instanceof BlockLeaves) {
                return true;
            }
        }
        return false;
    }

    public static boolean getSoulBlocks(Block block) {
        for (String blockName : NetherizedGeneralConfig.soulBlocks) {
            if (ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockName)) == block) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasSoulSpeed(EntityLivingBase entityIn) {
        return EnchantmentHelper.getMaxEnchantmentLevel(NetherizedEnchantments.SOUL_SPEED, entityIn) > 0;
    }

    /**
     * Calculates a value with the given variable and precentage.
     * <p>
     * 1st value is the value you want to take the precentage of.
     * <p>
     * 2nd value is how much precentage you want to use. This value cannot exceed 100.
     */
    public static float calculateValueWithPrecentage(float precentageOf, float precentageVal) {
        return (precentageOf * Math.min(precentageVal, 100.0F)) / 100.0F;
    }

    /**
     * Takes two values and calcuates a precentage with them. Cannot exceed 100.
     */
    public static float findPrecentageOf(float precentageOf, float precentageVal) {
        return Math.min((precentageVal * 100.0F) / precentageOf, 100.0F);
    }

    /**
     * This piece of code is copied from 1.16 Forge, I own no rights to it.
     */
    public static int getPlantGrowthAmount(Random rand) {
        double d0 = 1.0D;
        int i;
        for (i = 0; rand.nextDouble() < d0; ++i) {
            d0 *= 0.826D;
        }
        return i;
    }

    /*
     * Extra MathHelper function, taken from 1.16 forge.
     */
    public static int nextInt(Random random, int minimum, int maximum) {
        return minimum >= maximum ? minimum : random.nextInt(maximum - minimum + 1) + minimum;
    }

    public static BlockPos.MutableBlockPos createMutablePosOffset(BlockPos pos, int x, int y, int z) {
        return new BlockPos.MutableBlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
    }

    /*
     * Grows Nether vegetation, code taken from 1.16 Forge
     */
    public static boolean growNetherVegetation(World worldIn, Random rand, BlockPos pos, EnumNetherForestType forestType) {
        Block block = worldIn.getBlockState(pos.down()).getBlock();
        if (block == forestType.getVegetationBlocks("nylium")) {
            int i = pos.getY();
            if (i >= 1 && i + 1 < worldIn.getHeight()) {
                int j = 0;

                for (int k = 0; k < 9; ++k) {
                    BlockPos newPos = pos.add(rand.nextInt(3) - rand.nextInt(3), rand.nextInt(1), rand.nextInt(3) - rand.nextInt(3));
                    IBlockState newState = null;
                    if (rand.nextInt(23) == 0) {
                        newState = forestType.getOpposite().getVegetationBlocks("fungus").getDefaultState();
                    } else if (rand.nextInt(11) == 0) {
                        newState = forestType.getVegetationBlocks("fungus").getDefaultState();
                    } else if (rand.nextInt(3) == 0) {
                        newState = forestType.getVegetationBlocks("roots").getDefaultState();
                    }

                    if (newState != null) {
                        if (worldIn.isAirBlock(newPos) && newPos.getY() > 0 && worldIn.getBlockState(newPos.down()).getBlock() == forestType.getVegetationBlocks("nylium")) {
                            worldIn.setBlockState(newPos, newState);
                            ++j;
                        }
                    }
                }
                return j > 0;
            }
        }
        return false;
    }

    public static int getAverageGroundHeight(World world, int x, int z, int sizeX, int sizeZ, int maxVariation) {
        sizeX = x + sizeX;
        sizeZ = z + sizeZ;
        int corner1 = calculateGenerationHeight(world, x, z);
        int corner2 = calculateGenerationHeight(world, sizeX, z);
        int corner3 = calculateGenerationHeight(world, x, sizeZ);
        int corner4 = calculateGenerationHeight(world, sizeX, sizeZ);

        int max = Math.max(Math.max(corner3, corner4), Math.max(corner1, corner2));
        int min = Math.min(Math.min(corner3, corner4), Math.min(corner1, corner2));
        if (max - min > maxVariation) {
            return -1;
        }
        return min;
    }

    public static int calculateGenerationHeight(World world, int x, int z) {
        return world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z)).getY();
    }

    public static void replaceBlocksInRandCircle(World world, int radius, double x, double y, double z) {

        //This is for the Conversion to Ash Wastelands blocks
        List<BlockPos> affectedConversionPositions = Lists.newArrayList();
        int radius_int_conversion = (int) Math.ceil(radius);

        for (int dx = -radius_int_conversion; dx < radius_int_conversion + 1; dx++) {
            // fast calculate affected blocks
            int y_lim = (int) Math.sqrt(radius_int_conversion * radius_int_conversion - dx * dx);
            for (int dy = -y_lim; dy < y_lim + 1; dy++) {
                int z_lim = (int) Math.sqrt(radius_int_conversion * radius_int_conversion - dx * dx - dy * dy);
                for (int dz = -z_lim; dz < z_lim + 1; dz++) {
                    BlockPos blockPos = new BlockPos(x + dx, y + dy, z + dz);
                    double power = interperetVar(Math.sqrt(dx * dx + dy * dy + dz * dz), radius);
                    if ((power > 1) || (power > new Random().nextDouble())) {
                        affectedConversionPositions.add(blockPos);
                    }
                }
            }
        }

        for (BlockPos blockPos : affectedConversionPositions) {
            if (world.getBlockState(blockPos).getBlock() == NetherizedBlocks.SOUL_SOIL) {
                world.setBlockState(blockPos, Blocks.SOUL_SAND.getDefaultState());
            }
        }
    }

    public static double interperetVar(double dist, double radius) {
        double decay_rd = radius * 0.95;
        if (dist < decay_rd) {
            return 1.1d;
        } else {
            return -(1 / (radius - decay_rd)) * (dist - decay_rd) + 1;
        }
    }
}