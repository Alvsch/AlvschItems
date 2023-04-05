package me.alvsch.alvschitems.api.ability;

public enum AbilityType {

    RIGHT_CLICK, SNEAK_RIGHT_CLICK,
    LEFT_CLICK, SNEAK_LEFT_CLICK;

    public String toString() {
        return this.name().replaceAll("_", " ");
    }

}
