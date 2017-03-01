package kitpvp.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
public class AbilityListener implements Listener {
	
	@EventHandler
	public void onAbility(PlayerInteractEvent e){
	
		Player p = e.getPlayer();
		
		ItemStack item = e.getItem();
		ItemMeta meta = item.getItemMeta();
		Material type = item.getType();
		
	}
	
}
