package me.alvsch.alvschitems.api.item;

import me.alvsch.alvschitems.api.CustomRecipe;
import me.alvsch.alvschitems.api.Rarity;
import me.alvsch.alvschitems.core.attributes.Equippable;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class CustomArmorPiece extends BaseItem implements Equippable {

    public CustomArmorPiece(ItemStack item) {
        super(item);
    }
    public CustomArmorPiece(String id, String name, ItemStack item, Rarity rarity, CustomRecipe recipe) {
        this(id, name, new ArrayList<>(), item, rarity, recipe);
    }
    public CustomArmorPiece(String id, String name, List<String> lore, ItemStack item, Rarity rarity, CustomRecipe recipe) {
        super(id, name, lore, item, rarity, recipe);
    }

}
