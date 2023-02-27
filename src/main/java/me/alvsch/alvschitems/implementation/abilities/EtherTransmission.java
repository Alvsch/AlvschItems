package me.alvsch.alvschitems.implementation.abilities;

import me.alvsch.alvschitems.api.events.AbilityUseEvent;
import me.alvsch.alvschitems.api.items.AAbility;
import me.alvsch.alvschitems.api.items.AAbilityType;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.util.List;

public class EtherTransmission extends AAbility {

	public EtherTransmission() {
		super("ETHER_TRANSMISSION", "Ether Transmission", List.of(
				"&7Teleport to your targetted block",
				"&7up to &a57 blocks &7away"
		), 0, AAbilityType.SNEAK_RIGHT_CLICK);
	}

	@Override
	public void onAbilityUse(AbilityUseEvent event) {
		Player player = event.getPlayer();

		Block block = player.getTargetBlock(null,57);
		if(block.getType().isAir()) return;
		if(!block.getRelative(BlockFace.UP).getType().isAir()) return;

		Location loc = block.getLocation();
		loc.setPitch(player.getLocation().getPitch());
		loc.setYaw(player.getLocation().getYaw());
		loc.add(.5, 1, .5);

		player.teleport(loc);
		player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_HURT, 1, 1);
	}
}
