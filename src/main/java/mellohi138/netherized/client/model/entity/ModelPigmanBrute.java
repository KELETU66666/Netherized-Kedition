package mellohi138.netherized.client.model.entity;
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports

import mellohi138.netherized.objects.entity.neutral.EntityPigmanBrute;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumHandSide;

public class ModelPigmanBrute extends ModelBiped {

    public ModelPigmanBrute(float modelSize, boolean p_i1168_2_) {
        super(modelSize, 0.0F, 64, p_i1168_2_ ? 32 : 64);
    }

    public ModelPigmanBrute() {
        this(0.0F, false);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        EntityPigmanBrute piglin = ((EntityPigmanBrute) entityIn);

        if (piglin.getAttackTarget() != null) {
            this.bipedRightArm.rotateAngleZ = 0.0F;
            this.bipedLeftArm.rotateAngleZ = 0.0F;
            this.bipedRightArm.rotateAngleY = 0.15707964F;
            this.bipedLeftArm.rotateAngleY = -0.15707964F;
            if (piglin.getPrimaryHand() == EnumHandSide.RIGHT) {
                this.bipedRightArm.rotateAngleX = -1.8F;
            } else {
                this.bipedLeftArm.rotateAngleX = -1.8F;
            }
        }
    }
}