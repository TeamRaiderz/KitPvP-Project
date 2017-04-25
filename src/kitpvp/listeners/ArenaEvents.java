package kitpvp.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import kitpvp.Main;
import kitpvp.Util.KitAPI;
import kitpvp.Util.Language;
import kitpvp.arena.events.ArenaEnterEvent;
import kitpvp.arena.events.ArenaKillEvent;
import kitpvp.object.ArenaPlayer;

public class ArenaEvents implements Listener{

	@EventHandler
	public void onArenaEnter(ArenaEnterEvent e){
		
		ArenaPlayer p = e.getPlayer();
		
		if(p.getLanguage() == Language.FINNISH){
			p.sendActionBar("§c§lValmistaudu taistoon " + p.getName() + "!");
		}
		else if(p.getLanguage() == Language.ENGLISH){
			p.sendActionBar("§c§lPrepare to fight " + p.getName() + "!");
		}
		
	}
	
	@EventHandler
	public void onArenaKill(ArenaKillEvent e){
		
		ArenaPlayer killer = e.getKiller();
		ArenaPlayer victim = e.getVictim();
		
		killer.addKillToKillStreak();
		KitAPI api = new KitAPI();
		
		int money = Main.getAPI().randInt(10, 20);
		int xp = Main.getAPI().randInt(5, 25);

		Main.getAPI().addBalance(killer.getName(), money);
		Main.getAPI().addXp(killer.getName(), xp);

		if (Main.getAPI().getLanguage(killer.getName()) == Language.FINNISH) {
			killer.sendMessage("§7Tapoit pelaajan §c" + victim.getName() + " §7ja sait §c" + xp
					+ " §7xp:tä ja §c" + money + " §7rahaa!");
		} else if (Main.getAPI().getLanguage(killer.getName()) == Language.ENGLISH) {
			killer.sendMessage("§7You killed the player §c" + victim.getName()
					+ " §7and got §c" + xp + " §7XP and §c" + money + " §7money from it!");
		}

		if (api.getLanguage(victim.getName()) == Language.FINNISH) {

			victim.sendMessage("§7Pelaaja §c" + killer.getName()
					+ " §7tappoi sinut. Hänen elämänsä: §c" + killer.getHealth() / 2 + "§7!");

		} else if (api.getLanguage(victim.getName()) == Language.ENGLISH) {

			victim.sendMessage("§7You were killed by §c" + killer.getName()
					+ "§7. Your killer's health: §c" + killer.getHealth() / 2 + "§7!");

		}
		
	}
	
}
