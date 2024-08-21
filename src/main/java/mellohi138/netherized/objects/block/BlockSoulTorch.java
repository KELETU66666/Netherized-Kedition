package mellohi138.netherized.objects.block;

import mellohi138.netherized.Netherized;
import mellohi138.netherized.enums.EnumNetherizedParticles;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockSoulTorch extends BlockTorch {
	public BlockSoulTorch(String name, SoundType type, CreativeTabs tab) {
		super();
		this.setTranslationKey(name);
		this.setRegistryName(Netherized.MODID, name);
		this.setSoundType(type);
		this.setCreativeTab(tab);
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        EnumFacing side = (EnumFacing)stateIn.getValue(FACING);
        double d0 = (double)pos.getX() + 0.5D;
        double d1 = (double)pos.getY() + 0.7D;
        double d2 = (double)pos.getZ() + 0.5D;

        if (side.getAxis().isHorizontal()) {
            EnumFacing oppositeSide = side.getOpposite();
            worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + 0.27D * (double)oppositeSide.getXOffset(), d1 + 0.22D, d2 + 0.27D * (double)oppositeSide.getZOffset(), 0.0D, 0.0D, 0.0D);
            Netherized.PROXY.spawnParticle(EnumNetherizedParticles.SOUL_FLAME, worldIn, d0 + 0.27D * (double)oppositeSide.getXOffset(), d1 + 0.22D, d2 + 0.27D * (double)oppositeSide.getZOffset(), 0.0D, 0.0D, 0.0D);
        } else {
            worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D);
            Netherized.PROXY.spawnParticle(EnumNetherizedParticles.SOUL_FLAME, worldIn, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }
}
