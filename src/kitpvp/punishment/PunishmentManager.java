package kitpvp.punishment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import kitpvp.Main;

public class PunishmentManager implements Listener{
	
	private Connection connection = Main.getMySQLManager().getConnection();
	
	public void setBlacklisted(String target, boolean value, String punisher){
		OfflinePlayer p = Bukkit.getOfflinePlayer(target);
		Main.getDataFile().set(p.getUniqueId().toString() + ".blacklist.blacklisted", value);
		Main.getDataFile().set(p.getUniqueId().toString() + ".blacklist.punisher", punisher);
		Main.saveDataFile();
	}
	
	public boolean isBlacklisted(String target){
		OfflinePlayer p = Bukkit.getOfflinePlayer(target);
		return (Main.getDataFile().getBoolean(p.getUniqueId().toString() + ".blacklist.blacklisted"));
	}
	
	public void tempBanDays(String target, int days, String punisher, String reason){
		OfflinePlayer p = Bukkit.getOfflinePlayer(target);
		
		long current = System.currentTimeMillis();
	    long endOfBan = (current / 1000) + (24 * 60 * 60) * days;
		
		Main.getDataFile().set(p.getUniqueId().toString() + ".ban.punished", true);
		Main.getDataFile().set(p.getUniqueId().toString() + ".ban.banTime", endOfBan);
		Main.getDataFile().set(p.getUniqueId().toString() + ".ban.punisher", punisher);
		Main.getDataFile().set(p.getUniqueId().toString() + ".ban.reason", reason);
		Main.saveDataFile();
		
		if(p.isOnline()){
			p.getPlayer().kickPlayer("§cYou have been banned from this server! \n §7Reason: §c" + reason + " \n §7Banned by: §c" + punisher +
					"\n §7You will be unbanned in: §c" + getBanTimeLeft(target));
		}
		
		addBans(target, 1);
		
	}
	
	public void unbanPlayer(String target){
		OfflinePlayer p = Bukkit.getOfflinePlayer(target);
		Main.getDataFile().set(p.getUniqueId().toString() + ".ban.punished", false);
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
		    long millis = current - endOfban;
		    
		    int seconds = 0;
		    int minutes = 0;
		    int hours = 0;
		    int days = 0;

		    while (millis < 0) {
		      millis += 1000L;
		      seconds--;
		    }
		    while (seconds < 0) {
		      seconds += 60;
		      minutes--;
		    }
		    while (minutes <= 0) {
		      minutes += 60;
		      hours--;
		    }
		    while (hours < 0) {
		      hours += 24;
		      if(days <= 1){
		    	  days = 0;
		      }else{
			      days--;
		      }
		    }
		    
		    return days + "d " + hours + "h " + minutes + "min " + seconds + "s";
	}
	
	public Long getEndOfBan(String player){
		OfflinePlayer p = Bukkit.getOfflinePlayer(player);
		return Long.valueOf(Main.getDataFile().getLong(p.getUniqueId().toString() + ".ban.banTime"));
	}

	public String setReason(String[] args, int start) {
	    String reason = "";
	    for (int i = start; i < args.length; i++) {
	      reason = reason + args[i] + " ";
	    }

	    return reason;
	  }
	
	public void warnPlayer(String target, String reason, Player punisher){
		OfflinePlayer p = Bukkit.getOfflinePlayer(target);
		
		if(p.isOnline()){
			p.getPlayer().kickPlayer("§cYou have been warned! \n §7Warned by: §c" + punisher.getName() + " \n §7Reason: §c" + reason);
		}
		
		Calendar cal = new GregorianCalendar(TimeZone.getDefault());
		int day = cal.get(Calendar.DAY_OF_YEAR);
		
		if(Main.getDataFile().get(p.getUniqueId().toString() + ".lastWarn") == null){
			setWarnings(p.getName(), 1);
			Main.getDataFile().set(p.getUniqueId().toString() + ".lastWarn", day);
			Main.saveDataFile();
		}
		else if (Main.getDataFile().get(p.getUniqueId().toString() + ".lastWarn") == null &&
				Main.getDataFile().getInt(p.getUniqueId().toString() + ".lastWarn") - day >= 7){
			setWarnings(p.getName(), 1);
			Main.getDataFile().set(p.getUniqueId().toString() + ".lastWarn", day);
			Main.saveDataFile();
		}
		else if (Main.getDataFile().get(p.getUniqueId().toString() + ".lastWarn") == null &&
				Main.getDataFile().getInt(p.getUniqueId().toString() + ".lastWarn") - day < 7){
			addWarnings(p.getName(), 1);
			Main.getDataFile().set(p.getUniqueId().toString() + ".lastWarn", day);
			Main.saveDataFile();
			if(getWarnings(p.getName()) > 2){
				tempBanDays(p.getName(), 7, punisher.getName(), reason);
			}
		}
		
	}
	
	public void getHistory(Player receiver, String target){
		OfflinePlayer p = Bukkit.getOfflinePlayer(target);
		String uuid = p.getUniqueId().toString();
		
	}
	
	public void kickPlayer(String target, String reason){
		addKicks(target, 1);
		Bukkit.getPlayer(target).kickPlayer("§cYou have been kicked! \n §7Reason: §c" + reason);
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
					"\n §7You will be unbanned in: §c" + getBanTimeLeft(p.getName()));
		}
		
	}
	
	public int getWarnings(String player){
		try {
			
			if(Main.getMySQLManager().punishmentContainsPlayer(Bukkit.getOfflinePlayer(player).getName())){

				PreparedStatement sql = connection.prepareStatement("SELECT warnings FROM `punishments` WHERE player = ?;");
				sql.setString(1, player);
				
				ResultSet result = sql.executeQuery();
				result.next();
				
				int kill = result.getInt("warnings");
				
				return kill;
			}
			else{
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `punishments` values(?,0,0,0)");
				newPlayer.setString(1, Bukkit.getOfflinePlayer(player).getName());
				newPlayer.execute();
				newPlayer.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void setWarnings(String player, int warnings){
		try {
			
			if(Main.getMySQLManager().punishmentContainsPlayer(Bukkit.getOfflinePlayer(player).getName())){
				
				PreparedStatement sql = connection.prepareStatement("SELECT warnings FROM `punishments` WHERE player = ?;");
				sql.setString(1, player);
				
				ResultSet result = sql.executeQuery();
				result.next();
				
				PreparedStatement updatewarnings = connection.prepareStatement("UPDATE `punishments` SET warnings=? WHERE player = ?;");
				updatewarnings.setInt(1, warnings);
				updatewarnings.setString(2, player);
				updatewarnings.executeUpdate();
			
				updatewarnings.close();
				sql.close();
				result.close();
				
			}
			else{
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `punishments` values(?,0,0,0)");
				newPlayer.setString(1, Bukkit.getOfflinePlayer(player).getName());
				newPlayer.execute();
				newPlayer.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	public void addWarnings(String player, int warnings){
		try {
			
			if(Main.getMySQLManager().punishmentContainsPlayer(Bukkit.getOfflinePlayer(player).getName())){
				
				PreparedStatement sql = connection.prepareStatement("SELECT warnings FROM `punishments` WHERE player = ?;");
				sql.setString(1, player);
				
				ResultSet result = sql.executeQuery();
				result.next();
				
				int kill = result.getInt("warnings");
				
				PreparedStatement updatewarnings = connection.prepareStatement("UPDATE `punishments` SET warnings=? WHERE player = ?;");
				updatewarnings.setInt(1, kill + warnings);
				updatewarnings.setString(2, player);
				updatewarnings.executeUpdate();
				
				updatewarnings.close();
				sql.close();
				result.close();
			}
			else{
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `punishments` values(?,0,0,0)");
				newPlayer.setString(1, Bukkit.getOfflinePlayer(player).getName());
				newPlayer.execute();
				newPlayer.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public int getBans(String player){
		try {
			
			if(Main.getMySQLManager().punishmentContainsPlayer(Bukkit.getOfflinePlayer(player).getName())){

				PreparedStatement sql = connection.prepareStatement("SELECT bans FROM `punishments` WHERE player = ?;");
				sql.setString(1, player);
				
				ResultSet result = sql.executeQuery();
				result.next();
				
				int kill = result.getInt("bans");
				
				return kill;
			}
			else{
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `punishments` values(?,0,0,0)");
				newPlayer.setString(1, Bukkit.getOfflinePlayer(player).getName());
				newPlayer.execute();
				newPlayer.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void setBans(String player, int bans){
		try {
			
			if(Main.getMySQLManager().punishmentContainsPlayer(Bukkit.getOfflinePlayer(player).getName())){
				
				PreparedStatement sql = connection.prepareStatement("SELECT bans FROM `punishments` WHERE player = ?;");
				sql.setString(1, player);
				
				ResultSet result = sql.executeQuery();
				result.next();
				
				PreparedStatement updatebans = connection.prepareStatement("UPDATE `punishments` SET bans=? WHERE player = ?;");
				updatebans.setInt(1, bans);
				updatebans.setString(2, player);
				updatebans.executeUpdate();
			
				updatebans.close();
				sql.close();
				result.close();
				
			}
			else{
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `punishments` values(?,0,0,0)");
				newPlayer.setString(1, Bukkit.getOfflinePlayer(player).getName());
				newPlayer.execute();
				newPlayer.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	public void addBans(String player, int bans){
		try {
			
			if(Main.getMySQLManager().punishmentContainsPlayer(Bukkit.getOfflinePlayer(player).getName())){
				
				PreparedStatement sql = connection.prepareStatement("SELECT bans FROM `punishments` WHERE player = ?;");
				sql.setString(1, player);
				
				ResultSet result = sql.executeQuery();
				result.next();
				
				int kill = result.getInt("bans");
				
				PreparedStatement updatebans = connection.prepareStatement("UPDATE `punishments` SET bans=? WHERE player = ?;");
				updatebans.setInt(1, kill + bans);
				updatebans.setString(2, player);
				updatebans.executeUpdate();
				
				updatebans.close();
				sql.close();
				result.close();
			}
			else{
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `punishments` values(?,0,0,0)");
				newPlayer.setString(1, Bukkit.getOfflinePlayer(player).getName());
				newPlayer.execute();
				newPlayer.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public int getKicks(String player){
		try {
			
			if(Main.getMySQLManager().punishmentContainsPlayer(Bukkit.getOfflinePlayer(player).getName())){

				PreparedStatement sql = connection.prepareStatement("SELECT kicks FROM `punishments` WHERE player = ?;");
				sql.setString(1, player);
				
				ResultSet result = sql.executeQuery();
				result.next();
				
				int kill = result.getInt("kicks");
				
				return kill;
			}
			else{
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `punishments` values(?,0,0,0)");
				newPlayer.setString(1, Bukkit.getOfflinePlayer(player).getName());
				newPlayer.execute();
				newPlayer.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void setKicks(String player, int kicks){
		try {
			
			if(Main.getMySQLManager().punishmentContainsPlayer(Bukkit.getOfflinePlayer(player).getName())){
				
				PreparedStatement sql = connection.prepareStatement("SELECT kicks FROM `punishments` WHERE player = ?;");
				sql.setString(1, player);
				
				ResultSet result = sql.executeQuery();
				result.next();
				
				PreparedStatement updatekicks = connection.prepareStatement("UPDATE `punishments` SET kicks=? WHERE player = ?;");
				updatekicks.setInt(1, kicks);
				updatekicks.setString(2, player);
				updatekicks.executeUpdate();
			
				updatekicks.close();
				sql.close();
				result.close();
				
			}
			else{
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `punishments` values(?,0,0,0)");
				newPlayer.setString(1, Bukkit.getOfflinePlayer(player).getName());
				newPlayer.execute();
				newPlayer.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	public void addKicks(String player, int kicks){
		try {
			
			if(Main.getMySQLManager().punishmentContainsPlayer(Bukkit.getOfflinePlayer(player).getName())){
				
				PreparedStatement sql = connection.prepareStatement("SELECT kicks FROM `punishments` WHERE player = ?;");
				sql.setString(1, player);
				
				ResultSet result = sql.executeQuery();
				result.next();
				
				int kill = result.getInt("kicks");
				
				PreparedStatement updatekicks = connection.prepareStatement("UPDATE `punishments` SET kicks=? WHERE player = ?;");
				updatekicks.setInt(1, kill + kicks);
				updatekicks.setString(2, player);
				updatekicks.executeUpdate();
				
				updatekicks.close();
				sql.close();
				result.close();
			}
			else{
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `punishments` values(?,0,0,0)");
				newPlayer.setString(1, Bukkit.getOfflinePlayer(player).getName());
				newPlayer.execute();
				newPlayer.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public String getDateFormat(long millis) {
	    Date date = new Date(millis);
	    DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	    return format.format(date);
	}
	
}
