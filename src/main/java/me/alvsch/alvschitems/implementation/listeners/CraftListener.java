package me.alvsch.alvschitems.implementation.listeners;

import me.alvsch.alvschitems.AlvschItems;
import me.alvsch.alvschitems.api.CustomRecipe;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CraftListener implements Listener {

	private final List<Integer> slots = List.of(10, 11, 12, 19, 20, 21, 28, 29, 30); // Result slot: 24

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		String title = event.getView().getTitle();
		Inventory inv = event.getClickedInventory();
		Player player = (Player) event.getWhoClicked();

		if(!title.equals("Craft Menu") || inv == null || !Objects.equals(event.getClickedInventory(), event.getView().getTopInventory())) return;

		if(event.getSlot() == 49) {
			event.setCancelled(true);
			player.closeInventory();
			return;
		}

		if(!slots.contains(event.getSlot())) {
			event.setCancelled(true);
		}
		if(event.getSlot() == 24) {
			List<ItemStack> matrix = new ArrayList<>();
			for(int i : slots) {
				matrix.add(inv.getItem(i));
			}
			CustomRecipe recipe = getRecipe(matrix);
			if(recipe != null) {
				CustomRecipe.consumeItems(inv, recipe);
				player.getInventory().addItem(recipe.getResult());
			}
		}
	}

    /*
	@EventHandler
	public void onDrag(InventoryDragEvent event) {
		if(event.getView().getTitle().equals("Craft Menu")) event.setCancelled(true);
	}
	*/

	@EventHandler
	public void onClose(InventoryCloseEvent event) {
		Player player = (Player) event.getPlayer();
		Inventory playerInv = player.getInventory();
		Inventory inv = event.getInventory();

		if(event.getView().getTitle().equals("Craft Menu")) {
			for(int i : slots) {
				if(inv.getItem(i) == null) continue;
				playerInv.addItem(inv.getItem(i));
			}
		}
	}


	private CustomRecipe getRecipe(List<ItemStack> matrix) {
		for(CustomRecipe recipe : AlvschItems.getRegistry().getShapedRecipeList()) {
			if(recipe.canCraft(matrix)) {
				return recipe;
			}
		}
		List<String> shapeless = new ArrayList<>();
		for(ItemStack item : matrix) {
			if(item == null) continue;
			String id = "null";
			Optional<String> optionalId = AlvschItems.getCustomItemDataService().getItemData(item);
			if(optionalId.isPresent()) {
				id = optionalId.get();
			}
			shapeless.add(item.getType().name() + ":" + id + ":" +  item.getAmount());
		}
		for(CustomRecipe recipe : AlvschItems.getRegistry().getShapelessRecipeList()) {
			if(recipe.getItems().size() != shapeless.size()) continue;
			if(isRecipeValid(recipe, shapeless)) return recipe;
		}

		return null;
	}

	private boolean isRecipeValid(CustomRecipe recipe, List<String> matrix) {
		for(int i = 0; i < recipe.getItems().size(); i++) {
			String[] recipeData = recipe.getItems().get(i).split(":");
			String[] matrixData = matrix.get(i).split(":");

			if(Integer.parseInt(matrixData[2]) < Integer.parseInt(recipeData[2])) return false;
			if(!matrixData[0].equals(recipeData[0])) return false;
			if(!matrixData[1].equals(recipeData[1])) return false;
		}
		return true;
	}




}
