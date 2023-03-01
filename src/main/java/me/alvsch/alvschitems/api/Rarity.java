package me.alvsch.alvschitems.api;

import org.bukkit.ChatColor;

public enum Rarity {

	COMMON(ChatColor.WHITE),
	UNCOMMON(ChatColor.GREEN),
	RARE(ChatColor.BLUE),
	EPIC(ChatColor.DARK_PURPLE),
	LEGENDARY(ChatColor.GOLD),
	MYTHIC(ChatColor.LIGHT_PURPLE),
	SPECIAL(ChatColor.RED),
	VERY_SPECIAL(ChatColor.RED),
	TEST(ChatColor.DARK_RED);

	private final ChatColor color;

	Rarity(final ChatColor color) {
		this.color = color;
	}

	public ChatColor getColor() {
		return this.color;
	}

	public Rarity next() {
		if(this.ordinal() == values().length - 1) return this;
		return values()[this.ordinal() + 1 % values().length];
	}

}
