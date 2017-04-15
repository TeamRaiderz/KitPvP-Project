package kitpvp.StaffMode;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import kitpvp.Main;
import kitpvp.Util.KitAPI;
import kitpvp.Util.Language;

public class PlayerSettings {

	private KitAPI api = new KitAPI();
	
	public void openGui(Player p, Player target) {

		if (Main.getAPI().getLanguage(p.getName()) == Language.FINNISH) {

			Inventory inv = Bukkit.createInventory(null, 27, "Asetukset " + target.getName());
			
			p.openInventory(inv);
		} else if (Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH) {

			Inventory inv = Bukkit.createInventory(null, 27, "Options " + target.getName());

			p.openInventory(inv);
		}

	}

}
