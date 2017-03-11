package kitpvp.commands;

import java.util.Arrays;

import org.bukkit.Bukkit;
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

import kitpvp.Language;
import kitpvp.Main;
import kitpvp.SpawnItems;
import kitpvp.Util.ChatUtils;
import kitpvp.Util.KitAPI;
import kitpvp.commands.PrefixCommand.NameColor;

public class LangCommand implements CommandExecutor, Listener{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(label.equalsIgnoreCase("lang") || label.equalsIgnoreCase("kieli")){
			
			if(!(sender instanceof Player)){
				sender.sendMessage("§cOnly for players!");
				return true;
			}
			
			Player p = (Player) sender;
			openLangGUI(p);
			p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 0.2f);
		}
		
		return true;
	}

	public static void openLangGUI(Player p){
		
		KitAPI api = Main.getAPI();
		
		if (Main.getAPI().getLanguage(p.getName()) == Language.FINNISH) {
			
			Inventory inv = Bukkit.createInventory(null, 27, "Valitse kieli");
			
			//FINNISH
			
			api.createWoolItem(inv, 10, DyeColor.RED, "§c§lENGLISH", Arrays.asList("§7Change your language to English."));
			api.createWoolItem(inv, 16, DyeColor.BLUE, "§9§lSUOMI", Arrays.asList("§7Vaihda kielesi suomeksi."));
			
			api.createItem(inv, 13, Material.STAINED_GLASS_PANE, 1, "§fValitse kielesi!", 
					Arrays.asList("§7Täällä voit muokata kieltäsi.", "§7Meillä on tällä hetkellä kaksi kieltä.", 
							"§7Suomi ja englanti. Lisää kielivaihtoehtoja", "§7tulossa myöhemmin..."));
			
			p.openInventory(inv);
			
		} 
		else if (Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH) {
			
			Inventory inv = Bukkit.createInventory(null, 27, "Select language");
			
			//ENGLISH
			
			api.createWoolItem(inv, 10, DyeColor.RED, "§c§lENGLISH", Arrays.asList("§7Change your language to English."));
			api.createWoolItem(inv, 16, DyeColor.BLUE, "§9§lSUOMI", Arrays.asList("§7Vaihda kielesi suomeksi."));
			
			api.createItem(inv, 13, Material.STAINED_GLASS_PANE, 1, "§lSelect your language!", 
					Arrays.asList("§7Here you can change your language.", "§7Currently we have two languages.", "§7English and Finnish. More languages", 
							"§7are coming soon..."));
			
			p.openInventory(inv);
		}
		else if (Main.getAPI().getLanguage(p.getName()) == Language.DEFAULT) {
			
			Inventory inv = Bukkit.createInventory(null, 27, "Select language");
			
			//ENGLISH
			
			api.createWoolItem(inv, 10, DyeColor.RED, "§c§lENGLISH", Arrays.asList("§7Change your language to English."));
			api.createWoolItem(inv, 16, DyeColor.BLUE, "§9§lSUOMI", Arrays.asList("§7Vaihda kielesi suomeksi."));
			
			api.createItem(inv, 13, Material.STAINED_GLASS_PANE, 1, "§lSelect your language!", 
					Arrays.asList("§7Please select your language.", "§7Currently we have two languages.", "§7English and Finnish. More languages", 
							"§7are coming soon...", "", "§7§oYou can change your language later with", "§7§othe command §a/lang§7§o!"));
			
			p.openInventory(inv);
		}
		
	}
	@EventHandler
	public void onInvClick(InventoryClickEvent e){
		
		Player p = (Player) e.getWhoClicked();
		
		ItemStack item = e.getCurrentItem();
		KitAPI api = Main.getAPI();
		
		if(e.getInventory().getName().equals("Select language") || e.getInventory().getName().equals("Valitse kieli") && item != null && item.getType() != Material.AIR){
			
			e.setCancelled(true);
			
			if(item.getType() == Material.WOOL){
				if(item.getData().getData() == DyeColor.RED.getData()){
					e.setCancelled(true);
					p.closeInventory();
					api.setLanguage(p.getName(), Language.ENGLISH);
					ChatUtils.sendMessageWithPrefix(p, "§7Your language is now §c§lENGLISH§7!");
					SpawnItems.giveItems(p, p.getInventory());
				}
				else if(item.getData().getData() == DyeColor.BLUE.getData()){
					e.setCancelled(true);
					p.closeInventory();
					api.setLanguage(p.getName(), Language.FINNISH);
					ChatUtils.sendMessageWithPrefix(p, "§7Sinun kielesi on nyt §9§lSUOMI§7!");
					SpawnItems.giveItems(p, p.getInventory());
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
			else if (item == null) return;
			else if (item.getType() == null) return;
			else if(item.getType() ==  Material.AIR) return;
			
			else{ return; }
		}
		
	}

	
}
