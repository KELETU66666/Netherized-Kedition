package mellohi138.netherized.proxy;

import mellohi138.netherized.Netherized;
import mellohi138.netherized.client.EntityRenderRegistry;
import mellohi138.netherized.client.NetherizedTEISR;
import mellohi138.netherized.client.model.animation.IAnimatedEntity;
import mellohi138.netherized.client.render.entity.*;
import mellohi138.netherized.enums.EnumNetherizedParticles;
import mellohi138.netherized.init.NetherizedItems;
import mellohi138.netherized.objects.entity.hostile.EntityHoveringInferno;
import mellohi138.netherized.objects.entity.neutral.EntityHoglin;
import mellohi138.netherized.objects.entity.neutral.EntityPigman;
import mellohi138.netherized.objects.entity.neutral.EntityPigmanBrute;
import mellohi138.netherized.objects.entity.neutral.EntityZoglin;
import mellohi138.netherized.objects.entity.passive.EntityStrider;
import mellohi138.netherized.util.interfaces.IProxy;
import mellohi138.netherized.util.interfaces.ITEISRModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = Netherized.MODID, value = Side.CLIENT)
public class ClientProxy implements IProxy {
	@Override
	public void preInit() {
		this.registerEntityModels();
	}
	
	@Override
	public void init() {
	}

	@Override
	public void postInit() {
		for(Item item : NetherizedItems.ITEM_LIST) {
			if(item instanceof ITEISRModel) {
				item.setTileEntityItemStackRenderer(new NetherizedTEISR());
			}
		}
	}

	@Override
	public void handleAnimationPacket(int entityId, int index) {
		EntityPlayerSP player = Minecraft.getMinecraft().player;
		if (player != null) {
			IAnimatedEntity entity = (IAnimatedEntity) player.world.getEntityByID(entityId);
			if (entity != null) {
				if (index == -1) {
					entity.setAnimation(IAnimatedEntity.NO_ANIMATION);
				} else {
					entity.setAnimation(entity.getAnimations()[index]);
				}
				entity.setAnimationTick(0);
			}
		}
	}

	private void registerEntityModels() {	
		RenderingRegistry.registerEntityRenderingHandler(EntityStrider.class, new EntityRenderRegistry<EntityStrider>() {
			@Override
			public Render<? super EntityStrider> createRenderFor(RenderManager manager) {
				return new RenderStrider(manager);
			}
		});

		RenderingRegistry.registerEntityRenderingHandler(EntityHoveringInferno.class, new EntityRenderRegistry<EntityHoveringInferno>() {
			@Override
			public Render<? super EntityHoveringInferno> createRenderFor(RenderManager manager) {
				return new RenderHoveringInferno(manager);
			}
		});

		RenderingRegistry.registerEntityRenderingHandler(EntityPigman.class, new EntityRenderRegistry<EntityPigman>() {
			@Override
			public Render<? super EntityPigman> createRenderFor(RenderManager manager) {
				return new RenderPigman(manager);
			}
		});

		RenderingRegistry.registerEntityRenderingHandler(EntityPigmanBrute.class, new EntityRenderRegistry<EntityPigmanBrute>() {
			@Override
			public Render<? super EntityPigmanBrute> createRenderFor(RenderManager manager) {
				return new RenderPigmanBrute(manager);
			}
		});

		RenderingRegistry.registerEntityRenderingHandler(EntityHoglin.class, new EntityRenderRegistry<EntityHoglin>() {
			@Override
			public Render<? super EntityHoglin> createRenderFor(RenderManager manager) {
				return new RenderHoglin(manager);
			}
		});

		RenderingRegistry.registerEntityRenderingHandler(EntityZoglin.class, new EntityRenderRegistry<EntityZoglin>() {
			@Override
			public Render<? super EntityZoglin> createRenderFor(RenderManager manager) {
				return new RenderZoglin(manager);
			}
		});
    }
    
	@Override
    public void spawnParticle(EnumNetherizedParticles particleEnum, World worldIn, double x, double y, double z, double motX, double motY, double motZ) {
		if(worldIn == null) worldIn = Minecraft.getMinecraft().world;
        
		Minecraft.getMinecraft().effectRenderer.addEffect(particleEnum.getParticleFactory().createParticle(0, worldIn, x, y, z, motX, motY, motZ));        	
    }
}