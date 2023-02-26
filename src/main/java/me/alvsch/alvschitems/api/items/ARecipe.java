package me.alvsch.alvschitems.api.items;

import me.alvsch.alvschitems.AlvschItems;
import me.alvsch.alvschitems.Logger;
import me.alvsch.alvschitems.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.persistence.PersistentDataType;
import org.checkerframework.checker.units.qual.A;

import java.util.*;

public class ARecipe {

	private final ItemStack result;
	private final List<String> items;
	private final boolean shaped;

	public ARecipe(ShapedRecipe recipe) {
		this.items = new ArrayList<>();
		this.result = recipe.getResult();

		this.shaped = true;

		Map<Character, ItemStack> map = recipe.getIngredientMap();

		List<String> shape = new ArrayList<>(Arrays.asList(recipe.getShape()));

		for(String spot : shape) {
			StringBuilder spotBuilder = new StringBuilder(spot);
			while (spotBuilder.length() < 3) {
				spotBuilder.append("z");
			}
			shape.set(shape.indexOf(spot), spotBuilder.toString());

		}

		while (shape.size() < 3) {
			shape.add("zzz");
		}

		// MATERIAL:ID:AMOUNT
		for (String spot : shape) {

			for (int j = 0; j < spot.length(); j++) {
				StringBuilder line = new StringBuilder();
				char c = spot.charAt(j);
				ItemStack item = map.get(c);

				if (item != null) {
					String id = "null";
					if(AItem.isAItem(item)) {
						id = item.getItemMeta().getPersistentDataContainer().get(AlvschItems.getInstance().getRegistry().getIdKey(), PersistentDataType.STRING);
					}
					line.append(item.getType().name()).append(":").append(id).append(":").append(item.getAmount());
				} else line.append("AIR");

				items.add(line.toString());
			}
		}

		AlvschItems.getInstance().getRegistry().getShapedRecipeList().add(this);
	}

	public ARecipe(ShapelessRecipe recipe) {
		this.result = recipe.getResult();
		this.items = new ArrayList<>();

		this.shaped = false;

		for(ItemStack item : recipe.getIngredientList()) {
			this.items.add(item.getType().name() + ":" + item.getItemMeta().getPersistentDataContainer().get(AlvschItems.getInstance().getRegistry().getIdKey(), PersistentDataType.STRING) + ":" + item.getAmount());
		}

		AlvschItems.getInstance().getRegistry().getShapelessRecipeList().add(this);
	}

	public ARecipe(ItemStack result, List<String> items, boolean shaped) {
		this.result = result;
		this.items = items;

		this.shaped = shaped;
	}

	public void register() {
		if(this.shaped) {
			AlvschItems.getInstance().getRegistry().getShapedRecipeList().add(this);
		} else {
			AlvschItems.getInstance().getRegistry().getShapelessRecipeList().add(this);
		}
	}

	public boolean canCraft(List<ItemStack> matrix) {
		for(int i = 0; i < items.size(); i++) {
			String[] data = items.get(i).split(":");
			ItemStack matrixItem = matrix.get(i);
			if(matrixItem == null) {
				if(!data[0].equals("AIR")) return false;
				continue;
			} else if (data[0].equals("AIR")){
				return false;
			}
			if(matrixItem.getAmount() < Integer.parseInt(data[2])) return false;
			if(data[1].equals("null")) {
				// Is a normal item
				if(!Objects.equals(Material.getMaterial(data[0]), matrixItem.getType())) return false;
			} else {
				// Is an AItem
				if(!AItem.isAItem(matrixItem)) return false;
				if(!data[1].equals(
						matrixItem.getItemMeta().getPersistentDataContainer().get(AlvschItems.getInstance().getRegistry().getIdKey(), PersistentDataType.STRING)
				)) return false;
			}
		}
		return true;
	}

	public ItemStack getResult() {
		return result;
	}

	public List<String> getItems() {
		return items;
	}

	public boolean isShaped() {
		return shaped;
	}

}
