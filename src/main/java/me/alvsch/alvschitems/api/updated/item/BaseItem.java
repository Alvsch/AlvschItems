package me.alvsch.alvschitems.api.updated.item;

import de.tr7zw.nbtapi.NBTItem;
import me.alvsch.alvschitems.api.updated.CustomRecipe;
import me.alvsch.alvschitems.api.updated.Rarity;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BaseItem {

	private final String id;
	private final String name;
	private final List<String> lore;
	private final Rarity rarity;

	private final CustomRecipe recipe;

	private final ItemStack orig;
	private ItemStack item;

	private boolean glint;

	/**
	 * Create a {@link BaseItem} from an {@link ItemStack}
	 *
	 * @param item item
	 */
	public BaseItem(ItemStack item) {
		this.item = item;
		this.orig = item;

		NBTItem nbtItem = new NBTItem(item);

		this.id = nbtItem.getString("id");
		this.name = nbtItem.getString("name");

	}

	public BaseItem(String id, String name, ItemStack item, Rarity rarity, CustomRecipe recipe) {
		this.id = id;
		this.name = name;
		this.item = item;
		this.rarity  = rarity;
		this.recipe = recipe;

	}

	public ItemStack createItem() {
		this.item = this.orig.clone();

		ItemMeta meta = this.item.getItemMeta();
		assert meta != null;

		meta.setDisplayName(rarity.getColor() + this.name);

		List<String> lore = new ArrayList<>(this.lore);
		lore.add("");
		lore.add("");

		lore.add(rarity.getColor().toString() + ChatColor.BOLD + rarity.name());

		if(glint) meta.addEnchant(Enchantment.DURABILITY, 1, true);

		meta.setLore(lore);
		meta.setUnbreakable(true);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);

		item.setItemMeta(meta);

		NBTItem nbtItem = new NBTItem(this.item);
		nbtItem.setString("id", this.id);
		nbtItem.setString("name", this.name);
		nbtItem.setString("rarity", this.rarity.name());

		item = nbtItem.getItem();
		return item;
	}


}
