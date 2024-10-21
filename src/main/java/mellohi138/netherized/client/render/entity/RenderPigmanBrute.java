package mellohi138.netherized.client.render.entity;

import mellohi138.netherized.Netherized;
import mellohi138.netherized.client.model.entity.ModelPigmanBrute;
import mellohi138.netherized.objects.entity.neutral.EntityPigmanBrute;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.util.ResourceLocation;

public class RenderPigmanBrute extends RenderLiving<EntityPigmanBrute> {
    public RenderPigmanBrute(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelPigmanBrute(), 0.5F);
        this.addLayer(new LayerHeldItem(this));
        this.addLayer(new LayerBipedArmor(this) {
            protected void initArmor() {
                this.modelLeggings = new ModelZombie(0.5F, true);
                this.modelArmor = new ModelZombie(1.0F, true);
            }
        });
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityPigmanBrute entity) {
        return new ResourceLocation(Netherized.MODID + ":textures/entity/pigman/pigman_brute.png");
    }

    @Override
    protected void applyRotations(EntityPigmanBrute striderIn, float ageInTicks, float rotationYaw, float partialTicks) {
        if (striderIn.convertTooZombie)
            rotationYaw += (float) (Math.cos((double) striderIn.ticksExisted * 3.25D) * Math.PI * 0.25D);

        super.applyRotations(striderIn, ageInTicks, rotationYaw, partialTicks);
    }

}