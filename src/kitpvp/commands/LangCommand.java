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
import org.bukkit.inventory.Inventory;

import kitpvp.Language;
import kitpvp.Main;
import kitpvp.Util.KitAPI;

public class LangCommand implements CommandExecutor{

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
			
			Inventory inv = Bukkit.createInventory(null, 27, "Vaihda kieli");
			
			//FINNISH
			
			api.createWoolItem(inv, 10, DyeColor.RED, "§c§lENGLISH", Arrays.asList("§7Change your language to English."));
			api.createWoolItem(inv, 16, DyeColor.BLUE, "§9§lSUOMI", Arrays.asList("§7Vaihda kielesi suomeksi."));
			
			api.createItem(inv, 13, Material.STAINED_GLASS_PANE, 1, "§fVaihda kielesi!", 
					Arrays.asList("§7Täällä voit muokata kieltäsi.", "§7Oletuksena se on englantina.", "§7Meillä on tällä hetkellä kaksi kieltä.", 
							"§7Suomi ja englanti. Lisää kielivaihtoehtoja", "§7tulossa myöhemmin..."));
			
			p.openInventory(inv);
			
		} 
		else if (Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH) {
			
			Inventory inv = Bukkit.createInventory(null, 36, "Change language");
			
			//ENGLISH
			
			api.createWoolItem(inv, 10, DyeColor.RED, "§c§lENGLISH", Arrays.asList("§7Change your language to English."));
			api.createWoolItem(inv, 16, DyeColor.BLUE, "§9§lSUOMI", Arrays.asList("§7Vaihda kielesi suomeksi."));
			
			api.createItem(inv, 13, Material.STAINED_GLASS_PANE, 1, "§fChange your language!", 
					Arrays.asList("§7Here you can change your language.", "§7By default it's English.", "§7Currently we have two languages.", "§7English and Finnish. More languages", 
							"§7are coming soon..."));
			
			p.openInventory(inv);
		}
		
	}
	
	
	
}
