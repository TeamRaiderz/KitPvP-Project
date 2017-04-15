package kitpvp.other;

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
import kitpvp.Util.KitAPI;
import kitpvp.Util.Language;

public class Staff implements Listener, CommandExecutor {

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		
		if(!(arg0 instanceof Player)) return true;
		
		openStaffMenu(((Player)arg0));
		
		return true;
	}
	
	public void openStaffMenu(Player p){
		
		KitAPI api = new KitAPI();
		
		if(api.getLanguage(p.getName()) == Language.FINNISH){
			
			Inventory inv = Bukkit.createInventory(null, 36, "Ylläpito");
			
			api.createItem(inv, 8, Material.BOOK, 1, "§a§lYlläpito hakemukset", Arrays.asList("§7Luuletko, että olisit hyvä ylläpitäjä?"));
			
			api.createSkullItem(inv, 0, "KassuGFX", "§bKassuGFX", Arrays.asList("§7Arvo: §4§lADMIN"));
			api.createSkullItem(inv, 1, "TeamRaiderz", "§bTeamRaiderz", Arrays.asList("§7Arvo: §4§lADMIN"));

			api.createSkullItem(inv, 9, "Noisyy", "§bNoisyy", Arrays.asList("§7Arvo: §2§lRAKENTAJA?"));
			
			p.openInventory(inv);
		}
		else if(api.getLanguage(p.getName()) == Language.ENGLISH){
			
			Inventory inv = Bukkit.createInventory(null, 36, "Staff");
			
			api.createSkullItem(inv, 0, "KassuGFX", "§bKassuGFX", Arrays.asList("§7Rank: §4§lADMIN"));
			api.createSkullItem(inv, 1, "TeamRaiderz", "§bTeamRaiderz", Arrays.asList("§7Rank: §4§lADMIN"));

			api.createSkullItem(inv, 9, "Noisyy", "§bNoisyy", Arrays.asList("§7Rank: §2§lBUILDER?"));
			
			p.openInventory(inv);
		}
		
	}
	
	public void sendStaffRequirements(Player p){
		
	}

	@EventHandler
	public void onInvClick(InventoryClickEvent e){
		
		if(e.getCurrentItem() == null || e.getCurrentItem().getType() == null){ return; }
		if(!(e.getWhoClicked() instanceof Player)){ return; }
		Player p = (Player) e.getWhoClicked();
		if(e.getInventory().getName().equals("Ylläpito") || e.getInventory().getName().equals("Staff")){
			e.setCancelled(true);
			
			if(e.getCurrentItem().getType() == Material.BOOK && e.getCurrentItem().hasItemMeta()){
				p.closeInventory();
				sendStaffRequirements(p);
			}
			
		}
		
	}
	
}
