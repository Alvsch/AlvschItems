package me.alvsch.alvschitems.api.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class AItemStack extends ItemStack {

	private String id;

	public AItemStack(Material material) {
		super(material);
	}
	public AItemStack(Material material, int amount) {
		super(material, amount);
	}

	public String getId() {
		return id;
	}

}
