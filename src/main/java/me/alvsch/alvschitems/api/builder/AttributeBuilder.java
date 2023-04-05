package me.alvsch.alvschitems.api.builder;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;

import java.util.UUID;

public class AttributeBuilder {

    private Attribute attribute = Attribute.GENERIC_ATTACK_DAMAGE;

    private double amount = 1;
    private AttributeModifier.Operation operation = AttributeModifier.Operation.ADD_NUMBER;
    private EquipmentSlot slot = EquipmentSlot.HAND;

    // name amount operation slot

    public AttributeBuilder() {

    }

    public AttributeBuilder(Attribute attribute, double amount, AttributeModifier.Operation operation, EquipmentSlot slot) {
        this.attribute = attribute;
        this.amount = amount;
        this.operation = operation;
        this.slot = slot;
    }

    public AttributeBuilder setAttribute(Attribute attribute) {
        this.attribute = attribute;
        return this;
    }

    public AttributeBuilder setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public AttributeBuilder setOperation(AttributeModifier.Operation operation) {
        this.operation = operation;
        return this;
    }

    public AttributeBuilder setSlot(EquipmentSlot slot) {
        this.slot = slot;
        return this;
    }

    public AttributeModifier buildModifier() {
        return new AttributeModifier(UUID.randomUUID(), attribute.getKey().getKey(), amount, operation, slot);
    }
    public Attribute getAttribute() {
        return attribute;
    }

}
