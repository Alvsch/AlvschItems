package me.alvsch.alvschitems.implementation.listeners;

import me.alvsch.alvschitems.AlvschItems;
import me.alvsch.alvschitems.api.events.AbilityUseEvent;
import me.alvsch.alvschitems.api.items.AAbility;
import me.alvsch.alvschitems.api.items.AItem;
import me.alvsch.alvschitems.core.CooldownManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;
import java.util.UUID;

public class AbilityUseListener implements Listener {

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {

		if(event.getItem() == null || !AItem.isAItem(event.getItem())) return;
		AItem item = new AItem(event.getItem());
		List<AAbility> abilities = item.getAbilities();

		AAbility aAbility = null;
		for(AAbility a : abilities) {
			if(a == null) continue;
			String action = a.getType().name();
			if(event.getPlayer().isSneaking()) {
				if(!action.startsWith("SNEAK")) continue;
			} else {
				if(action.startsWith("SNEAK")) continue;
			}

			if(event.getAction().toString().startsWith(action.replace("SNEAK_", ""))) {
				aAbility = a;
				break;
			}
		}
		if(aAbility == null) return;

		event.setCancelled(true);
		AbilityUseEvent customEvent = new AbilityUseEvent(event.getPlayer(), aAbility, item);
		AlvschItems.getInstance().getServer().getPluginManager().callEvent(customEvent);
	}

	@EventHandler
	public void onAbilityUse(AbilityUseEvent event) {
		UUID uuid = event.getPlayer().getUniqueId();
		String itemId = event.getItem().getId();

		CooldownManager cdManager = AlvschItems.getInstance().getCooldownManager();
		if(cdManager.isOnCooldown(uuid, itemId)) return;

		cdManager.addCooldown(uuid, itemId, event.getAbility().getCooldown());
		event.getAbility().onAbilityUse(event);
	}

}
