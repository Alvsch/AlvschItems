package me.alvsch.alvschitems.api.ability;

import lombok.Getter;
import lombok.Setter;
import me.alvsch.alvschitems.AlvschItems;
import me.alvsch.alvschitems.api.event.AbilityUseEvent;
import me.alvsch.alvschitems.utils.Utils;

import java.util.ArrayList;
import java.util.List;

@Setter
public abstract class Ability {

    @Getter
    private final String id;
    @Getter
    private final String name;
    @Getter
    private final List<String> description;
    @Getter
    private final double cooldown;
    @Getter
    private final AbilityType abilityType;


    private String prefix1 = "";
    private String prefix2 = "";
    @Getter
    private boolean showCooldown = true;

    @Getter
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

}