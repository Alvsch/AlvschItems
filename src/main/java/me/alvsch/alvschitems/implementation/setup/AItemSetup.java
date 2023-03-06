package me.alvsch.alvschitems.implementation.setup;

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


	}

}
