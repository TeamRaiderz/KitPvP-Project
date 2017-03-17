package kitpvp.cosmetics;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import kitpvp.Main;
import kitpvp.MySQL.MySQLManager;

public class CosmeticManager {

	private Connection connection = Main.getMySQLManager().getConnection();
	
	 HashMap<String, Integer> tokens = new HashMap<String, Integer>();
	 HashMap<String, Integer> chests = new HashMap<String, Integer>();
	
		public void getTokensDB(String player){
			
			BukkitRunnable r = new BukkitRunnable() {
				   @Override
				   public void run() {
					   
					  
						try {
							MySQLManager m = new MySQLManager();
							if(m.getConnection().isClosed()){
								m.openConnection();
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						try {
							
							if(Main.getMySQLManager().playerDataContainsPlayer(player)){

								PreparedStatement sql = connection.prepareStatement("SELECT tokens FROM `player_data` WHERE player = ?;");
								sql.setString(1, player);
								
								ResultSet result = sql.executeQuery();
								
								while(result.next()){
									int kill = result.getInt("tokens");
									tokens.put(player, kill);
								}
								
								
							}
							else{
								PreparedStatement newPlayer = connection.prepareStatement("INSERT `player_data` values(?,0,0,0,0,0)");
								newPlayer.setString(1, player);
								newPlayer.execute();
								newPlayer.close();
								tokens.put(player, 0);
							}
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					   
				   }
				};

				r.runTaskAsynchronously(Main.getInstance());
		}
		
		public int getTokens(String p){
			if(tokens.containsKey(p)){
				return tokens.get(p);
			}
			return 0;
		}
	
	public void setTokens(String player, int tokens){
		
		Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Main.getInstance(), new Runnable(){
			@Override
			public void run() {

				try {
					MySQLManager m = new MySQLManager();
					if(m.getConnection().isClosed()){
						m.openConnection();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				try {
					
					if(Main.getMySQLManager().playerDataContainsPlayer(player)){
						
						PreparedStatement sql = connection.prepareStatement("SELECT tokens FROM `player_data` WHERE player = ?;");
						sql.setString(1, player);
						
						ResultSet result = sql.executeQuery();
						result.next();
						
						PreparedStatement updatetokens = connection.prepareStatement("UPDATE `player_data` SET tokens=? WHERE player = ?;");
						updatetokens.setInt(1, tokens);
						updatetokens.setString(2, player);
						updatetokens.executeUpdate();
					
						updatetokens.close();
						sql.close();
						result.close();
						
					}
					else{
						PreparedStatement newPlayer = connection.prepareStatement("INSERT `player_data` values(?,0,0,0,0,0)");
						newPlayer.setString(1, player);
						newPlayer.execute();
						newPlayer.close();
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
			},1);
	}
	
	public void addTokens(String player, int tokens){
		
		Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Main.getInstance(), new Runnable() {
			@Override
			public void run() {
				try {
					MySQLManager m = new MySQLManager();
					if(m.getConnection().isClosed()){
						m.openConnection();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				try {
					
					if(Main.getMySQLManager().playerDataContainsPlayer(player)){
						
						PreparedStatement sql = connection.prepareStatement("SELECT tokens FROM `player_data` WHERE player = ?;");
						sql.setString(1, player);
						
						ResultSet result = sql.executeQuery();
						result.next();
						
						int kill = result.getInt("tokens");
						
						PreparedStatement updatetokens = connection.prepareStatement("UPDATE `player_data` SET tokens=? WHERE player = ?;");
						updatetokens.setInt(1, kill + tokens);
						updatetokens.setString(2, player);
						updatetokens.executeUpdate();
						
						updatetokens.close();
						sql.close();
						result.close();
					}
					else{
						PreparedStatement newPlayer = connection.prepareStatement("INSERT `player_data` values(?,0,0,0,0,0)");
						newPlayer.setString(1, player);
						newPlayer.execute();
						newPlayer.close();
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}, 1);
	}
	
	public void getChestsDB(String player) {

		BukkitRunnable r = new BukkitRunnable() {
			@Override
			public void run() {

				try {
					MySQLManager m = new MySQLManager();
					if (m.getConnection().isClosed()) {
						m.openConnection();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				try {

					if (Main.getMySQLManager().playerDataContainsPlayer(player)) {

						PreparedStatement sql = connection
								.prepareStatement("SELECT chests FROM `player_data` WHERE player = ?;");
						sql.setString(1, player);

						ResultSet result = sql.executeQuery();

						while (result.next()) {
							int kill = result.getInt("chests");
							chests.put(player, kill);
						}

					} else {
						PreparedStatement newPlayer = connection
								.prepareStatement("INSERT `player_data` values(?,0,0,0,0,0)");
						newPlayer.setString(1, player);
						newPlayer.execute();
						newPlayer.close();
						chests.put(player, 0);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		};

		r.runTaskAsynchronously(Main.getInstance());
	}

	public int getChests(String p) {
		if (chests.containsKey(p)) {
			return chests.get(p);
		}
		return 0;
	}

	public void setChests(String player, int chests) {

		Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Main.getInstance(), new Runnable() {
			@Override
			public void run() {

				try {
					MySQLManager m = new MySQLManager();
					if (m.getConnection().isClosed()) {
						m.openConnection();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				try {

					if (Main.getMySQLManager().playerDataContainsPlayer(player)) {

						PreparedStatement sql = connection
								.prepareStatement("SELECT chests FROM `player_data` WHERE player = ?;");
						sql.setString(1, player);

						ResultSet result = sql.executeQuery();
						result.next();

						PreparedStatement updatechests = connection
								.prepareStatement("UPDATE `player_data` SET chests=? WHERE player = ?;");
						updatechests.setInt(1, chests);
						updatechests.setString(2, player);
						updatechests.executeUpdate();

						updatechests.close();
						sql.close();
						result.close();

					} else {
						PreparedStatement newPlayer = connection
								.prepareStatement("INSERT `player_data` values(?,0,0,0,0,0)");
						newPlayer.setString(1, player);
						newPlayer.execute();
						newPlayer.close();
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 1);
	}

	public void addChests(String player, int chests) {

		Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Main.getInstance(), new Runnable() {
			@Override
			public void run() {
				try {
					MySQLManager m = new MySQLManager();
					if (m.getConnection().isClosed()) {
						m.openConnection();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				try {

					if (Main.getMySQLManager().playerDataContainsPlayer(player)) {

						PreparedStatement sql = connection
								.prepareStatement("SELECT chests FROM `player_data` WHERE player = ?;");
						sql.setString(1, player);

						ResultSet result = sql.executeQuery();
						result.next();

						int kill = result.getInt("chests");

						PreparedStatement updatechests = connection
								.prepareStatement("UPDATE `player_data` SET chests=? WHERE player = ?;");
						updatechests.setInt(1, kill + chests);
						updatechests.setString(2, player);
						updatechests.executeUpdate();

						updatechests.close();
						sql.close();
						result.close();
					} else {
						PreparedStatement newPlayer = connection
								.prepareStatement("INSERT `player_data` values(?,0,0,0,0,0)");
						newPlayer.setString(1, player);
						newPlayer.execute();
						newPlayer.close();
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 1);
}
	
}
