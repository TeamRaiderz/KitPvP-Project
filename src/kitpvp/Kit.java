package kitpvp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class Kit {
	
	private String name;
	private ItemStack[] items;
	private ItemStack[] armor;
	private PotionEffect[] effect;
	private Ability ability;
	
	public Kit(String name, ItemStack[] items, ItemStack[] armor, Ability ability) {
		this.name = name;
		this.items = items;
		this.armor = armor;
		this.ability = ability;
	}
	
	public Kit(String name, ItemStack[] items, ItemStack[] armor, PotionEffect[] effect, Ability ability) {
		this.name = name;
		this.items = items;
		this.armor = armor;
		this.effect = effect;
		this.ability = ability;
	}
	
	public String getName() {
		return ChatColor.translateAlternateColorCodes('&', name);
	}
	public ItemStack[] getItems() {
		return items;
	}
	public ItemStack[] getArmor() {
		return armor;
	}
	public PotionEffect[] getEffects(){
		return effect;
	}

	public Ability getAbility() {
		return ability;
	}

	public void giveKit(Player player){
		
		if(player.getActivePotionEffects() != null){
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "effect " + player.getName() + " clear");
		}
		
		if(this.effect != null){
			for(PotionEffect effect : this.effect){
				player.addPotionEffect(effect);
			}
		}
		
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		player.getInventory().setContents(null);
		
		player.getInventory().setArmorContents(armor);
		player.getInventory().setContents(items);
		
		for(ItemStack item : items){
			item.getItemMeta().setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		}
		for(ItemStack item : armor){
			item.getItemMeta().setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		}
		
	}
	
}
