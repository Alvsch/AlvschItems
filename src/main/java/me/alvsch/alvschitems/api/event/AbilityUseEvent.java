package me.alvsch.alvschitems.api.event;

import me.alvsch.alvschitems.api.ability.Ability;
import me.alvsch.alvschitems.api.item.BaseItem;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class AbilityUseEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final Ability ability;
    private final BaseItem item;

    public AbilityUseEvent(Player player, Ability ability, BaseItem item) {
        this.player = player;
        this.ability = ability;

        this.item = item;
    }

    public Player getPlayer() {
        return player;
    }

    public Ability getAbility() {
        return ability;
    }

    public BaseItem getItem() {
        return item;
    }

    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
