package kitpvp.StaffMode;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import kitpvp.Main;
import kitpvp.Util.ChatUtils;
import kitpvp.Util.Language;

public class FreezeManager {

	public static List<String> frozenPlayers = new ArrayList<String>();
	
	public void freeze(Player target){
		frozenPlayers.add(target.getName());
		if(Main.getAPI().getLanguage(target.getName()) == Language.FINNISH){
			ChatUtils.sendMessageWithPrefix(target, "�7Olet nyt j��dytetty!");
		}
		else if (Main.getAPI().getLanguage(target.getName()) == Language.ENGLISH){
			ChatUtils.sendMessageWithPrefix(target, "�7You're now frozen!");
		}
	}
	
	public void unFreeze(Player target){
		frozenPlayers.remove(target.getName());
		if(Main.getAPI().getLanguage(target.getName()) == Language.FINNISH){
			ChatUtils.sendMessageWithPrefix(target, "�7Et ole en�� j��dytetty!");
		}
		else if (Main.getAPI().getLanguage(target.getName()) == Language.ENGLISH){
			ChatUtils.sendMessageWithPrefix(target, "�7You're not frozen anymore!");
		}
	}
	
	public boolean isFrozen(Player p){
		return frozenPlayers.contains(p.getName());
	}
	
	public void sendFreezeDenyMsg(Player p){
		if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
			ChatUtils.sendMessageWithPrefix(p, "�7Et voi tehd� tuota kun olet �cj��dytetty�7!");
		}
		else if (Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
			ChatUtils.sendMessageWithPrefix(p, "�7You can't do that whilst �cfrozen�7!");
		}
	}
	
}
