package kitpvp.MySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;

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
	
	public synchronized void openConnection(){
		try{
			connection = DriverManager.getConnection("jdbc:mysql://mysql-pr01.:3306/mc_11138", "mc_11138", "29444c33d1");
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
	
	public synchronized boolean playerDataContainsPlayer(Player p){
		try{
			PreparedStatement sql = connection.prepareStatement("SELECT * FROM `player_data` WHERE player=?;");
			sql.setString(1, p.getName());
			
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
	
}
