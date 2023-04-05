package me.alvsch.alvschitems.core.guis;

import me.alvsch.alvschitems.utils.ItemBuilder;
import me.alvsch.alvschitems.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class CraftMenu {

	private final Inventory inv;

	public CraftMenu() {
		this.inv = Bukkit.createInventory(null, 9*6, "Craft Menu");
		Utils.fillInventory(inv, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE, 1).setDisplayName(" ").toItemStack());
		Utils.setItems(inv, null, 10, 11, 12,
				19, 20, 21,
				28, 29, 30
		);
		Utils.setItems(inv, new ItemBuilder(Material.RED_STAINED_GLASS_PANE, 1).setDisplayName(" ").toItemStack(),
				45, 46, 47, 48, 50, 51, 52, 53);
		inv.setItem(49, new ItemBuilder(Material.ARROW, 1).setDisplayName(Utils.color("&aGo Back")).setLore(Utils.color("&7To Skyblock Menu")).toItemStack());
		inv.setItem(24, new ItemBuilder(Material.BARRIER, 1).setDisplayName(Utils.color("&cRecipe Required")).toItemStack());
	}

	public Inventory getInventory() {
		return inv;
	}

}
