package mellohi138.netherized.proxy;

import mellohi138.netherized.Netherized;
import mellohi138.netherized.enums.EnumNetherizedParticles;
import mellohi138.netherized.util.interfaces.IProxy;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = Netherized.MODID, value = Side.SERVER)
public class CommonProxy implements IProxy {
	@Override
	public void preInit() {
	}
	
	@Override
	public void init() {
	}
	
	@Override
	public void postInit() {
	}
    
	@Override
    public void spawnParticle(EnumNetherizedParticles particleEnum, World worldIn, double x, double y, double z, double motX, double motY, double motZ) {
    }

	@Override
	public void handleAnimationPacket(int entityId, int index) {

	}
}