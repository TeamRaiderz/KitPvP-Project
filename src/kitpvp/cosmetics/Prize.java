package kitpvp.cosmetics;

import org.bukkit.inventory.ItemStack;

public class Prize {

	private String name;
	private ItemStack displayItem;
	private int chance;
	
	public Prize(String name, ItemStack displayItem, int chance){
		this.name = name;
		this.displayItem = displayItem;
		this.chance = chance;
	}

	public String getName() {
		return name;
	}

	public ItemStack getDisplayItem() {
		return displayItem;
	}

	public int getChance() {
		return chance;
	}
	
	
	
}
