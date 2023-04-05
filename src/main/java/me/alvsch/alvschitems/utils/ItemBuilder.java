package me.alvsch.alvschitems.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Easily create ItemStacks, without messing your hands.
 * <i>Note that if you do use this in one of your projects, leave this notice.</i>
 * <i>Please do credit me if you do use this in one of your projects.</i>
 * @author NonameSL
 */
@SuppressWarnings({"unused", })
public class ItemBuilder {
	private final ItemStack is;
	/**
	 * Create a new ItemBuilder from scratch.
	 * @param m The material to create the ItemBuilder with.
	 */
	public ItemBuilder(Material m){
		this(m, 1);
	}
	/**
	 * Create a new ItemBuilder over an existing ItemStack.
	 * @param is The ItemStack to create the ItemBuilder over.
	 */
	public ItemBuilder(ItemStack is){
		this.is=is;
	}
	/**
	 * Create a new ItemBuilder from scratch.
	 * @param m The material of the item.
	 * @param amount The amount of the item.
	 */
	public ItemBuilder(Material m, int amount){
		is= new ItemStack(m, amount);
	}
	/**
	 * Set the display name of the item.
	 * @param name The name to change it to.
	 */
	public ItemBuilder setDisplayName(String name){
		ItemMeta im = is.getItemMeta();
		assert im != null;
		im.setDisplayName(name);
		is.setItemMeta(im);
		return this;
	}
	/**
	 * Add an unsafe enchantment.
	 * @param enchant The enchantment to add.
	 * @param level The level of the enchantment.
	 */
	public ItemBuilder addUnsafeEnchantment(Enchantment enchant, int level){
		is.addUnsafeEnchantment(enchant, level);
		return this;
	}
	/**
	 * Remove a certain enchant from the item.
	 * @param enchant The enchantment to remove
	 */
	public ItemBuilder removeEnchantment(Enchantment enchant){
		is.removeEnchantment(enchant);
		return this;
	}
	/**
	 * Add an enchant to the item.
	 * @param enchant The enchantment to add
	 * @param level The level
	 */
	public ItemBuilder addEnchant(Enchantment enchant, int level){
		ItemMeta im = is.getItemMeta();
		assert im != null;
		im.addEnchant(enchant, level, true);
		is.setItemMeta(im);
		return this;
	}
	/**
	 * Add multiple enchants at once.
	 * @param enchantments The enchants to add.
	 */
	public ItemBuilder addEnchantments(Map<Enchantment, Integer> enchantments){
		is.addEnchantments(enchantments);
		return this;
	}
	/**
	 * Sets infinity durability on the item by setting the durability to Short.MAX_VALUE.
	 */
	public ItemBuilder setUnbreakable(boolean unbreakable){
		ItemMeta im = is.getItemMeta();
		assert im != null;
		im.setUnbreakable(unbreakable);
		return this;
	}
	/**
	 * Re-sets the lore.
	 * @param lore The lore to set it to.
	 */
	public ItemBuilder setLore(String... lore){
		ItemMeta im = is.getItemMeta();
		assert im != null;
		im.setLore(Arrays.asList(lore));
		is.setItemMeta(im);
		return this;
	}
	/**
	 * Re-sets the lore.
	 * @param lore The lore to set it to.
	 */
	public ItemBuilder setLore(List<String> lore) {
		ItemMeta im = is.getItemMeta();
		assert im != null;
		im.setLore(lore);
		is.setItemMeta(im);
		return this;
	}
	/**
	 * Remove a lore line.
	 * @param line The lore to remove.
	 */
	public ItemBuilder removeLoreLine(String line){
		ItemMeta im = is.getItemMeta();
		assert im != null;
		List<String> lore = im.getLore();
		assert lore != null;
		if(!lore.contains(line)) return this;
		lore.remove(line);
		im.setLore(lore);
		is.setItemMeta(im);
		return this;
	}
	/**
	 * Remove a lore line.
	 * @param index The index of the lore line to remove.
	 */
	public ItemBuilder removeLoreLine(int index){
		ItemMeta im = is.getItemMeta();
		assert im != null;
		List<String> lore = im.getLore();
		assert lore != null;
		if(index < 0 || index > lore.size()) return this;
		lore.remove(index);
		im.setLore(lore);
		is.setItemMeta(im);
		return this;
	}
	/**
	 * Add a lore line.
	 * @param line The lore line to add.
	 */
	public ItemBuilder addLoreLine(String line){
		ItemMeta im = is.getItemMeta();
		List<String> lore = new ArrayList<>();
		assert im != null;
		if(im.hasLore())lore = im.getLore();
		assert lore != null;
		lore.add(line);
		im.setLore(lore);
		is.setItemMeta(im);
		return this;
	}
	/**
	 * Add a lore line.
	 * @param line The lore line to add.
	 * @param pos The index of where to put it.
	 */
	public ItemBuilder addLoreLine(String line, int pos){
		ItemMeta im = is.getItemMeta();
		assert im != null;
		List<String> lore = im.getLore();
		assert lore != null;
		lore.set(pos, line);
		im.setLore(lore);
		is.setItemMeta(im);
		return this;
	}
	/**
	 * Add a lore line.
	 * @param line The lore lines to add.
	 */
	public ItemBuilder addLoreLine(List<String> line){
		ItemMeta im = is.getItemMeta();
		assert im != null;
		List<String> lore = im.getLore();
		assert lore != null;
		lore.addAll(line);
		im.setLore(lore);
		is.setItemMeta(im);
		return this;
	}
	/**
	 * Retrieves the ItemStack from the ItemBuilder.
	 * @return The ItemStack created/modified by the ItemBuilder instance.
	 */
	public ItemStack toItemStack(){
		return is;
	}
}