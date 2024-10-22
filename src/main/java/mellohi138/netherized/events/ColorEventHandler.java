package mellohi138.netherized.events;

import mellohi138.netherized.Netherized;
import mellohi138.netherized.world.biome.INetherizedBiomes;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

@Mod.EventBusSubscriber(modid = Netherized.MODID)
public class ColorEventHandler
{
    @SubscribeEvent
    public static void renderBiomeFog(final EntityViewRenderEvent.RenderFogEvent event) {
        if (event.getEntity().dimension == -1) {
            GL11.glFogi(2917, 2048);
            GL11.glFogf(2914, 0.015F);
        }
    }
    
    @SubscribeEvent
    public static void onGetFogColor(final EntityViewRenderEvent.FogColors event) {
        if (event.getEntity() instanceof EntityPlayer) {
            if (event.getEntity().dimension == -1) {
                final EntityPlayer player = (EntityPlayer) event.getEntity();
                final World world = player.world;
                final int x = MathHelper.floor(player.posX);
                final int y = MathHelper.floor(player.posY);
                final int z = MathHelper.floor(player.posZ);
                final IBlockState blockAtEyes = ActiveRenderInfo.getBlockStateAtEntityViewpoint(world, event.getEntity(), (float) event.getRenderPartialTicks());
                if (blockAtEyes.getMaterial() == Material.LAVA) {
                    return;
                }
                final Vec3d mixedColor = getFogBlendColor(world, player, x, y, z, event.getRed(), event.getGreen(), event.getBlue(), event.getRenderPartialTicks());
                event.setRed((float) mixedColor.x);
                event.setGreen((float) mixedColor.y);
                event.setBlue((float) mixedColor.z);
            }
        }
    }
    
    private static Vec3d postProcessColor(final World world, final EntityLivingBase player, float r, float g, float b, final double renderPartialTicks) {
        double darkScale = (player.lastTickPosY + (player.posY - player.lastTickPosY) * renderPartialTicks) * world.provider.getVoidFogYFactor();
        if (player.isPotionActive(MobEffects.BLINDNESS)) {
            final int duration = player.getActivePotionEffect(MobEffects.BLINDNESS).getDuration();
            darkScale *= ((duration < 20) ? (1.0f - duration / 20.0f) : 0.0);
        }
        if (darkScale < 1.0) {
            darkScale = ((darkScale < 0.0) ? 0.0 : (darkScale * darkScale));
            r *= (float)darkScale;
            g *= (float)darkScale;
            b *= (float)darkScale;
        }
        if (player.isPotionActive(MobEffects.NIGHT_VISION)) {
            final int duration = player.getActivePotionEffect(MobEffects.NIGHT_VISION).getDuration();
            final float brightness = (duration > 200) ? 1.0f : (0.7f + MathHelper.sin((float)((duration - renderPartialTicks) * 3.141592653589793 * 0.20000000298023224)) * 0.3f);
            float scale = 1.0f / r;
            scale = Math.min(scale, 1.0f / g);
            scale = Math.min(scale, 1.0f / b);
            r = r * (1.0f - brightness) + r * scale * brightness;
            g = g * (1.0f - brightness) + g * scale * brightness;
            b = b * (1.0f - brightness) + b * scale * brightness;
        }
        if (Minecraft.getMinecraft().gameSettings.anaglyph) {
            final float aR = (r * 30.0f + g * 59.0f + b * 11.0f) / 100.0f;
            final float aG = (r * 30.0f + g * 70.0f) / 100.0f;
            final float aB = (r * 30.0f + b * 70.0f) / 100.0f;
            r = aR;
            g = aG;
            b = aB;
        }
        return new Vec3d(r, g, b);
    }
    
    private static Vec3d getFogBlendColor(final World world, final EntityLivingBase playerEntity, final int playerX, final int playerY, final int playerZ, final float defR, final float defG, final float defB, final double renderPartialTicks) {
        final GameSettings settings = Minecraft.getMinecraft().gameSettings;
        final int[] ranges = ForgeModContainer.blendRanges;
        int distance = 0;
        if (settings.fancyGraphics && settings.renderDistanceChunks >= 0 && settings.renderDistanceChunks < ranges.length) {
            distance = ranges[settings.renderDistanceChunks];
        }
        float rBiomeFog = 0.0f;
        float gBiomeFog = 0.0f;
        float bBiomeFog = 0.0f;
        float weightBiomeFog = 0.0f;
        for (int x = -distance; x <= distance; ++x) {
            for (int z = -distance; z <= distance; ++z) {
                final Biome biome = world.getBiome(new BlockPos(playerX + x, 0, playerZ + z));
                if (biome instanceof INetherizedBiomes) {
                    final INetherizedBiomes biomeFog = (INetherizedBiomes)biome;
                    final int fogColor = biomeFog.getBiomeColor(playerX + x, playerY, playerZ + z);
                    float rPart = (float)((fogColor & 0xFF0000) >> 16);
                    float gPart = (float)((fogColor & 0xFF00) >> 8);
                    float bPart = (float)(fogColor & 0xFF);
                    float weightPart = 1.0f;
                    if (x == -distance) {
                        final double xDiff = 1.0 - (playerEntity.posX - playerX);
                        rPart *= (float)xDiff;
                        gPart *= (float)xDiff;
                        bPart *= (float)xDiff;
                        weightPart *= (float)xDiff;
                    }
                    else if (x == distance) {
                        final double xDiff = playerEntity.posX - playerX;
                        rPart *= (float)xDiff;
                        gPart *= (float)xDiff;
                        bPart *= (float)xDiff;
                        weightPart *= (float)xDiff;
                    }
                    if (z == -distance) {
                        final double zDiff = 1.0 - (playerEntity.posZ - playerZ);
                        rPart *= (float)zDiff;
                        gPart *= (float)zDiff;
                        bPart *= (float)zDiff;
                        weightPart *= (float)zDiff;
                    }
                    else if (z == distance) {
                        final double zDiff = playerEntity.posZ - playerZ;
                        rPart *= (float)zDiff;
                        gPart *= (float)zDiff;
                        bPart *= (float)zDiff;
                        weightPart *= (float)zDiff;
                    }
                    rBiomeFog += rPart;
                    gBiomeFog += gPart;
                    bBiomeFog += bPart;
                    weightBiomeFog += weightPart;
                }
            }
        }
        if (weightBiomeFog == 0.0f || distance == 0) {
            return new Vec3d(defR, defG, defB);
        }
        rBiomeFog /= 255.0f;
        gBiomeFog /= 255.0f;
        bBiomeFog /= 255.0f;
        final float rScale = 0.3f;
        final float gScale = 0.3f;
        final float bScale = 0.3f;
        rBiomeFog *= rScale / weightBiomeFog;
        gBiomeFog *= gScale / weightBiomeFog;
        bBiomeFog *= bScale / weightBiomeFog;
        final Vec3d processedColor = postProcessColor(world, playerEntity, rBiomeFog, gBiomeFog, bBiomeFog, renderPartialTicks);
        rBiomeFog = (float)processedColor.x;
        gBiomeFog = (float)processedColor.y;
        bBiomeFog = (float)processedColor.z;
        final float weightMixed = (float)(distance * 2 * (distance * 2));
        final float weightDefault = weightMixed - weightBiomeFog;
        processedColor.x = (rBiomeFog * weightBiomeFog + defR * weightDefault) / weightMixed;
        processedColor.y = (gBiomeFog * weightBiomeFog + defG * weightDefault) / weightMixed;
        processedColor.z = (bBiomeFog * weightBiomeFog + defB * weightDefault) / weightMixed;
        return processedColor;
    }
}
