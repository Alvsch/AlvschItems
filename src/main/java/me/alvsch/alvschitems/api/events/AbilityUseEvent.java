package me.alvsch.alvschitems.api.events;

import me.alvsch.alvschitems.api.items.AAbility;
import me.alvsch.alvschitems.api.items.AItem;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class AbilityUseEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final AAbility ability;
    private final AItem item;

    public AbilityUseEvent(Player player, AAbility ability, AItem item) {
        this.player = player;
        this.ability = ability;

        this.item = item;
    }

    public Player getPlayer() {
        return player;
    }

    public AAbility getAbility() {
        return ability;
    }

    public AItem getItem() {
        return item;
    }

    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
