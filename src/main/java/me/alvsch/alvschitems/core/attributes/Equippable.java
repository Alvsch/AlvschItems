package me.alvsch.alvschitems.core.attributes;

import me.alvsch.alvschitems.api.event.ArmorEquipEvent;
import me.alvsch.alvschitems.api.event.ArmorUnequipEvent;

public interface Equippable {

	default void onEquip(ArmorEquipEvent event) {}
	default void onUnequip(ArmorUnequipEvent event) {}

}
