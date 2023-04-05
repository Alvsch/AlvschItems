package me.alvsch.alvschitems.api.item;

import me.alvsch.alvschitems.api.CustomRecipe;
import me.alvsch.alvschitems.api.Rarity;
import me.alvsch.alvschitems.api.ability.Ability;
import me.alvsch.alvschitems.core.attributes.AbilityHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CustomWeapon extends BaseItem implements AbilityHolder {

    private final List<Ability> abilityList = new ArrayList<>();

    public CustomWeapon(ItemStack item) {
        super(item);
    }
    public CustomWeapon(String id, String name, ItemStack item, Rarity rarity, CustomRecipe recipe) {
        this(id, name, new ArrayList<>(), item, rarity, recipe);
    }
    public CustomWeapon(String id, String name, List<String> lore, ItemStack item, Rarity rarity, CustomRecipe recipe) {
        super(id, name, lore, item, rarity, recipe);
    }

    @Override
    public void register() {
        super.register();
        for(Ability a : getAbilities()) {
            extraLore.addAll(a.toLore());
            extraLore.add("");
        }
    }

    public void addAbility(Ability ability) {
        this.abilityList.add(ability);
    }

    @Override
    public @NotNull List<Ability> getAbilities() {
        return abilityList;
    }
}
