package me.alvsch.alvschitems.implementation.listeners;

import de.tr7zw.nbtapi.NBTEntity;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Collection;

public class DamageListener implements Listener {

    @SuppressWarnings("ConstantConditions")
    @EventHandler
    public void onDummyHit(EntityDamageByEntityEvent event) {
        if(!(event.getDamager() instanceof Player player)) {
            return;
        }
        LivingEntity livingEntity = (LivingEntity) event.getEntity();

        boolean isDummy = new NBTEntity(livingEntity).getByte("dummy") != null;

        if(!isDummy || livingEntity.isDead()) {
            return;
        }
        event.setCancelled(true);

        double armor = 0;
        try {
            Collection<AttributeModifier> attributeModifiers = livingEntity.getEquipment().getHelmet().getItemMeta().getAttributeModifiers(Attribute.GENERIC_ARMOR);
            for (AttributeModifier modifier : attributeModifiers) {
                armor += modifier.getAmount();
            }
        } catch (NullPointerException e) {
            player.sendMessage("NullPointerException");
            return;
        }

        player.sendMessage(String.valueOf(
                calculateDamage(event.getDamage(), armor)
        ));
    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {

    }

    private double calculateDamage(double attack, double armor) {
        double damage;
        if (attack >= armor) {
            damage = attack * 2 - armor;
        } else {
            damage = attack * attack / armor;
        }
        return damage;
    }

}
