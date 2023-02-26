package me.alvsch.alvschitems.core;

import me.alvsch.alvschitems.AlvschItems;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {

	private final AlvschItems plugin;
	private final Map<UUID, Map<String, Long>> cooldowns = new HashMap<>();

	public CooldownManager(AlvschItems plugin) {
		this.plugin = plugin;
	}

	public void addCooldown(UUID uuid, String itemId, double cooldownSeconds) {
		Map<String, Long> playerCooldowns = cooldowns.computeIfAbsent(uuid, k -> new HashMap<>());

		long endTime = ((long) cooldownSeconds * 1000L) + System.currentTimeMillis();
		playerCooldowns.put(itemId, endTime);
		new BukkitRunnable() {
			@Override
			public void run() {
				playerCooldowns.remove(itemId);
				if (playerCooldowns.isEmpty()) {
					cooldowns.remove(uuid);
				}
			}
		}.runTaskLater(plugin, (long) cooldownSeconds * 20L);
	}

	public boolean isOnCooldown(UUID uuid, String itemId) {
		Map<String, Long> playerCooldowns = cooldowns.get(uuid);

		if(playerCooldowns == null) return false;
		if(playerCooldowns.get(itemId) == null) return false;

		return true;
	}

	public int getRemainingSeconds(UUID uuid, String itemId) {
		if(!isOnCooldown(uuid, itemId)) return 0;

		long endTime = cooldowns.get(uuid).get(itemId);
		return (int) Math.ceil((endTime - System.currentTimeMillis()) / 1000.0);
	}

}
