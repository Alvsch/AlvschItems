package me.alvsch.alvschitems.core;

import lombok.Getter;
import me.alvsch.alvschitems.AlvschItems;
import me.alvsch.alvschitems.api.CustomRecipe;
import me.alvsch.alvschitems.api.ability.Ability;
import me.alvsch.alvschitems.api.item.BaseItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class AlvschRegistry {

	private final Map<String, BaseItem> itemIds = new HashMap<>();
	private final Map<String, Ability> abilityIds = new HashMap<>();
	private final List<CustomRecipe> shapedRecipeList = new ArrayList<>();
	private final List<CustomRecipe> shapelessRecipeList = new ArrayList<>();

	public AlvschRegistry(AlvschItems instance) {

	}

}
