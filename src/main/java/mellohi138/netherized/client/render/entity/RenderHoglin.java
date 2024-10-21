package mellohi138.netherized.client.render.entity;

import mellohi138.netherized.Netherized;
import mellohi138.netherized.client.model.animation.BasicModelEntity;
import mellohi138.netherized.client.model.entity.ModelHoglin;
import mellohi138.netherized.client.render.RenderModEntity;
import mellohi138.netherized.objects.entity.neutral.EntityHoglin;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderHoglin extends RenderModEntity<EntityHoglin> {

    public <U extends BasicModelEntity> RenderHoglin(RenderManager rendermanagerIn) {

        super(rendermanagerIn, "hoglin.png", new ModelHoglin());

    }


    @Override
    protected void applyRotations(EntityHoglin striderIn, float ageInTicks, float rotationYaw, float partialTicks) {
        if (striderIn.convertTooZombie)
            rotationYaw += (float) (Math.cos((double) striderIn.ticksExisted * 3.25D) * Math.PI * 0.25D);

        super.applyRotations(striderIn, ageInTicks, rotationYaw, partialTicks);
    }


    @Override
    protected ResourceLocation getEntityTexture(EntityHoglin entity) {
        String texture = TEXTURES[0];


        return new ResourceLocation(Netherized.MODID + ":textures/entity/hoglin/" + texture);
    }
}