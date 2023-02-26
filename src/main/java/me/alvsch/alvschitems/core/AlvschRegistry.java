package me.alvsch.alvschitems.core;

import me.alvsch.alvschitems.AlvschItems;
import me.alvsch.alvschitems.api.items.AAbility;
import me.alvsch.alvschitems.api.items.AItem;
import me.alvsch.alvschitems.api.items.ARecipe;
import org.bukkit.NamespacedKey;

import java.util.*;

public class AlvschRegistry {

	private final Map<String, AItem> itemIds = new HashMap<>();
	private final Map<String, AAbility> abilityIds = new HashMap<>();
	private final List<ARecipe> shapedRecipeList = new ArrayList<>();
	private final List<ARecipe> shapelessRecipeList = new ArrayList<>();

	private final NamespacedKey idKey;
	private final NamespacedKey nameKey;
	private final NamespacedKey loreKey;
	private final NamespacedKey rarityKey;
	private final NamespacedKey recipeKey;
	private final NamespacedKey abilityIdKey;
	private final NamespacedKey upgradedKey;
	private final NamespacedKey statsKey;

	public AlvschRegistry(AlvschItems instance) {
		this.idKey = new NamespacedKey(instance, "id");
		this.abilityIdKey = new NamespacedKey(instance, "abilityIds");
		this.nameKey = new NamespacedKey(instance, "name");
		this.loreKey = new NamespacedKey(instance, "lore");
		this.rarityKey = new NamespacedKey(instance, "rarity");
		this.recipeKey = new NamespacedKey(instance, "recipe");
		this.upgradedKey = new NamespacedKey(instance, "upgraded");
		this.statsKey = new NamespacedKey(instance, "stats");
	}

	public Map<String, AItem> getItemIds() {
		return itemIds;
	}
	public Map<String, AAbility> getAbilityIds() {
		return abilityIds;
	}
	public List<ARecipe> getShapedRecipeList() {
		return shapedRecipeList;
	}
	public List<ARecipe> getShapelessRecipeList() {
		return shapelessRecipeList;
	}

	public NamespacedKey getIdKey() {
		return idKey;
	}
	public NamespacedKey getAbilityIdKey() {
		return abilityIdKey;
	}
	public NamespacedKey getNameKey() {
		return nameKey;
	}
	public NamespacedKey getLoreKey() {
		return loreKey;
	}
	public NamespacedKey getRarityKey() {
		return rarityKey;
	}
	public NamespacedKey getRecipeKey() {
		return recipeKey;
	}
	public NamespacedKey getUpgradedKey() {
		return upgradedKey;
	}
	public NamespacedKey getStatsKey() {
		return statsKey;
	}
}
