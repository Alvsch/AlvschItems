package me.alvsch.alvschitems.core.commands;

import me.alvsch.alvschitems.AlvschItems;
import me.alvsch.alvschitems.api.item.BaseItem;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MainCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if(args.length < 1) {
			return false;
		}

		if(args[0].equalsIgnoreCase("list")) {
			Player player;
			if (args.length >= 2) {
				player = Bukkit.getPlayerExact(args[1]);
				if(player == null) return false;
			} else {
				if(!(sender instanceof Player)) {
					return false;
				}
				player = (Player) sender;
			}

			Inventory inv = Bukkit.createInventory(null, 6 * 9, "Items");
			for (BaseItem item : AlvschItems.getInstance().getRegistry().getItemIds().values()) {
				inv.addItem(item.createItem());
			}

			player.openInventory(inv);
			return true;
		}
		if(args[0].equalsIgnoreCase("give")) {
			if(!(args.length >= 3)) {
				return false;
			}

			Player player = Bukkit.getPlayerExact(args[1]);
			BaseItem aItem = BaseItem.getById(args[2]);
			if(aItem == null || player == null) {
				return false;
			}
			int amount = 1;
			if(args.length >= 4) {
				amount = Math.max(1, Integer.parseInt(args[3]));
				if (Double.isNaN(Double.parseDouble(args[3]))) amount = 1;
				amount = Math.min(64, amount);
			}

			ItemStack item = aItem.createItem();
			item.setAmount(amount);
			player.getInventory().addItem(item);
		}

		return true;
	}

	@Nullable
	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		List<String> list = new ArrayList<>();

		if(args.length == 1) {
			list.addAll(Stream.of("give", "list")
					.filter(entry -> entry.startsWith(args[0]))
					.toList());

			return list;
		}
		if(args[0].equalsIgnoreCase("list")) {
			if(args.length == 2) {
				Bukkit.getOnlinePlayers().stream()
						.filter(player -> player.getName().toLowerCase().startsWith(args[1].toLowerCase()))
						.forEach(player -> list.add(player.getName()));
			}
		}

		if(args[0].equalsIgnoreCase("give")) {
			if(args.length == 2) {
				Bukkit.getOnlinePlayers().stream()
						.filter(player -> player.getName().toLowerCase().startsWith(args[1].toLowerCase()))
						.forEach(player -> list.add(player.getName()));
			}

			if(args.length == 3) {
				AlvschItems.getInstance().getRegistry().getItemIds().keySet().stream()
						.filter(key -> key.toLowerCase().startsWith(args[2].toLowerCase()))
						.forEach(list::add);
			}
		}


		return list;
	}

}
