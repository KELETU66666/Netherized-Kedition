package mellohi138.netherized.util.interfaces;

import mellohi138.netherized.enums.EnumNetherizedParticles;
import net.minecraft.world.World;

public interface IProxy {
	public void preInit();
	
	public void init();
	
	public void postInit();
	
	public void spawnParticle(EnumNetherizedParticles particleEnum, World worldIn, double x, double y, double z, double motX, double motY, double motZ);

	public void handleAnimationPacket(int entityId, int index);
}
