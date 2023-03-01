package me.alvsch.alvschitems.api.item.weapon;

import me.alvsch.alvschitems.api.CustomRecipe;
import me.alvsch.alvschitems.api.Rarity;
import me.alvsch.alvschitems.api.ability.Ability;
import me.alvsch.alvschitems.api.item.BaseItem;
import me.alvsch.alvschitems.api.item.ItemStats;
import me.alvsch.alvschitems.core.attributes.AbilityHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CustomWeapon extends BaseItem implements AbilityHolder {

    private final ItemStats itemStats;
    private final List<Ability> abilityList = new ArrayList<>();

    public CustomWeapon(ItemStack item, ItemStats itemStats) {
        super(item);
        this.itemStats = itemStats;
    }
    public CustomWeapon(String id, String name, ItemStack item, ItemStats itemStats, Rarity rarity, CustomRecipe recipe) {
        this(id, name, new ArrayList<>(), item, itemStats, rarity, recipe);
    }
    public CustomWeapon(String id, String name, List<String> lore, ItemStack item, ItemStats itemStats, Rarity rarity, CustomRecipe recipe) {
        super(id, name, lore, item, rarity, recipe);
        this.itemStats = itemStats;
    }

    public void addAbility(Ability ability) {
        this.abilityList.add(ability);
    }

    @Override
    public @NotNull List<Ability> getAbilities() {
        return abilityList;
    }
}
