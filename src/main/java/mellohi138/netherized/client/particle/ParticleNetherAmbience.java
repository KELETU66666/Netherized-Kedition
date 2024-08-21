package mellohi138.netherized.client.particle;

import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

@SideOnly(Side.CLIENT)
public class ParticleNetherAmbience extends Particle {	
	public ParticleNetherAmbience(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
		this.setSize(0.01F, 0.01F);
		this.particleScale *= this.rand.nextFloat() * 0.6F + 0.6F;
		this.particleMaxAge = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
		this.canCollide = false;
	}
	
	@Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.particleMaxAge-- <= 0) {
            this.setExpired();
        } else {
        	this.move(this.motionX, this.motionY, this.motionZ);
        }
    }

	@SideOnly(Side.CLIENT)
    public static class CrimsonFactory implements IParticleFactory {
    	public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
    		Random rand = worldIn.rand;
    		double xSpeed = rand.nextGaussian() * (double)1.0E-6F;
    		double ySpeed = rand.nextGaussian() * (double)1.0E-4F;
    		double zSpeed = rand.nextGaussian() * (double)1.0E-6F;
    		ParticleNetherAmbience particle = new ParticleNetherAmbience(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeed, ySpeed, zSpeed);
    		particle.setRBGColorF(0.9F, 0.4F, 0.6F);
    		return particle;
    	}
    }
	
	@SideOnly(Side.CLIENT)
    public static class WarpedFactory implements IParticleFactory {
    	public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
            double ySpeed = (double)worldIn.rand.nextFloat() * -1.9D * (double)worldIn.rand.nextFloat() * 0.1D;
            ParticleNetherAmbience particle = new ParticleNetherAmbience(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0F, ySpeed, 0.0F);
    		particle.setRBGColorF(0.1F, 0.1F, 0.3F);
    		particle.setSize(0.001F, 0.001F);
    		return particle;
    	}
    }
	
	@SideOnly(Side.CLIENT)
    public static class BasaltFactory implements IParticleFactory {
    	public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
    		Random rand = worldIn.rand;
            double xSpeed = (double)rand.nextFloat() * -1.9D * (double)rand.nextFloat() * 0.1D;
            double ySpeed = (double)rand.nextFloat() * -0.5D * (double)rand.nextFloat() * 0.1D * 5.0D;
            double zSpeed = (double)rand.nextFloat() * -1.9D * (double)rand.nextFloat() * 0.1D;
    		ParticleNetherAmbience particle = new ParticleNetherAmbience(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeed, ySpeed, zSpeed);
    		particle.setRBGColorF(0.7294118F, 0.69411767F, 00.7607843F);
    		return particle;
    	}
    }
}
