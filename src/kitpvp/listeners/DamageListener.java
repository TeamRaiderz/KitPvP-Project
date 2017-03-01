package kitpvp.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DamageListener implements Listener{

	@EventHandler
	public void onDeath(PlayerDeathEvent e){
		
		if(e.getEntity().getKiller() == null || !(e.getEntity().getKiller() instanceof Player)) return;
		
		Player victim = e.getEntity();
		Player killer = e.getEntity().getKiller();
		
		
		
	}
	
}
