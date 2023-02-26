package me.alvsch.alvschitems.api.items;

import de.tr7zw.nbtapi.NBTItem;
import me.alvsch.alvschitems.AlvschItems;
import me.alvsch.alvschitems.utils.Utils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class AItem {

	private final String id;
	private final String name;
	private final List<String> lore;
	private ItemStack orig;
	private ItemStack item;
	private AItemStats stats;
	private final List<AAbility> abilityList;
	private final ARarity rarity;
	private ARecipe recipe;

	private final Material material;
	private final int amount;

	private boolean glint = false;
	private boolean upgraded = false;

	public AItem(ItemStack item) {
		this.item = item;
		this.orig = item;

		NBTItem nbtItem = new NBTItem(item);


		this.material = item.getType();
		this.amount = item.getAmount();


		this.id = nbtItem.getString("id");
		this.name = nbtItem.getString("name");
		this.lore = nbtItem.getStringList("lore");
		this.rarity = ARarity.valueOf(nbtItem.getString("rarity"));
		this.recipe = AItem.getById(id).getRecipe();
		this.stats = new AItemStats(nbtItem.getString("stats"));

		this.abilityList = new ArrayList<>(AItem.getById(this.id).getAbilities());
		this.upgraded = nbtItem.hasTag("upgraded");
	}

	public AItem(String id, String name, List<String> lore, ItemStack item, ARarity rarity, List<String> recipe, boolean shaped) {
		this.id = id;
		this.name = name;
		this.lore = lore;
		this.rarity = rarity;
		this.orig = item;

		this.material = item.getType();
		this.amount = item.getAmount();

		this.stats = new AItemStats();
		this.abilityList = new ArrayList<>();

		if(recipe == null) {
			this.recipe = null;
		} else {
			this.recipe = new ARecipe(this.createItem(), recipe, shaped);
		}
	}
	public AItem(String id, String name, ItemStack item, ARarity rarity, List<String> recipe, boolean shaped) {
		this(id, name, new ArrayList<>(), item, rarity, recipe, shaped);
	}

	public ItemStack createItem() {
		if(this.orig == null) this.orig = new ItemStack(this.material, this.amount);
		this.item = orig.clone();


		ItemMeta meta = item.getItemMeta();
		assert meta != null;
		if(upgraded) {
			meta.setDisplayName(rarity.next().getColor() + name);
		} else {
			meta.setDisplayName(rarity.getColor() + name);
		}
		List<String> lore = new ArrayList<>();

		lore.addAll(stats.toLore());
		lore.add("");

		if(!Utils.isAllBlank(this.lore)) {
			lore.addAll(this.lore);
			lore.add("");
		}

		for(AAbility ability : abilityList) {
			lore.addAll(ability.toLore());
			lore.add("");
		}

		if(upgraded) {
			lore.add(rarity.next().getColor() + Utils.color("&l&kA ") + rarity.next().getColor() + Utils.color("&l") + rarity.next().toString() + Utils.color(" &kA"));
		} else {
			lore.add(rarity.getColor() + Utils.color("&l") + rarity.toString());
		}

		meta.setLore(lore);
		meta.setUnbreakable(true);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);

		if(glint) {
			meta.addEnchant(Enchantment.DURABILITY, 1, true);
		}

		item.setItemMeta(meta);

		NBTItem nbtItem = new NBTItem(item);
		nbtItem.setString("id", this.id);
		nbtItem.setString("name", this.name);
		nbtItem.setString("lore", this.lore.toString());
		nbtItem.setString("rarity", this.rarity.toString());
		nbtItem.setString("stats", this.stats.toString());
		if(upgraded) nbtItem.setBoolean("upgraded", true);

		item = nbtItem.getItem();
		return item;
	}


	public void register() {
		AlvschItems.getInstance().getRegistry().getItemIds().put(id, this);
		if(recipe != null) {
			this.recipe = new ARecipe(this.createItem(), recipe.getItems(), recipe.isShaped());
			this.recipe.register();
		}
	}


	// Getters
	public List<AAbility> getAbilities() {
		return abilityList;
	}
	public AItemStats getStats() {
		return stats;
	}
	public ARecipe getRecipe() {
		return recipe;
	}
	public String getId() {
		return id;
	}

	public static AItem getById(String id) {
		if(id == null) return null;
		return AlvschItems.getInstance().getRegistry().getItemIds().get(id);
	}
	@SuppressWarnings("ConstantConditions")
	public static AItem getByItem(ItemStack item) {
		try {
			return getById(new NBTItem(item).getString("id"));
		} catch (NullPointerException e) { return null; }
	}

	public AItem addAbility(AAbility aAbility) {
		abilityList.add(aAbility);
		return this;
	}
	public AItem removeAbility(AAbility aAbility) {
		abilityList.remove(aAbility);
		return this;
	}

	// Setters
	public AItem setStats(AItemStats stats) {
		this.stats = stats;
		return this;
	}
	public AItem setUpgraded(boolean bool) {
		upgraded = bool;
		return this;
	}
	public AItem setGlint(boolean glint) {
		this.glint = glint;
		return this;
	}

	public static boolean isAItem(ItemStack item) {
		return new NBTItem(item).hasTag("id");
	}

}
