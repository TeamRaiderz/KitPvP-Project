package kitpvp.cosmetics.menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import kitpvp.Main;
import kitpvp.Util.KitAPI;
import kitpvp.Util.Language;
import kitpvp.cosmetics.CSGOCrate;

public class ParticleMenu implements Listener{

public void openMenu(Player p){
		
		if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
			
			Inventory inv = Bukkit.createInventory(null, 36, "Partikkelit");
			
			inv.setItem(8, KitAPI.makeItem(Material.STAINED_GLASS_PANE, 1, 1, "§6§lLegendary"));
			inv.setItem(17, KitAPI.makeItem(Material.STAINED_GLASS_PANE, 1, 14, "§c§lRare"));
			inv.setItem(26, KitAPI.makeItem(Material.STAINED_GLASS_PANE, 1, 10, "§5§lUncommon"));
			inv.setItem(35, KitAPI.makeItem(Material.STAINED_GLASS_PANE, 1, 11, "§9§lCommon"));
			
			p.openInventory(inv);
			
		}
		else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
			
			Inventory inv = Bukkit.createInventory(null, 36, "Particles");
			
			inv.setItem(8, KitAPI.makeItem(Material.STAINED_GLASS_PANE, 1, 1, "§6§lLegendary"));
			inv.setItem(17, KitAPI.makeItem(Material.STAINED_GLASS_PANE, 1, 14, "§c§lRare"));
			inv.setItem(26, KitAPI.makeItem(Material.STAINED_GLASS_PANE, 1, 10, "§5§lUncommon"));
			inv.setItem(35, KitAPI.makeItem(Material.STAINED_GLASS_PANE, 1, 11, "§9§lCommon"));
			
			p.openInventory(inv);
			
		}
		
	}

	@EventHandler
	public void onInvClick(InventoryClickEvent e){
		
		if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR){ return; }
		
		if(e.getInventory().getName().equals("Partikkelit") || e.getInventory().getName().equals("Particles")){
			
			e.setCancelled(true);
			
		}
		
	}
	
}
