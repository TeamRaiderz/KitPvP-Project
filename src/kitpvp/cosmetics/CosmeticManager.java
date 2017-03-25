package kitpvp.cosmetics;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import kitpvp.Language;
import kitpvp.Main;
import kitpvp.MySQL.MySQLManager;

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

				PreparedStatement sql = connection
						.prepareStatement("SELECT tokens FROM `cosmetic_data` WHERE player = ?;");
				sql.setString(1, player);

				ResultSet result = sql.executeQuery();

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

		new BukkitRunnable() {

			@Override
			public void run() {
				
				int i = 0;
				i += 1;
				
				if(i > 10){ cancel(); }
				getTokensDB(p);
			}

		}.runTaskAsynchronously(Main.getInstance());

		if (tokens.containsKey(p)) {
			return tokens.get(p);
		}
		return 0;
	}

	public void setTokens(String player, int tokens) {

		Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Main.getInstance(), new Runnable() {
			@Override
			public void run() {

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

						PreparedStatement sql = connection
								.prepareStatement("SELECT tokens FROM `cosmetic_data` WHERE player = ?;");
						sql.setString(1, player);

						ResultSet result = sql.executeQuery();
						result.next();

						PreparedStatement updatetokens = connection
								.prepareStatement("UPDATE `cosmetic_data` SET tokens=? WHERE player = ?;");
						updatetokens.setInt(1, tokens);
						updatetokens.setString(2, player);
						updatetokens.executeUpdate();

						updatetokens.close();
						sql.close();
						result.close();
						
						getTokensDB(player);

					} else {
						Main.getMySQLManager().putPlayerToCosmeticData(player);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 1);
	}

	public void addTokens(String player, int tokens) {

		Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Main.getInstance(), new Runnable() {
			@Override
			public void run() {
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

						PreparedStatement sql = connection
								.prepareStatement("SELECT tokens FROM `cosmetic_data` WHERE player = ?;");
						sql.setString(1, player);

						ResultSet result = sql.executeQuery();
						result.next();

						int kill = result.getInt("tokens");

						PreparedStatement updatetokens = connection
								.prepareStatement("UPDATE `cosmetic_data` SET tokens=? WHERE player = ?;");
						updatetokens.setInt(1, kill + tokens);
						updatetokens.setString(2, player);
						updatetokens.executeUpdate();

						updatetokens.close();
						sql.close();
						result.close();
						
						getTokensDB(player);
					} else {
						Main.getMySQLManager().putPlayerToCosmeticData(player);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 1);
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

				PreparedStatement sql = connection
						.prepareStatement("SELECT chests FROM `cosmetic_data` WHERE player = ?;");
				sql.setString(1, player);

				ResultSet result = sql.executeQuery();

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

		new BukkitRunnable() {

			@Override
			public void run() {
				
				int i = 0;
				i += 1;
				
				if(i > 10){ cancel(); }
				getChestsDB(p);
			}

		}.runTaskAsynchronously(Main.getInstance());

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
					if (m.getConnection().isClosed() || m.getConnection() == null) {
						m.openConnection();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				try {

					if (Main.getMySQLManager().cosmeticContainsPlayer(player)) {

						PreparedStatement sql = connection
								.prepareStatement("SELECT chests FROM `cosmetic_data` WHERE player = ?;");
						sql.setString(1, player);

						ResultSet result = sql.executeQuery();
						result.next();

						PreparedStatement updatechests = connection
								.prepareStatement("UPDATE `cosmetic_data` SET chests=? WHERE player = ?;");
						updatechests.setInt(1, chests);
						updatechests.setString(2, player);
						updatechests.executeUpdate();

						updatechests.close();
						sql.close();
						result.close();

						getChestsDB(player);
						
					} else {
						Main.getMySQLManager().putPlayerToCosmeticData(player);
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
					if (m.getConnection().isClosed() || m.getConnection() == null) {
						m.openConnection();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				try {

					if (Main.getMySQLManager().cosmeticContainsPlayer(player)) {

						PreparedStatement sql = connection
								.prepareStatement("SELECT chests FROM `cosmetic_data` WHERE player = ?;");
						sql.setString(1, player);

						ResultSet result = sql.executeQuery();
						result.next();

						int kill = result.getInt("chests");

						PreparedStatement updatechests = connection
								.prepareStatement("UPDATE `cosmetic_data` SET chests=? WHERE player = ?;");
						updatechests.setInt(1, kill + chests);
						updatechests.setString(2, player);
						updatechests.executeUpdate();

						updatechests.close();
						sql.close();
						result.close();
						
						getChestsDB(player);
					} else {
						Main.getMySQLManager().putPlayerToCosmeticData(player);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 1);
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

						PreparedStatement sql = connection
								.prepareStatement("SELECT boosters FROM `cosmetic_data` WHERE player = ?;");
						sql.setString(1, player);

						ResultSet result = sql.executeQuery();

						while (result.next()) {
							int kill = result.getInt("boosters");
							boosters.put(player, kill);
							System.out.println(1);
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
		System.out.println("Checking if player " + p + " is in 'boosters' HashMap... (0)");
		if (boosters.containsKey(p)) {
			String isInHashMap =  boosters.containsKey(p) ? "true" : "false";
			System.out.println("Succesfully checked if " + p + " is in 'boosters' HashMap, and got the result: " + isInHashMap + " (1)");
			return boosters.get(p);
		}
		else if (!(boosters.containsKey(p))){
			
			System.out.println("Trying again to check if player " + p + " is in 'boosters' HashMap... (2)");
			
			getBoostersDB(p);
			System.out.println("Succesfully got the boosters of " + p + " (3)");
			
			String isInHashMap =  boosters.containsKey(p) ? "true" : "false";
			System.out.println("Succesfully checked if " + p + " is in 'boosters' HashMap, and got the result: " + isInHashMap + " (4)");
			System.out.println("Returning to value 0... (5)");
			return 0;
		}
		else{
			String isInHashMap =  boosters.containsKey(p) ? "true" : "false";
			System.out.println("Succesfully checked if " + p + " is in 'boosters' HashMap, and got the result: " + isInHashMap + " (6)");
			return 0;
		}
	}

	public void setBoosters(String player, int boosters) {

		Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Main.getInstance(), new Runnable() {
			@Override
			public void run() {

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

						PreparedStatement sql = connection
								.prepareStatement("SELECT boosters FROM `cosmetic_data` WHERE player = ?;");
						sql.setString(1, player);

						ResultSet result = sql.executeQuery();
						result.next();

						PreparedStatement updateboosters = connection
								.prepareStatement("UPDATE `cosmetic_data` SET boosters=? WHERE player = ?;");
						updateboosters.setInt(1, boosters);
						updateboosters.setString(2, player);
						updateboosters.executeUpdate();

						updateboosters.close();
						sql.close();
						result.close();
						
					} else {
						Main.getMySQLManager().putPlayerToCosmeticData(player);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 1);
	}

	public void addBoosters(String player, int boosters) {

		Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Main.getInstance(), new Runnable() {
			@Override
			public void run() {
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

						PreparedStatement sql = connection
								.prepareStatement("SELECT boosters FROM `cosmetic_data` WHERE player = ?;");
						sql.setString(1, player);

						ResultSet result = sql.executeQuery();
						result.next();

						int kill = result.getInt("boosters");

						PreparedStatement updateboosters = connection
								.prepareStatement("UPDATE `cosmetic_data` SET boosters=? WHERE player = ?;");
						updateboosters.setInt(1, kill + boosters);
						updateboosters.setString(2, player);
						updateboosters.executeUpdate();

						updateboosters.close();
						sql.close();
						result.close();
					} else {
						Main.getMySQLManager().putPlayerToCosmeticData(player);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 1);
	}
	
}
