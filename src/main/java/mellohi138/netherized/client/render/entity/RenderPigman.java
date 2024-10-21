package mellohi138.netherized.client.render.entity;

import mellohi138.netherized.Netherized;
import mellohi138.netherized.client.model.entity.ModelPigman;
import mellohi138.netherized.objects.entity.neutral.EntityPigman;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.util.ResourceLocation;

public class RenderPigman extends RenderLiving<EntityPigman> {

    public RenderPigman(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelPigman(), 0.5F);
        this.addLayer(new LayerHeldItem(this));
        LayerBipedArmor layerbipedarmor = new LayerBipedArmor(this) {
            protected void initArmor() {
                this.modelLeggings = new ModelZombie(0.5F, true);
                this.modelArmor = new ModelZombie(1.0F, true);
            }
        };
        this.addLayer(layerbipedarmor);

    }

    @Override
    protected ResourceLocation getEntityTexture(EntityPigman entity) {
        return new ResourceLocation(Netherized.MODID + ":textures/entity/pigman/pigman.png");
    }

    @Override
    protected void applyRotations(EntityPigman striderIn, float ageInTicks, float rotationYaw, float partialTicks) {
        if(striderIn.convertTooZombie)
            rotationYaw += (float)(Math.cos((double)striderIn.ticksExisted * 3.25D) * Math.PI * 0.25D);

        super.applyRotations(striderIn, ageInTicks, rotationYaw, partialTicks);
    }




}