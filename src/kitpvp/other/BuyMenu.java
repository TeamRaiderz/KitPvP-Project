package kitpvp.other;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import kitpvp.Main;
import kitpvp.Util.KitAPI;
import kitpvp.Util.Language;

public class BuyMenu {

	int price;
	
	public BuyMenu(int price){
		this.price = price;
	}

	public void openMenu(Player p){
		
		if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
			
			Inventory inv = Bukkit.createInventory(null, 54, "Oston varmistus");
		
			Main.getAPI().createItem(inv, 13, Material.BOOK, 1, "�bHinta: �e" + getPrice(), Arrays.asList(""));
			
			int[] greenGlass = new int[] { 27, 28, 29, 26, 27, 38, 45, 46, 47 };
			int[] redGlass = new int[] { 33, 24, 25, 42, 43, 44, 51, 52, 53 };
			
			for(int i : greenGlass){
				inv.setItem(i, KitAPI.makeItem(Material.STAINED_GLASS_PANE, 1, 5, "�a�lHyv�ksy"));
			}
			
			for(int i : redGlass){
				inv.setItem(i, KitAPI.makeItem(Material.STAINED_GLASS_PANE, 1, 5, "�c�lHylk��"));
			}
			
			p.openInventory(inv);
		}
		else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
			
			Inventory inv = Bukkit.createInventory(null, 54, "Confirm purchase");
			
			Main.getAPI().createItem(inv, 13, Material.BOOK, 1, "�bPrice: �e" + getPrice(), Arrays.asList(""));
			
			int[] greenGlass = new int[] { 27, 28, 29, 26, 27, 38, 45, 46, 47 };
			int[] redGlass = new int[] { 33, 24, 25, 42, 43, 44, 51, 52, 53 };
			
			for(int i : greenGlass){
				inv.setItem(i, KitAPI.makeItem(Material.STAINED_GLASS_PANE, 1, 5, "�a�lAccept"));
			}
			
			for(int i : redGlass){
				inv.setItem(i, KitAPI.makeItem(Material.STAINED_GLASS_PANE, 1, 5, "�c�lDecline"));
			}
			
			p.openInventory(inv);
		}
		
	}

	public int getPrice() {
		return price;
	}
	
	
	
}
