package kitpvp.object;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public interface ArenaPlayer {

	Player getPlayer();
	
	void sendMessage(String message);
	
	int getKillStreak();
	
	Location getLocation();
	
	String getName();
	
	void die();
	
	boolean isInArena();
	
	PlayerInventory getInventory();
	
	boolean isInCombat();
	
	void addKillToKillStreak();
	
	void setKillStreak(int value);
	
}
