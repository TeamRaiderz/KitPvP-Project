package kitpvp.arena.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import kitpvp.arena.ArenaManager;
import kitpvp.object.ArenaPlayer;

public class ArenaKillEvent extends Event{

	private static final HandlerList handlers = new HandlerList();
	
	private ArenaPlayer killer, victim;
	ArenaManager am = new ArenaManager();
	
	public ArenaKillEvent(String killer, String victim){
		this.killer = am.getArenaPlayer(killer);
		this.victim = am.getArenaPlayer(victim);
	}
	
	public ArenaPlayer getKiller(){
		return killer;
	}
	
	public ArenaPlayer getVictim(){
		return victim;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public HandlerList getHandlerList(){
		return handlers;
	}
	
}
