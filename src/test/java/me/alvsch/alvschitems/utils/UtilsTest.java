package me.alvsch.alvschitems.utils;

import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.junit.jupiter.api.Test;
class UtilsTest {

    @Test
    void attributeToName() {
        System.out.println(Utils.attributeToName(Attribute.GENERIC_ATTACK_DAMAGE));
    }

    @Test
    void testFormatEnchant() {
        System.out.println(Utils.formatEnchant(Enchantment.DAMAGE_ALL, 10));
    }

}