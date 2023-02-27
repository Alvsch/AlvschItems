package me.alvsch.alvschitems.utils;

import me.alvsch.alvschitems.api.items.AItem;
import me.alvsch.alvschitems.api.items.ARecipe;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class RecipeUtils {

	private static final Map<Integer, Integer> slots = new HashMap<>(Map.of(
			0, 10,
			1, 11,
			2, 12,
			3, 19,
			4, 20,
			5, 21,
			6, 28,
			7, 29,
			8, 30)
	); // Result slot: 24

	public static String createIngredient(ItemStack item, int amount) {
		return item.getType().name() + ":" + "null" + ":" + amount;
	}

	public static String createIngredient(AItem aItem, int amount) {
		ItemStack item = aItem.createItem();
		return item.getType().name() + ":" + aItem.getId() + ":" + amount;
	}

	public static String createIngredient(Material material, int amount) {
		return material.name() + ":" + "null" + ":" + amount;
	}

	public static void consumeItems(Inventory inv, ARecipe recipe) {
		for(int i = 0; i < recipe.getItems().size(); i++) {
			String[] data = recipe.getItems().get(i).split(":");
			if(data[0].equals("AIR")) continue;
			ItemStack item = inv.getItem(slots.get(i));
			if(item == null) continue;
			int amount = item.getAmount() - Integer.parseInt(data[2]);
			amount = Math.max(0, amount);
			item.setAmount(amount);

		}
	}

}
