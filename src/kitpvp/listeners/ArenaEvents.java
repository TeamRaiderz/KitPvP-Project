package kitpvp.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import kitpvp.arena.events.ArenaEnterEvent;
import kitpvp.arena.events.ArenaKillEvent;
import kitpvp.object.ArenaPlayer;

public class ArenaEvents implements Listener{

	@EventHandler
	public void onArenaEnter(ArenaEnterEvent e){
		
		ArenaPlayer p = e.getPlayer();
		
		p.sendMessage("TESTING CUSTOM EVENTS!!!");
	}
	
	@EventHandler
	public void onArenaKill(ArenaKillEvent e){
		
		ArenaPlayer killer = e.getKiller();
		ArenaPlayer victim = e.getVictim();
		
		killer.addKillToKillStreak();
		
		killer.sendMessage("TESTING CUSTOM EVENTS!!!");
		victim.sendMessage("TESTING CUSTOM EVENTS!!!");
		
	}
	
}
