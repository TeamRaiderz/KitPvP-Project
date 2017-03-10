package kitpvp;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.Inventory;

import kitpvp.Util.ChatUtils;
import kitpvp.Util.KitAPI;

public class Info implements Listener, CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) return true;
		
		Player p = (Player) sender;
		
		openInfoMenu(p);
		
		return true;
	}
	
	//TODO Finish this
	public void openInfoMenu(Player p){
		
		KitAPI api = Main.getAPI();
		
		if (Main.getAPI().getLanguage(p.getName()) == Language.FINNISH) {

			Inventory inv = Bukkit.createInventory(null, 27, "Info");
			
			api.createItem(inv, 10, Material.BOOK_AND_QUILL, 1, "ßaßlKomennot", Arrays.asList("ß7Klikkaa n‰hd‰ksesi komennot!"));
			api.createItem(inv, 12, Material.BOOK, 1, "ßcßlYll‰pito", Arrays.asList("ß7Klikkaa n‰hd‰ksesi yll‰pidosta", "ß7infoa!"));
			api.createItem(inv, 14, Material.PAPER, 1, "ßbßlS‰‰nnˆt", Arrays.asList("ß7Klikkaa n‰hd‰ksesi s‰‰nnˆt!", "ß7ßoMuistathan lukea ne ;)"));
			api.createItem(inv, 16, Material.NETHER_STAR, 1, "ß9ßlDiscord", Arrays.asList("ß7Klikkaa p‰‰st‰ksesi meid‰n discordiin!"));
			
			p.openInventory(inv);
		} else if (Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH) {

			Inventory inv = Bukkit.createInventory(null, 27, "Info");

			api.createItem(inv, 10, Material.BOOK_AND_QUILL, 1, "ßaßlCommands", Arrays.asList("ß7Click to see the commands!"));
			api.createItem(inv, 12, Material.BOOK, 1, "ßcßlStaff", Arrays.asList("ß7Click to see info about the staff!"));
			api.createItem(inv, 14, Material.PAPER, 1, "ßbßlRules", Arrays.asList("ß7Click to see the rules!", "ß7ßoRemember to read ;)"));
			api.createItem(inv, 16, Material.NETHER_STAR, 1, "ß9ßlDiscord", Arrays.asList("ß7Click to get to our discord!"));
			
			p.openInventory(inv);
		}
		
	}

	@EventHandler
	public void onInvClick(InventoryClickEvent e){
		Player p = (Player) e.getWhoClicked();
		
		if(e.getInventory().getName().equals("Info")){
			
			e.setCancelled(true);
			
			if(e.getCurrentItem().getType() == Material.BOOK_AND_QUILL){
				p.closeInventory();
				sendCommandHelp(p);
			}
			else if(e.getCurrentItem().getType() == Material.BOOK){
				p.closeInventory();
				Bukkit.dispatchCommand(p, "staff");
			}
			else if(e.getCurrentItem().getType() == Material.PAPER){
				p.closeInventory();
				Bukkit.dispatchCommand(p, "rules");
			}
			else if(e.getCurrentItem().getType() == Material.NETHER_STAR){
				p.closeInventory();
				Bukkit.dispatchCommand(p, "discord");
			}
			
		}
		
	}
	
	public static void sendCommandHelp(Player p){
		
		if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
			ChatUtils.sendMessage(p, "ß7ßm--------ßaßl Komennot ß7ßm--------");
			p.sendMessage("ßa/stats ß7N‰‰ itsesi tai muiden tilastot!");
			p.sendMessage("ßa/profiili ß7N‰‰ itsesi tai muiden profiilin!");
			p.sendMessage("ßa/asetukset ß7Avaa asetusvalikko.");
			p.sendMessage("ßa/? ß7N‰‰ t‰rke‰‰ infoa serverist‰!");
			p.sendMessage("ßa/help ß7L‰het‰ yll‰pidolle kysymys tai pyyd‰ apua. ß7ßoƒl‰ k‰yt‰ turhaan!");
			p.sendMessage("ßa/discord ß7Liity meid‰n Discordiin!");
			ChatUtils.sendMessage(p, "ß7ßm--------------------------");
		}
		else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
			ChatUtils.sendMessage(p, "ß7ßm--------ßaßl Commands ß7ßm--------");
			p.sendMessage("ßa/stats ß7See yours or others stats!");
			p.sendMessage("ßa/profile ß7See yours or others profiles!");
			p.sendMessage("ßa/settings ß7Open the settings menu.");
			p.sendMessage("ßa/? ß7See important info about the server!");
			p.sendMessage("ßa/help ß7Send a message or ask for help from online staff! ß7ßoOnly use if necessary!");
			p.sendMessage("ßa/discord ß7Join our discord!");
			ChatUtils.sendMessage(p, "ß7ßm--------------------------");
		}
		
	}
	
}
