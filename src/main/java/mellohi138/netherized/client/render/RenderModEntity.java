package mellohi138.netherized.client.render;

import mellohi138.netherized.Netherized;
import mellohi138.netherized.client.model.animation.BasicModelEntity;
import mellohi138.netherized.client.model.animation.EZRenderLiving;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public abstract class RenderModEntity<T extends EntityLiving> extends EZRenderLiving<T> {
    public String[] TEXTURES;
    private final ResourceLocation DEATH_TEXTURES;

    public <U extends BasicModelEntity> RenderModEntity(RenderManager rendermanagerIn, String textures, U modelClass) {
        this(rendermanagerIn, modelClass, textures);
    }

    public <U extends BasicModelEntity> RenderModEntity(RenderManager rendermanagerIn, U modelClass, String... textures) {
        super(rendermanagerIn, modelClass, 0.5f);
        if (textures.length == 0) {
            throw new IllegalArgumentException("Must provide at least one texture to render an entity.");
        }
        this.TEXTURES = textures;
        this.DEATH_TEXTURES = new ResourceLocation(String.format("%s:textures/entity/disintegration_%d_%d.png", Netherized.MODID, modelClass.textureWidth, modelClass.textureHeight));
    }

    @Override
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
        if (!entity.isInvisible()) {
            // The blending here allows for rendering of translucent textures
            GlStateManager.enableNormalize();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            super.doRender(entity, x, y, z, entityYaw, partialTicks);
            GlStateManager.disableBlend();
            GlStateManager.disableNormalize();

        } else {
            super.doRender(entity, x, y, z, entityYaw, partialTicks);
        }
    }
}