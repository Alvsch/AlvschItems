package me.alvsch.alvschitems.api.event;

import me.alvsch.alvschitems.api.item.BaseItem;
import me.alvsch.alvschitems.api.item.CustomArmorPiece;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ArmorUnequipEvent extends Event {

	private static final HandlerList handlers = new HandlerList();

	private final Player player;
	private final CustomArmorPiece armorPiece;

	public ArmorUnequipEvent(Player player, CustomArmorPiece armorPiece) {
		this.player = player;
		this.armorPiece = armorPiece;
	}

	public Player getPlayer() {
		return player;
	}

	public BaseItem getArmorPiece() {
		return armorPiece;
	}

	public @NotNull HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}

