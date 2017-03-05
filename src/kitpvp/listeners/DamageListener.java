package kitpvp.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import kitpvp.Language;
import kitpvp.Main;
import kitpvp.Util.ChatUtils;
import kitpvp.Util.KitAPI;

public class DamageListener implements Listener{

	@EventHandler
	public void onDeath(PlayerDeathEvent e){
		
		KitAPI api = Main.getAPI();
		
		if(e.getEntity().getKiller() == null || !(e.getEntity().getKiller() instanceof Player)) return;
		
		Player victim = e.getEntity();
		Player killer = e.getEntity().getKiller();
		
		api.addKills(killer.getName(), 1);
		api.addDeaths(victim.getName(), 1);
		
		api.addKillToKillStreak(killer);
		api.clearKillStreak(victim);
		
		e.setDeathMessage(null);
		
		if (api.getLanguage(victim.getName()) == Language.FINNISH) {

			ChatUtils.sendMessageWithPrefix(victim, "§7Pelaaja §c" + killer.getName() + " §7tappoi sinut. Hänen elämänsä: §c" + killer.getHealth() / 2 + "§7!");
			
		} else if (api.getLanguage(victim.getName()) == Language.ENGLISH) {

			ChatUtils.sendMessageWithPrefix(victim, "§7You were killed by §c" + killer.getName() + "§7. Your killer's health: §c" + killer.getHealth() / 2 + "§7!");
			
		}

	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e){
		
		if(!(e.getEntity() instanceof Player)) return;
		if(!(e.getDamager() instanceof Player)) return;
		
		Player victim = (Player) e.getEntity();
		Player dmger = (Player) e.getDamager();
		
		Main.getPacketUtils().sendActionBar(dmger, "§2§l" + victim.getName() + " §a-> " + victim.getHealth() / 2 + "/" + victim.getMaxHealth() / 2);
		
	}
	
}
