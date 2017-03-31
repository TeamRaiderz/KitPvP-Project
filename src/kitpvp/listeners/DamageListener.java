package kitpvp.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

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
		
		killer.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));
		
		e.setDeathMessage(null);
		e.getDrops().clear();
		
		int money =  Main.getAPI().randInt(10, 20);
		int xp = Main.getAPI().randInt(5, 25);
		
		Main.getAPI().addBalance(killer.getName(), money);
		Main.getAPI().addXp(killer.getName(), xp);
		
		if(Main.getAPI().getLanguage(killer.getName()) == Language.FINNISH){
			ChatUtils.sendMessageWithPrefix(killer, "§7Tapoit pelaajan §c" + victim.getName() + " §7ja sait §c" + xp + " §7xp:tä ja §c" + money + " §7rahaa!");
		}
		else if(Main.getAPI().getLanguage(killer.getName()) == Language.ENGLISH){
			ChatUtils.sendMessageWithPrefix(killer, "§7You killed the player §c" + victim.getName() + " §7and got §c" + xp + " §7XP and §c" + money + " §7money from it!");
		}
		
		if (api.getLanguage(victim.getName()) == Language.FINNISH) {

			ChatUtils.sendMessageWithPrefix(victim, "§7Pelaaja §c" + killer.getName() + " §7tappoi sinut. Hänen elämänsä: §c" + Main.getAPI().getHealth(killer) / 2 + "§7!");
			
		} else if (api.getLanguage(victim.getName()) == Language.ENGLISH) {

			ChatUtils.sendMessageWithPrefix(victim, "§7You were killed by §c" + killer.getName() + "§7. Your killer's health: §c" + Main.getAPI().getHealth(killer) / 2 + "§7!");
			
		}

	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e){
		
		if(!(e.getEntity() instanceof Player)) return;
		if(!(e.getDamager() instanceof Player)) return;
		
		Player victim = (Player) e.getEntity();
		Player dmger = (Player) e.getDamager();
		
		Main.getPacketUtils().sendActionBar(dmger, "§2§l" + victim.getName() + " §a-> " + Main.getAPI().getHealth(victim) / 2 + "/" + victim.getMaxHealth() / 2);
		
	}
	
}
