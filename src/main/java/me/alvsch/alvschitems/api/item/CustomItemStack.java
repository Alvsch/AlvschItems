package me.alvsch.alvschitems.api.item;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CustomItemStack extends ItemStack {

	@Getter
	private final String id;

	public CustomItemStack(String id, Material material) {
		this(id, material, 1);
	}
	public CustomItemStack(String id, Material material, int amount) {
		super(material, amount);
		this.id = id;
	}

	@Override
	public String toString() {
		return this.toString(getAmount());
	}
	public String toString(int amount) {
		return getType() + ":" + id + ":" + amount;
	}
}
