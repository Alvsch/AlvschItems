package me.alvsch.alvschitems.implementation.abilities;

import me.alvsch.alvschitems.api.events.AbilityUseEvent;
import me.alvsch.alvschitems.api.items.AAbility;
import me.alvsch.alvschitems.api.items.AAbilityType;
import me.alvsch.alvschitems.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.List;

public class InstantTransmission extends AAbility {

	public InstantTransmission() {
		super("INSTANT_TRANSMISSION", "Instant Transmission", List.of(
				"&7Teleport &a8 blocks &7ahead of",
				"&7you and gain &a+50 &f✦ Speed",
				"&7for &a3 seconds&7."
		), 0, AAbilityType.RIGHT_CLICK);
	}

	@Override
	public void onAbilityUse(AbilityUseEvent event) {
		Player player = event.getPlayer();

		Block block = player.getTargetBlock(null,8);
		Location loc = block.getLocation();
		loc.setPitch(player.getLocation().getPitch());
		loc.setYaw(player.getLocation().getYaw());
		loc.add(.5, 0, .5);

		if(!block.getType().isAir()) loc.add(0, 1, 0);
		player.teleport(loc);
		if(!block.getType().isAir()) player.sendMessage(Utils.color("&cThere are blocks in the way!"));

		player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);


	}
}
