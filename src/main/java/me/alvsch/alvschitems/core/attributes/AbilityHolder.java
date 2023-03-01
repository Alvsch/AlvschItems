package me.alvsch.alvschitems.core.attributes;

import me.alvsch.alvschitems.api.ability.Ability;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public interface AbilityHolder {

	@NotNull
	default List<Ability> getAbilities() {
		return new ArrayList<>();
	}

}
