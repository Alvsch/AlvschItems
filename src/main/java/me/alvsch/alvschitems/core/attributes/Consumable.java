package me.alvsch.alvschitems.core.attributes;

import me.alvsch.alvschitems.api.event.ItemConsumeEvent;
import org.jetbrains.annotations.NotNull;

public interface Consumable extends ItemAttribute {

    void onConsume(ItemConsumeEvent event);
    default boolean consumeOnUse() {
        return true;
    }

    @NotNull
    String getConsumableDescription();

}
