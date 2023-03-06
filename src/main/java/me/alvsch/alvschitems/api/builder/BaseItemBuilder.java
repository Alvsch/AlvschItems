package me.alvsch.alvschitems.api.builder;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import me.alvsch.alvschitems.api.CustomRecipe;
import me.alvsch.alvschitems.api.Rarity;
import me.alvsch.alvschitems.api.item.BaseItem;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BaseItemBuilder {

    private final String id;
    private String name = "null";
    private List<String> lore = new ArrayList<>();
    private Rarity rarity = Rarity.TEST;

    private CustomRecipe recipe;

    private Multimap<Attribute, AttributeModifier> modifiers = ArrayListMultimap.create();

    private ItemStack item;
    private boolean glint = false;

    public BaseItemBuilder(String id) {
        this.id = id;
    }

    //region Setters

    public BaseItemBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public BaseItemBuilder setLore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public BaseItemBuilder setRarity(Rarity rarity) {
        this.rarity = rarity;
        return this;
    }

    public BaseItemBuilder setRecipe(CustomRecipe recipe) {
        this.recipe = recipe;
        return this;
    }

    public BaseItemBuilder addModifier(Attribute attribute, AttributeModifier modifier) {
        this.modifiers.put(attribute, modifier);
        return this;
    }

    public BaseItemBuilder setItem(ItemStack item) {
        this.item = item;
        return this;
    }

    public BaseItemBuilder setGlint(boolean glint) {
        this.glint = glint;
        return this;
    }
    //endregion

    public BaseItem buildItem() {
        if(id == null) throw new NullPointerException("ID cannot be null");

        BaseItem item = new BaseItem(id, name, lore, this.item, rarity, recipe);
        item.addAllModifiers(modifiers);
        item.setGlint(glint);
        return item;
    }

}
