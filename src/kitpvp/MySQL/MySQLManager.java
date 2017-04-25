package kitpvp.MySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import kitpvp.Main;

public class MySQLManager {
	private static Connection connection;
	
	public static void disable(){
		try {
			if(connection != null || !connection.isClosed()){
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void openAsyncConnection(){
		new BukkitRunnable(){

			@Override
			public void run() {
				openConnection();
			}
			
		}.runTaskAsynchronously(Main.getInstance());
	}
	
	public synchronized void openConnection(){
		
		String host = Main.getConfigFile().getString("MySQL.hostname");
		String port = Main.getConfigFile().getString("MySQL.port");
		String username = Main.getConfigFile().getString("MySQL.username");
		String password = Main.getConfigFile().getString("MySQL.password");
		
		try{
			connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + username, username, password);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public synchronized void closeConnection(){
		try{
			connection.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public synchronized boolean playerDataContainsPlayer(String p){
		try{
			PreparedStatement sql = connection.prepareStatement("SELECT * FROM `player_data` WHERE player=?;");
			sql.setString(1, p);
			
			ResultSet set = sql.executeQuery();
			
			boolean containsPlayer = set.next();
			
			sql.close();
			set.close();
			
			return containsPlayer;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public synchronized boolean punishmentContainsPlayer(String p){
		try{
			PreparedStatement sql = connection.prepareStatement("SELECT * FROM `punishments` WHERE player=?;");
			sql.setString(1, p);
			
			ResultSet set = sql.executeQuery();
			
			boolean containsPlayer = set.next();
			
			sql.close();
			set.close();
			
			return containsPlayer;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public synchronized boolean cosmeticContainsPlayer(String p){
		try{
			PreparedStatement sql = connection.prepareStatement("SELECT * FROM `cosmetic_data` WHERE player=?;");
			sql.setString(1, p);
			
			ResultSet set = sql.executeQuery();
			
			boolean containsPlayer = set.next();
			
			sql.close();
			set.close();
			
			return containsPlayer;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public synchronized boolean languageContainsPlayer(String p){
		try{
			PreparedStatement sql = connection.prepareStatement("SELECT * FROM `language` WHERE player=?;");
			sql.setString(1, p);
			
			ResultSet set = sql.executeQuery();
			
			boolean containsPlayer = set.next();
			
			sql.close();
			set.close();
			
			return containsPlayer;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public Connection getConnection(){
		return connection;
	}
	
	public void putPlayerToPlayerData(String player){
		try{
			PreparedStatement newPlayer = connection.prepareStatement("INSERT `player_data` values(?,0,0,0,0,0)");
			newPlayer.setString(1, player);
			newPlayer.execute();
			newPlayer.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void putPlayerToLang(String player){
		try{
			PreparedStatement newPlayer = connection.prepareStatement("INSERT `language` values(?,?)");
			newPlayer.setString(1, player);
			newPlayer.setString(2, "DEF");
			newPlayer.execute();
			newPlayer.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void putPlayerToCosmeticData(String player){
		try{
			PreparedStatement newPlayer = connection
					.prepareStatement("INSERT `cosmetic_data` values(?,0,0,0)");
			newPlayer.setString(1, player);
			newPlayer.execute();
			newPlayer.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void putPlayerToPunishments(String player){
		try{
			PreparedStatement newPlayer = connection.prepareStatement("INSERT `punishments` values(?,0,0,0)");
			newPlayer.setString(1, Bukkit.getOfflinePlayer(player).getName());
			newPlayer.execute();
			newPlayer.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
