package me.alvsch.alvschitems.api.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class AItemStack extends ItemStack {

	private String id;

	public AItemStack(String id, Material material) {
		super(material);
	}
	public AItemStack(String id, Material material, int amount) {
		super(material, amount);
	}

	public String getId() {
		return id;
	}

}
