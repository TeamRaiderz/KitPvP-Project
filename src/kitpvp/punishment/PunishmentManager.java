package kitpvp.punishment;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import kitpvp.Main;

public class PunishmentManager implements Listener{
	
	public void setBlacklisted(String target, boolean value, String punisher){
		OfflinePlayer p = Bukkit.getOfflinePlayer(target);
		Main.getDataFile().set(p.getUniqueId().toString() + ".blacklist.blacklisted", value);
		Main.getDataFile().set(p.getUniqueId().toString() + ".blacklist.punisher", value);
		Main.saveDataFile();
	}
	
	public boolean isBlacklisted(String target){
		OfflinePlayer p = Bukkit.getOfflinePlayer(target);
		return (Main.getDataFile().getBoolean(p.getUniqueId().toString() + ".blacklist.blacklisted"));
	}
	
	public void tempBanDays(String target, int days, String punisher, String reason){
		OfflinePlayer p = Bukkit.getOfflinePlayer(target);
		int secondsInDay = 24*60*60;
		long banTime = ((System.currentTimeMillis()/1000) + secondsInDay * days);
		
		Main.getDataFile().set(p.getUniqueId().toString() + ".ban.punished", true);
		Main.getDataFile().set(p.getUniqueId().toString() + ".ban.banTime", banTime);
		Main.getDataFile().set(p.getUniqueId().toString() + ".ban.punisher", punisher);
		Main.getDataFile().set(p.getUniqueId().toString() + ".ban.reason", reason);
		Main.saveDataFile();
		
		if(p.isOnline()){
			p.getPlayer().kickPlayer("§cYou have been banned from this server! \n §7Reason: §c" + reason + " \n §7Banned by: §c" + punisher +
					"§7You will be unbanned in: §c" + getBanTimeLeft(target));
		}
		
	}
	
	public void unbanPlayer(String target){
		OfflinePlayer p = Bukkit.getOfflinePlayer(target);
		Main.getDataFile().set(p.getUniqueId().toString() + ".ban.punished", false);
		Main.getDataFile().set(p.getUniqueId().toString() + ".ban.banTime", 0);
		Main.getDataFile().set(p.getUniqueId().toString() + ".ban.punisher", "");
		Main.getDataFile().set(p.getUniqueId().toString() + ".ban.reason", "");
		Main.saveDataFile();
	}
	
	public boolean isBanned(String player){
		OfflinePlayer p = Bukkit.getOfflinePlayer(player);
		String uuid = p.getUniqueId().toString();
		return Main.getDataFile().getBoolean(uuid + ".ban.punished");
	}
	
	
	public String getBanTimeLeft(String player) {
		OfflinePlayer p = Bukkit.getOfflinePlayer(player);
		String uuid = p.getUniqueId().toString();
		 long current = System.currentTimeMillis();
		    long endOfban = getEndOfBan(uuid).longValue();
		    long millis = endOfban - current;

		    int seconds = 0;
		    int minutes = 0;
		    int hours = 0;
		    int days = 0;

		    while (millis > 1000L) {
		      millis -= 1000L;
		      seconds++;
		    }
		    while (seconds > 60) {
		      seconds -= 60;
		      minutes++;
		    }
		    while (minutes > 60) {
		      minutes -= 60;
		      hours++;
		    }
		    while (hours > 24) {
		      hours -= 24;
		      days++;
		    }

		    return days + "d " + hours + "h " + minutes + "min " + seconds + "s";
	}
	
	public Long getEndOfBan(String player){
		OfflinePlayer p = Bukkit.getOfflinePlayer(player);
		return Long.valueOf(Main.getDataFile().getLong(p.getUniqueId().toString() + ".banTime"));
	}

	public String setReason(String[] args, int start) {
	    String reason = "";
	    for (int i = start; i < args.length; i++) {
	      reason = reason + args[i] + " ";
	    }

	    return reason;
	  }
	
	@EventHandler
	public void onLogin(PlayerLoginEvent e){
		
		Player p = e.getPlayer();
		
		if(isBlacklisted(p.getName())){
			String punisher = Main.getDataFile().getString(p.getUniqueId().toString() + ".blacklist.punisher");
			e.disallow(Result.KICK_BANNED, "§cYou have been blacklisted from this server! \n §7You were blacklisted by: §c" + punisher + "§7!");
		}
		else if (isBanned(p.getName())){
			String punisher = Main.getDataFile().getString(p.getUniqueId().toString() + ".ban.punisher");
			String reason = Main.getDataFile().getString(p.getUniqueId().toString() + ".ban.reason");
			e.disallow(Result.KICK_BANNED, "§cYou have been banned from this server! \n §7Reason: §c" + reason + " \n §7Banned by: §c" + punisher +
					"§7You will be unbanned in: §c" + getBanTimeLeft(p.getName()));
		}
		
	}
	
}
