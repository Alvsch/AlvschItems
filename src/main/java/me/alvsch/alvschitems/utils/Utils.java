package me.alvsch.alvschitems.utils;

import me.alvsch.alvschitems.AlvschItems;
import me.alvsch.alvschitems.api.item.CustomItemStack;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
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

	public static String capitalize(String string) {
		StringBuilder sb = new StringBuilder();

		boolean nextTitleCase = true;
		for (char c : string.toCharArray()) {
			if (Character.isSpaceChar(c)) {
				nextTitleCase = true;
			} else if (nextTitleCase) {
				c = Character.toTitleCase(c);
				nextTitleCase = false;
			}

			sb.append(c);
		}

		return sb.toString();
	}

	public static String attributeToName(Attribute attribute) {
		String name = attribute.getKey().getKey();
		name = name.replace("generic.", "");
		name = capitalize(name.replace("_", " "));

		return name;
	}

	public static String format(String format, Object... args) {
		return String.format(format, args);
	}

	public static boolean containsSimilarItem(Inventory inventory, CustomItemStack item) {
		if (inventory == null || item == null) {
			return false;
		}

		for (ItemStack stack : inventory.getStorageContents()) {
			if (stack == null || stack.getType() == Material.AIR) {
				continue;
			}

			if (AlvschItems.getCustomItemDataService().hasEqualItemData(stack.getItemMeta(), item.getItemMeta())) {
				return true;
			}
		}

		return false;
	}

}
