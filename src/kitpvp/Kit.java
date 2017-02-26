package kitpvp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class Kit {
	
	private static String name;
	private ItemStack[] items;
	private ItemStack[] armor;
	private PotionEffect[] effect;
	private AbilityType ability;
	
	public Kit(String name, ItemStack[] items, ItemStack[] armor, AbilityType ability) {
		Kit.name = name.toLowerCase();
		this.items = items;
		this.armor = armor;
		this.ability = ability;
	}
	
	public Kit(String name, ItemStack[] items, ItemStack[] armor, PotionEffect[] effect, AbilityType ability) {
		Kit.name = name.toLowerCase();
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

	public AbilityType getAbility() {
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
	
	public static Kit getKit(String kitName){
		
		Kit kit = null;
		String name = kitName.toLowerCase();
		
		try{
			
			FileConfiguration file = Main.getKitFile();
			
			kit = new Kit(file.getString(name), (ItemStack[]) file.get(name + ".items"), (ItemStack[]) file.get(name + ".armor"),
					(PotionEffect[]) file.get(name + ".potionEffects"), AbilityType.valueOf(file.getString(name + ".ability")));
			
		} catch (NullPointerException ex){
			System.err.println("Could not get kit " + name + "from the file, because it doesn't exist. Please use the correct names.");
		}
		
		return kit;
	}
	
	public void loadToFile(){
		
		FileConfiguration file = Main.getKitFile();
		
		file.createSection(name.toLowerCase());
		file.getConfigurationSection(name.toLowerCase()).set("items", getItems());
		file.getConfigurationSection(name.toLowerCase()).set("armor", getArmor());
		file.getConfigurationSection(name.toLowerCase()).set("ability", getAbility().toString());
		file.getConfigurationSection(name.toLowerCase()).set("potionEffects", getEffects().toString());
		file.getConfigurationSection(name.toLowerCase()).set("name", getName());
		
		Main.saveKitFile();
	}
	
	public static void removeFromFile(){
		
		FileConfiguration file = Main.getKitFile();
		
		try{
			
			file.set(name.toLowerCase(), " ");
			Main.saveKitFile();
			
		} catch (NullPointerException ex) {
			System.err.println("Could not remove kit " + name + "from the file, because it doesn't exist. Please use the correct names.");
		}
		
	}
	
}
