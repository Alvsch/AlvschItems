package me.alvsch.alvschitems.implementation.abilities;

import me.alvsch.alvschitems.api.events.AbilityUseEvent;
import me.alvsch.alvschitems.api.items.AAbility;
import me.alvsch.alvschitems.api.items.AAbilityType;

import java.util.List;

public class SpeedBoost extends AAbility {

	public SpeedBoost() {
		super("SPEED_BOOST", "Speed Boost", List.of(
				"&7Grants &f+100✦ Speed &7for",
				"&a30s&7."
		), 0, AAbilityType.RIGHT_CLICK);
	}

	@Override
	public void onAbilityUse(AbilityUseEvent event) {
		
	}
}
