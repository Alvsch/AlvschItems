package me.alvsch.alvschitems.implementation.setup;

import me.alvsch.alvschitems.api.CustomRecipe;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import java.util.Iterator;

public class RecipeSetup {

	private static boolean registeredRecipes = false;

	public static void setup() {
		if (!registeredRecipes) {

			registeredRecipes = true;

			for (Iterator<Recipe> it = Bukkit.recipeIterator(); it.hasNext(); ) {
				Recipe recipe = it.next();
				if (recipe instanceof ShapedRecipe) {
					new CustomRecipe((ShapedRecipe) recipe);
				} else if (recipe instanceof ShapelessRecipe) {
					new CustomRecipe((ShapelessRecipe) recipe);
				}

			}

		}

	}

}
