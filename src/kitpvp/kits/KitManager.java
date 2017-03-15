package kitpvp.kits;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import kitpvp.Language;
import kitpvp.Main;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class KitManager {

	public void createKitMenuItem(Inventory inv, String name, int pos, Material material, List<String> description){
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		meta.setLore(description);
		item.setItemMeta(meta);
		inv.setItem(pos, item);
	}
	
	public void giveItem(Player p, Material material, String name, List<String> lore){
		Inventory inv = p.getInventory();
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		meta.setLore(lore);
		meta.spigot().setUnbreakable(true);
		item.setItemMeta(meta);
		inv.addItem(item);
	}
	
	public void giveItem(Player p, Material material, String name, List<String> lore, Enchantment ench, int enchLvl){
		PlayerInventory inv = p.getInventory();
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		meta.setLore(lore);
		meta.spigot().setUnbreakable(true);
		meta.addEnchant(ench, enchLvl, true);
		item.setItemMeta(meta);
		inv.addItem(item);
	}
	
	public void giveLeatherArmor(Player p, Material material, Color c, String name, List<String> lore){
		PlayerInventory inv = p.getInventory();
		ItemStack item = new ItemStack(material);
		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		meta.setColor(c);
		meta.setLore(lore);
		meta.spigot().setUnbreakable(true);
		meta.addEnchant(Enchantment.DURABILITY, 1, true);
		item.setItemMeta(meta);
		
		if(item.getType() == Material.LEATHER_HELMET){
			inv.setHelmet(item);
		}
		if(item.getType() == Material.LEATHER_CHESTPLATE){
			inv.setChestplate(item);
		}
		if(item.getType() == Material.LEATHER_LEGGINGS){
			inv.setLeggings(item);
		}
		if(item.getType() == Material.LEATHER_BOOTS){
			inv.setBoots(item);
		}
		
	}
	
	public void giveLeatherArmor(Player p, Material material, Color c, String name, List<String> lore, Enchantment ench, int enchLvl){
		PlayerInventory inv = p.getInventory();
		ItemStack item = new ItemStack(material);
		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		meta.setColor(c);
		meta.setLore(lore);
		meta.spigot().setUnbreakable(true);
		meta.addEnchant(ench, enchLvl, true);
		item.setItemMeta(meta);
		
		if(item.getType() == Material.LEATHER_HELMET){
			inv.setHelmet(item);
		}
		if(item.getType() == Material.LEATHER_CHESTPLATE){
			inv.setChestplate(item);
		}
		if(item.getType() == Material.LEATHER_LEGGINGS){
			inv.setLeggings(item);
		}
		if(item.getType() == Material.LEATHER_BOOTS){
			inv.setBoots(item);
		}
		
	}
	
	public void giveArmor(Player p, Material material, String name, List<String> lore){
		PlayerInventory inv = p.getInventory();
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		meta.setLore(lore);
		meta.spigot().setUnbreakable(true);
		meta.addEnchant(Enchantment.DURABILITY, 1, true);
		item.setItemMeta(meta);
		
		if(item.getType() == Material.DIAMOND_HELMET || item.getType() == Material.IRON_HELMET || item.getType() == Material.GOLD_HELMET || item.getType() == Material.CHAINMAIL_HELMET){
			inv.setHelmet(item);
		}
		if(item.getType() == Material.DIAMOND_CHESTPLATE || item.getType() == Material.IRON_CHESTPLATE || item.getType() == Material.GOLD_CHESTPLATE || item.getType() == Material.CHAINMAIL_CHESTPLATE){
			inv.setChestplate(item);
		}
		if(item.getType() == Material.DIAMOND_LEGGINGS || item.getType() == Material.IRON_LEGGINGS || item.getType() == Material.GOLD_LEGGINGS || item.getType() == Material.CHAINMAIL_LEGGINGS){
			inv.setLeggings(item);
		}
		if(item.getType() == Material.DIAMOND_BOOTS || item.getType() == Material.IRON_BOOTS || item.getType() == Material.GOLD_BOOTS || item.getType() == Material.CHAINMAIL_BOOTS){
			inv.setBoots(item);
		}
	}
	
	public void giveArmor(Player p, Material material, String name, List<String> lore, Enchantment ench, int enchLvl){
		PlayerInventory inv = p.getInventory();
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		meta.setLore(lore);
		meta.spigot().setUnbreakable(true);
		meta.addEnchant(ench, enchLvl, true);
		item.setItemMeta(meta);
		
		if(item.getType() == Material.DIAMOND_HELMET || item.getType() == Material.IRON_HELMET || item.getType() == Material.GOLD_HELMET || item.getType() == Material.CHAINMAIL_HELMET){
			inv.setHelmet(item);
		}
		if(item.getType() == Material.DIAMOND_CHESTPLATE || item.getType() == Material.IRON_CHESTPLATE || item.getType() == Material.GOLD_CHESTPLATE || item.getType() == Material.CHAINMAIL_CHESTPLATE){
			inv.setChestplate(item);
		}
		if(item.getType() == Material.DIAMOND_LEGGINGS || item.getType() == Material.IRON_LEGGINGS || item.getType() == Material.GOLD_LEGGINGS || item.getType() == Material.CHAINMAIL_LEGGINGS){
			inv.setLeggings(item);
		}
		if(item.getType() == Material.DIAMOND_BOOTS || item.getType() == Material.IRON_BOOTS || item.getType() == Material.GOLD_BOOTS || item.getType() == Material.CHAINMAIL_BOOTS){
			inv.setBoots(item);
		}
	}
	
	public enum Kit{
		SPIDER, FISHER, AIRMAN, ARCHER, GHOST, TANK, PYRO, BOMB_ARCHER, THUNDER_GOD, KNIGHT, BOMBER;
	}
	
	public void giveKit(Player p, Kit kit){
		
		Inventory inv = p.getInventory();
		
		p.getInventory().clear();
		
		ItemStack gapple = new ItemStack(Material.GOLDEN_APPLE);
		gapple.setAmount(5);
		
		switch(kit){
		
		case SPIDER:
			giveLeatherArmor(p, Material.LEATHER_HELMET, Color.RED, "§7Spider", Arrays.asList(""), Enchantment.PROTECTION_ENVIRONMENTAL, 1);
			giveLeatherArmor(p, Material.LEATHER_BOOTS, Color.RED, "§7Spider", Arrays.asList(""), Enchantment.PROTECTION_ENVIRONMENTAL, 1);
			giveItem(p, Material.SPIDER_EYE, "§7Spider", Arrays.asList(""), Enchantment.DAMAGE_ALL, 2);
			inv.addItem(gapple);
			break;
		case FISHER:
			giveLeatherArmor(p, Material.LEATHER_HELMET, Color.BLUE, "§7Fisher", Arrays.asList(""));
			giveLeatherArmor(p, Material.LEATHER_CHESTPLATE, Color.GRAY, "§7Fisher", Arrays.asList(""));
			giveLeatherArmor(p, Material.LEATHER_LEGGINGS, Color.GRAY, "§7Fisher", Arrays.asList(""));
			giveLeatherArmor(p, Material.LEATHER_BOOTS, Color.BLUE, "§7Fisher", Arrays.asList(""));
			giveItem(p, Material.STONE_SWORD, "§7Fisher", Arrays.asList(""), Enchantment.DAMAGE_ALL, 1);
			giveItem(p, Material.FISHING_ROD, "§7Fisher", Arrays.asList(""), Enchantment.DURABILITY, 1);
			inv.addItem(gapple);
			break;
		case AIRMAN:
			giveLeatherArmor(p, Material.LEATHER_HELMET, Color.ORANGE, "§7Airman", Arrays.asList(""), Enchantment.PROTECTION_ENVIRONMENTAL, 1);
			giveLeatherArmor(p, Material.LEATHER_CHESTPLATE, Color.WHITE, "§7Airman", Arrays.asList(""));
			giveLeatherArmor(p, Material.LEATHER_LEGGINGS, Color.WHITE, "§7Airman", Arrays.asList(""));
			giveLeatherArmor(p, Material.LEATHER_BOOTS, Color.ORANGE, "§7Airman", Arrays.asList(""), Enchantment.PROTECTION_ENVIRONMENTAL, 1);
			giveItem(p, Material.WOOD_SWORD, "§7Airman", Arrays.asList(""), Enchantment.DAMAGE_ALL, 1);
			giveItem(p, Material.FEATHER, "§7Airman", Arrays.asList(""), Enchantment.DURABILITY, 1);
			inv.addItem(gapple);
			break;
		case ARCHER:
			
			inv.addItem(gapple);
			break;
		case GHOST:
			Main.getAPI().removePotionEffects(p);
			giveItem(p, Material.WOOD_SWORD, "§7Ghost", Arrays.asList(""), Enchantment.DAMAGE_ALL, 1);
			inv.addItem(gapple);
			p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
			break;
		case TANK:
			Main.getAPI().removePotionEffects(p);
			p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 3));
			
			giveArmor(p, Material.DIAMOND_HELMET, "§7Tank", Arrays.asList(""));
			giveArmor(p, Material.IRON_CHESTPLATE, "§7Tank", Arrays.asList(""));
			giveArmor(p, Material.IRON_LEGGINGS, "§7Tank", Arrays.asList(""));
			giveArmor(p, Material.DIAMOND_BOOTS, "§7Tank", Arrays.asList(""));
			giveItem(p, Material.WOOD_SWORD, "§7Tank", Arrays.asList(""), Enchantment.DAMAGE_ALL, 1);
			
			inv.addItem(gapple);
			break;
		case PYRO:
			
			inv.addItem(gapple);
			break;
		case BOMB_ARCHER:
			
			inv.addItem(gapple);
			break;
		case THUNDER_GOD:
			
			inv.addItem(gapple);
			break;
		case KNIGHT:
			
			inv.addItem(gapple);
			break;
		case BOMBER:
			
			inv.addItem(gapple);
			break;
			
		}
	}
	
	public void openKitMenu(Player p){
		if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
			Inventory inv = Bukkit.createInventory(null, 54, "Pakkaukset");
			
			p.openInventory(inv);
		}
		else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
			Inventory inv = Bukkit.createInventory(null, 54, "Kits");
			
			p.openInventory(inv);
		}
	}
	
}
