package me.alvsch.alvschitems.core.commands;

import me.alvsch.alvschitems.api.items.AItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Upgrade implements CommandExecutor {
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if(!(sender instanceof Player player)) {
			return false;
		}

		ItemStack item = player.getInventory().getItemInMainHand();
		if(item.getType().isAir() || !AItem.isAItem(item)) {
			return false;
		}

		AItem aitem = new AItem(item);
		aitem.setUpgraded(true);
		player.getInventory().setItemInMainHand(aitem.createItem());

		return true;
	}
}
