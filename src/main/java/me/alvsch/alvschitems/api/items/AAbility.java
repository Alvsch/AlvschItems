package me.alvsch.alvschitems.api.items;

import de.tr7zw.nbtapi.NBTItem;
import me.alvsch.alvschitems.AlvschItems;
import me.alvsch.alvschitems.api.events.AbilityUseEvent;
import me.alvsch.alvschitems.utils.Utils;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AAbility {

	private final String id;
	private final String name;
	private final List<String> description;
	private final double cooldown;
	private final AAbilityType abilityType;

	private String prefix1 = "";
	private String prefix2 = "";
	private boolean showCooldown = true;

	public AAbility(String id, String name, List<String> description, double cooldown, AAbilityType abilityType) {
		this.id = id;

		this.name = name;
		this.description = description;

		if(cooldown <= 0) showCooldown = false;
		this.cooldown = Math.max(0.05, cooldown);;
		this.abilityType = abilityType;

		AlvschItems.getInstance().getRegistry().getAbilityIds().put(id, this);
	}

	public List<String> toLore() {
		List<String> lore = new ArrayList<>();

		if(!prefix1.isBlank()) {
			lore.add(prefix1);
		}
		lore.add("&6" + prefix2 + "Ability: " + name + " &e&l" + abilityType.toString() + "&r");
		lore.addAll(description);
		if(showCooldown) {
			lore.add("&8Cooldown: &a" + cooldown + "s");
		}

		return Utils.color(lore);
	}

	public abstract void onAbilityUse(AbilityUseEvent event);


	public AAbilityType getType() {
		return abilityType;
	}
	public double getCooldown() {
		return cooldown;
	}
	public String getId() {
		return id;
	}

	public AAbility setPrefix1(String prefix1) {
		this.prefix1 = prefix1;
		return this;
	}
	public AAbility setPrefix2(String prefix2) {
		this.prefix2 = prefix2;
		return this;
	}
	public AAbility setShowCooldown(boolean showCooldown) {
		this.showCooldown = showCooldown;
		return this;
	}

	public static AAbility getById(String id) {
		if(id == null) return null;
		return AlvschItems.getInstance().getRegistry().getAbilityIds().get(id);
	}
	@SuppressWarnings("ConstantConditions")
	public static List<AAbility> getByItem(ItemStack item) {
		List<AAbility> abilities = new ArrayList<>();
		for(String id : new NBTItem(item).getStringList("id")) {
			abilities.add(getById(id));
		}
		return abilities;
	}


}
