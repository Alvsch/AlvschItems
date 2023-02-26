package me.alvsch.alvschitems.implementation.abilities;

import me.alvsch.alvschitems.api.events.AbilityUseEvent;
import me.alvsch.alvschitems.api.items.AAbility;
import me.alvsch.alvschitems.api.items.AAbilityType;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class WitherImpact extends AAbility {

	public WitherImpact() {
		super("WITHER_IMPACT", "Wither Impact", List.of(
						"&7Teleports &a10 blocks&7 ahead of ",
						"&7you. Then implode dealing",
						"&c10000&7 damage to nearby",
						"&7enemies. Also applies the wither",
						"&7shield scroll ability reducing ",
						"&7damage taken and granting an ",
						"&7absorption shield for &e5 ",
						"&7seconds."
				), 0.15D, AAbilityType.RIGHT_CLICK);
	}

	@Override
	public void onAbilityUse(AbilityUseEvent event) {
		Player player = event.getPlayer();

		Block block = player.getTargetBlock(null,10);
		Location loc = block.getLocation();
		loc.setPitch(player.getLocation().getPitch());
		loc.setYaw(player.getLocation().getYaw());
		loc.add(.5, 0, .5);

		if(!block.getType().isAir()) loc.add(0, 1, 0);
		player.teleport(loc);

		player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
		player.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, loc, 6, 0, 0, 0, 7);

		for(Entity entity : player.getNearbyEntities(6, 6, 6)) {
			if(!(entity instanceof LivingEntity livingEntity) || entity instanceof Player) continue;
			livingEntity.damage(10_000, player);
		}

	}
}
