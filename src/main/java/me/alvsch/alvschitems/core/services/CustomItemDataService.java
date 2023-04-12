package me.alvsch.alvschitems.core.services;

import me.alvsch.alvschitems.api.item.CustomItemStack;
import org.apache.commons.lang.Validate;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * I "borrowed" the code
 *
 * @author TheBusyBiscuit
 */
public class CustomItemDataService implements Keyed {

    /**
     * This is the {@link NamespacedKey} used to store/read data.
     */
    private final NamespacedKey namespacedKey;

    /**
     * This creates a new {@link CustomItemDataService} for the given {@link Plugin} and the
     * provided data key.
     *
     * @param plugin
     *            The {@link Plugin} for this service to use
     * @param key
     *            The key under which to store data
     */
    public CustomItemDataService(@NotNull Plugin plugin, @NotNull String key) {
        // Null-Validation is performed in the NamespacedKey constructor
        namespacedKey = new NamespacedKey(plugin, key);
    }

    @Override
    public NamespacedKey getKey() {
        return namespacedKey;
    }

    /**
     * This method stores the given id on the provided {@link ItemStack} via
     * persistent data.
     *
     * @param item
     *            The {@link ItemStack} to store data on
     * @param id
     *            The id to store on the {@link ItemStack}
     */
    public void setItemData(@NotNull ItemStack item, @NotNull String id) {
        Validate.notNull(item, "The Item cannot be null!");
        Validate.notNull(id, "Cannot store null on an Item!");

        ItemMeta im = item.getItemMeta();
        setItemData(im, id);
        item.setItemMeta(im);
    }

    /**
     * This method stores the given id on the provided {@link ItemMeta} via
     * persistent data.
     *
     * @param meta
     *            The {@link ItemMeta} to store data on
     * @param id
     *            The id to store on the {@link ItemMeta}
     */
    public void setItemData(@NotNull ItemMeta meta, @NotNull String id) {
        Validate.notNull(meta, "The ItemMeta cannot be null!");
        Validate.notNull(id, "Cannot store null on an ItemMeta!");

        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(namespacedKey, PersistentDataType.STRING, id);
    }

    /**
     * This method returns an {@link Optional} holding the data stored on the given {@link ItemStack}.
     * The {@link Optional} will be empty if the given {@link ItemStack} is null, doesn't have any {@link ItemMeta}
     * or if the requested data simply does not exist on that {@link ItemStack}.
     *
     * @param item
     *            The {@link ItemStack} to check
     *
     * @return An {@link Optional} describing the result
     */
    public @NotNull Optional<String> getItemData(@NotNull ItemStack item) {
        if (item == null || item.getType() == Material.AIR || !item.hasItemMeta()) {
            return Optional.empty();
        }

        return getItemData(item.getItemMeta());
    }

    /**
     * This method returns an {@link Optional}, either empty or holding the data stored
     * on the given {@link ItemMeta}.
     *
     * @param meta
     *            The {@link ItemMeta} to check
     *
     * @return An {@link Optional} describing the result
     */
    public @NotNull Optional<String> getItemData(@NotNull ItemMeta meta) {
        Validate.notNull(meta, "Cannot read data from null!");

        PersistentDataContainer container = meta.getPersistentDataContainer();
        return Optional.ofNullable(container.get(namespacedKey, PersistentDataType.STRING));
    }

    /**
     * This method compares the custom data stored on two {@link ItemMeta} objects.
     * This method will only return {@literal true} if both {@link ItemMeta}s contain
     * custom data and if both of their data values are equal.
     *
     * @param meta1
     *            The first {@link ItemMeta}
     * @param meta2
     *            The second {@link ItemMeta}
     *
     * @return Whether both metas have data on them, and it's the same.
     */
    public boolean hasEqualItemData(@NotNull ItemMeta meta1, @NotNull ItemMeta meta2) {
        Validate.notNull(meta1, "Cannot read data from null (first arg)");
        Validate.notNull(meta2, "Cannot read data from null (second arg)");

        Optional<String> data1 = getItemData(meta1);

        // Check if the first data is present
        if (data1.isPresent()) {
            // Only retrieve the second data where necessary.
            Optional<String> data2 = getItemData(meta2);

            /*
             * Check if both are present and equal.
             * Optional#equals(...) compares their values, so no need
             * to call Optional#get() here.
             */
            return data2.isPresent() && data1.equals(data2);
        } else {
            // No value present, we can return immediately.
            return false;
        }
    }

    public boolean hasEqualItemData(@NotNull ItemStack item1, @NotNull ItemStack item2) {
        String id1 = null;
        String id2 = null;
        if(item1 instanceof CustomItemStack stack1) {
            id1 = stack1.getId();
        }

        // Check if item2 is a CustomItemStack
        if(item2 instanceof CustomItemStack stack2) {
            id2 = stack2.getId();
        }

        if(id1 != null) {
            if(id2 != null) {
                return id1.equals(id2);
            }
        }

        if(id2 != null) {
            Optional<String> data1 = getItemData(item1);
            return data1.isPresent() && data1.get().equals(id2);
        }

        return hasEqualItemData(item1.getItemMeta(), item2.getItemMeta());
    }


}
