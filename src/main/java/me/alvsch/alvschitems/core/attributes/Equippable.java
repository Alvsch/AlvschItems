package me.alvsch.alvschitems.core.attributes;

import me.alvsch.alvschitems.api.event.ArmorEquipEvent;
import me.alvsch.alvschitems.api.event.ArmorUnequipEvent;

public interface Equippable extends ItemAttribute {

	void onEquip(ArmorEquipEvent event);
	void onUnequip(ArmorUnequipEvent event);

}
