package kitpvp.cosmetics;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

import kitpvp.Main;
import kitpvp.Util.KitAPI;
import kitpvp.Util.Language;

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
			
			Inventory inv = Bukkit.createInventory(null, 36, "Kosmetiikka");
			
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
	
}
