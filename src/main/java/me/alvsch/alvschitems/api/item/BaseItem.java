package me.alvsch.alvschitems.api.item;

import de.tr7zw.nbtapi.NBTItem;
import me.alvsch.alvschitems.AlvschItems;
import me.alvsch.alvschitems.api.CustomRecipe;
import me.alvsch.alvschitems.api.Rarity;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class BaseItem {

	private final String id;
	private final String name;
	private final List<String> lore;
	private final Rarity rarity;

	private CustomRecipe recipe;

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

		BaseItem baseItem = BaseItem.getById(id);
		this.name = nbtItem.getName();
		this.lore = baseItem.getLore();
		this.rarity = baseItem.getRarity();
		this.recipe = baseItem.getRecipe();

	}

	public BaseItem(String id, String name, ItemStack item, Rarity rarity, CustomRecipe recipe) {
		this(id, name, new ArrayList<>(), item, rarity, recipe);

	}

	public BaseItem(String id, String name, List<String> lore, ItemStack item, Rarity rarity, CustomRecipe recipe) {
		this.id = id;
		this.name = name;
		this.lore = lore;
		this.orig = item;

		this.rarity = rarity;
		this.recipe = recipe;
	}

	public void register() {
		AlvschItems.getInstance().getRegistry().getItemIds().put(id, this);
		if(recipe != null) {
			this.recipe = new CustomRecipe(this.createItem(), recipe.getItems(), recipe.isShaped());
			this.recipe.register();
		}
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

		item = nbtItem.getItem();
		return item;
	}

	//region Getters Setters

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<String> getLore() {
		return lore;
	}

	public Rarity getRarity() {
		return rarity;
	}

	public CustomRecipe getRecipe() {
		return recipe;
	}

	public void setRecipe(CustomRecipe recipe) {
		this.recipe = recipe;
	}

	public ItemStack getOriginal() {
		return orig;
	}

	public ItemStack getItem() {
		return item;
	}

	public void setItem(ItemStack item) {
		this.item = item;
	}

	public boolean hasGlint() {
		return glint;
	}

	public void setGlint(boolean glint) {
		this.glint = glint;
	}

	//endregion


	public static BaseItem getById(String id) {
		return AlvschItems.getInstance().getRegistry().getItemIds().get(id);
	}
	public static boolean isCustomItem(ItemStack item) {
		NBTItem nbt = new NBTItem(item);
		return nbt.hasTag("id");
	}

}
