package me.alvsch.alvschitems.implementation.setup;

import me.alvsch.alvschitems.api.Rarity;
import me.alvsch.alvschitems.api.item.BaseItem;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class AItemSetup {

/*
				List.of(RecipeUtils.createIngredient(), RecipeUtils.createIngredient(), RecipeUtils.createIngredient(),
						RecipeUtils.createIngredient(), RecipeUtils.createIngredient(), RecipeUtils.createIngredient(),
						RecipeUtils.createIngredient(), RecipeUtils.createIngredient(), RecipeUtils.createIngredient()
				)
	 */



	private static boolean registeredItems = false;

	public static void setup() {
		if(registeredItems) {
			return;
		}

		registeredItems = true;

		BaseItem baseItem = new BaseItem("test", "Test", new ItemStack(Material.DIAMOND_SWORD), Rarity.TEST, null);
		baseItem.addModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(
				UUID.randomUUID(), "generic.attackSpeed", 1.6, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND)
		);
		baseItem.addModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(
				UUID.randomUUID(), "generic.attackDamage", 10, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND)
		);
		baseItem.register();
	}

}
