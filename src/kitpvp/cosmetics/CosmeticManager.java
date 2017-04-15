package kitpvp.cosmetics;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import kitpvp.Main;
import kitpvp.MySQL.MySQLManager;
import kitpvp.Util.Language;

public class CosmeticManager {

	private Connection connection = new MySQLManager().getConnection();
	
	public static HashMap<String, Integer> tokens = new HashMap<String, Integer>();
	public static HashMap<String, Integer> chests = new HashMap<String, Integer>();
	public static HashMap<String, Integer> boosters = new HashMap<String, Integer>();
	 
	public void getTokensDB(String player) {

		try {
			MySQLManager m = new MySQLManager();
			if (m.getConnection().isClosed() || m.getConnection() == null) {
				m.openConnection();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {

			if (Main.getMySQLManager().cosmeticContainsPlayer(player)) {

				Statement statement = connection.createStatement();
				ResultSet result = statement.executeQuery("SELECT * FROM `cosmetic_data` WHERE player = '" + player + "';");

				while (result.next()) {
					int kill = result.getInt("tokens");
					tokens.put(player, kill);
				}

			} else {
				Main.getMySQLManager().putPlayerToCosmeticData(player);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getTokens(String p) {
		if (tokens.containsKey(p)) {
			return tokens.get(p);
		}
		else{
			return 0;
		}
	}

	public void setTokens(String player, int value) {

		tokens.put(player, value);
		
	}

	public void addTokens(String player, int value) {

		if(tokens.containsKey(player)){
			tokens.put(player, tokens.get(player) + value);
		}
		else{
			tokens.put(player, value);
		}
		
	}

	public void getChestsDB(String player) {

		try {
			MySQLManager m = new MySQLManager();
			if (m.getConnection().isClosed() || m.getConnection() == null) {
				m.openConnection();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {

			if (Main.getMySQLManager().cosmeticContainsPlayer(player)) {

				Statement statement = connection.createStatement();
				ResultSet result = statement.executeQuery("SELECT * FROM `cosmetic_data` WHERE player = '" + player + "';");

				while (result.next()) {
					int kill = result.getInt("chests");
					chests.put(player, kill);
				}

			} else {
				Main.getMySQLManager().putPlayerToCosmeticData(player);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public int getChests(String p) {
		if (chests.containsKey(p)) {
			return chests.get(p);
		} else {
			return 0;
		}
	}

	public void setChests(String player, int value) {
		
		chests.put(player, value);
		
	}

	public void addChests(String player, int value) {

		if(chests.containsKey(player)){
			chests.put(player, chests.get(player) + value);
		}
		else{
			chests.put(player, value);
		}
		
	}

	public void getBoostersDB(String player) {

		new BukkitRunnable(){

			@Override
			public void run() {
				MySQLManager m = new MySQLManager();
				try {
					if (m.getConnection().isClosed() || m.getConnection() == null) {
						m.openConnection();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				try {

					if (m.cosmeticContainsPlayer(player)) {

						Statement statement = connection.createStatement();
						ResultSet result = statement.executeQuery("SELECT * FROM `cosmetic_data` WHERE player = '" + player + "';");
						while (result.next()) {
							int kill = result.getInt("boosters");
							boosters.put(player, kill);
						}

					} else {
						m.putPlayerToCosmeticData(player);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}.runTaskAsynchronously(Main.getInstance());

	}

	public int getBoosters(String p) {
		if (boosters.containsKey(p)) {
			return boosters.get(p);
		}
		else{
			return 0;
		}
	}

	public void setBoosters(String player, int value) {

		boosters.put(player, value);
		
	}

	public void addBoosters(String player, int value) {

		if(boosters.containsKey(player)){
			boosters.put(player, boosters.get(player) + value);
		}
		else{
			boosters.put(player, value);
		}
		
	}
	
}
