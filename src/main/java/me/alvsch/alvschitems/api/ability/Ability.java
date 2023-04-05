package me.alvsch.alvschitems.api.ability;

import me.alvsch.alvschitems.AlvschItems;
import me.alvsch.alvschitems.api.event.AbilityUseEvent;
import me.alvsch.alvschitems.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public abstract class Ability {

    private final String id;
    private final String name;
    private final List<String> description;
    private final double cooldown;
    private final AbilityType abilityType;

    private String prefix1 = "";
    private String prefix2 = "";
    private boolean showCooldown = true;

    private boolean overwrite = false;

    public Ability(String id, String name, List<String> description, double cooldown, AbilityType abilityType) {
        this.id = id;

        this.name = name;
        this.description = description;

        if(cooldown <= 0) showCooldown = false;
        this.cooldown = Math.max(0.05, cooldown);;
        this.abilityType = abilityType;

        AlvschItems.getRegistry().getAbilityIds().put(id, this);
    }

    public List<String> toLore() {
        List<String> lore = new ArrayList<>();

        if(!prefix1.isBlank()) {
            lore.add(prefix1);
        }
        lore.add("&6" + prefix2 + "Ability: " + name + " &e&l" + abilityType.toString() + "&r");
        lore.addAll(description);
        if(showCooldown) {
            lore.add("&8Cooldown: &a" + cooldown + "s");
        }

        return Utils.color(lore);
    }

    public abstract void onAbilityUse(AbilityUseEvent event);

    //region Getters Setters

    public final String getId() {
        return id;
    }

    public final String getName() {
        return name;
    }

    public final List<String> getDescription() {
        return description;
    }

    public final double getCooldown() {
        return cooldown;
    }

    public final AbilityType getType() {
        return abilityType;
    }

    public final void setPrefix1(String prefix1) {
        this.prefix1 = prefix1;
    }

    public final void setPrefix2(String prefix2) {
        this.prefix2 = prefix2;
    }

    public final boolean displayCooldown() {
        return showCooldown;
    }

    public final void setShowCooldown(boolean showCooldown) {
        this.showCooldown = showCooldown;
    }

    public final void setOverwrite(boolean bool) {
        this.overwrite = bool;
    }

    public final boolean isOverwrite() {
        return overwrite;
    }

    //endregion

}