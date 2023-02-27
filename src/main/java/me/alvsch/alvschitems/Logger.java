package me.alvsch.alvschitems;

import me.alvsch.alvschitems.utils.Utils;
import org.bukkit.Bukkit;

public class Logger {
	public static void log(LogLevel level, String message) {
		if(message == null) return;

		switch (level) {
			case ERROR -> Bukkit.getConsoleSender().sendMessage(Utils.color("&8[&c&lERROR&8] &f" + message));
			case WARNING -> Bukkit.getConsoleSender().sendMessage(Utils.color("&8[&7WARNING&8] &f" + message));
			case INFO -> Bukkit.getConsoleSender().sendMessage(Utils.color("&8[&fINFO&8] &f" + message));
			case SUCCESS -> Bukkit.getConsoleSender().sendMessage(Utils.color("&8[&aSUCCESS&8] &f" + message));
			case OUTLINE -> Bukkit.getConsoleSender().sendMessage(Utils.color("&7" + message));
		}


	}

	public enum LogLevel {ERROR, WARNING, INFO, SUCCESS, OUTLINE}
}