package me.alvsch.alvschitems.core.attributes;

import me.alvsch.alvschitems.api.ability.Ability;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface AbilityHolder extends ItemAttribute {

	@NotNull
	List<Ability> getAbilities();

}
