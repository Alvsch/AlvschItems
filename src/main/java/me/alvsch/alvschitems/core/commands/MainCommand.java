package me.alvsch.alvschitems.core.commands;

import lombok.RequiredArgsConstructor;
import me.alvsch.alvschitems.AlvschItems;
import me.alvsch.alvschitems.api.item.BaseItem;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class MainCommand implements TabExecutor {

	private final AlvschItems plugin;

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if(args.length < 1) {
			return false;
		}

		// Open list
		if(args[0].equalsIgnoreCase("list")) {
			if(!sender.hasPermission(plugin.getPermission("items-list"))) {
				sender.sendMessage(plugin.getMessage("no-permission"));
				return false;
			}

			// Minimum two args
			Player player;
			if (args.length >= 2) {
				// Get player from second arg
				player = Bukkit.getPlayerExact(args[1]);
				if (player == null) {
					sender.sendMessage(plugin.getMessage("player-not-found"));
					return false;
				}
			} else {
				// Get sender as player
				if (!(sender instanceof Player)) {
					sender.sendMessage(plugin.getMessage("console-cant-execute"));
					return false;
				}
				player = (Player) sender;
			}

			// Create items inventory
			Inventory inv = Bukkit.createInventory(null, 6 * 9, "Items");
			for (BaseItem item : AlvschItems.getRegistry().getItemIds().values()) {
				inv.addItem(item.createItem());
			}

			player.sendMessage(plugin.getMessage("item-list-open"));
			player.openInventory(inv);
			return true;
		}
		// Give
		if(args[0].equalsIgnoreCase("give")) {
			if(!sender.hasPermission(plugin.getPermission("items-give"))) {
				sender.sendMessage(plugin.getMessage("no-permission"));
				return false;
			}
			if(args.length < 3) {
				return false;
			}
			Player target = Bukkit.getPlayerExact(args[1]);
			if(target == null) {
				sender.sendMessage(plugin.getMessage("player-not-found"));
				return false;
			}
			BaseItem customItem = BaseItem.getById(args[2]);
			if(customItem == null) {
				sender.sendMessage(plugin.getMessage("custom-item-not-found"));
				return false;
			}

			int amount = 1;
			// Set custom amount
			if(args.length >= 4) {
				amount = Math.max(1, Integer.parseInt(args[3]));
				if (Double.isNaN(Double.parseDouble(args[3]))) amount = 1;
				amount = Math.min(64, amount);
			}

			ItemStack item = customItem.createItem();
			item.setAmount(amount);

			sender.sendMessage(plugin.getMessage("item-give-success").replace("{sender}", target.getName()));
			target.getInventory().addItem(item);
		}
		// Debug
		if(args[0].equalsIgnoreCase("debug")) {
			if(!sender.hasPermission(plugin.getPermission("items-debug"))) {
				sender.sendMessage(plugin.getMessage("no-permission"));
				return false;
			}
			if(args.length < 2) {
				return false;
			}

			if(args[1].equalsIgnoreCase("item")) {
				if(!(sender instanceof Player player)) {
					return false;
				}
				BaseItem item = BaseItem.getByItem(player.getInventory().getItemInMainHand());

				player.sendMessage("ID: " + item.getId());
				player.sendMessage("Name: " + item.getName());
				player.sendMessage("Rarity: " + item.getRarity());
				return true;
			}
			return false;

		}
		// Reload
		if(args[0].equalsIgnoreCase("reload")) {
			if(!sender.hasPermission(plugin.getPermission("items-reload"))) {
				sender.sendMessage(plugin.getMessage("no-permission"));
				return false;
			}
			sender.sendMessage(plugin.getMessage("reload-start"));
			plugin.createFiles();
			sender.sendMessage(plugin.getMessage("reload-finish"));
		}
		// Fix item
		if(args[0].equalsIgnoreCase("fixitem")) {
			if(!sender.hasPermission(plugin.getPermission("items-fixitem"))) {
				sender.sendMessage(plugin.getMessage("no-permission"));
				return false;
			}
			if(!(sender instanceof Player player)) {
				sender.sendMessage(plugin.getMessage("console-cant-execute"));
				return false;
			}

			ItemStack itemStack = player.getInventory().getItemInMainHand();
			if(!BaseItem.isCustomItem(itemStack)) {
				player.sendMessage("You need to hold a custom item");
				return false;
			}
			BaseItem item = new BaseItem(itemStack);
			player.getInventory().setItemInMainHand(item.createItem());
			sender.sendMessage(plugin.getMessage("item-fixitem-done"));
		}

		return true;
	}

	@Nullable
	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		List<String> list = new ArrayList<>();

		if(args.length == 1) {
			list.addAll(Stream.of("give", "list", "debug", "reload", "fixitem")
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
				AlvschItems.getRegistry().getItemIds().keySet().stream()
						.filter(key -> key.toLowerCase().startsWith(args[2].toLowerCase()))
						.forEach(list::add);
			}
		}

		if(args[0].equalsIgnoreCase("debug")) {
			if(args.length == 2) {
				list.addAll(Stream.of( "item")
						.filter(entry -> entry.startsWith(args[1]))
						.toList());
			}
		}


		return list;
	}

}
