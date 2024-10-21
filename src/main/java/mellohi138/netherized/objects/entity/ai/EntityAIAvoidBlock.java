package mellohi138.netherized.objects.entity.ai;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

/*
* Author: grim3212
* Under CC-BY-NC-SA License https://creativecommons.org/licenses/by-nc-sa/4.0
* Changed by keletu: allow user detect multiple blocks at once.
* */
public class EntityAIAvoidBlock extends EntityAIBase {

    private final EntityCreature theEntityCreature;
    protected double speed;
    private double randPosX;
    private double randPosY;
    private double randPosZ;
    private final List<Block> block;
    private final int amount;

    public EntityAIAvoidBlock(EntityCreature creature, double speed, List<Block> block, int amount) {
        this.theEntityCreature = creature;
        this.speed = speed;
        this.block = block;
        this.amount = amount;
        this.setMutexBits(1);
    }

    public EntityAIAvoidBlock(EntityCreature creature, double speed, List<Block> block) {
        this(creature, speed, block, 1);
    }

    @Override
    public boolean shouldExecute() {
        BlockPos pos = new BlockPos(this.theEntityCreature.posX, this.theEntityCreature.getEntityBoundingBox().minY, this.theEntityCreature.posZ);
        if (!checkForBlocks(pos)) {
            return false;
        } else {

            Vec3d vec3 = this.findPossiblePosition();

            if (vec3 == null) {
                return false;
            } else {
                this.randPosX = vec3.x;
                this.randPosY = vec3.y;
                this.randPosZ = vec3.z;
                return true;
            }

        }
    }

    @Override
    public void startExecuting() {
        this.theEntityCreature.getNavigator().tryMoveToXYZ(this.randPosX, this.randPosY, this.randPosZ, this.speed);
    }

    @Override
    public boolean shouldContinueExecuting() {
        return !this.theEntityCreature.getNavigator().noPath();
    }

    private boolean checkForBlocks(BlockPos pos) {
        int i = 0;
        for (int j = (int) (-3D + pos.getX()); (double) j <= 3D + pos.getX(); j++) {
            for (int k = (int) (-3D + pos.getY()); (double) k <= 3D + pos.getY(); k++) {
                for (int l = (int) (-3D + pos.getZ()); (double) l <= 3D + pos.getZ(); l++) {
                    Block toAvoid = this.theEntityCreature.world.getBlockState(new BlockPos(j, k, l)).getBlock();
                    if (this.block.contains(toAvoid)) {
                        i++;
                    }
                }
            }
        }

        return i >= this.amount;
    }

    private Vec3d findPossiblePosition() {
        Random random = this.theEntityCreature.getRNG();
        BlockPos blockpos = new BlockPos(this.theEntityCreature.posX, this.theEntityCreature.getEntityBoundingBox().minY, this.theEntityCreature.posZ);

        for (int i = 0; i < 10; ++i) {
            BlockPos blockpos1 = blockpos.add(random.nextInt(20) - 10, random.nextInt(6) - 3, random.nextInt(20) - 10);

            if (!this.checkForBlocks(blockpos1) && this.theEntityCreature.getBlockPathWeight(blockpos1) < 0.0F) {
                return new Vec3d(blockpos1.getX(), blockpos1.getY(), blockpos1.getZ());
            }
        }

        return null;
    }
}