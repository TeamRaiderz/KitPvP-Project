package kitpvp.achievements;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import kitpvp.Main;
import kitpvp.Util.Language;

public class AchievementMenu implements Listener {

	public void openMenu(Player p){
	
		if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
			
		}
		else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
			
		}
		
	}
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e){
		
	}
	
}
