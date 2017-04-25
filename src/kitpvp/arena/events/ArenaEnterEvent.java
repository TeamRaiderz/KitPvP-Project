package kitpvp.arena.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import kitpvp.arena.Arena;
import kitpvp.arena.ArenaManager;
import kitpvp.object.ArenaPlayer;

public class ArenaEnterEvent extends Event implements Cancellable{

	private static final HandlerList handlers = new HandlerList();
	private boolean isCancelled;
	private ArenaPlayer player;
	private ArenaManager am = new ArenaManager();
	
	public ArenaEnterEvent(String name){
		this.isCancelled = false;
		player = am.getArenaPlayer(name);
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList(){
		return handlers;
	}
	
	public Arena getArena(){
		return am.getArena();
	}

	@Override
	public boolean isCancelled() {
		return isCancelled;
	}

	@Override
	public void setCancelled(boolean value) {
		isCancelled = value;
	}
	
	public ArenaPlayer getPlayer(){
		return player;
	}

}
