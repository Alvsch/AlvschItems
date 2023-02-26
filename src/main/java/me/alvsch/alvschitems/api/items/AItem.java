package me.alvsch.alvschitems.api.items;

import me.alvsch.alvschitems.AlvschItems;
import me.alvsch.alvschitems.utils.Utils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

		PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();

		this.material = item.getType();
		this.amount = item.getAmount();

		this.id = pdc.get(AlvschItems.getInstance().getRegistry().getIdKey(), PersistentDataType.STRING);
		this.name = pdc.get(AlvschItems.getInstance().getRegistry().getNameKey(), PersistentDataType.STRING);
		this.lore = List.of(pdc.get(AlvschItems.getInstance().getRegistry().getLoreKey(), PersistentDataType.STRING).split("\n"));
		this.abilityList = new ArrayList<>(AAbility.getByItem(item));
		this.rarity = ARarity.valueOf(pdc.get(AlvschItems.getInstance().getRegistry().getRarityKey(), PersistentDataType.STRING));
		this.recipe = AItem.getById(id).getRecipe();
		this.upgraded = pdc.has(AlvschItems.getInstance().getRegistry().getUpgradedKey(), PersistentDataType.SHORT);

		this.stats = new AItemStats(pdc.get(AlvschItems.getInstance().getRegistry().getStatsKey(), PersistentDataType.STRING));
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

		PersistentDataContainer pdc = meta.getPersistentDataContainer();
		pdc.set(AlvschItems.getInstance().getRegistry().getIdKey(), PersistentDataType.STRING, id);
		pdc.set(AlvschItems.getInstance().getRegistry().getNameKey(), PersistentDataType.STRING, name);
		pdc.set(AlvschItems.getInstance().getRegistry().getLoreKey(), PersistentDataType.STRING, String.join("\n", this.lore));
		pdc.set(AlvschItems.getInstance().getRegistry().getRarityKey(), PersistentDataType.STRING, rarity.toString());

		if(upgraded) pdc.set(AlvschItems.getInstance().getRegistry().getUpgradedKey(), PersistentDataType.SHORT, (short) 1);
		StringBuilder sb = new StringBuilder();
		for(AAbility a : abilityList) {
			sb.append(a.getId());
			sb.append("\n");
		}
		pdc.set(AlvschItems.getInstance().getRegistry().getAbilityIdKey(), PersistentDataType.STRING, sb.toString());
		pdc.set(AlvschItems.getInstance().getRegistry().getStatsKey(), PersistentDataType.STRING, stats.toString());

		item.setItemMeta(meta);
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
			return getById(item.getItemMeta().getPersistentDataContainer().get(AlvschItems.getInstance().getRegistry().getIdKey(), PersistentDataType.STRING));
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
		return item.getItemMeta().getPersistentDataContainer().has(AlvschItems.getInstance().getRegistry().getIdKey(), PersistentDataType.STRING);
	}

}
