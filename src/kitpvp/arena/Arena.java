package kitpvp.arena;

import java.util.ArrayList;
import java.util.List;

import kitpvp.object.ArenaPlayer;

public class Arena {

	private static List<ArenaPlayer> players = new ArrayList<ArenaPlayer>();
	
	public List<ArenaPlayer> getPlayers(){
		return players;
	}
	
}
