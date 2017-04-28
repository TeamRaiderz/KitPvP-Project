package kitpvp.cosmetics;

import java.util.Arrays;

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
import kitpvp.cosmetics.menus.ArrowtrailMenu;
import kitpvp.cosmetics.menus.DeatheffectMenu;
import kitpvp.cosmetics.menus.ParticleMenu;

public class CosmeticBox implements Listener{

	private KitAPI api = new KitAPI();
	
	public void open(Player p){
		
		if(api.getLanguage(p.getName()) == Language.FINNISH){
			Inventory inv = Bukkit.createInventory(null, 36, "Kosmetiikka");
			
			api.createItem(inv, 10, Material.BOW, 1, "§aNuolijanat", Arrays.asList("§7Valitse haluamasi nuolijana!", "", "§7Omistat: §e" + ArrowTrail.getAmount(p)));
			api.createItem(inv, 13, Material.CHEST, 1, "§aOnnenlaatikko", Arrays.asList("§7Avaa laatikko ja voita mahtavia palkintoja!", "",
					"§7Omistat: §e" + Main.getCosmeticManager().getChests(p.getName())));
			api.createItem(inv, 16, Material.EMERALD, 1, "§aPartikkelit", Arrays.asList("§7Valitse haluamasi partikkeli!", "",
					"§7Omistat: §e0"));
			api.createItem(inv, 20, Material.SKULL_ITEM, 1, "§aKuolonefektit", Arrays.asList("§7Valitse haluamasi kuolonefekti!", "",
					"§7Omistat: §e0"));
			api.createItem(inv, 24, Material.DEAD_BUSH, 1, "§c§lTulossa pian...", Arrays.asList("§7..."));
			
			p.openInventory(inv);
		}
		else if (api.getLanguage(p.getName()) == Language.ENGLISH){
			
			Inventory inv = Bukkit.createInventory(null, 36, "Cosmetics");
			
			api.createItem(inv, 10, Material.BOW, 1, "§aArrow trails", Arrays.asList("§7Select the arrow trail you want!", "", "§7You own: §e" + ArrowTrail.getAmount(p)));
			api.createItem(inv, 13, Material.CHEST, 1, "§aBox of luck", Arrays.asList("§7Open a box and win awesome prizes!", "",
					"§7You own: §e" + Main.getCosmeticManager().getChests(p.getName())));
			api.createItem(inv, 16, Material.EMERALD, 1, "§aParticles", Arrays.asList("§7Select the particle effect you want!", "",
					"§7You own: §e0"));
			api.createItem(inv, 20, Material.SKULL_ITEM, 1, "§aDeath effect", Arrays.asList("§7Select the death effect you want!", "",
					"§7You own: §e0"));
			api.createItem(inv, 24, Material.DEAD_BUSH, 1, "§c§lComing soon...", Arrays.asList("§7..."));
			
			p.openInventory(inv);
		}
		
	}
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e){
		Player p = (Player) e.getWhoClicked();
		
		if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
		
		if(e.getInventory().getName().equals("Kosmetiikka") || e.getInventory().getName().equals("Cosmetics")){
			
			e.setCancelled(true);
			
			if(e.getCurrentItem().getType() == Material.CHEST && e.getCurrentItem().hasItemMeta() && Main.getCosmeticManager().getTokens(p.getName()) >= 1){
				CSGOCrate c = new CSGOCrate();
				c.openCSGO(p);
				Main.getCosmeticManager().setTokens(p.getName(), Main.getCosmeticManager().getTokens(p.getName()) - 1);
			}
			else if(e.getCurrentItem().getType() == Material.BOW && e.getCurrentItem().hasItemMeta()){
				ParticleMenu m = new ParticleMenu();
				m.openMenu(p);
			}
			else if(e.getCurrentItem().getType() == Material.EMERALD && e.getCurrentItem().hasItemMeta()){
				ArrowtrailMenu m = new ArrowtrailMenu();
				m.openMenu(p);
			}
			else if(e.getCurrentItem().getType() == Material.SKULL_ITEM && e.getCurrentItem().hasItemMeta()){
				DeatheffectMenu m = new DeatheffectMenu();
				m.openMenu(p);
			}
			
		}
		
	}
	
}
