package kitpvp;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import kitpvp.Util.ChatUtils;

public class Kit {
	
	private String name;
	private List<ItemStack> items;
	private List<ItemStack> armor;
	private PotionEffect[] effect;
	private AbilityType ability;
	
	public Kit(String name, List<ItemStack> items, List<ItemStack> armor, AbilityType ability) {
		this.name = name.toLowerCase();
		this.items = items;
		this.armor = armor;
		this.ability = ability;
	}
	
	public Kit(String name, List<ItemStack> items, List<ItemStack> armor, PotionEffect[] effect, AbilityType ability) {
		this.name = name.toLowerCase();
		this.items = items;
		this.armor = armor;
		this.effect = effect;
		this.ability = ability;
	}
	
	public String getName() {
		return ChatColor.translateAlternateColorCodes('&', name);
	}
	public List<ItemStack> getItems() {
		return items;
	}
	public List<ItemStack> getArmor() {
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
		
		if(player.getInventory().getContents() != null){
			player.getInventory().clear();
		}
		if(player.getInventory().getArmorContents() != null){
			player.getInventory().setArmorContents(null);
		}
		
		FileConfiguration file = Main.getKitFile();
		
		ItemStack[] content = ((List<ItemStack>) file.get(name + ".items")).toArray(new ItemStack[0]);
        ItemStack[]  armor = ((List<ItemStack>) file.get(name + ".armor")).toArray(new ItemStack[0]);
        
        for(ItemStack item : content){
        	player.getInventory().addItem(item);
        }
        
        for(ItemStack item : armor){
        	if(item.getType() == Material.LEATHER_HELMET || item.getType() == Material.GOLD_HELMET || item.getType() == Material.IRON_HELMET
        			|| item.getType() == Material.DIAMOND_HELMET || item.getType() == Material.CHAINMAIL_HELMET){
        		player.getInventory().setHelmet(item);
        	}
        	else if(item.getType() == Material.LEATHER_CHESTPLATE || item.getType() == Material.GOLD_CHESTPLATE || item.getType() == Material.IRON_CHESTPLATE
        			|| item.getType() == Material.DIAMOND_CHESTPLATE || item.getType() == Material.CHAINMAIL_CHESTPLATE){
        		player.getInventory().setChestplate(item);
        	}
        	else if(item.getType() == Material.LEATHER_LEGGINGS || item.getType() == Material.GOLD_LEGGINGS || item.getType() == Material.IRON_LEGGINGS
        			|| item.getType() == Material.DIAMOND_LEGGINGS || item.getType() == Material.CHAINMAIL_LEGGINGS){
        		player.getInventory().setLeggings(item);
        	}
        	else if(item.getType() == Material.LEATHER_BOOTS || item.getType() == Material.GOLD_BOOTS || item.getType() == Material.IRON_BOOTS
        			|| item.getType() == Material.DIAMOND_BOOTS || item.getType() == Material.CHAINMAIL_BOOTS){
        		player.getInventory().setBoots(item);
        	}
        }
		
        if(Main.getAPI().getLanguage(player.getName()) == Language.FINNISH){
        	ChatUtils.sendMessageWithPrefix(player, "�7Sinulle annettiin kit �c" + name + "�7!");
        }
        else if(Main.getAPI().getLanguage(player.getName()) == Language.ENGLISH){
        	ChatUtils.sendMessageWithPrefix(player, "�7You were given the kit �c" + name + "�7!");
        }
        
	}
	
	public void loadToFile(){
		
		FileConfiguration file = Main.getKitFile();
		
		file.createSection(name.toLowerCase());
		file.getConfigurationSection(name.toLowerCase()).set("items", getItems());
		file.getConfigurationSection(name.toLowerCase()).set("armor", getArmor());
		if(getAbility() == null)
			file.getConfigurationSection(name.toLowerCase()).set("ability", "");
		else
			file.getConfigurationSection(name.toLowerCase()).set("ability", getAbility());
		if(getEffects() == null)
			file.getConfigurationSection(name.toLowerCase()).set("potionEffects", "");
		else
			file.getConfigurationSection(name.toLowerCase()).set("potionEffects", getEffects().toString());
		file.getConfigurationSection(name.toLowerCase()).set("name", getName());
		
		Main.saveKitFile();
	}
	
	public void removeFromFile(){
		
		FileConfiguration file = Main.getKitFile();
		
		try{
			
			file.set(name.toLowerCase(), " ");
			Main.saveKitFile();
			
		} catch (NullPointerException ex) {
			System.err.println("Could not remove kit " + name + "from the file, because it doesn't exist. Please use the correct names.");
		}
		
	}
	
}
