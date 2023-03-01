package me.alvsch.alvschitems.api.item;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemStats {

    private int damage = 0;
    private int strength = 0;
    private int armor = 0;
    private int health = 0;

    public ItemStats() {

    }

    public ItemStats(String s) {
        Pattern pattern = Pattern.compile("(?<=ItemStats\\{).*(?<!})");
        Matcher matcher = pattern.matcher(s);

        Map<String, Integer> map = new HashMap<>();

        if(matcher.find()) {
            s = matcher.group(0);

            String[] parts = s.split(",");
            for (String part : parts) {
                String[] data = part.split("=");

                String key = data[0].trim();
                int value = Integer.parseInt(data[1].trim());

                map.put(key, value);
            }
        }
        damage = map.getOrDefault("damage", 0);
        strength = map.getOrDefault("strength", 0);
        armor = map.getOrDefault("armor", 0);
        health = map.getOrDefault("health", 0);

    }

    //region Getters Setters
    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
    //endregion


    @Override
    public String toString() {
        return "ItemStats{" +
                "damage=" + damage +
                ", strength=" + strength +
                ", armor=" + armor +
                ", health=" + health +
                '}';
    }
}
