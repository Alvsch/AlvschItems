package me.alvsch.alvschitems.implementation.listeners;

import me.alvsch.alvschitems.AlvschItems;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.getPlayer().setScoreboard(AlvschItems.getInstance().getScoreboard());
    }

}
