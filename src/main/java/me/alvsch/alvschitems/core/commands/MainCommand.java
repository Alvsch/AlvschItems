package me.alvsch.alvschitems.core.commands;

import me.alvsch.alvschitems.AlvschItems;
import me.alvsch.alvschitems.api.item.BaseItem;
import me.alvsch.alvschitems.utils.Utils;
import me.alvsch.alvschitems.utils.Validate;
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

public class MainCommand implements TabExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if(!Validate.argsLength(args, 1)) return false;

		// Open list
		if(args[0].equalsIgnoreCase("list")) {
			Player player;

			// Minimum two args
			if (args.length >= 2) {
				// Get player from second arg
				player = Bukkit.getPlayerExact(args[1]);
				if(player == null) return false;
			} else {
				// Get sender as player
				if(!(sender instanceof Player)) {
					return false;
				}
				player = (Player) sender;
			}

			// Create items inventory
			Inventory inv = Bukkit.createInventory(null, 6 * 9, "Items");
			for (BaseItem item : AlvschItems.getRegistry().getItemIds().values()) {
				inv.addItem(item.createItem());
			}

			player.sendMessage(Utils.color("&aOpening items list"));
			player.openInventory(inv);
			return true;
		}
		// Give
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
		// Debug
		if(args[0].equalsIgnoreCase("debug")) {
			if(!Validate.argsLength(args, 2)) return false;

			if(args[1].equalsIgnoreCase("item")) {
				if(!Validate.isInstanceOf(Player.class, sender)) return false;

				Player player = (Player) sender;
				BaseItem item = BaseItem.getByItem(player.getInventory().getItemInMainHand());

				player.sendMessage("ID: " + item.getId());
				player.sendMessage("Name: " + item.getName());
				player.sendMessage("Rarity: " + item.getRarity());
			}

		}
		// Reload
		if(args[0].equalsIgnoreCase("reload")) {
			sender.sendMessage("Reloading config.yml");
			AlvschItems.getInstance().createFiles();
			sender.sendMessage("Reloaded config.yml");
		}
		// Fix item
		if(args[0].equalsIgnoreCase("fixitem")) {
			if(!(sender instanceof Player player)) {
				return false;
			}
			ItemStack itemStack = player.getInventory().getItemInMainHand();
			if(!BaseItem.isCustomItem(itemStack)) {
				player.sendMessage("You need to hold a custom item");
				return false;
			}
			BaseItem item = new BaseItem(itemStack);
			player.getInventory().setItemInMainHand(item.createItem());
			sender.sendMessage("fixed");
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
