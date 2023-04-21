package me.alvsch.alvschitems.api.item;

import me.alvsch.alvschitems.api.CustomRecipe;
import me.alvsch.alvschitems.api.Rarity;
import me.alvsch.alvschitems.core.attributes.Consumable;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class ConsumableItem extends BaseItem implements Consumable {

    public ConsumableItem(ItemStack item) {
        super(item);
    }
    public ConsumableItem(String id, String name, ItemStack item, Rarity rarity, CustomRecipe recipe) {
        super(id, name, item, rarity, recipe);
    }
    public ConsumableItem(String id, String name, List<String> lore, ItemStack item, Rarity rarity, CustomRecipe recipe) {
        super(id, name, lore, item, rarity, recipe);
    }

    @Override
    public void register() {
        super.register();
        this.extraLore.add("&eConsumable: " + getConsumableDescription());
        this.extraLore.add("");
    }

}
