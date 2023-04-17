package me.alvsch.alvschitems.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RomanConverterTest {

    @Test
    void intToRoman() {
        assertEquals("VII", RomanConverter.intToRoman(7));
        assertEquals("X", RomanConverter.intToRoman(10));
        assertEquals("XX", RomanConverter.intToRoman(20));
        assertEquals("XL", RomanConverter.intToRoman(40));
        assertEquals("L", RomanConverter.intToRoman(50));
        assertEquals("LXXIX", RomanConverter.intToRoman(79));
        assertEquals("CCXXV", RomanConverter.intToRoman(225));
        assertEquals("DCCCXLV", RomanConverter.intToRoman(845));
        assertEquals("MMXXII", RomanConverter.intToRoman(2022));
    }
}