package kitpvp.cosmetics;

import java.util.Arrays;

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
		ItemStack item = api.makeWoolItem(rarity.color, rarity.chatColor + "§l" + name.toUpperCase(), Arrays.asList(rarity.chatColor + rarity.toString()));
		return item;
	}
	
	public Rarity getRarity(){
		return rarity;
	}

	public int getChance() {
		return chance;
	}
	
	
	
}
