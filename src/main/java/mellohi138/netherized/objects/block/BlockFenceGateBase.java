package mellohi138.netherized.objects.block;

import mellohi138.netherized.Netherized;
import mellohi138.netherized.util.interfaces.ICustomRenderer;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFenceGateBase extends BlockFenceGate implements ICustomRenderer {
	private final Material blockMaterial;
	private final MapColor mapColor;
	
	public BlockFenceGateBase(String name, Material material, MapColor color, CreativeTabs tab) {
		super(EnumType.OAK);
		this.setTranslationKey(name);
		this.setRegistryName(Netherized.MODID, name);
		this.setCreativeTab(tab);
		
		this.blockMaterial = material;
		this.mapColor = color;
	}
	
	@Override
    public Material getMaterial(IBlockState state) {
        return this.blockMaterial;
    }
	
	@Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return this.mapColor;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(this.getRegistryName(), "inventory"));
		ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(POWERED).build());
	}
}
