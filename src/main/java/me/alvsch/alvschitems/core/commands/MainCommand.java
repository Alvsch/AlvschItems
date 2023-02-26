package me.alvsch.alvschitems.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MainCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		return false;
	}

	@Nullable
	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		List<String> list = new ArrayList<>();

		if(args.length == 1) {
			list.add("give");
			list.add("list");
		}
		if(args[0].equalsIgnoreCase("give")) {

		}
		if(args.length == 3 && args[0].equalsIgnoreCase("give")) {
		}


		return list;
	}

}
