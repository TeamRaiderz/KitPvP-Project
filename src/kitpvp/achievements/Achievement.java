package kitpvp.achievements;

import org.bukkit.entity.Player;

import kitpvp.Main;
import kitpvp.Util.Language;

public abstract class Achievement {
	
	public abstract String getName();
	
	public abstract String getDisplayName();
	
	public abstract String getNameENG();
	
	public abstract String getNameFIN();
	
	public abstract int getXPReward();
	
	public abstract int getMoneyReward();
	
	public void giveReward(Player p, Achievement a){
		
		if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
			Main.getPacketUtils().sendTitle(p, "§a§lSAAVUTUS SUORITETTU!", "§f" + a.getNameFIN(),
					20, 40, 20);
			Main.getAPI().addBalance(p.getName(), a.getMoneyReward());
			Main.getAPI().addXp(p.getName(), a.getXPReward());
		}
		else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
			Main.getPacketUtils().sendTitle(p, "§a§lACHIEVEMENT COMPLETED!", "§f" + a.getNameENG(),
					20, 40, 20);
			Main.getAPI().addBalance(p.getName(), a.getMoneyReward());
			Main.getAPI().addXp(p.getName(), a.getXPReward());
		}
		
	}
	
}
