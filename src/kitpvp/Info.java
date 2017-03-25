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
			
			api.createItem(inv, 10, Material.BOOK_AND_QUILL, 1, "�a�lKomennot", Arrays.asList("�7Klikkaa n�hd�ksesi komennot!"));
			api.createItem(inv, 12, Material.BOOK, 1, "�c�lYll�pito", Arrays.asList("�7Klikkaa n�hd�ksesi yll�pidosta", "�7infoa!"));
			api.createItem(inv, 14, Material.PAPER, 1, "�b�lS��nn�t", Arrays.asList("�7Klikkaa n�hd�ksesi s��nn�t!", "�7�oMuistathan lukea ne ;)"));
			api.createItem(inv, 16, Material.NETHER_STAR, 1, "�9�lDiscord", Arrays.asList("�7Klikkaa p��st�ksesi meid�n discordiin!"));
			
			p.openInventory(inv);
		} else if (Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH) {

			Inventory inv = Bukkit.createInventory(null, 27, "Info");

			api.createItem(inv, 10, Material.BOOK_AND_QUILL, 1, "�a�lCommands", Arrays.asList("�7Click to see the commands!"));
			api.createItem(inv, 12, Material.BOOK, 1, "�c�lStaff", Arrays.asList("�7Click to see info about the staff!"));
			api.createItem(inv, 14, Material.PAPER, 1, "�b�lRules", Arrays.asList("�7Click to see the rules!", "�7�oRemember to read ;)"));
			api.createItem(inv, 16, Material.NETHER_STAR, 1, "�9�lDiscord", Arrays.asList("�7Click to get to our discord!"));
			
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
			ChatUtils.sendMessage(p, "�7�m--------�a�l Komennot �7�m--------");
			p.sendMessage("�a/stats �7N�� itsesi tai muiden tilastot!");
			p.sendMessage("�a/profiili �7N�� itsesi tai muiden profiilin!");
			p.sendMessage("�a/asetukset �7Avaa asetusvalikko.");
			p.sendMessage("�a/? �7N�� t�rke�� infoa serverist�!");
			p.sendMessage("�a/help �7L�het� yll�pidolle kysymys tai pyyd� apua. �7�o�l� k�yt� turhaan!");
			p.sendMessage("�a/discord �7Liity meid�n Discordiin!");
			ChatUtils.sendMessage(p, "�7�m--------------------------");
		}
		else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
			ChatUtils.sendMessage(p, "�7�m--------�a�l Commands �7�m--------");
			p.sendMessage("�a/stats �7See yours or others stats!");
			p.sendMessage("�a/profile �7See yours or others profiles!");
			p.sendMessage("�a/settings �7Open the settings menu.");
			p.sendMessage("�a/? �7See important info about the server!");
			p.sendMessage("�a/help �7Send a message or ask for help from online staff! �7�oOnly use if necessary!");
			p.sendMessage("�a/discord �7Join our discord!");
			ChatUtils.sendMessage(p, "�7�m--------------------------");
		}
		
	}
	
}
