package me.alvsch.alvschitems.core.commands;

import de.tr7zw.nbtapi.NBT;
import me.alvsch.alvschitems.api.builder.AttributeBuilder;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class Dummy implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(!(sender instanceof Player player)) {
            return true;
        }
        if(!(args.length >= 1)) {
            return false;
        }
        double armor = Double.parseDouble(args[0]);
        if(Double.isNaN(armor)) {
            return false;
        }

        Zombie zombie = player.getWorld().spawn(player.getLocation().setDirection(new Vector(90, 0, 0)), Zombie.class);

        zombie.setInvulnerable(true);
        zombie.setCustomNameVisible(true);
        zombie.setCustomName("Dummy");
        zombie.setAI(false);
        zombie.setSilent(true);
        if(zombie.getEquipment() == null) return false;

        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        AttributeBuilder builder = new AttributeBuilder()
                .setAttribute(Attribute.GENERIC_ARMOR)
                .setAmount(armor);

        ItemMeta meta = helmet.getItemMeta();
        if(meta != null) {
            meta.setUnbreakable(true);
            meta.addAttributeModifier(builder.getAttribute(), builder.buildModifier());
        }
        helmet.setItemMeta(meta);
        zombie.getEquipment().setHelmet(helmet);

        NBT.modify(zombie, nbt -> {
            nbt.mergeCompound(NBT.parseNBT("{Dummy:1b}"));
        });

        return true;
    }

}
