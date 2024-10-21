package mellohi138.netherized.client.model.entity;
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports

import mellohi138.netherized.objects.entity.neutral.EntityPigman;
import mellohi138.netherized.util.ModUtils;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBow;
import net.minecraft.util.EnumHandSide;

public class ModelPigman extends ModelBiped {

	public ModelPigman(float modelSize, boolean p_i1168_2_) {
		super(modelSize, 0.0F, 64, p_i1168_2_ ? 32 : 64);
	}

	public ModelPigman() {
		this(0.0F, false);
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
	{
		super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
		EntityPigman piglin = ((EntityPigman) entityIn);

		if(piglin.getAttackTarget() != null){
			if(!(piglin.getHeldItemMainhand().getItem() instanceof ItemBow)) {
				this.bipedRightArm.rotateAngleZ = 0.0F;
				this.bipedLeftArm.rotateAngleZ = 0.0F;
				this.bipedRightArm.rotateAngleY = 0.15707964F;
				this.bipedLeftArm.rotateAngleY = -0.15707964F;
				if (piglin.getPrimaryHand() == EnumHandSide.RIGHT) {
					this.bipedRightArm.rotateAngleX = -1.8F;
				} else {
					this.bipedLeftArm.rotateAngleX = -1.8F;
				}
			}else{
				animateCrossbowHold(bipedRightArm, bipedLeftArm, bipedHead, piglin.getPrimaryHand() == EnumHandSide.LEFT);
			}


		} else if(piglin.getHeldItemOffhand().getItem() == ModUtils.getOffhandItem()){
			this.bipedHead.rotateAngleX = 0.5F;
			this.bipedHead.rotateAngleY = 0.0F;
			this.bipedHeadwear.rotateAngleX = 0.5F;
			this.bipedHeadwear.rotateAngleY = 0.0F;
			if(piglin.getPrimaryHand() == EnumHandSide.LEFT) {
				this.bipedRightArm.rotateAngleY = -0.5F;
				this.bipedRightArm.rotateAngleX = -0.9F;
			}else{
				this.bipedLeftArm.rotateAngleY = 0.5F;
				this.bipedLeftArm.rotateAngleX = -0.9F;
			}
		}

		//Arm Movements
		//if(pigling.isLoadedACrossBow()) {
		//	//animator.rotate(bipedLeftArm, (float) Math.toRadians(-80),(float) Math.toRadians(30),0);
		//	//animator.rotate(bipedRightArm, (float) Math.toRadians(-80), (float) Math.toRadians(-20), 0);
		//	bipedRightArm.rotateAngleX = (-(float)Math.PI / 2F) + bipedHead.rotateAngleX + 0.1F;
		//	bipedRightArm.rotateAngleY = -0.3F + bipedHead.rotateAngleY;
		//	bipedLeftArm.rotateAngleX = -1.5F + bipedHead.rotateAngleX;
		//	bipedLeftArm.rotateAngleY = 0.6F + bipedHead.rotateAngleY;
//
		//} else if(!pigling.isMeleeAttack() && !pigling.isRangedAttack() && !pigling.isLoadedACrossBow()) {
		//	this.walk(bipedRightArm, walkSpeed, walkDegree, true, 0F, 0.1F, limbSwing, limbSwingAmount);
		//	this.walk(bipedLeftArm, walkSpeed, walkDegree, false, 0F, 0.1F, limbSwing, limbSwingAmount);
		//}
		////Body Bobbing
		//float bodyBob = EZMath.walkValue(limbSwing, limbSwingAmount, walkSpeed * 1.2F, 0.5F, 1F, true);
		//this.Torso.rotationPointY += bodyBob;
		////Legs Walking
		//this.walk(bipedRightLeg, walkSpeed, walkDegree, true, 0F, 0.1F, limbSwing, limbSwingAmount);
		//this.walk(bipedLeftLeg, walkSpeed, walkDegree, false, 0F, 0.1F, limbSwing, limbSwingAmount);
		//////Ear Movements
		////this.flap(LEar, walkSpeed, walkDegree * 0.25F, true, 0F, 0.1F, limbSwing, limbSwingAmount);
		////this.flap(REar, walkSpeed, walkDegree * 0.25F, false, 0F, 0.1F, limbSwing, limbSwingAmount);
		////Again this is for Individual components such as heads to look as they please
		//this.faceTarget(netHeadYaw, headPitch, 1, bipedHead);

	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	public void faceTarget(float yaw, float pitch, float rotationDivisor, ModelRenderer... boxes) {
		float actualRotationDivisor = rotationDivisor * (float) boxes.length;
		float yawAmount = yaw / 57.295776F / actualRotationDivisor;
		float pitchAmount = pitch / 57.295776F / actualRotationDivisor;
		ModelRenderer[] var8 = boxes;
		int var9 = boxes.length;

		for (int var10 = 0; var10 < var9; ++var10) {
			ModelRenderer box = var8[var10];
			box.rotateAngleY += yawAmount;
			box.rotateAngleX += pitchAmount;
		}

	}

	public static void animateCrossbowHold(ModelRenderer rightArm, ModelRenderer leftArm, ModelRenderer head, boolean isRight) {
		rightArm.rotateAngleX = 0;
		rightArm.rotateAngleY = 0;
		rightArm.rotateAngleZ = 0;
		leftArm.rotateAngleX = 0;
		leftArm.rotateAngleY = 0;
		leftArm.rotateAngleZ = 0;
		ModelRenderer hand1 = isRight ? rightArm : leftArm;
		ModelRenderer hand2 = isRight ? leftArm : rightArm;
		hand1.rotateAngleY = (isRight ? -0.3F : 0.3F) + head.rotateAngleY;
		hand2.rotateAngleY = (isRight ? 0.6F : -0.6F) + head.rotateAngleY;
		hand1.rotateAngleX = (-(float)Math.PI / 2F) + head.rotateAngleX + 0.1F;
		hand2.rotateAngleX = -1.5F + head.rotateAngleX;
	}
}