package me.alvsch.alvschitems.utils;

import org.bukkit.attribute.Attribute;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class UtilsTest {

    @Test
    void attributeToName() {

        assertEquals("generic.attackDamage", Utils.attributeToName(Attribute.GENERIC_ATTACK_DAMAGE));
    }
}