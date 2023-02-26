package me.alvsch.alvschitems.implementation.setup;

import me.alvsch.alvschitems.AlvschItems;
import me.alvsch.alvschitems.api.items.AItem;
import me.alvsch.alvschitems.api.items.AItemStats;
import me.alvsch.alvschitems.api.items.ARarity;
import me.alvsch.alvschitems.api.items.ARecipe;
import me.alvsch.alvschitems.implementation.abilities.EtherTransmission;
import me.alvsch.alvschitems.implementation.abilities.InstantTransmission;
import me.alvsch.alvschitems.implementation.abilities.SpeedBoost;
import me.alvsch.alvschitems.implementation.abilities.WitherImpact;
import me.alvsch.alvschitems.utils.ItemBuilder;
import me.alvsch.alvschitems.utils.RecipeUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.checkerframework.checker.units.qual.A;

import java.util.List;

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

		//region Register Ingredients
		String blazePowder16 = RecipeUtils.createIngredient(Material.BLAZE_POWDER, 16);

		String diamond32 = RecipeUtils.createIngredient(Material.DIAMOND, 32);
		String obsidian32 = RecipeUtils.createIngredient(Material.OBSIDIAN, 32);


		String enderPearl4 = RecipeUtils.createIngredient(Material.ENDER_PEARL, 4);

		AItem nullSphere = new AItem("NULL_SPHERE", "Null Sphere", new ItemStack(Material.FIREWORK_STAR), ARarity.UNCOMMON,
				null, false
		).setGlint(true);
		nullSphere.register();

		//endregion
		//region Register Materials
		AItem eDiamond = new AItem("ENCHANTED_DIAMOND", "Enchanted Diamond", new ItemStack(Material.DIAMOND), ARarity.UNCOMMON,
				List.of(diamond32, diamond32, diamond32,
						diamond32, diamond32
				), false
		).setGlint(true);
		eDiamond.register();
		AItem eObsidian = new AItem("ENCHANTED_OBSIDIAN", "Enchanted Obsidian", new ItemStack(Material.OBSIDIAN), ARarity.UNCOMMON,
				List.of(obsidian32, obsidian32, obsidian32,
						obsidian32, obsidian32
				), false
		).setGlint(true);
		eDiamond.register();

		AItem eEnderPearl = new AItem("ENCHANTED_ENDER_PEARL", "Enchanted Ender Pearl", new ItemStack(Material.ENDER_PEARL), ARarity.UNCOMMON,
				List.of(enderPearl4, enderPearl4, enderPearl4,
						enderPearl4, enderPearl4
				), false
		).setGlint(true);
		eEnderPearl.register();

		AItem eEyeOfEnder = new AItem("ENCHANTED_EYE_OF_ENDER", "Enchanted Eye of Ender", new ItemStack(Material.ENDER_EYE), ARarity.UNCOMMON,
				List.of(blazePowder16, blazePowder16, blazePowder16,
						blazePowder16, RecipeUtils.createIngredient(eEnderPearl, 16)
				), false
		).setGlint(true);
		eEyeOfEnder.register();

		AItem nullOvoid = new AItem("NULL_OVOID", "Null Ovoid", new ItemStack(Material.ENDERMAN_SPAWN_EGG), ARarity.RARE,
				List.of(RecipeUtils.createIngredient(nullSphere, 32), RecipeUtils.createIngredient(nullSphere, 32), RecipeUtils.createIngredient(nullSphere, 32),
						RecipeUtils.createIngredient(nullSphere, 32), RecipeUtils.createIngredient(eObsidian, 32)
				), false
		).setGlint(true);
		nullOvoid.register();

		//endregion
		//region Register Items
		new AItem("HYPERION", "Hyperion", new ItemStack(Material.IRON_SWORD), ARarity.LEGENDARY,
				null, true
		)
				.addAbility(new WitherImpact())
				.setStats(new AItemStats()
						.setDamage(325)
						.setStrength(235)
						.setCritDamage(70)

						.setIntelligence(420)
						.setFerocity(35)

				).register();
		AItem aote = new AItem("ASPECT_OF_THE_END", "Aspect of the End", new ItemStack(Material.DIAMOND_SWORD), ARarity.RARE,
				List.of("AIR", RecipeUtils.createIngredient(eEyeOfEnder, 16), "AIR",
						"AIR", RecipeUtils.createIngredient(eEyeOfEnder, 16), "AIR",
						"AIR", RecipeUtils.createIngredient(eDiamond, 1), "AIR"
				), true
		)
				.addAbility(new InstantTransmission())
				.setStats(new AItemStats()
						.setDamage(100)
						.setStrength(100)

				);
		aote.register();
		new AItem("ASPECT_OF_THE_VOID", "Aspect of the Void", new ItemStack(Material.DIAMOND_SHOVEL), ARarity.EPIC,
				List.of("AIR", RecipeUtils.createIngredient(nullOvoid, 8), "AIR",
						RecipeUtils.createIngredient(nullOvoid, 8), RecipeUtils.createIngredient(aote, 1), RecipeUtils.createIngredient(nullOvoid, 8),
						"AIR", RecipeUtils.createIngredient(nullOvoid, 8), "AIR"

				), true
		)
				.addAbility(new InstantTransmission())
				.addAbility(new EtherTransmission())
				.setStats(new AItemStats()
						.setDamage(120)
						.setStrength(100)

				).register();
		new AItem("ROUGE_SWORD", "Rouge Sword", new ItemStack(Material.GOLDEN_SWORD), ARarity.COMMON,
				null, true
		)
				.addAbility(new SpeedBoost())
				.setStats(new AItemStats()
						.setDamage(20)

				).register();

		//endregion
	}

}
