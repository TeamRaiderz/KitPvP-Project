package kitpvp.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import kitpvp.Language;
import kitpvp.Main;
import kitpvp.Util.ChatUtils;
import kitpvp.Util.KitAPI;

public class PrefixCommand implements Listener, CommandExecutor {
	
	public void openPrefixGUI(Player p){
		
		if (Main.getAPI().getLanguage(p.getName()) == Language.FINNISH) {
			
			Inventory inv = Bukkit.createInventory(null, 36, "Lempinimet");
			
			//FINNISH
			
			createWoolItem(inv, 10, DyeColor.RED, "§c§lPUNAINEN", Arrays.asList("§7Vaihda nimesi väri punaiseksi!", "", "§aKlikkaa vaihtaaksesi!"));
			createWoolItem(inv, 11, DyeColor.GREEN, "§a§lVIHREÄ", Arrays.asList("§7Vaihda nimesi väri vihreäksi!", "", "§aKlikkaa vaihtaaksesi!"));
			createWoolItem(inv, 12, DyeColor.LIGHT_BLUE, "§b§lVAALEANSININEN", Arrays.asList("§7Vaihda nimesi väri vaaleansiniseksi!", "", "§aKlikkaa vaihtaaksesi!"));
			createWoolItem(inv, 13, DyeColor.PURPLE, "§5§lVIOLETTI", Arrays.asList("§7Vaihda nimesi väri violetiksi!", "", "§aKlikkaa vaihtaaksesi!"));
			createWoolItem(inv, 14, DyeColor.BLUE, "§9§lSININEN", Arrays.asList("§7Vaihda nimesi väri siniseksi!", "", "§aKlikkaa vaihtaaksesi!"));
			createWoolItem(inv, 15, DyeColor.YELLOW, "§e§lKELTAINEN", Arrays.asList("§7Vaihda nimesi väri keltaiseksi!", "", "§aKlikkaa vaihtaaksesi!"));
			createWoolItem(inv, 16, DyeColor.ORANGE, "§6§lKULTAINEN", Arrays.asList("§7Vaihda nimesi väri kultaiseksi!", "", "§aKlikkaa vaihtaaksesi!"));
			createWoolItem(inv, 22, DyeColor.PINK, "§d§lPINKKI", Arrays.asList("§7Vaihda nimesi väri pinkiksi!", "", "§aKlikkaa vaihtaaksesi!"));
			
			ItemStack restore = new ItemStack(Material.BARRIER);
			ItemMeta restoreMeta = restore.getItemMeta();
			restoreMeta.setDisplayName("§c§lRESETOI NIMESI");
			restoreMeta.setLore(Arrays.asList("§7Klikkaa resetoidaksesi nimesi"));
			restore.setItemMeta(restoreMeta);
			
			inv.setItem(35, restore);
			
			p.openInventory(inv);
			
		} 
		else if (Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH) {
			
			Inventory inv = Bukkit.createInventory(null, 36, "Nicknames");
			
			//ENGLISH
			
			createWoolItem(inv, 10, DyeColor.RED, "§c§lRED", Arrays.asList("§7Change your name to red color!", "", "§aClick to change!"));
			createWoolItem(inv, 11, DyeColor.GREEN, "§a§lGREEN", Arrays.asList("§7Change your name to green color!", "", "§aClick to change!"));
			createWoolItem(inv, 12, DyeColor.LIGHT_BLUE, "§b§lAQUA", Arrays.asList("§7Change your name to light blue color!", "", "§aClick to change!"));
			createWoolItem(inv, 13, DyeColor.PURPLE, "§5§lPURPLE", Arrays.asList("§7Change your name to purple color!", "", "§aClick to change!"));
			createWoolItem(inv, 14, DyeColor.BLUE, "§9§lBLUE", Arrays.asList("§7Change your name to blue color!", "", "§aClick to change!"));
			createWoolItem(inv, 15, DyeColor.YELLOW, "§e§lYELLOW", Arrays.asList("§7Change your name to yellow color!", "", "§aClick to change!"));
			createWoolItem(inv, 16, DyeColor.ORANGE, "§6§lGOLD", Arrays.asList("§7Change your name to gold color!", "", "§aClick to change!"));
			createWoolItem(inv, 22, DyeColor.PINK, "§d§lPINK", Arrays.asList("§7Change your name to pink color!", "", "§aClick to change!"));
		
			ItemStack restore = new ItemStack(Material.BARRIER);
			ItemMeta restoreMeta = restore.getItemMeta();
			restoreMeta.setDisplayName("§c§lRESET YUOUR NAME");
			restoreMeta.setLore(Arrays.asList("§7Click to reset your name"));
			restore.setItemMeta(restoreMeta);
			
			inv.setItem(35, restore);
			
			p.openInventory(inv);
		}
	}
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e){
		
		Player p = (Player) e.getWhoClicked();
		
		ItemStack item = e.getCurrentItem();
		KitAPI api = Main.getAPI();
		
		if(e.getInventory().getName().equals("Nicknames") || e.getInventory().getName().equals("Lempinimet") && item != null && item.getType() != Material.AIR){
			
			e.setCancelled(true);
			
			if(item.getType() == Material.WOOL){
				if(item.getData().getData() == DyeColor.RED.getData()){
					e.setCancelled(true);
					p.closeInventory();
					api.setNick(p.getName(), NameColor.RED);
					if (Main.getAPI().getLanguage(p.getName()) == Language.FINNISH) {
						ChatUtils.sendMessageWithPrefix(p, "§7Sinun nimesi väri on nyt §c§lPUNAINEN.");
					} else if (Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH) {
						ChatUtils.sendMessageWithPrefix(p, "§7Your name's color has been changed to §c§lRED.");
					}
				}
				else if(item.getData().getData() == DyeColor.GREEN.getData()){
					e.setCancelled(true);
					p.closeInventory();
					api.setNick(p.getName(), NameColor.GREEN);
					if (Main.getAPI().getLanguage(p.getName()) == Language.FINNISH) {
						ChatUtils.sendMessageWithPrefix(p, "§7Sinun nimesi väri on nyt §a§lVIHREÄ.");
					} else if (Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH) {
						ChatUtils.sendMessageWithPrefix(p, "§7Your name's color has been changed to §a§lGREEN.");
					}
				}
				else if(item.getData().getData() == DyeColor.LIGHT_BLUE.getData()){
					e.setCancelled(true);
					p.closeInventory();
					api.setNick(p.getName(), NameColor.AQUA);
					if (Main.getAPI().getLanguage(p.getName()) == Language.FINNISH) {
						ChatUtils.sendMessageWithPrefix(p, "§7Sinun nimesi väri on nyt §b§lVAALEANSININEN.");
					} else if (Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH) {
						ChatUtils.sendMessageWithPrefix(p, "§7Your name's color has been changed to §b§lLIGHT BLUE.");
					}
				}
				else if(item.getData().getData() == DyeColor.BLUE.getData()){
					e.setCancelled(true);
					p.closeInventory();
					api.setNick(p.getName(), NameColor.BLUE);
					if (Main.getAPI().getLanguage(p.getName()) == Language.FINNISH) {
						ChatUtils.sendMessageWithPrefix(p, "§7Sinun nimesi väri on nyt §9§lSININEN.");
					} else if (Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH) {
						ChatUtils.sendMessageWithPrefix(p, "§7Your name's color has been changed to §9§lBLUE.");
					}
					
				}
				else if(item.getData().getData() == DyeColor.YELLOW.getData()){
					e.setCancelled(true);
					p.closeInventory();
					api.setNick(p.getName(), NameColor.YELLOW);
					if (Main.getAPI().getLanguage(p.getName()) == Language.FINNISH) {
						ChatUtils.sendMessageWithPrefix(p, "§7Sinun nimesi väri on nyt §e§lKELTAINEN.");
					} else if (Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH) {
						ChatUtils.sendMessageWithPrefix(p, "§7Your name's color has been changed to §e§lYELLOW.");
					}
				}
				else if(item.getData().getData() == DyeColor.ORANGE.getData()){
					e.setCancelled(true);
					p.closeInventory();
					api.setNick(p.getName(), NameColor.GOLD);
					if (Main.getAPI().getLanguage(p.getName()) == Language.FINNISH) {
						ChatUtils.sendMessageWithPrefix(p, "§7Sinun nimesi väri on nyt §6§lKULTAINEN.");
					} else if (Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH) {
						ChatUtils.sendMessageWithPrefix(p, "§7Your name's color has been changed to §6§lGOLD.");
					}
				}
				else if(item.getData().getData() == DyeColor.PURPLE.getData()){
					e.setCancelled(true);
					p.closeInventory();
					api.setNick(p.getName(), NameColor.PURPLE);
					if (Main.getAPI().getLanguage(p.getName()) == Language.FINNISH) {
						ChatUtils.sendMessageWithPrefix(p, "§7Sinun nimesi väri on nyt §5§lVIOLETTI.");
					} else if (Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH) {
						ChatUtils.sendMessageWithPrefix(p, "§7Your name's color has been changed to §5§lPURPLE.");
					}
				}
				else if(item.getData().getData() == DyeColor.PINK.getData()){
					e.setCancelled(true);
					p.closeInventory();
					api.setNick(p.getName(), NameColor.PINK);
					if (Main.getAPI().getLanguage(p.getName()) == Language.FINNISH) {
						ChatUtils.sendMessageWithPrefix(p, "§7Sinun nimesi väri on nyt §d§lPINKKI.");
					} else if (Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH) {
						ChatUtils.sendMessageWithPrefix(p, "§7Your name's color has been changed to §d§lPINK.");
					}
					
				}
				else if (item.getType() == null) return;
				else if (item == null) return;
				else if(item.getType() ==  Material.AIR) return;
			}
			else if (item.getType() == Material.BARRIER){
				e.setCancelled(true);
				p.closeInventory();
				Main.getAPI().setNick(p.getName(), NameColor.DEFAULT);
				if (Main.getAPI().getLanguage(p.getName()) == Language.FINNISH) {
					ChatUtils.sendMessageWithPrefix(p, "§7Sinun nimesi väri on nyt resetoitu.");
				} else if (Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH) {
					ChatUtils.sendMessageWithPrefix(p, "§7Your name's color has been reset.");
				}
			}
			else if (item.getType() == null) return;
			else if (item == null) return;
			else if(item.getType() ==  Material.AIR) return;
			
			else{ return; }
		}
		
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(label.equalsIgnoreCase("prefix") || label.equalsIgnoreCase("nick")){
			
			if(!(sender instanceof Player)){ return true; }
			
			Player p = (Player) sender;
			
			if(!p.hasPermission("kitpvp.nick")){
				if (Main.getAPI().getLanguage(p.getName()) == Language.FINNISH) {
					ChatUtils.sendMessageWithPrefix(p, "§7Tähän toimintoon tarvitset rankin §3§lBOOSTER§7!");
				} else if (Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH) {
					ChatUtils.sendMessageWithPrefix(p, "§7You must have the rank §3§lBOOSTER §7to do this!");
				}
				return true;
			}
			
			if(args.length == 0){
				openPrefixGUI(p);
				p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 0.2f);
			}
			else{
				if (Main.getAPI().getLanguage(p.getName()) == Language.FINNISH) {
					ChatUtils.sendMessageWithPrefix(p, "§7Käyttötapa: /prefix or /nick!");
				} else if (Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH) {
					ChatUtils.sendMessageWithPrefix(p, "§7Usage: /prefix or /nick!");
				}
			}
			
			
		}
		
		return true;
	}
	
	public void createWoolItem(Inventory inv, int pos, DyeColor color, String displayName, List<String> lore){
		ItemStack wool = new ItemStack(Material.WOOL, 1, color.getData());
		ItemMeta meta = wool.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
		meta.setLore(lore);
		wool.setItemMeta(meta);
		inv.setItem(pos, wool);
	}
	
	public enum NameColor{
		
		RED, GREEN, AQUA, CYAN, BLUE, YELLOW, WHITE, GOLD, PURPLE, PINK, DEFAULT;
		
	}
	
}
