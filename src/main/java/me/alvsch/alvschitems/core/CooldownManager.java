package me.alvsch.alvschitems.core;

import me.alvsch.alvschitems.AlvschItems;
import me.alvsch.alvschitems.api.ability.Ability;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {

	private final AlvschItems plugin;
	private final Map<UUID, Map<String, BukkitTask>> cooldowns = new HashMap<>();

	public CooldownManager(AlvschItems plugin) {
		this.plugin = plugin;
	}

	public void addCooldown(UUID uuid, Ability ability) {
		Map<String, BukkitTask> playerCooldowns = cooldowns.computeIfAbsent(uuid, k -> new HashMap<>());

		playerCooldowns.put(ability.getId(), new BukkitRunnable() {
			@Override
			public void run() {
				playerCooldowns.remove(ability.getId());
				if (playerCooldowns.isEmpty()) {
					cooldowns.remove(uuid);
				}
			}
		}.runTaskLater(plugin, (long) ability.getCooldown() * 20L));
	}


	public boolean isOnCooldown(UUID uuid, String itemId) {
		Map<String, BukkitTask> playerCooldowns = cooldowns.get(uuid);

		if(playerCooldowns == null) return false;
		return playerCooldowns.get(itemId) != null;
	}

}
