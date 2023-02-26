package me.alvsch.alvschitems.api.items;

import me.alvsch.alvschitems.utils.Utils;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class AItemStats {

	private int damage = 0;
	private int strength = 0;
	private int crit_damage = 0;
	private int crit_chance = 0;
	private int attack_speed = 0;

	private int armor = 0;
	private int health = 0;
	private int intelligence = 0;
	private int ferocity = 0;


	public AItemStats() {

	}

	public AItemStats(String s) {
		String[] list = s.split(" ");
		damage = Integer.parseInt(list[0]);
		strength = Integer.parseInt(list[1]);
		crit_damage = Integer.parseInt(list[2]);
		crit_chance = Integer.parseInt(list[3]);
		attack_speed = Integer.parseInt(list[4]);
		armor = Integer.parseInt(list[5]);
		health = Integer.parseInt(list[6]);
		intelligence = Integer.parseInt(list[7]);
		ferocity = Integer.parseInt(list[8]);
	}

	@Override
	public String toString() {
		return damage + " " + strength + " " + crit_damage + " " + crit_chance + " " + attack_speed + " " + armor + " " + health + " " + intelligence + " " + ferocity;
	}

	public List<String> toLore() {
		List<String> lore = new ArrayList<>();

		if(damage != 0) {
			String i = damage > 0 ? "+" : "-";
			lore.add("&r&7Damage: &c" + i + damage);
		}
		if(strength != 0) {
			String i = strength > 0 ? "+" : "-";
			lore.add("&r&7Strength: &c" + i + strength);
		}
		if(crit_chance != 0) {
			String i = crit_chance > 0 ? "+" : "-";
			lore.add("&r&7Crit Chance: &c" + i + crit_chance + "%");
		}
		if(crit_damage != 0) {
			String i = crit_damage > 0 ? "+" : "-";
			lore.add("&r&7Crit Damage: &c" + i + crit_damage + "%");
		}
		if(attack_speed != 0) {
			String i = attack_speed > 0 ? "+" : "-";
			lore.add("&r&7Attack Speed: &c" + i + attack_speed + "%");
		}

		if(armor != 0 ||
				health != 0 ||
				intelligence != 0 ||
				ferocity != 0
		) lore.add("");

		if(armor != 0) {
			String i = armor > 0 ? "+" : "-";
			lore.add("&r&7Armor: &a" + i + armor);
		}
		if(health != 0) {
			String i = health > 0 ? "+" : "-";
			lore.add("&r&7Health: &a" + i + health);
		}
		if(intelligence != 0) {
			String i = intelligence > 0 ? "+" : "-";
			lore.add("&r&7Intelligence: &a" + i + intelligence);
		}
		if(ferocity != 0) {
			String i = ferocity > 0 ? "+" : "-";
			lore.add("&r&7Ferocity: &a" + i + ferocity);
		}

		return Utils.color(lore);
	}


	public AItemStats setDamage(int damage) {
		this.damage = damage;
		return this;
	}

	public AItemStats setStrength(int strength) {
		this.strength = strength;
		return this;
	}

	public AItemStats setCritChance(int crit_chance) {
		this.crit_chance = crit_chance;
		return this;
	}

	public AItemStats setCritDamage(int crit_damage) {
		this.crit_damage = crit_damage;
		return this;
	}
	public AItemStats setAttackSpeed(int attack_speed) {
		this.attack_speed = attack_speed;
		return this;
	}

	public AItemStats setArmor(int armor) {
		this.armor = armor;
		return this;
	}

	public AItemStats setHealth(int health) {
		this.health = health;
		return this;
	}

	public AItemStats setIntelligence(int intelligence) {
		this.intelligence = intelligence;
		return this;
	}

	public AItemStats setFerocity(int ferocity) {
		this.ferocity = ferocity;
		return this;
	}

	public int getDamage() {
		return damage;
	}

	public int getStrength() {
		return strength;
	}

	public int getCritChance() {
		return crit_chance;
	}

	public int getCritDamage() {
		return crit_damage;
	}

	public int getAttackSpeed() {
		return attack_speed;
	}

	public int getArmor() {
		return armor;
	}

	public int getHealth() {
		return health;
	}

	public int getIntelligence() {
		return intelligence;
	}

	public int getFerocity() {
		return ferocity;
	}
}
