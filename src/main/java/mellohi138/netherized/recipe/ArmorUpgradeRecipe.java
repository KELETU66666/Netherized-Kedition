/**
 * This class was created by <Lazersmoke>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 *
 * File Created @ [May 6, 2015, 9:45:56 PM (GMT)]
 */
package mellohi138.netherized.recipe;

import mellohi138.netherized.init.NetherizedItems;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

public class ArmorUpgradeRecipe extends ShapelessRecipes {
	
	private final Item diamondSuit;

	public ArmorUpgradeRecipe(Item output, Item diamondSuit) {
		super("netherized:upgrade_netherite", new ItemStack(output), NonNullList.from(Ingredient.EMPTY, Ingredient.fromItem(diamondSuit), Ingredient.fromItem(NetherizedItems.NETHERITE_INGOT)));
		this.diamondSuit = diamondSuit;
	}

	@Nonnull
	@Override
	public ItemStack getCraftingResult(@Nonnull InventoryCrafting var1) {
		ItemStack suit = ItemStack.EMPTY;

		for(int i = 0; i < var1.getSizeInventory(); i++) {
			ItemStack stack = var1.getStackInSlot(i);
			if(!stack.isEmpty() && stack.getItem() == diamondSuit)
				suit = stack;
		}

		if(suit.isEmpty())
			return ItemStack.EMPTY;

		ItemStack suitUpgrade = suit.copy();
		Item suitNeedsUpgrade = suitUpgrade.getItem();

		ItemStack suitNetherite;

		if(suitNeedsUpgrade == Items.DIAMOND_HELMET)
			suitNetherite = new ItemStack(NetherizedItems.NETHERITE_HELMET);
		else if(suitNeedsUpgrade == Items.DIAMOND_CHESTPLATE)
			suitNetherite = new ItemStack(NetherizedItems.NETHERITE_CHESTPLATE);
		else if(suitNeedsUpgrade == Items.DIAMOND_LEGGINGS)
			suitNetherite = new ItemStack(NetherizedItems.NETHERITE_LEGGINGS);
		else if(suitNeedsUpgrade == Items.DIAMOND_BOOTS)
			suitNetherite = new ItemStack(NetherizedItems.NETHERITE_BOOTS);
		else if(suitNeedsUpgrade == Items.DIAMOND_SWORD)
			suitNetherite = new ItemStack(NetherizedItems.NETHERITE_SWORD);
		else if(suitNeedsUpgrade == Items.DIAMOND_SHOVEL)
			suitNetherite = new ItemStack(NetherizedItems.NETHERITE_SHOVEL);
		else if(suitNeedsUpgrade == Items.DIAMOND_PICKAXE)
			suitNetherite = new ItemStack(NetherizedItems.NETHERITE_PICKAXE);
		else if(suitNeedsUpgrade == Items.DIAMOND_AXE)
			suitNetherite = new ItemStack(NetherizedItems.NETHERITE_AXE);
		else if(suitNeedsUpgrade == Items.DIAMOND_HOE)
			suitNetherite = new ItemStack(NetherizedItems.NETHERITE_HOE);
		else return ItemStack.EMPTY;

		if(suitUpgrade.hasTagCompound()){
			suitNetherite.setTagCompound(suitUpgrade.getTagCompound());
		}

		suitNetherite.setItemDamage(Math.min(suitNetherite.getMaxDamage() * suitUpgrade.getItemDamage() / suitUpgrade.getMaxDamage(), suitNetherite.getMaxDamage()));

		return suitNetherite;
	}

	@Override
	public boolean isDynamic() {
		return true;
	}
}