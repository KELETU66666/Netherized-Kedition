package mellohi138.netherized.client.render.entity;

import mellohi138.netherized.Netherized;
import mellohi138.netherized.client.model.animation.BasicModelEntity;
import mellohi138.netherized.client.model.entity.ModelZoglin;
import mellohi138.netherized.client.render.RenderModEntity;
import mellohi138.netherized.objects.entity.neutral.EntityZoglin;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderZoglin extends RenderModEntity<EntityZoglin> {
    public <U extends BasicModelEntity> RenderZoglin(RenderManager rendermanagerIn) {
        super(rendermanagerIn, "zoglin.png", new ModelZoglin());

    }


    @Override
    protected ResourceLocation getEntityTexture(EntityZoglin entity) {
        String texture = TEXTURES[0];


        return new ResourceLocation(Netherized.MODID + ":textures/entity/hoglin/" + texture);
    }
}