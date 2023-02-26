package me.alvsch.alvschitems.core;

import me.alvsch.alvschitems.AlvschItems;
import me.alvsch.alvschitems.api.items.AAbility;
import me.alvsch.alvschitems.api.items.AItem;
import me.alvsch.alvschitems.api.items.ARecipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlvschRegistry {

	private final Map<String, AItem> itemIds = new HashMap<>();
	private final Map<String, AAbility> abilityIds = new HashMap<>();
	private final List<ARecipe> shapedRecipeList = new ArrayList<>();
	private final List<ARecipe> shapelessRecipeList = new ArrayList<>();

	public AlvschRegistry(AlvschItems instance) {
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

}
