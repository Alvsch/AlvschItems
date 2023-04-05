package me.alvsch.alvschitems.api.item;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import me.alvsch.alvschitems.AlvschItems;
import me.alvsch.alvschitems.api.CustomRecipe;
import me.alvsch.alvschitems.api.Rarity;
import me.alvsch.alvschitems.core.attributes.NotPlaceable;
import me.alvsch.alvschitems.utils.Utils;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
public class BaseItem implements NotPlaceable {

	private final String id;
	private final String name;
	private final List<String> lore;
	private final Rarity rarity;

	private CustomRecipe recipe;

	protected final List<String> extraLore = new ArrayList<>();
	private final Multimap<Attribute, AttributeModifier> modifiers = ArrayListMultimap.create();

	private final ItemStack orig;
	private ItemStack item;

	private boolean glint;

	/**
	 * Constructs a new BaseItem object with the given ItemStack.
	 *
	 * @param item the ItemStack to be used to create the BaseItem.
	 */
	public BaseItem(ItemStack item) {
		this.item = item;
		this.orig = item;

		BaseItem baseItem = BaseItem.getByItem(item);

		this.id = baseItem.id;
		this.name = baseItem.getName();
		this.lore = baseItem.getLore();
		this.rarity = baseItem.getRarity();
		this.recipe = baseItem.getRecipe();
		this.glint = baseItem.hasGlint();

		this.extraLore.addAll(baseItem.getExtraLore());
		this.modifiers.putAll(baseItem.getModifiers());
	}

	/**
	 * Constructs a new BaseItem object with the given parameters.
	 *
	 * @param id the unique identifier of the BaseItem object.
	 * @param name the display name of the BaseItem object.
	 * @param item the ItemStack used to create the BaseItem object.
	 * @param rarity the rarity of the BaseItem object.
	 * @param recipe the recipe for the BaseItem object.
	 */
	public BaseItem(String id, String name, ItemStack item, Rarity rarity, CustomRecipe recipe) {
		this(id, name, new ArrayList<>(), item, rarity, recipe);
	}

	/**
	 * Constructs a new BaseItem object with the given parameters.
	 *
	 * @param id the unique identifier of the BaseItem object.
	 * @param name the display name of the BaseItem object.
	 * @param lore the lore of the BaseItem object.
	 * @param item the ItemStack used to create the BaseItem object.
	 * @param rarity the rarity of the BaseItem object.
	 * @param recipe the recipe for the BaseItem object.
	 */
	public BaseItem(String id, String name, List<String> lore, ItemStack item, Rarity rarity, CustomRecipe recipe) {
		this.id = id;
		this.name = name;
		this.lore = lore;
		this.orig = item;

		this.rarity = rarity;
		this.recipe = recipe;
	}

	/**
	 * Registers the item to the item registry.
	 * Registers the item recipe and settings the result of the recipe.
	 */
	public void register() {
		AlvschItems.getRegistry().getItemIds().put(id, this);
		if(recipe != null) {
			this.recipe = new CustomRecipe(this.createItem(), recipe.getItems(), recipe.isShaped());
			this.recipe.register();
		}
	}

	/**
	 * Creates an ItemStack object based on the BaseItem object's attributes.
	 * The orig attribute is cloned to create a new ItemStack object.
	 *
	 * @return The new modified ItemStack object is returned.
	 */
	public ItemStack createItem() {
		this.item = this.orig.clone();

		ItemMeta meta = this.item.getItemMeta();
		assert meta != null;

		meta.setDisplayName(rarity.getColor() + this.name);

		List<String> lore = new ArrayList<>(this.lore);
		lore.add("");
		lore.addAll(this.extraLore);

		lore.add(rarity.getColor().toString() + "&l" + rarity.name().replaceAll("_", " "));

		if(glint) meta.addEnchant(Enchantment.DURABILITY, 1, true);

		meta.setLore(Utils.color(lore));
		meta.setUnbreakable(true);
		meta.setAttributeModifiers(modifiers);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);

		AlvschItems.getCustomItemDataService().setItemData(meta, id);
		item.setItemMeta(meta);
		return item;
	}

	//region Getters Setters

	public final String getId() {
		return id;
	}

	public final String getName() {
		return name;
	}

	public final List<String> getLore() {
		return lore;
	}

	public final Rarity getRarity() {
		return rarity;
	}

	public final CustomRecipe getRecipe() {
		return recipe;
	}

	public final ItemStack getOriginal() {
		return orig;
	}

	public final ItemStack getItem() {
		return item;
	}

	public final boolean hasGlint() {
		return glint;
	}

	public final void setItem(ItemStack item) {
		this.item = item;
	}
	public final void setRecipe(CustomRecipe recipe) {
		this.recipe = recipe;
	}
	public final void setGlint(boolean glint) {
		this.glint = glint;
	}
	public final List<String> getExtraLore() {
		return extraLore;
	}

	public final Multimap<Attribute, AttributeModifier> getModifiers() {
		return modifiers;
	}

	public final void addModifier(Attribute attribute, AttributeModifier attributeModifier) {
		modifiers.put(attribute, attributeModifier);
	}
	public final void addAllModifiers(Multimap<Attribute, AttributeModifier> multimap) {
		modifiers.putAll(multimap);
	}

	//endregion

	/**
	 * Get the BaseItem from the registry by id
	 *
	 * @param id The item id
	 * @return The BaseItem if found or null
	 */
	public static BaseItem getById(String id) {
		return AlvschItems.getRegistry().getItemIds().get(id);
	}

	public static BaseItem getByItem(ItemStack item) {
		if (item == null || item.getType() == Material.AIR) {
			return null;
		}

		if (item instanceof CustomItemStack stack) {
			return getById(stack.getId());
		}

		Optional<String> itemID = AlvschItems.getCustomItemDataService().getItemData(item);

		if (itemID.isPresent()) {
			return getById(itemID.get());
		}

		return null;
	}

	/**
	 * Check if a ItemStack contains a AlvschItems ID
	 *
	 * @param item The ItemStack to check
	 * @return If the item has an id tag
	 */
	public static boolean isCustomItem(ItemStack item) {
		return AlvschItems.getCustomItemDataService().getItemData(item).isPresent();
	}

}
