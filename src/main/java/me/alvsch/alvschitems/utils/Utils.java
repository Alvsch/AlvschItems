package me.alvsch.alvschitems.utils;

import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Utils {

	public static String color(String text) {
		return ChatColor.translateAlternateColorCodes('&',text);
	}
	public static List<String> color(List<String> list) {
		List<String> list2 = new ArrayList<>();
		for(String s : list) {
			list2.add(Utils.color(s));
		}
		return list2;
	}

	public static boolean isAllBlank(List<String> list) {
		if(list == null || list.isEmpty()) return true;
		for(String s : list) {
			if(!s.isBlank()) return false;
		}
		return true;
	}

	public static void fillInventory(Inventory inv, ItemStack filler) {
		for(int i = 0; i < inv.getSize(); i++) {
			inv.setItem(i, filler);
		}
	}

	public static void setItems(Inventory inv, ItemStack item, int... amount) {
		for(int i : amount) {
			inv.setItem(i, item);
		}
	}

}
