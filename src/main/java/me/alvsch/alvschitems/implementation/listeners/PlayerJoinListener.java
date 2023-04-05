package me.alvsch.alvschitems.implementation.listeners;

import me.alvsch.alvschitems.api.item.BaseItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Inventory inv = player.getInventory();

        for(int i = 0; i < inv.getContents().length; i++) {
            if(inv.getContents()[i] == null) continue;
            if(!BaseItem.isCustomItem(inv.getContents()[i])) continue;

            BaseItem item = new BaseItem(inv.getContents()[i]);
            inv.setItem(i, item.createItem());
        }
    }

}
