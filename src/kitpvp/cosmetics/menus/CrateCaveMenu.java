package kitpvp.cosmetics.menus;

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
import org.bukkit.inventory.Inventory;

import kitpvp.Main;
import kitpvp.Util.Language;
import kitpvp.cosmetics.CSGOCrate;

public class CrateCaveMenu implements Listener, CommandExecutor{

	public void openMenu(Player p){
		
		if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
			
			Inventory inv = Bukkit.createInventory(null, 27, "Laatikot");
			
			Main.getAPI().createItem(inv, 12, Material.ENDER_CHEST, 1, "§bKosmetiikka", Arrays.asList("§7Avaa chesti josta voit", "§7löytää mahtavia kosmeettisia", 
					"§7tavaroita!", "", "§7Hinta: §e1 token"));
			Main.getAPI().createItem(inv, 14, Material.CHEST, 1, "§bPakkaukset", Arrays.asList("§7Avaa chesti josta voit", "§7löytää mahtavia PvP-",
					"§7pakkauksia!", "", "§7Hinta: §e50"));
			
			p.openInventory(inv);
		}
		else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
			
			Inventory inv = Bukkit.createInventory(null, 27, "Crates");
			
			Main.getAPI().createItem(inv, 12, Material.ENDER_CHEST, 1, "§bCosmetics", Arrays.asList("§7Open a chest where you", "§7can find awesome cosmetics!", "", "§7Price: §e1 token"));
			Main.getAPI().createItem(inv, 14, Material.CHEST, 1, "§bKits", Arrays.asList("§7Open a chest where you", "§7can find awesome PvP-kits!"
					, "", "§7Price: §e50"));
			
			p.openInventory(inv);
		}
		
	}
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e){
		
		Player p = (Player) e.getWhoClicked();
		
		if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR){ return; }
		
		if(e.getInventory().getName().equals("Laatikot") || e.getInventory().getName().equals("Crates")){
			
			e.setCancelled(true);
			
			if(e.getCurrentItem().getType() == Material.ENDER_CHEST){
				CSGOCrate c = new CSGOCrate();
				c.openCSGO(p);
			}
			
		}
		
	}

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if(!(arg0 instanceof Player)) return true;
		openMenu((Player) arg0);
		return true;
	}
	
}
