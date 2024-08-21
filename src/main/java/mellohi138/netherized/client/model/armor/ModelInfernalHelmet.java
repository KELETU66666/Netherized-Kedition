package mellohi138.netherized.client.model.armor;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * ModelInfernalHelmet - Mellohi138
 * Created using Tabula 7.1.0
 */
@SideOnly(Side.CLIENT)
public class ModelInfernalHelmet extends ModelBiped {
    private final ModelRenderer helm;
    private final ModelRenderer coreSupport;
    private final ModelRenderer crown1;
    private final ModelRenderer crown2;
    private final ModelRenderer frontPlate1;
    private final ModelRenderer frontPlate2;
    private final ModelRenderer stick1;
    private final ModelRenderer stick2;
    private final ModelRenderer stick3;
    private final ModelRenderer stick4;
    private final ModelRenderer plate1;
    private final ModelRenderer plate2;
    private final ModelRenderer backPlate;
    private final ModelRenderer shape1;
    private final ModelRenderer shape2;
    private final ModelRenderer shape3;
    private final ModelRenderer shape4;
    private final ModelRenderer shape5;
    private final ModelRenderer shape6;
    private final ModelRenderer shape7;
    private final ModelRenderer shape8;
    private final ModelRenderer shape9;
    private final ModelRenderer redCore;
    
    public ModelInfernalHelmet() {
    	this(0.0F);
    }
    
    public ModelInfernalHelmet(float extraYoffset) {
    	super(1.2F, 0.0F, 64, 32);
    	
        this.helm = new ModelRenderer(this);
        this.helm.setRotationPoint(0.0F, 0.0F, 0.0F);
        
        this.crown1 = new ModelRenderer(this, 33, 5);
        this.crown1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.crown1.addBox(-2.0F, -7.5F + extraYoffset, -4.0F, 4, 1, 1, 0.1F);
        this.helm.addChild(this.crown1);
        
        this.crown2 = new ModelRenderer(this, 33, 8);
        this.crown2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.crown2.addBox(-1.0F, -8.5F + extraYoffset, -4.0F, 2, 1, 1, 0.1F);
        this.helm.addChild(this.crown2);
        
        this.redCore = new ModelRenderer(this, 40, 7);
        this.redCore.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.redCore.addBox(-1.0F, -6.5F + extraYoffset, -4.25F, 2, 2, 1, 0.1F);
        this.helm.addChild(this.redCore);
        
        this.coreSupport = new ModelRenderer(this, 32, 1);
        this.coreSupport.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.coreSupport.addBox(-3.0F, -6.5F + extraYoffset, -4.0F, 6, 2, 1, 0.1F);
        this.helm.addChild(this.coreSupport);
        
        this.frontPlate1 = new ModelRenderer(this, 48, 4);
        this.frontPlate1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.frontPlate1.addBox(-3.0F, -3.5F + extraYoffset, -4.0F, 1, 4, 1, 0.1F);
        this.helm.addChild(this.frontPlate1);
        
        this.frontPlate2 = new ModelRenderer(this, 48, 4);
        this.frontPlate2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.frontPlate2.addBox(2.0F, -3.5F + extraYoffset, -4.0F, 1, 4, 1, 0.1F);
        this.helm.addChild(this.frontPlate2);
        
        this.plate1 = new ModelRenderer(this, 50, 7);
        this.plate1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.plate1.addBox(3.0F, -6.5F + extraYoffset, -3.0F, 1, 3, 6, 0.1F);
        this.helm.addChild(this.plate1);
        
        this.plate2 = new ModelRenderer(this, 50, 7);
        this.plate2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.plate2.addBox(-4.0F, -6.5F + extraYoffset, -3.0F, 1, 3, 6, 0.1F);
        this.plate2.mirror = true;
        this.helm.addChild(this.plate2);
        
        this.backPlate = new ModelRenderer(this, 0, 0);
        this.backPlate.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.backPlate.addBox(-3.0F, -6.5F + extraYoffset, 3.0F, 6, 6, 1, 0.1F);
        this.helm.addChild(this.backPlate);
        
        this.stick1 = new ModelRenderer(this, 48, 1);
        this.stick1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.stick1.addBox(3.0F, -7.5F + extraYoffset, -4.0F, 1, 8, 1, 0.1F);
        this.helm.addChild(this.stick1);
        
        this.stick2 = new ModelRenderer(this, 48, 1);
        this.stick2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.stick2.addBox(-4.0F, -7.5F + extraYoffset, -4.0F, 1, 8, 1, 0.1F);
        this.stick2.mirror = true;
        this.helm.addChild(this.stick2);
        
        this.stick3 = new ModelRenderer(this, 48, 1);
        this.stick3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.stick3.addBox(3.0F, -7.5F + extraYoffset, 3.0F, 1, 8, 1, 0.1F);
        this.helm.addChild(this.stick3);
        
        this.stick4 = new ModelRenderer(this, 48, 1);
        this.stick4.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.stick4.addBox(-4.0F, -7.5F + extraYoffset, 3.0F, 1, 8, 1, 0.1F);
        this.stick4.mirror = true;
        this.helm.addChild(this.stick4);
        
        this.shape1 = new ModelRenderer(this, 0, 0);
        this.shape1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape1.addBox(3.0F, -7.5F + extraYoffset, 2.0F, 1, 1, 1, 0.1F);
        this.helm.addChild(this.shape1);
        
        this.shape2 = new ModelRenderer(this, 58, 0);
        this.shape2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape2.addBox(3.0F, -7.5F + extraYoffset, -1.0F, 1, 1, 2, 0.1F);
        this.helm.addChild(this.shape2);
        
        this.shape3 = new ModelRenderer(this, 58, 0);
        this.shape3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape3.addBox(-4.0F, -7.5F + extraYoffset, -1.0F, 1, 1, 2, 0.1F);
        this.helm.addChild(this.shape3);
        
        this.shape4 = new ModelRenderer(this, 59, 1);
        this.shape4.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape4.addBox(-4.0F, -7.5F + extraYoffset, 2.0F, 1, 1, 1, 0.1F);
        this.helm.addChild(this.shape4);
        
        this.shape5 = new ModelRenderer(this, 32, 14);
        this.shape5.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape5.addBox(-1.5F, -7.5F + extraYoffset, 3.F, 3, 1, 1, 0.1F);
        this.helm.addChild(this.shape5);
        
        this.shape6 = new ModelRenderer(this, 59, 1);
        this.shape6.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape6.addBox(3.0F, -7.5F + extraYoffset, -3.0F, 1, 1, 1, 0.1F);
        this.helm.addChild(this.shape6);
        
        this.shape7 = new ModelRenderer(this, 59, 1);
        this.shape7.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape7.addBox(-4.0F, -7.5F + extraYoffset, -3.0F, 1, 1, 1, 0.1F);
        this.helm.addChild(this.shape7);
        
        this.shape8 = new ModelRenderer(this, 59, 1);
        this.shape8.setRotationPoint(4.0F, 3.0F, 6.0F);
        this.shape8.addBox(-1.0F, -6.5F + extraYoffset, -4.0F, 1, 1, 1, 0.1F);
        this.helm.addChild(this.shape8);
        
        this.shape9 = new ModelRenderer(this, 59, 1);
        this.shape9.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape9.addBox(-4.0F, -3.5F + extraYoffset, 2.0F, 1, 1, 1, 0.1F);
        this.helm.addChild(this.shape9);
        
        this.bipedHead.addChild(this.helm);
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) { 
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
        GlStateManager.pushMatrix();
        
        if (this.isChild) {
            GlStateManager.scale(0.75F, 0.75F, 0.75F);
            GlStateManager.translate(0.0F, 16.0F * scale, 0.0F);
        } else {
            if (entityIn.isSneaking()) {
                GlStateManager.translate(0.0F, 0.2F, 0.0F);
            }
        }
        
        GlStateManager.scale(1.2F, 1.2F, 1.2F);
        this.helm.render(scale);
        GlStateManager.popMatrix();
    }
    
    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    	if(entityIn instanceof EntityArmorStand) {
    		EntityArmorStand armorStand = (EntityArmorStand)entityIn;
            this.bipedHead.rotateAngleX = 0.017453292F * armorStand.getHeadRotation().getX();
            this.bipedHead.rotateAngleY = 0.017453292F * armorStand.getHeadRotation().getY();
            this.bipedHead.rotateAngleZ = 0.017453292F * armorStand.getHeadRotation().getZ();
            this.bipedHead.setRotationPoint(0.0F, 1.0F, 0.0F);
    	} else {
        	super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        	
        	copyModelAngles(this.bipedHead, this.helm);
    	}
    }
}
