package kitpvp.cosmetics;

import org.bukkit.inventory.ItemStack;

import kitpvp.Util.KitAPI;

public class Prize {

	private String name;
	private int chance;
	private Rarity rarity;
	
	public Prize(String name, int chance, Rarity rarity){
		this.name = name;
		this.chance = chance;
		this.rarity = rarity;
	}

	public String getName() {
		return name;
	}

	public ItemStack getDisplayItem() {
		KitAPI api = new KitAPI();
		ItemStack item = api.makeWoolItem(rarity.color, rarity.getName());
		return item;
	}
	
	public Rarity getRarity(){
		return rarity;
	}

	public int getChance() {
		return chance;
	}
	
	
	
}
