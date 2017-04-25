package kitpvp.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import kitpvp.Main;
import kitpvp.Util.ChatUtils;
import kitpvp.Util.KitAPI;
import kitpvp.Util.Language;
import kitpvp.arena.events.ArenaKillEvent;

public class DamageListener implements Listener{
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e){
		
		if(!(e.getEntity() instanceof Player)) return;
		if(!(e.getDamager() instanceof Player)) return;
		
		Player victim = (Player) e.getEntity();
		Player dmger = (Player) e.getDamager();
		
		Main.getPacketUtils().sendActionBar(dmger, "§2§l" + victim.getName() + " §a-> " + Main.getAPI().getHealth(victim) / 2 + "/" + victim.getMaxHealth() / 2);
		
	}
	
}
