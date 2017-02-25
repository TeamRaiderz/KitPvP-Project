package kitpvp;

import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

public class Ability {

	private String name;
	private Sound sound;
	private AbilityType abilityType;
	private ItemStack item;

	public Ability(String name, Sound sound, AbilityType abilityType, ItemStack item) {
		this.name = name;
		this.sound = sound;
		this.abilityType = abilityType;
		this.item = item;
	}
	
	public String getName() {
		return name;
	}

	public Sound getSound() {
		return sound;
	}

	public AbilityType getAbilityType() {
		return abilityType;
	}

	public ItemStack getItem() {
		return item;
	}
}
