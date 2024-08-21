package mellohi138.netherized.objects.block;

import mellohi138.netherized.Netherized;
import net.minecraft.block.BlockOre;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockNetherOre extends BlockOre {
	private final int oreID;
    public BlockNetherOre(String name, Material material, MapColor color, String usedTool, int toolStrength, SoundType soundType, CreativeTabs tab, int oreID) {
		super(color);
		this.setTranslationKey(name);
		this.setRegistryName(Netherized.MODID, name);
		this.setCreativeTab(tab);
        this.setHarvestLevel(usedTool, toolStrength);
        this.setSoundType(soundType);
        
        this.oreID = oreID;
    }
    
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    	if(this.oreID == 0) {
    		return Items.IRON_NUGGET;
    	} else if(this.oreID == 1 || this.oreID == 3) {
    		return Items.GOLD_NUGGET;
    	} else if(this.oreID == 2) {
    		return Items.COAL;
    	}
		return Item.getItemFromBlock(this);
    }
    
    @Override
    public int quantityDropped(Random random) {
    	if(this.oreID == 0 || this.oreID == 3) {
            return 1 + random.nextInt(2);
    	} else if(this.oreID == 1) {
            return 2 + random.nextInt(6);
    	}
    	return 1;
    }
    
    @Override
    public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune) {
        Random rand = world instanceof World ? ((World)world).rand : new Random();
    	int i = 0;
        if (this.getItemDropped(state, rand, fortune) != Item.getItemFromBlock(this)) {
        	if(this.oreID == 0 || this.oreID == 3) {
        		i += 3;
        	} else if(this.oreID == 1) {
        		i += 1;
        	} else if(this.oreID == 2) {
        		i += 2;
        	}
        }
        return MathHelper.getInt(rand, 0, i);
    }
}