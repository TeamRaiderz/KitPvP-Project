package kitpvp.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import kitpvp.Kit;
import kitpvp.Language;
import kitpvp.Main;

public class KitAPI {

	public int getKills(String player){
		Statement s = null;
		ResultSet res = null;
		try { 
			s = Main.getMySQLManager().getConnection().createStatement();
			res = s.executeQuery("SELECT * FROM kills WHERE PlayerName = '" + player + "';");
			if(res.getString("PlayerName") == null){
				return 0;
			}
			else{
				return res.getInt("kills");
			}
		} catch (SQLException e) { e.printStackTrace(); }
		return 0;
	}
	
	public void setKills(String player, int kills){
		Statement s = null;
		ResultSet res = null;
		try { 
			s = Main.getMySQLManager().getConnection().createStatement();
			s.executeUpdate("INSERT INTO kills (`PlayerName`, `kills`) VALUES ('" + player + "', '" + kills + "');");
			System.out.println("The player's " + player + " kills are now " + kills);
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public int getBalance(String player){
		Statement s = null;
		ResultSet res = null;
		try { 
			s = Main.getMySQLManager().getConnection().createStatement();
			res = s.executeQuery("SELECT * FROM balance WHERE PlayerName = '" + player + "';");
			if(res.getString("PlayerName") == null){
				return 0;
			}
			else{
				return res.getInt("balance");
			}
		} catch (SQLException e) { e.printStackTrace(); }
		return 0;
	}
	
	public void setBalance(String player, int kills){
		Statement s = null;
		ResultSet res = null;
		try { 
			s = Main.getMySQLManager().getConnection().createStatement();
			s.executeUpdate("INSERT INTO kills (`PlayerName`, `balance`) VALUES ('" + player + "', '" + kills + "');");
			System.out.println("The player's " + player + " balance is now " + kills);
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public Language getLanguage(String player){
		
		OfflinePlayer p = Bukkit.getOfflinePlayer(player);
		String uuid = p.getUniqueId().toString();
		
		if(Main.getDataFile().getString(uuid + ".lang").equalsIgnoreCase("ENG")){
			return Language.ENGLISH;
		}
		else if(Main.getDataFile().getString(uuid + ".lang").equalsIgnoreCase("FIN")){
			return Language.FINNISH;
		}
		return null;
	}
	
	public void setLanguage(String player, Language language){
		
		OfflinePlayer p = Bukkit.getOfflinePlayer(player);
		String uuid = p.getUniqueId().toString();
		
		if (Main.getDataFile().get(uuid) == null) {
			System.out.println("That player is not in our database.");
			return;
		}
		
		switch (language) {
		case FINNISH:
			Main.getDataFile().set(uuid + ".lang", "FIN");
			Main.saveDataFile();
			break;
		case ENGLISH:
			Main.getDataFile().set(uuid + ".lang", "ENG");
			Main.saveDataFile();
			break;
		}
		
	}
	
}
