package kitpvp.achievements;

import org.bukkit.entity.Player;

public abstract class Achievement {
	
	public abstract String getName();
	
	public abstract String getDisplayName();
	
	public abstract int getXPReward();
	
	public abstract Achievement[] getChildren();
	
	public abstract boolean hasChildren();
	
	public abstract String toString();
	
	public void giveReward(Player p, Achievement a){
	}
	
}
