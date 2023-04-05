package me.alvsch.alvschitems.implementation.listeners;

import me.alvsch.alvschitems.AlvschItems;
import me.alvsch.alvschitems.api.ability.Ability;
import me.alvsch.alvschitems.api.event.AbilityUseEvent;
import me.alvsch.alvschitems.api.event.ItemConsumeEvent;
import me.alvsch.alvschitems.api.item.BaseItem;
import me.alvsch.alvschitems.api.item.ConsumableItem;
import me.alvsch.alvschitems.core.CooldownManager;
import me.alvsch.alvschitems.core.attributes.AbilityHolder;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;
import java.util.UUID;

public class EventListener implements Listener {

	@EventHandler
	public void abilityListener(PlayerInteractEvent event) {

		if(event.getItem() == null || event.getItem().getType().isAir() || !BaseItem.isCustomItem(event.getItem())) return;
		BaseItem item = BaseItem.getByItem(event.getItem());
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
		Bukkit.getPluginManager().callEvent(customEvent);
	}

	@EventHandler
	public void itemConsumeListener(PlayerInteractEvent event) {
		if(!event.getAction().equals(Action.RIGHT_CLICK_AIR) &&
				!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)
		) return;

		if(event.getItem() == null || event.getItem().getType().isAir() || !BaseItem.isCustomItem(event.getItem())) return;
		BaseItem item = BaseItem.getByItem(event.getItem());
		if (!(item instanceof ConsumableItem consumable)) return;

		ItemConsumeEvent customEvent = new ItemConsumeEvent(event.getPlayer(), consumable);
		event.setCancelled(true);
		if(consumable.consumeOnUse()) {
			event.getItem().setAmount(event.getItem().getAmount() - 1);
		}

		Bukkit.getPluginManager().callEvent(customEvent);

	}

	@EventHandler
	public void onAbilityEvent(AbilityUseEvent event) {
		UUID uuid = event.getPlayer().getUniqueId();
		String itemId = event.getItem().getId();

		CooldownManager cdManager = AlvschItems.getInstance().getCooldownManager();
		if(cdManager.isOnCooldown(uuid, itemId)) {
			if(!event.getAbility().isOverwrite()) {
				return;
			}
		}

		cdManager.addCooldown(uuid, event.getAbility());
		event.getAbility().onAbilityUse(event);
	}

	@EventHandler
	public void onItemConsumeEvent(ItemConsumeEvent event) {
		event.getConsumable().onConsume(event);
	}

}
