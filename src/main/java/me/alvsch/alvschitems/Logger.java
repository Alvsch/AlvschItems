package me.alvsch.alvschitems;

import me.alvsch.alvschitems.utils.Utils;
import org.bukkit.Bukkit;

public class Logger {

	/**
	 * Log a message to the bukkit console
	 * @param level What type of log it should be
	 * @param message The message along with the log
	 */
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

	/**
	 * The different types of logs
	 */
	public enum LogLevel {ERROR, WARNING, INFO, SUCCESS, OUTLINE}
}