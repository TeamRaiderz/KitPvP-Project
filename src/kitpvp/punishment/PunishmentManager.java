package kitpvp.punishment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import kitpvp.MySQL.MySQLManager;
import kitpvp.Util.ChatUtils;

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
	
	public void tempBanDays(String target, int time, String punisher, String reason, int multi){
		OfflinePlayer p = Bukkit.getOfflinePlayer(target);
		
		if(isBanned(p.getName())){
			ChatUtils.sendMessageWithPrefix(Bukkit.getPlayer(punisher), "§7That player is already banned!");
			return;
		}
		
		    long endOfBan = (System.currentTimeMillis()/1000) + (24 * 60 * 60) * time;
		    
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
		
		long current = (System.currentTimeMillis()/1000);
	    long endOfban = getEndOfBan(p.getName()).longValue();
	    long millis = endOfban - current;

	    int seconds = 0;
	    int minutes = 0;
	    int hours = 0;
	    int days = 0;

	    while (millis > 1L) {
	      millis -= 1L;
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
				day - Main.getDataFile().getInt(p.getUniqueId().toString() + ".lastWarn") >= 7){
			setWarnings(p.getName(), 1);
			Main.getDataFile().set(p.getUniqueId().toString() + ".lastWarn", day);
			Main.saveDataFile();
		}
		else if (Main.getDataFile().get(p.getUniqueId().toString() + ".lastWarn") == null &&
				day - Main.getDataFile().getInt(p.getUniqueId().toString() + ".lastWarn") < 7){
			addWarnings(p.getName(), 1);
			Main.getDataFile().set(p.getUniqueId().toString() + ".lastWarn", day);
			Main.saveDataFile();
			if(getWarnings(p.getName()) > 2){
				tempBanDays(p.getName(), 7, punisher.getName(), reason, 86400000);
			}
		}
		
	}
	
	public void getHistory(Player receiver, String target){
		OfflinePlayer p = Bukkit.getOfflinePlayer(target);
		
		int kicks = getKicks(p.getName());
		int bans = getBans(p.getName());
		int warnings = getWarnings(p.getName());
		boolean isBanned = isBanned(p.getName());
		int overall = kicks + bans + warnings;
		
		receiver.sendMessage("§7§m-------§a§l History of " + p.getName() + " §7§m-------");
		receiver.sendMessage("§7Kicks: §a" + kicks);
		receiver.sendMessage("§7Bans: §a" + bans);
		receiver.sendMessage("§7Warnings: §a" + warnings);
		receiver.sendMessage("§7Overall punishments: §a" + overall);
		receiver.sendMessage("§7Banned: §a" + isBanned);
		receiver.sendMessage("§7§m-------------------------------------------------");
		
	}
	
	public void kickPlayer(String target, String reason){
		addKicks(target, 1);
		Bukkit.getPlayer(target).kickPlayer("§cYou have been kicked! \n §7Reason: §c" + reason);
	}
	
	@EventHandler
	public void onLogin(PlayerLoginEvent e){
		
		Player p = e.getPlayer();
		
		long current = System.currentTimeMillis() / 1000;
		long end = getEndOfBan(p.getName()).longValue();
		
		if(isBlacklisted(p.getName())){
			String punisher = Main.getDataFile().getString(p.getUniqueId().toString() + ".blacklist.punisher");
			e.disallow(Result.KICK_BANNED, "§cYou have been blacklisted from this server! \n §7You were blacklisted by: §c" + punisher + "§7!");
		}
		else if (isBanned(p.getName())){
			String punisher = Main.getDataFile().getString(p.getUniqueId().toString() + ".ban.punisher");
			String reason = Main.getDataFile().getString(p.getUniqueId().toString() + ".ban.reason");
			
			if(current < end){
				e.disallow(Result.KICK_BANNED, "§cYou have been banned from this server! \n §7Reason: §c" + reason + " \n §7Banned by: §c" + punisher +
						"\n §7You will be unbanned in: §c" + getBanTimeLeft(p.getName().toString()));
			}
			else if (end < current){
				unbanPlayer(p.getName());
			}
			
		}
		
	}
	
	public int getWarnings(String player){
		
		try {
			MySQLManager m = new MySQLManager();
			if(m.getConnection().isClosed()){
				m.openConnection();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
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
			MySQLManager m = new MySQLManager();
			if(m.getConnection().isClosed()){
				m.openConnection();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
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
			MySQLManager m = new MySQLManager();
			if(m.getConnection().isClosed()){
				m.openConnection();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
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
			MySQLManager m = new MySQLManager();
			if(m.getConnection().isClosed()){
				m.openConnection();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
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
			MySQLManager m = new MySQLManager();
			if(m.getConnection().isClosed()){
				m.openConnection();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
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
			MySQLManager m = new MySQLManager();
			if(m.getConnection().isClosed()){
				m.openConnection();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
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
			MySQLManager m = new MySQLManager();
			if(m.getConnection().isClosed()){
				m.openConnection();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
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
			MySQLManager m = new MySQLManager();
			if(m.getConnection().isClosed()){
				m.openConnection();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
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
			MySQLManager m = new MySQLManager();
			if(m.getConnection().isClosed()){
				m.openConnection();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
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
