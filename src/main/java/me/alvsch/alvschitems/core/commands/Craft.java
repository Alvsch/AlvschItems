package me.alvsch.alvschitems.core.commands;

import me.alvsch.alvschitems.core.guis.CraftMenu;
import me.alvsch.alvschitems.utils.ItemBuilder;
import me.alvsch.alvschitems.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;

public class Craft implements CommandExecutor {

	// Crafting matrix:
	// 10, 11, 12,
	// 19, 20, 21,
	// 28, 29, 30

	// Result slot:
	// 24

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if(!(sender instanceof Player player)) {
			return false;
		}

		player.openInventory(new CraftMenu().getInventory());

		return true;
	}
}
