package me.alvsch.alvschitems.core.commands;

import me.alvsch.alvschitems.AlvschItems;
import me.alvsch.alvschitems.api.items.AItem;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class Items implements CommandExecutor {
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if(!(sender instanceof Player player)) {
			return false;
		}
		Inventory inv = Bukkit.createInventory(null, 6*9, "Items");
		for(AItem item : AlvschItems.getInstance().getRegistry().getItemIds().values()) {
			inv.addItem(item.createItem());
		}

		player.openInventory(inv);
		return true;
	}
}
