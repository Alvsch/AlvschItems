package me.alvsch.alvschitems.api;

import me.alvsch.alvschitems.AlvschItems;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import java.util.*;

public class CustomRecipe {

	private final ItemStack result;
	private final List<String> items;
	private final boolean shaped;

	public CustomRecipe(ShapedRecipe recipe) {
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
					Optional<String> optionalId = AlvschItems.getCustomItemDataService().getItemData(item);
					if(optionalId.isPresent()) {
						id = optionalId.get();
					}

					line.append(item.getType().name()).append(":").append(id).append(":").append(item.getAmount());
				} else line.append("AIR");

				items.add(line.toString());
			}
		}

		AlvschItems.getRegistry().getShapedRecipeList().add(this);
	}

	public CustomRecipe(ShapelessRecipe recipe) {
		this.result = recipe.getResult();
		this.items = new ArrayList<>();

		this.shaped = false;

		for(ItemStack item : recipe.getIngredientList()) {
			this.items.add(item.getType().name() + ":" + AlvschItems.getCustomItemDataService().getItemData(item) + ":" + item.getAmount());
		}

		AlvschItems.getRegistry().getShapelessRecipeList().add(this);
	}

	public CustomRecipe(List<String> items, boolean shaped) {
		this(null, items, shaped);
	}

	public CustomRecipe(ItemStack result, List<String> items, boolean shaped) {
		this.result = result;
		this.items = items;

		this.shaped = shaped;
	}

	public void register() {
		if(this.result == null) return;
		if(this.shaped) {
			AlvschItems.getRegistry().getShapedRecipeList().add(this);
		} else {
			AlvschItems.getRegistry().getShapelessRecipeList().add(this);
		}
	}

	public boolean canCraft(List<ItemStack> matrix) {
		if(this.items == null) return false;
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
				Optional<String> optionalId = AlvschItems.getCustomItemDataService().getItemData(matrixItem);
				if(optionalId.isEmpty()) return false;

				if(!data[1].equals(
						optionalId.get()
				)) return false;
			}
		}
		return true;
	}

	//region Getters
	public ItemStack getResult() {
		return result;
	}

	public List<String> getItems() {
		return items;
	}

	public boolean isShaped() {
		return shaped;
	}
	//endregion

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

	public static void consumeItems(Inventory inv, CustomRecipe recipe) {
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

	/**
	 *
	 * @param material The material
	 * @param amount The amount
	 * @return Format material and amount to a recipe string format
	 */
	public static String format(Material material, int amount) {
		return material + ":null:" + amount;
	}

}
