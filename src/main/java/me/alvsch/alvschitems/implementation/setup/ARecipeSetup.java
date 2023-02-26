package me.alvsch.alvschitems.implementation.setup;

import me.alvsch.alvschitems.api.items.ARecipe;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import java.util.Iterator;

public class ARecipeSetup {

	private static boolean registeredRecipes = false;

	public static void setup() {
		if (!registeredRecipes) {

			registeredRecipes = true;

			for (Iterator<Recipe> it = Bukkit.recipeIterator(); it.hasNext(); ) {
				Recipe recipe = it.next();
				if (recipe instanceof ShapedRecipe) {
					new ARecipe((ShapedRecipe) recipe);
				} else if (recipe instanceof ShapelessRecipe) {
					new ARecipe((ShapelessRecipe) recipe);
				}

			}

		}

	}

}
