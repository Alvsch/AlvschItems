package me.alvsch.alvschitems.implementation.listeners;

import de.tr7zw.nbtapi.NBTItem;
import me.alvsch.alvschitems.AlvschItems;
import me.alvsch.alvschitems.api.ability.Ability;
import me.alvsch.alvschitems.api.event.AbilityUseEvent;
import me.alvsch.alvschitems.api.item.BaseItem;
import me.alvsch.alvschitems.core.CooldownManager;
import me.alvsch.alvschitems.core.attributes.AbilityHolder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;

import java.util.List;
import java.util.UUID;

public class EventListener implements Listener {

	@EventHandler
	public void onAbilityListener(PlayerInteractEvent event) {

		if(event.getItem() == null || !BaseItem.isCustomItem(event.getItem())) return;
		BaseItem item = BaseItem.getById(new NBTItem(event.getItem()).getString("id"));
		if (!(item instanceof AbilityHolder abilityContainer)) return;
		List<Ability> abilities = abilityContainer.getAbilities();

		Ability ability = null;
		for(Ability a : abilities) {
			if(a == null) continue;
			String action = a.getType().name();
			if(event.getPlayer().isSneaking()) {
				if(!action.startsWith("SNEAK")) continue;
			} else {
				if(action.startsWith("SNEAK")) continue;
			}

			if(event.getAction().toString().startsWith(action.replace("SNEAK_", ""))) {
				ability = a;
				break;
			}
		}
		if(ability == null) return;

		event.setCancelled(true);
		AbilityUseEvent customEvent = new AbilityUseEvent(event.getPlayer(), ability, item);
		AlvschItems.getInstance().getServer().getPluginManager().callEvent(customEvent);
	}

	@EventHandler
	public void onAbilityEvent(AbilityUseEvent event) {
		UUID uuid = event.getPlayer().getUniqueId();
		String itemId = event.getItem().getId();

		CooldownManager cdManager = AlvschItems.getInstance().getCooldownManager();
		if(cdManager.isOnCooldown(uuid, itemId)) return;

		cdManager.addCooldown(uuid, itemId, event.getAbility().getCooldown());
		event.getAbility().onAbilityUse(event);
	}



}
