package me.alvsch.alvschitems.api.item.armor;

import me.alvsch.alvschitems.api.CustomRecipe;
import me.alvsch.alvschitems.api.Rarity;
import me.alvsch.alvschitems.api.item.BaseItem;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CustomArmor extends BaseItem {


    public CustomArmor(ItemStack item) {
        super(item);
    }

    public CustomArmor(String id, String name, ItemStack item, Rarity rarity, CustomRecipe recipe) {
        super(id, name, item, rarity, recipe);
    }

    public CustomArmor(String id, String name, List<String> lore, ItemStack item, Rarity rarity, CustomRecipe recipe) {
        super(id, name, lore, item, rarity, recipe);
    }
}
