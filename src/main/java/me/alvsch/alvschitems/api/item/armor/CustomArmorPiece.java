package me.alvsch.alvschitems.api.item.armor;

import me.alvsch.alvschitems.api.CustomRecipe;
import me.alvsch.alvschitems.api.Rarity;
import me.alvsch.alvschitems.api.event.ArmorEquipEvent;
import me.alvsch.alvschitems.api.event.ArmorUnequipEvent;
import me.alvsch.alvschitems.api.item.BaseItem;
import me.alvsch.alvschitems.api.item.ItemStats;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class CustomArmorPiece extends BaseItem {

    private final ItemStats itemStats;

    public CustomArmorPiece(ItemStack item, ItemStats itemStats) {
        super(item);
        this.itemStats = itemStats;
    }
    public CustomArmorPiece(String id, String name, ItemStack item, ItemStats itemStats, Rarity rarity, CustomRecipe recipe) {
        this(id, name, new ArrayList<>(), item, itemStats, rarity, recipe);
    }
    public CustomArmorPiece(String id, String name, List<String> lore, ItemStack item, ItemStats itemStats, Rarity rarity, CustomRecipe recipe) {
        super(id, name, lore, item, rarity, recipe);
        this.itemStats = itemStats;
    }

    public abstract void onEquip(ArmorEquipEvent event);
    public abstract void onUnequip(ArmorUnequipEvent event);

}
