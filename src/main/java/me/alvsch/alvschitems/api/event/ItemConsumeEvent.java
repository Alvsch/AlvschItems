package me.alvsch.alvschitems.api.event;

import me.alvsch.alvschitems.api.item.ConsumableItem;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ItemConsumeEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final ConsumableItem consumable;

    public ItemConsumeEvent(Player player, ConsumableItem consumable) {
        this.player = player;
        this.consumable = consumable;
    }

    public Player getPlayer() {
        return player;
    }
    public ConsumableItem getConsumable() {
        return consumable;
    }

    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
