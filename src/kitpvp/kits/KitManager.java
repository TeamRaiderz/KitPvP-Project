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

	public final String SPIDER_NAME = "§bSpider", FISHER_NAME = "§bFisher", AIRMAN_NAME = "§bAirman", ARCHER_NAME = "§bArcher", GHOST_NAME = "§bGhost",
			TANK_NAME = "§bTank", PYRO_NAME = "§bPyro", BOMB_ARCHER_NAME = "§bBomb Archer", THUNDER_GOD_NAME = "§bThunder God", KNIGHT_NAME = "§bKnight", BOMBER_NAME = "§bBomber",
			HULK_NAME = "§bHulk", PROTECTOR_NAME = "§bProtector";
	
	public boolean hasPermissionForKit(Player p, Kit kit){
		if(p.hasPermission("server.kit." + String.valueOf(kit).toLowerCase())){
			return true;
		}
		return false;
	}
	
	public String getPermissionForKit(Kit kit){
		return "server.kit." + String.valueOf(kit).toLowerCase();
	}
	
	public String permissionMessage(Player p, Kit kit){
		if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
			if (!(p.hasPermission(getPermissionForKit(kit)))) {
				return "§c§lEt omista tätä pakkausta!";
			} else {
				return "§a§lKlikkaa saadaksesi!";
			}
		}
		else if (Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
			if (!(p.hasPermission(getPermissionForKit(kit)))) {
				return "§c§lYou do not have this kit!";
			} else {
				return "§a§lClick to receive!";
			}
		}
		return "";
	}
	
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
		
		if(item.getType() == Material.BOW){
			meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
		}
		
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
		
		if(item.getType() == Material.BOW){
			meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
		}
		
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
		SPIDER, FISHER, AIRMAN, ARCHER, GHOST, TANK, PYRO, BOMB_ARCHER, THUNDER_GOD, KNIGHT, BOMBER, HULK, PROTECTOR;
	}
	
	public void giveKit(Player p, Kit kit){
		
		Inventory inv = p.getInventory();
		
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		
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
			giveLeatherArmor(p, Material.LEATHER_HELMET, Color.FUCHSIA, "§7Archer", Arrays.asList(""), Enchantment.PROTECTION_ENVIRONMENTAL, 1);
			giveLeatherArmor(p, Material.LEATHER_CHESTPLATE, Color.AQUA, "§7Archer", Arrays.asList(""), Enchantment.PROTECTION_ENVIRONMENTAL, 1);
			giveLeatherArmor(p, Material.LEATHER_LEGGINGS, Color.AQUA, "§7Archer", Arrays.asList(""), Enchantment.PROTECTION_ENVIRONMENTAL, 1);
			giveItem(p, Material.WOOD_SWORD, "§7Archer", Arrays.asList(""), Enchantment.DAMAGE_ALL, 1);
			giveItem(p, Material.BOW, "§7Archer", Arrays.asList(""));
			inv.addItem(gapple);
			inv.addItem(new ItemStack(Material.ARROW));
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
			Main.getAPI().removePotionEffects(p);
			p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 1));
			
			giveLeatherArmor(p, Material.LEATHER_HELMET, Color.RED, "§7Pyro", Arrays.asList(""), Enchantment.PROTECTION_ENVIRONMENTAL, 1);
			giveLeatherArmor(p, Material.LEATHER_CHESTPLATE, Color.RED, "§7Pyro", Arrays.asList(""), Enchantment.PROTECTION_ENVIRONMENTAL, 1);
			giveLeatherArmor(p, Material.LEATHER_LEGGINGS, Color.RED, "§7Pyro", Arrays.asList(""), Enchantment.PROTECTION_ENVIRONMENTAL, 1);
			giveLeatherArmor(p, Material.LEATHER_BOOTS, Color.RED, "§7Pyro", Arrays.asList(""), Enchantment.PROTECTION_ENVIRONMENTAL, 1);
			
			giveItem(p, Material.BLAZE_ROD, "§7Pyro", Arrays.asList(""), Enchantment.FIRE_ASPECT, 1);
			giveItem(p, Material.BOW, "§7Pyro", Arrays.asList(""), Enchantment.ARROW_FIRE, 1);
			
			inv.addItem(gapple);
			inv.addItem(new ItemStack(Material.ARROW));
			break;
		case BOMB_ARCHER:
			
			giveLeatherArmor(p, Material.LEATHER_HELMET, Color.LIME, "§7Bomb Archer", Arrays.asList(""), Enchantment.PROTECTION_ENVIRONMENTAL, 2);
			giveLeatherArmor(p, Material.LEATHER_CHESTPLATE, Color.LIME, "§7Bomb Archer", Arrays.asList(""), Enchantment.PROTECTION_ENVIRONMENTAL, 2);
			
			giveItem(p, Material.STICK, "§7Bomb Archer", Arrays.asList(""), Enchantment.DAMAGE_ALL, 3);
			giveItem(p, Material.BOW, "§7Bomb Archer", Arrays.asList(""));
			
			inv.addItem(gapple);
			inv.addItem(new ItemStack(Material.ARROW));
			break;
		case THUNDER_GOD:
			
			giveLeatherArmor(p, Material.LEATHER_HELMET, Color.BLACK, "§7Thunder God", Arrays.asList(""), Enchantment.PROTECTION_ENVIRONMENTAL, 1);
			giveLeatherArmor(p, Material.LEATHER_CHESTPLATE, Color.BLACK, "§7Thunder God", Arrays.asList(""), Enchantment.PROTECTION_ENVIRONMENTAL, 1);
			giveLeatherArmor(p, Material.LEATHER_LEGGINGS, Color.BLACK, "§7Thunder God", Arrays.asList(""), Enchantment.PROTECTION_ENVIRONMENTAL, 1);
			giveLeatherArmor(p, Material.LEATHER_BOOTS, Color.BLUE, "§7Thunder God", Arrays.asList(""), Enchantment.PROTECTION_ENVIRONMENTAL, 1);
			
			giveItem(p, Material.DIAMOND_AXE, "§7Thunder God", Arrays.asList(""), Enchantment.DURABILITY, 1);
			
			inv.addItem(gapple);
			break;
		case KNIGHT:
			
			giveLeatherArmor(p, Material.LEATHER_HELMET, Color.GRAY, "§7Knight", Arrays.asList(""), Enchantment.PROTECTION_ENVIRONMENTAL, 1);
			giveArmor(p, Material.CHAINMAIL_CHESTPLATE, "§7Knight", Arrays.asList(""));
			giveLeatherArmor(p, Material.LEATHER_LEGGINGS, Color.GRAY, "§7Knight", Arrays.asList(""), Enchantment.PROTECTION_ENVIRONMENTAL, 1);
			giveLeatherArmor(p, Material.LEATHER_BOOTS, Color.GRAY, "§7Knight", Arrays.asList(""), Enchantment.PROTECTION_ENVIRONMENTAL, 1);
			
			giveItem(p, Material.STONE_SWORD, "§7Knight", Arrays.asList(""));
			
			ItemStack knightGapple = new ItemStack(Material.GOLDEN_APPLE);
			knightGapple.setAmount(7);
			
			inv.addItem(knightGapple);
			break;
		case BOMBER:
			
			if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
				giveItem(p, Material.TNT, "§7Bomber", Arrays.asList("", "§aLeft-click §7-> Ammu räjähtävä TNT", "§7joka vahingoittaa pelaajia", "§7lähellä räjähdystä."));
			}
			else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
				giveItem(p, Material.TNT, "§7Bomber", Arrays.asList("", "§aLeft-click §7-> Shoot an exploding tnt", "§7what will harm players", "§7near the explosion."));
			}
			
			inv.addItem(gapple);
			break;
		case HULK:
			
			if (Main.getAPI().getLanguage(p.getName()) == Language.FINNISH) {
				giveItem(p, Material.GOLD_SWORD, "§7Hulk", Arrays.asList("", "§aRight-click §7-> Lennä taivaalle ja kun", "§7tiput vahingoita ja heitä", "§7lähellä olevat pelaajat",
						"§7taivaan tuuliin!"));
			} else if (Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH) {
				giveItem(p, Material.GOLD_SWORD, "§7Hulk", Arrays.asList("", "§aRight-click §7-> Fly up into the sky and", "§7when you land harm and throw", "§7near players up into the sky!"));
			}
			
			inv.addItem(gapple);
			break;
		case PROTECTOR:
			
			if (Main.getAPI().getLanguage(p.getName()) == Language.FINNISH) {
				giveItem(p, Material.SULPHUR, "§7Protector", Arrays.asList("", "§aRight-click §7-> Heitä lähellä olevat", "§7pelaajat pois ja anna heille", "§7näkymättömyys. Samalla anna",
						"§7itsellesi parempi vastustuskyky!"));
			} else if (Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH) {
				giveItem(p, Material.SULPHUR, "§7Protector", Arrays.asList("", "§aRight-click §7-> Throw near players away", "§7and give them blindness. Also you give yourself", 
						"§7better resistance!"));
			}
			
			inv.addItem(gapple);
			break;
		default:
			break;
		}
	}
	
	public void openKitMenu(Player p){
		if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
			Inventory inv = Bukkit.createInventory(null, 54, "Pakkaukset");
			
			createKitMenuItem(inv, KNIGHT_NAME, 10, Material.STONE_SWORD, Arrays.asList("", "§7§oKuvaus tähän...", "", permissionMessage(p, Kit.KNIGHT)));
			createKitMenuItem(inv, SPIDER_NAME, 11, Material.SPIDER_EYE, Arrays.asList("", "§7§oKuvaus tähän...", "", permissionMessage(p, Kit.SPIDER)));
			createKitMenuItem(inv, FISHER_NAME, 12, Material.FISHING_ROD, Arrays.asList("", "§7§oKuvaus tähän...", "", permissionMessage(p, Kit.FISHER)));
			createKitMenuItem(inv, ARCHER_NAME, 13, Material.BOW, Arrays.asList("", "§7§oKuvaus tähän...", "", permissionMessage(p, Kit.ARCHER)));

			createKitMenuItem(inv, GHOST_NAME, 14, Material.GHAST_TEAR, Arrays.asList("", "§7§oKuvaus tähän...", "", permissionMessage(p, Kit.GHOST)));
			createKitMenuItem(inv, TANK_NAME, 15, Material.IRON_CHESTPLATE, Arrays.asList("", "§7§oKuvaus tähän...", "", permissionMessage(p, Kit.TANK)));
			createKitMenuItem(inv, PYRO_NAME, 16, Material.BLAZE_ROD, Arrays.asList("", "§7§oKuvaus tähän...", "", permissionMessage(p, Kit.PYRO)));
			createKitMenuItem(inv, BOMB_ARCHER_NAME, 19, Material.BLAZE_POWDER, Arrays.asList("", "§7§oKuvaus tähän...", "", permissionMessage(p, Kit.BOMB_ARCHER)));
			createKitMenuItem(inv, THUNDER_GOD_NAME, 20, Material.IRON_AXE, Arrays.asList("", "§7§oKuvaus tähän...", "", permissionMessage(p, Kit.THUNDER_GOD)));
			createKitMenuItem(inv, BOMBER_NAME, 21, Material.TNT, Arrays.asList("", "§7§oKuvaus tähän...", "", permissionMessage(p, Kit.BOMBER)));
			createKitMenuItem(inv, AIRMAN_NAME, 22, Material.FEATHER, Arrays.asList("", "§7§oKuvaus tähän...", "", permissionMessage(p, Kit.AIRMAN)));
			createKitMenuItem(inv, PROTECTOR_NAME, 23, Material.DIAMOND_HELMET, Arrays.asList("", "§7§oKuvaus tähän...", "", permissionMessage(p, Kit.PROTECTOR)));
			createKitMenuItem(inv, HULK_NAME, 24, Material.PUMPKIN, Arrays.asList("", "§7§oKuvaus tähän...", "", permissionMessage(p, Kit.HULK)));
			
			p.openInventory(inv);
		}
		else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
			Inventory inv = Bukkit.createInventory(null, 54, "Kits");
			
			createKitMenuItem(inv, KNIGHT_NAME, 10, Material.STONE_SWORD, Arrays.asList("", "§7§oDescription here...", "", permissionMessage(p, Kit.KNIGHT)));
			createKitMenuItem(inv, SPIDER_NAME, 11, Material.SPIDER_EYE, Arrays.asList("", "§7§oDescription here...", "", permissionMessage(p, Kit.SPIDER)));
			createKitMenuItem(inv, FISHER_NAME, 12, Material.FISHING_ROD, Arrays.asList("", "§7§oDescription here...", "", permissionMessage(p, Kit.FISHER)));
			createKitMenuItem(inv, ARCHER_NAME, 13, Material.BOW, Arrays.asList("", "§7§oDescription here...", "", permissionMessage(p, Kit.ARCHER)));

			createKitMenuItem(inv, GHOST_NAME, 14, Material.GHAST_TEAR, Arrays.asList("", "§7§oDescription here...", "", permissionMessage(p, Kit.GHOST)));
			createKitMenuItem(inv, TANK_NAME, 15, Material.IRON_CHESTPLATE, Arrays.asList("", "§7§oDescription here...", "", permissionMessage(p, Kit.TANK)));
			createKitMenuItem(inv, PYRO_NAME, 16, Material.BLAZE_ROD, Arrays.asList("", "§7§oDescription here...", "", permissionMessage(p, Kit.PYRO)));
			createKitMenuItem(inv, BOMB_ARCHER_NAME, 19, Material.BLAZE_POWDER, Arrays.asList("", "§7§oDescription here...", "", permissionMessage(p, Kit.BOMB_ARCHER)));
			createKitMenuItem(inv, THUNDER_GOD_NAME, 20, Material.IRON_AXE, Arrays.asList("", "§7§oDescription here...", "", permissionMessage(p, Kit.THUNDER_GOD)));
			createKitMenuItem(inv, BOMBER_NAME, 21, Material.TNT, Arrays.asList("", "§7§oDescription here...", "", permissionMessage(p, Kit.BOMBER)));
			createKitMenuItem(inv, AIRMAN_NAME, 22, Material.FEATHER, Arrays.asList("", "§7§oDescription here...", "", permissionMessage(p, Kit.AIRMAN)));
			createKitMenuItem(inv, PROTECTOR_NAME, 23, Material.DIAMOND_HELMET, Arrays.asList("", "§7§oDescription here...", "", permissionMessage(p, Kit.PROTECTOR)));
			createKitMenuItem(inv, HULK_NAME, 24, Material.PUMPKIN, Arrays.asList("", "§7§oDescription here...", "", permissionMessage(p, Kit.HULK)));
			
			p.openInventory(inv);
		}
	}
	
}
