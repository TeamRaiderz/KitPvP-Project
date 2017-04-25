package kitpvp.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import kitpvp.Language;
import kitpvp.Main;
import kitpvp.MySQL.MySQLManager;
import kitpvp.commands.PrefixCommand.NameColor;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.NBTTagCompound;

public class KitAPI {
	
	private int onlineStaff = 0;
	private List<String> staffMembers = new ArrayList<String>();
	private Connection connection = new MySQLManager().getConnection();
	private HashMap<String, Integer> killStreak = new HashMap<String, Integer>();
	
	public static HashMap<String, Integer> kills = new HashMap<String, Integer>();
	public static HashMap<String, Integer> deaths = new HashMap<String, Integer>();
	public static  HashMap<String, Integer> balance = new HashMap<String, Integer>();
	public static  HashMap<String, Integer> level = new HashMap<String, Integer>();
	public static  HashMap<String, Integer> xp = new HashMap<String, Integer>();
	
	public boolean isBoosterInUse() {
		return Main.getConfigFile().getBoolean("Booster.inUse");
	}
	public void getKillsDB(String player){
		MySQLManager m = new MySQLManager();
		try {
			if (m.getConnection().isClosed() || m.getConnection() == null) {
				m.openConnection();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {

			if (m.playerDataContainsPlayer(player)) {

				PreparedStatement sql = connection
						.prepareStatement("SELECT kills FROM `player_data` WHERE player = ?;");
				sql.setString(1, player);

				ResultSet result = sql.executeQuery();

				while (result.next()) {
					int kill = result.getInt("kills");
					kills.put(player, kill);
				}
			} else {
				m.putPlayerToPlayerData(player);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getKills(String p){
		
		new BukkitRunnable(){

			@Override
			public void run() {
				getKillsDB(p);
			}
			
		}.runTaskAsynchronously(Main.getInstance());
		
		if(kills.containsKey(p)){
			return kills.get(p);
		}
		return 0;
	}
	
	public void setKills(String player, int kills){
		
		Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Main.getInstance(), new Runnable(){
			@Override
			public void run() {
				MySQLManager m = new MySQLManager();
				try {
					if(m.getConnection().isClosed() || m.getConnection() == null){
						m.openConnection();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				try {
					
					if(m.playerDataContainsPlayer(player)){
						
						PreparedStatement sql = connection.prepareStatement("SELECT kills FROM `player_data` WHERE player = ?;");
						sql.setString(1, player);
						
						ResultSet result = sql.executeQuery();
						result.next();
						
						PreparedStatement updateKills = connection.prepareStatement("UPDATE `player_data` SET kills=? WHERE player = ?;");
						updateKills.setInt(1, kills);
						updateKills.setString(2, player);
						updateKills.executeUpdate();
					
						updateKills.close();
						sql.close();
						result.close();
						
						getKillsDB(player);
						
					}
					else{
						m.putPlayerToPlayerData(player);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
			},1);
	}
	
	public void addKills(String player, int kills){
		
		Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Main.getInstance(), new Runnable() {
			@Override
			public void run() {
				MySQLManager m = new MySQLManager();
				try {
					if(m.getConnection().isClosed() || m.getConnection() == null){
						m.openConnection();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				try {
					
					if(m.playerDataContainsPlayer(player)){
						
						PreparedStatement sql = connection.prepareStatement("SELECT kills FROM `player_data` WHERE player = ?;");
						sql.setString(1, player);
						
						ResultSet result = sql.executeQuery();
						result.next();
						
						int kill = result.getInt("kills");
						
						PreparedStatement updateKills = connection.prepareStatement("UPDATE `player_data` SET kills=? WHERE player = ?;");
						updateKills.setInt(1, kill + kills);
						updateKills.setString(2, player);
						updateKills.executeUpdate();
						
						updateKills.close();
						sql.close();
						result.close();
						
						getKillsDB(player);
					}
					else{
						m.putPlayerToPlayerData(player);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}, 1);
	}
	
	public void getDeathsDB(String player){
		
		BukkitRunnable r = new BukkitRunnable() {
			   @Override
			   public void run() {
					MySQLManager m = new MySQLManager();
					try {
						if(m.getConnection().isClosed() || m.getConnection() == null){
							m.openConnection();
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					try {
						
						if(m.playerDataContainsPlayer(player)){

							PreparedStatement sql = connection.prepareStatement("SELECT deaths FROM `player_data` WHERE player = ?;");
							sql.setString(1, player);
							
							ResultSet result = sql.executeQuery();
							
							while(result.next()){
								int kill = result.getInt("deaths");
								deaths.put(player, kill);
							}
						}
						else{
							m.putPlayerToPlayerData(player);
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
			   }
			};

			r.runTaskAsynchronously(Main.getInstance());
	}
	
	public int getDeaths(String p){
		
		new BukkitRunnable(){

			@Override
			public void run() {
				
				int i = 0;
				i += 1;
				
				if(i > 10){ cancel(); }
				
				getDeathsDB(p);
			}
			
		}.runTaskAsynchronously(Main.getInstance());
		
		if(deaths.containsKey(p)){
			return deaths.get(p);
		}
		return 0;
	}
	
	public void setDeaths(String player, int deaths){
		
		Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Main.getInstance(), new Runnable(){
			@Override
			public void run() {
				MySQLManager m = new MySQLManager();
				try {
					if(m.getConnection().isClosed() || m.getConnection() == null){
						m.openConnection();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				try {
					
					if(m.playerDataContainsPlayer(player)){
						
						PreparedStatement sql = connection.prepareStatement("SELECT deaths FROM `player_data` WHERE player = ?;");
						sql.setString(1, player);
						
						ResultSet result = sql.executeQuery();
						result.next();
						
						PreparedStatement updatedeaths = connection.prepareStatement("UPDATE `player_data` SET deaths=? WHERE player = ?;");
						updatedeaths.setInt(1, deaths);
						updatedeaths.setString(2, player);
						updatedeaths.executeUpdate();
					
						updatedeaths.close();
						sql.close();
						result.close();
						
						
						
					}
					else{
						m.putPlayerToPlayerData(player);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
			},1);
		
	}
	
	public void addDeaths(String player, int deaths){

		Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Main.getInstance(), new Runnable(){
			@Override
			public void run() {
				MySQLManager m = new MySQLManager();
				try {
					if(m.getConnection().isClosed() || m.getConnection() == null){
						m.openConnection();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				try {
					
					if(m.playerDataContainsPlayer(player)){
						
						PreparedStatement sql = connection.prepareStatement("SELECT deaths FROM `player_data` WHERE player = ?;");
						sql.setString(1, player);
						
						ResultSet result = sql.executeQuery();
						result.next();
						
						int kill = result.getInt("deaths");
						
						PreparedStatement updatedeaths = connection.prepareStatement("UPDATE `player_data` SET deaths=? WHERE player = ?;");
						updatedeaths.setInt(1, kill + deaths);
						updatedeaths.setString(2, player);
						updatedeaths.executeUpdate();
						
						updatedeaths.close();
						sql.close();
						result.close();
					}
					else{
						m.putPlayerToPlayerData(player);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
			},1);
	}
	
	public void getBalanceDB(String player){
		
		BukkitRunnable r = new BukkitRunnable() {
			   @Override
			   public void run() {
					MySQLManager m = new MySQLManager();
					
					try {
						if(m.getConnection().isClosed() || m.getConnection() == null){
							m.openConnection();
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					try {
						
						if(m.playerDataContainsPlayer(player)){

							PreparedStatement sql = connection.prepareStatement("SELECT balance FROM `player_data` WHERE player = ?;");
							sql.setString(1, player);
							
							ResultSet result = sql.executeQuery();
							
							while(result.next()){
								int kill = result.getInt("balance");
								
								balance.put(player, kill);
							}
						}
						else{
							m.putPlayerToPlayerData(player);
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
			   }
			};

			r.runTaskAsynchronously(Main.getInstance());
	}
	
	public int getBalance(String p){
		
		new BukkitRunnable(){

			@Override
			public void run() {
				
				int i = 0;
				i += 1;
				
				if(i > 10){ cancel(); }
				
				getBalanceDB(p);
			}
			
		}.runTaskAsynchronously(Main.getInstance());
		
		
		if(balance.containsKey(p)){
			return balance.get(p);
		}
		return 0;
	}
	
	public void setBalance(String player, int money){
		
		Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Main.getInstance(), new Runnable(){
			@Override
			public void run() {
				MySQLManager m = new MySQLManager();
				try {
					if(m.getConnection().isClosed() || m.getConnection() == null){
						m.openConnection();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				try {
					
					if(m.playerDataContainsPlayer(player)){
						
						PreparedStatement sql = connection.prepareStatement("SELECT balance FROM `player_data` WHERE player = ?;");
						sql.setString(1, player);
						
						ResultSet result = sql.executeQuery();
						result.next();
						
						PreparedStatement updateKills = connection.prepareStatement("UPDATE `player_data` SET balance=? WHERE player = ?;");
						updateKills.setInt(1, money);
						updateKills.setString(2, player);
						updateKills.executeUpdate();
					
						updateKills.close();
						sql.close();
						result.close();
						
					}
					else{
						m.putPlayerToPlayerData(player);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			},1);
		
	}
	
	public void addBalance(String player, int money) {
		
		Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Main.getInstance(), new Runnable(){
			@Override
			public void run() {
				MySQLManager m = new MySQLManager();
				try {
					if(m.getConnection().isClosed() || m.getConnection() == null){
						m.openConnection();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				try {

					if(m.playerDataContainsPlayer(player)){

						PreparedStatement sql = connection
								.prepareStatement("SELECT balance FROM `player_data` WHERE player = ?;");
						sql.setString(1, player);

						ResultSet result = sql.executeQuery();
						result.next();

						int kill = result.getInt("balance");

						PreparedStatement updateKills = connection
								.prepareStatement("UPDATE `player_data` SET balance=? WHERE player = ?;");
						updateKills.setInt(1, kill + money);
						updateKills.setString(2, player);
						updateKills.executeUpdate();

						updateKills.close();
						sql.close();
						result.close();
					} else {
						m.putPlayerToPlayerData(player);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			},1);

	}
	
	public void getXpDB(String player){
		
		BukkitRunnable r = new BukkitRunnable() {
			   @Override
			   public void run() {
					MySQLManager m = new MySQLManager();
					try {
						if(m.getConnection().isClosed() || m.getConnection() == null){
							m.openConnection();
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					try {
						
						if(m.playerDataContainsPlayer(player)){

							PreparedStatement sql = connection.prepareStatement("SELECT xp FROM `player_data` WHERE player = ?;");
							sql.setString(1, player);
							
							ResultSet result = sql.executeQuery();
							
							while(result.next()){
								int kill = result.getInt("xp");
								
								xp.put(player, kill);
							}
							
						}
						else{
							m.putPlayerToPlayerData(player);
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
			   }
			};

			r.runTaskAsynchronously(Main.getInstance());
	}
	
	public int getXp(String p){
		
		new BukkitRunnable(){

			@Override
			public void run() {
				
				getXpDB(p);
			}
			
		}.runTaskAsynchronously(Main.getInstance());
		
		
		if(xp.containsKey(p)){
			return xp.get(p);
		}
		return 0;
	}
	
	public void setXp(String player, int money){
		
		Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Main.getInstance(), new Runnable(){
			@Override
			public void run() {
				MySQLManager m = new MySQLManager();
				try {
					if(m.getConnection().isClosed() || m.getConnection() == null){
						m.openConnection();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				try {
					
					if(m.playerDataContainsPlayer(player)){
						
						if(money < getXPToNextLVL(player)){

							PreparedStatement sql = connection.prepareStatement("SELECT xp FROM `player_data` WHERE player = ?;");
							sql.setString(1, player);
							
							ResultSet result = sql.executeQuery();
							result.next();
							
							PreparedStatement updateKills = connection.prepareStatement("UPDATE `player_data` SET xp=? WHERE player = ?;");
							updateKills.setInt(1, money);
							updateKills.setString(2, player);
							updateKills.executeUpdate();
							
							updateKills.close();
							sql.close();
							result.close();
							
						}
						else {
							if (money < getXPToNextLVL(player)) {
								return;
							}
							
							if (getXp(player) < 0) {

								PreparedStatement sql = connection
										.prepareStatement("SELECT xp FROM `player_data` WHERE player = ?;");
								sql.setString(1, player);

								ResultSet result = sql.executeQuery();
								result.next();

								PreparedStatement updateKills = connection
										.prepareStatement("UPDATE `player_data` SET xp=? WHERE player = ?;");
								updateKills.setInt(1, 0);
								updateKills.setString(2, player);
								updateKills.executeUpdate();

								updateKills.close();
								sql.close();
								result.close();
							} 
							
							while (getXp(player) >= getXp(player) * 100) {
								try {

									PreparedStatement sql = connection
											.prepareStatement("SELECT xp FROM `player_data` WHERE player = ?;");
									sql.setString(1, player);

									ResultSet result = sql.executeQuery();
									result.next();

									PreparedStatement updateKills = connection
											.prepareStatement("UPDATE `player_data` SET xp=? WHERE player = ?;");
									if (money - (getLevel(player) * 100) < 0) {
										updateKills.setInt(1, 0);
									} else {
										updateKills.setInt(1, money - (getLevel(player) * 100));
									}
									updateKills.setString(2, player);
									updateKills.executeUpdate();

									updateKills.close();
									sql.close();
									result.close();

									addLevel(player, 1);
									levelUp(player, getLevel(player));

								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}

					}
					else{
						m.putPlayerToPlayerData(player);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			},1);
		
	}
	
	public void addXp(String player, int money) {
		
		Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Main.getInstance(), new Runnable(){
			@Override
			public void run() {
				MySQLManager m = new MySQLManager();
				try {
					if(m.getConnection().isClosed() || m.getConnection() == null){
						m.openConnection();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				try {

					if(m.playerDataContainsPlayer(player)){

						if(money < getXPToNextLVL(player)){
							
							PreparedStatement sql = connection
									.prepareStatement("SELECT xp FROM `player_data` WHERE player = ?;");
							sql.setString(1, player);

							ResultSet result = sql.executeQuery();
							result.next();

							int kill = result.getInt("xp");

							PreparedStatement updateKills = connection
									.prepareStatement("UPDATE `player_data` SET xp=? WHERE player = ?;");
							updateKills.setInt(1, kill + money);
							updateKills.setString(2, player);
							
							updateKills.executeUpdate();
							
							updateKills.close();
							sql.close();
							result.close();
							
						} else if (money >= getXPToNextLVL(player)) {

							if (money == getXPToNextLVL(player)) {
								try {

									PreparedStatement sql = connection
											.prepareStatement("SELECT xp FROM `player_data` WHERE player = ?;");
									sql.setString(1, player);

									ResultSet result = sql.executeQuery();
									result.next();
									PreparedStatement updateKills = connection
											.prepareStatement("UPDATE `player_data` SET xp=? WHERE player = ?;");
									if (money - (getLevel(player) * 100) < 0) {
										updateKills.setInt(1, 0);
									} else {
										updateKills.setInt(1, money - (getLevel(player) * 100));
									}
									updateKills.setString(2, player);
									updateKills.executeUpdate();

									updateKills.close();
									sql.close();
									result.close();

									addLevel(player, 1);
									levelUp(player, getLevel(player));

								} catch (Exception e) {
									e.printStackTrace();
								}
							} else {

								xp.put(player, money);
								
								new BukkitRunnable() {

									@Override
									public void run() {

										if (!(xp.containsKey(player)) || !(level.containsKey(player))) {
											cancel();
											return;
										}
										
										if (xp.get(player) - (level.get(player) * 100) < 0) {
											xp.put(player, 0);
											level.put(player, level.get(player) + 1);
											levelUp(player, level.get(player));
											setlevel(player, level.get(player));
											try {
												PreparedStatement sql = connection.prepareStatement(
														"SELECT xp FROM `player_data` WHERE player = ?;");
												sql.setString(1, player);

												ResultSet result = sql.executeQuery();
												result.next();
												PreparedStatement updateKills = connection.prepareStatement(
														"UPDATE `player_data` SET xp=? WHERE player = ?;");
												updateKills.setInt(1, xp.get(player));

												updateKills.setString(2, player);
												updateKills.executeUpdate();

												updateKills.close();
												sql.close();
												result.close();

											} catch (Exception e) {
												e.printStackTrace();
											}
											cancel();
											return;
										} else if (xp.get(player) - (level.get(player) * 100) > 0){
											
											xp.put(player, xp.get(player) - (level.get(player) * 100));
											level.put(player, level.get(player) + 1);
											levelUp(player, level.get(player));
											setlevel(player, level.get(player));
											
											try {
												PreparedStatement sql = connection.prepareStatement(
														"SELECT xp FROM `player_data` WHERE player = ?;");
												sql.setString(1, player);

												ResultSet result = sql.executeQuery();
												result.next();
												PreparedStatement updateKills = connection.prepareStatement(
														"UPDATE `player_data` SET xp=? WHERE player = ?;");
												updateKills.setInt(1, xp.get(player));

												updateKills.setString(2, player);
												updateKills.executeUpdate();

												updateKills.close();
												sql.close();
												result.close();

											} catch (Exception e) {
												e.printStackTrace();
											}
										}
										else if (xp.get(player) == (level.get(player) * 100)){
											xp.put(player, 0);
											level.put(player, level.get(player) + 1);
											levelUp(player, level.get(player));
											setlevel(player, level.get(player));
											
											try {
												PreparedStatement sql = connection.prepareStatement(
														"SELECT xp FROM `player_data` WHERE player = ?;");
												sql.setString(1, player);

												ResultSet result = sql.executeQuery();
												result.next();
												PreparedStatement updateKills = connection.prepareStatement(
														"UPDATE `player_data` SET xp=? WHERE player = ?;");
												updateKills.setInt(1, xp.get(player));

												updateKills.setString(2, player);
												updateKills.executeUpdate();

												updateKills.close();
												sql.close();
												result.close();

											} catch (Exception e) {
												e.printStackTrace();
											}
										}
										else{
											cancel();
											return;
										}
									}

								}.runTaskTimerAsynchronously(Main.getInstance(), 0, 20);

							}
						}

					} else {
						m.putPlayerToPlayerData(player);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			},1);
	}
	
	public void getLevelDB(String player){
		
		BukkitRunnable r = new BukkitRunnable() {
			   @Override
			   public void run() {
					MySQLManager m = new MySQLManager();
					try {
						if(m.getConnection().isClosed() || m.getConnection() == null){
							m.openConnection();
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					try {
						
						if(m.playerDataContainsPlayer(player)){

							PreparedStatement sql = connection.prepareStatement("SELECT levels FROM `player_data` WHERE player = ?;");
							sql.setString(1, player);
							
							ResultSet result = sql.executeQuery();
							
							while(result.next()){
								level.put(player, result.getInt("levels"));
							}
							
						}
						else{
							m.putPlayerToPlayerData(player);
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
			   }
			};

			r.runTaskAsynchronously(Main.getInstance());
	}
	
	public int getLevel(String p){
		
		new BukkitRunnable(){

			@Override
			public void run() {
				
				int i = 0;
				i += 1;
				
				if(i > 10){ cancel(); }
				
				getLevelDB(p);
			}
			
		}.runTaskAsynchronously(Main.getInstance());
		
		
		if(level.containsKey(p)){
			return level.get(p);
		}
		return 0;
	}
	
	public void setlevel(String player, int money){
		
		Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Main.getInstance(), new Runnable(){
			@Override
			public void run() {
				MySQLManager m = new MySQLManager();
				try {
					if(m.getConnection().isClosed() || m.getConnection() == null){
						m.openConnection();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				try {
					
					if(m.playerDataContainsPlayer(player)){
						
						if(money >= 50){
							
							PreparedStatement sql = connection.prepareStatement("SELECT levels FROM `player_data` WHERE player = ?;");
							sql.setString(1, player);
							
							ResultSet result = sql.executeQuery();
							result.next();
							
							PreparedStatement updateKills = connection.prepareStatement("UPDATE `player_data` SET levels=? WHERE player = ?;");
							updateKills.setInt(1, 50);
							updateKills.setString(2, player);
							
							updateKills.executeUpdate();
						
							updateKills.close();
							sql.close();
							result.close();
							
						}else{
							PreparedStatement sql = connection.prepareStatement("SELECT levels FROM `player_data` WHERE player = ?;");
							sql.setString(1, player);
							
							ResultSet result = sql.executeQuery();
							result.next();
							
							PreparedStatement updateKills = connection.prepareStatement("UPDATE `player_data` SET levels=? WHERE player = ?;");
							updateKills.setInt(1, money);
							updateKills.setString(2, player);
							
							updateKills.executeUpdate();
							
							updateKills.close();
							sql.close();
							result.close();
						}
						
					}
					else{
						m.putPlayerToPlayerData(player);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			},1);
		
		
		
	}
	
	public void addLevel(String player, int money) {
		Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Main.getInstance(), new Runnable(){
			@Override
			public void run() {
				MySQLManager m = new MySQLManager();
				try {
					if(m.getConnection().isClosed() || m.getConnection() == null){
						m.openConnection();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				try {

					if(m.playerDataContainsPlayer(player)){
						
						if(getLevel(player) + money >= 50){
							

							PreparedStatement sql = connection
									.prepareStatement("SELECT levels FROM `player_data` WHERE player = ?;");
							sql.setString(1, player);

							ResultSet result = sql.executeQuery();
							result.next();
							
							PreparedStatement updateKills = connection
									.prepareStatement("UPDATE `player_data` SET levels=? WHERE player = ?;");
							updateKills.setInt(1, 50);
							updateKills.setString(2, player);
							
							updateKills.executeUpdate();
							
							updateKills.close();
							sql.close();
							result.close();
							
						} else {

							PreparedStatement sql = connection
									.prepareStatement("SELECT levels FROM `player_data` WHERE player = ?;");
							sql.setString(1, player);

							ResultSet result = sql.executeQuery();
							result.next();

							int kill = result.getInt("levels");

							PreparedStatement updateKills = connection
									.prepareStatement("UPDATE `player_data` SET levels=? WHERE player = ?;");
							updateKills.setInt(1, kill + money);
							updateKills.setString(2, player);
							
							updateKills.executeUpdate();
							
							updateKills.close();
							sql.close();
							result.close();
						}
						
					} else {
						m.putPlayerToPlayerData(player);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			},1);
		

	}
	
	public Language getLanguage(String player){
		
		if(Main.getDataFile().getString(player).equalsIgnoreCase("ENG")){
			return Language.ENGLISH;
		}
		else if(Main.getDataFile().getString(player).equalsIgnoreCase("FIN")){
			return Language.FINNISH;
		}
		else if(Main.getDataFile().getString(player).equalsIgnoreCase("DEF")){
			return Language.DEFAULT;
		}
		return null;
	}
	
	public void setLanguage(String player, Language language){
		
		if (Main.getDataFile().get(player) == null) {
			System.out.println("That player is not in our database.");
			return;
		}
		
		switch (language) {
		case FINNISH:
			Main.getDataFile().set(player, "FIN");
			Main.saveDataFile();
			break;
		case ENGLISH:
			Main.getDataFile().set(player, "ENG");
			Main.saveDataFile();
			break;
		case DEFAULT:
			Main.getDataFile().set(player, "DEF");
			Main.saveDataFile();
			break;
		}
		
	}
	
	public void setNick(String player, NameColor color){
		String uuid = Bukkit.getOfflinePlayer(player).getUniqueId().toString();
		FileConfiguration file = Main.getDataFile();
		
		switch(color){
		case RED:
			file.set(uuid + ".nick", "&c" + player);
			Main.saveDataFile();
			break;
		case GREEN:
			file.set(uuid + ".nick", "&a" + player);
			Main.saveDataFile();
			break;
		case AQUA:
			file.set(uuid + ".nick", "&b" + player);
			Main.saveDataFile();
			break;
		case CYAN:
			file.set(uuid + ".nick", "&3" + player);
			Main.saveDataFile();
			break;
		case BLUE:
			file.set(uuid + ".nick", "&9" + player);
			Main.saveDataFile();
			break;
		case YELLOW:
			file.set(uuid + ".nick", "&e" + player);
			Main.saveDataFile();
			break;
		case WHITE:
			file.set(uuid + ".nick", "&f" + player);
			Main.saveDataFile();
			break;
		case GOLD:
			file.set(uuid + ".nick", "&6" + player);
			Main.saveDataFile();
			break;
		case PURPLE:
			file.set(uuid + ".nick", "&5" + player);
			Main.saveDataFile();
			break;
		case PINK:
			file.set(uuid + ".nick", "&d" + player);
			Main.saveDataFile();
			break;
		case DEFAULT:
			file.set(uuid + ".nick", "&7" + player);
			Main.saveDataFile();
			break;
		}
		
	}
	
	public String getNick(String player){
		OfflinePlayer p = Bukkit.getOfflinePlayer(player);
		String uuid = p.getUniqueId().toString();
		return ChatColor.translateAlternateColorCodes('&', Main.getDataFile().getString(uuid + ".nick"));
	}
	
	public void createItem(Inventory inv, int pos, Material mat, int amount, String displayName, List<String> lore){
		ItemStack item = new ItemStack(mat);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
		meta.setLore(lore);
		item.setAmount(amount);
		item.setItemMeta(meta);
		inv.setItem(pos, item);
	}
	
	public void createWoolItem(Inventory inv, int pos, DyeColor color, String displayName, List<String> lore){
		ItemStack wool = new ItemStack(Material.WOOL, 1, color.getData());
		ItemMeta meta = wool.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
		meta.setLore(lore);
		wool.setItemMeta(meta);
		inv.setItem(pos, wool);
	}
	
	public void Silent(Entity YourEntity) {
		net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity) YourEntity).getHandle();
		NBTTagCompound tag = nmsEntity.getNBTTag();
		if (tag == null) {
			tag = new NBTTagCompound();
		}

		nmsEntity.c(tag);
		tag.setInt("Silent", 1);
		nmsEntity.f(tag);
	}
	
	public void sendPlayerStats(CommandSender receiver, String target){
		
		
		if(receiver.getName().equals(target)){
			
			int kills = getKills(receiver.getName());
			int deaths = getDeaths(receiver.getName());
			
			double KD = getKD(receiver.getName());
			int balance = getBalance(receiver.getName());
			int level = getLevel(receiver.getName());
			int xp = getXp(receiver.getName());
			
			if(Double.isInfinite(KD)) KD = 0.0D;
			if(Double.isNaN(KD)) KD = 0.0D;
			
			if(getLanguage(receiver.getName()) == Language.FINNISH){

				receiver.sendMessage("�7�m------�a�l Sinun tilastot �7�m------");
				receiver.sendMessage("�7Tapot: �a" + kills);
				receiver.sendMessage("�7Kuolemat: �a" + deaths);
				receiver.sendMessage("�7K/D: �a" + KD);
				receiver.sendMessage("�7Rahat: �a" + balance);
				receiver.sendMessage("�7Level: �a" + level + "/50");
				receiver.sendMessage("�7XP: �a" + xp + "/" + getLevel(target) * 100);
				receiver.sendMessage("�7Kieli: �a" + String.valueOf(getLanguage(target)));
				receiver.sendMessage("�7Peliaika: �a" + getPlayTime(target));
				receiver.sendMessage("�7�m---------------------------------------------");
				
			}
			else if (getLanguage(receiver.getName()) == Language.ENGLISH){
				receiver.sendMessage("�7�m------ �a�l Your stats �7�m------");
				receiver.sendMessage("�7Kills: �a" + kills);
				receiver.sendMessage("�7Deaths: �a" + deaths);
				receiver.sendMessage("�7K/D: �a" + KD);
				receiver.sendMessage("�7Balance: �a" + balance);
				receiver.sendMessage("�7Level: �a" + level + "/50");
				receiver.sendMessage("�7XP: �a" + xp + "/" + getLevel(target) * 100);
				receiver.sendMessage("�7Language: �a" + String.valueOf(getLanguage(target)));
				receiver.sendMessage("�7Playtime: �a" + getPlayTime(target)); 
				receiver.sendMessage("�7�m---------------------------------------------");
			}
		}
		else{
			
			OfflinePlayer offTarget = Bukkit.getOfflinePlayer(target);
			
			if(Main.getDataFile().getBoolean(offTarget.getUniqueId().toString() + ".privateAccount") && !receiver.hasPermission("privacy.bypass")){
				if(getLanguage(receiver.getName()) == Language.FINNISH){
					ChatUtils.sendMessageWithPrefix(receiver, "�7Tuolla pelaajalla on k�ytt�j� yksityisen�!");
					return;
				}
				else if(getLanguage(receiver.getName()) == Language.ENGLISH){
					ChatUtils.sendMessageWithPrefix(receiver, "�7That player's account is private!");
					return;
				}
			}
			
			int kills = getKills(offTarget.getName());
			int deaths = getDeaths(offTarget.getName());
			
			double KD = getKD(offTarget.getName());
			int balance = getBalance(offTarget.getName());
			int level = getLevel(offTarget.getName());
			int xp = getXp(offTarget.getName());
			
			if(Double.isInfinite(KD)) KD = 0.0D;
			if(Double.isNaN(KD)) KD = 0.0D;
			
			if(getLanguage(receiver.getName()) == Language.FINNISH){

				receiver.sendMessage("�7�m------�a�l Pelaajan " + offTarget.getName() + " tilastot �7�m------");
				receiver.sendMessage("�7Tapot: �a" + kills);
				receiver.sendMessage("�7Kuolemat: �a" + deaths);
				receiver.sendMessage("�7K/D: �a" + KD);
				receiver.sendMessage("�7Rahat: �a" + balance);
				receiver.sendMessage("�7Level: �a" + level + "/50");
				receiver.sendMessage("�7XP: �a" + xp + "/" + getLevel(target) * 100);
				receiver.sendMessage("�7Kieli: �a" + String.valueOf(getLanguage(target)));
				receiver.sendMessage("�7Peliaika: �a" + getPlayTime(target)); // Add this later on.
				receiver.sendMessage("�7�m---------------------------------------------");
				
			}
			else if (getLanguage(receiver.getName()) == Language.ENGLISH){
				receiver.sendMessage("�7�m------�a�l Stats of " + offTarget.getName() + " �7�m------");
				receiver.sendMessage("�7Kills: �a" + kills);
				receiver.sendMessage("�7Deaths: �a" + deaths);
				receiver.sendMessage("�7K/D: �a" + KD);
				receiver.sendMessage("�7Balance: �a" + balance);
				receiver.sendMessage("�7Level: �a" + level + "/50");
				receiver.sendMessage("�7XP: �a" + xp + "/" + getLevel(offTarget.getName()) * 100);
				receiver.sendMessage("�7Language: �a" + String.valueOf(getLanguage(offTarget.getName())));
				receiver.sendMessage("�7Playtime: �a" + getPlayTime(offTarget.getName())); // Add this later on.
				receiver.sendMessage("�7�m---------------------------------------------");
			}
		}
		
	}
	
	public void levelUp(String player, int newLvl){
		
		Player p = Bukkit.getPlayer(player);
		
		if(p.isOnline() && p != null){
			
			if(getLanguage(p.getName()) == Language.FINNISH){
				ChatUtils.sendMessageWithPrefix(p, "�7Ansaitsit uuden levelin! (�c" + newLvl + "�7)");
			}
			else if (getLanguage(p.getName()) == Language.ENGLISH){
				ChatUtils.sendMessageWithPrefix(p, "�7You unlocked a new level! (�c" + newLvl + "�7)");
			}
		}
		else return;
		
	}
	
	public int getXPToNextLVL(String player){
		
		return (getLevel(player) * 100) - getXp(player);
	}
	
	public int getKillStreak(Player p){
		if(killStreak.get(p.getName()) == null) return 0;
		return killStreak.get(p.getName());
	}
	
	public void addKillToKillStreak(Player p){
		if(killStreak.get(p.getName()) == null){
			killStreak.put(p.getName(), 1);
		}
		else killStreak.put(p.getName(), killStreak.get(p.getName()) + 1);
		
	}
	
	public void clearKillStreak(Player p){
		if(killStreak.get(p.getName()) == null){
			return;
		}
		else{
			killStreak.remove(p.getName());
		}
	}
	
	public int onlineStaff(){
		for(Player p : Bukkit.getOnlinePlayers()){
			if(p.hasPermission("server.mod")){
				onlineStaff++;
			}
		}
		return onlineStaff;
	}
	
	public List<String> getOnlineStaffMembers(){
		
		for(Player p : Bukkit.getOnlinePlayers()){
			if(p.hasPermission("server.mod")){
				staffMembers.add(p.getName());
			}
		}
		
		onlineStaff = staffMembers.size();
		
		return staffMembers;
	}
	
	  public long getFreeMemory(){
      	Runtime r = Runtime.getRuntime();
      	return r.freeMemory() / 1024L / 1024L;
      }
      public long getMaxMemory(){
      	Runtime r = Runtime.getRuntime();
      	return r.maxMemory() / 1024L / 1024L;
      }
      public long getTotalMemory(){
      	Runtime r = Runtime.getRuntime();
      	return r.totalMemory() / 1024L / 1024L;
      }
      
      @SuppressWarnings("deprecation")
      public String getStackData(ItemStack item) {
          int itemTypeId = item.getTypeId();
          short itemDur = item.getDurability();
          return itemTypeId + ":" + itemDur;
      } 
      
      public String getLastLogin(String player){
      	
    	  OfflinePlayer p = Bukkit.getOfflinePlayer(player);
    	  
      	String lastLogin = Main.getDataFile().getString(p.getUniqueId().toString() + ".lastLogin");
      	
      	if(lastLogin == null){

             LocalDate localDate = LocalDate.now();
             System.out.println(DateTimeFormatter.ofPattern("dd/MM/yy").format(localDate));

      		return DateTimeFormatter.ofPattern("dd/MM/yy").format(localDate);
      	}
      	
      	lastLogin = lastLogin.replace("-", ".");
      	
      	return lastLogin;
      }
      
      public void setLastLogin(String player){
		OfflinePlayer p = Bukkit.getOfflinePlayer(player);
		LocalDate localDate = LocalDate.now();
		Main.getDataFile().set(p.getUniqueId().toString() + ".lastLogin",
				DateTimeFormatter.ofPattern("dd/MM/yy").format(localDate));
		Main.saveDataFile();
      }
      
      public String getPlayTime(String player){
      	
    	  OfflinePlayer p = Bukkit.getOfflinePlayer(player);
    	  FileConfiguration data = Main.getDataFile();
    	  String uuid = p.getUniqueId().toString();
    	  
      	if(Main.getDataFile().get(p.getUniqueId().toString()) == null){ return "0 min"; }
      	
      		int min = data.getInt(uuid + ".playTime.minutes");
			int hour = data.getInt(uuid + ".playTime.hours");
			int day = data.getInt(uuid + ".playTime.days");
			int month = data.getInt(uuid + ".playTime.months");
			int year = data.getInt(uuid + ".playTime.years");
      	
      	if(hour == 0 && day == 0 && month == 0 && year == 0 && min >= 0){
				return min + "min";
			}
			 if(hour >= 1 && day == 0 && month == 0 && year == 0 && min >= 0){
				return  hour + "h " + min + "min";
			}
			 if (hour >= 1 && day >= 1 && month == 0 && year == 0 && min >= 0){
				return  day + "d " + hour + "h " + min + "min";
			}
			 if (hour >= 1 && day >= 1 && month >= 1 && year == 0 && min >= 0){
				return  month + "m " + day + "d " + hour + "h " + min + "min";
			}
			 if (hour >= 1 && day >= 1 && month >= 1 && year >= 1 && min >= 0){
				return  year + "y " + month + "m " + day + "d " + hour + "h " + min + "min";
			}
      	
			return null;
			
      }
	
      public void startPlayTimeCount(Player p){
    	  
    	  FileConfiguration data = Main.getDataFile();
    	  String uuid = p.getUniqueId().toString();
    	  
    	  new BukkitRunnable(){

				@Override
				public void run() {
					
					int minutes = DataYML.getFile().getInt(uuid + ".playTime.minutes");
					int hours = DataYML.getFile().getInt(uuid + ".playTime.hours");
					int days = DataYML.getFile().getInt(uuid + ".playTime.days");
					int months = DataYML.getFile().getInt(uuid + ".playTime.months");
					int years = DataYML.getFile().getInt(uuid + ".playTime.years");
					
					if(p.isOnline()){
						
						if(minutes >= minutes){
							minutes = minutes + 1;
							data.set(uuid + ".playTime.minutes", minutes);
							DataYML.saveFile();
						}
						
						if(minutes >= 60){
							minutes = 1;
							hours = hours + 1;
							DataYML.getFile().set(uuid + ".playTime.hours", hours);
							DataYML.getFile().set(uuid + ".playTime.minutes", minutes);
							DataYML.saveFile();
						}
						if(hours >= 24){
							hours = 1;
							days = days + 1;
							DataYML.getFile().set(uuid + ".playTime.days", days);
							DataYML.getFile().set(uuid + ".playTime.hours", hours);
							DataYML.saveFile();
						}
						if(days >= 31){
							days = 1;
							months = months + 1;
							DataYML.getFile().set(uuid + ".playTime.months", months);
							DataYML.getFile().set(uuid + ".playTime.days", days);
							DataYML.saveFile();
						}
						if(months >= 12){
							months = 1;
							years = years + 1;
							DataYML.getFile().set(uuid + ".playTime.years", years);
							DataYML.getFile().set(uuid + ".playTime.months", months);
							DataYML.saveFile();
						}
						
					}
					else if(!p.isOnline()){
						cancel();
						return;
					}
					
				}
				
			}.runTaskTimer(Main.getInstance(), 0, 1200);
		}
      
      public int getBoosters(String player){
    	  int i = Main.getCosmeticManager().getBoosters(player);
    	  return i;
      }
      
      public void setBoosters(String player, int value){
		Main.getCosmeticManager().setBoosters(player, value);
      }
      
	public void addBoosters(String player, int value) {
		
		Main.getCosmeticManager().addBoosters(player, value);
		
		if(Bukkit.getPlayer(player).isOnline() && Bukkit.getPlayer(player) != null){
			if(getLanguage(player) == Language.FINNISH){
				ChatUtils.sendMessageWithPrefix(Bukkit.getPlayer(player), "�7Sin� sait �c" + value + " �7uutta boosteria!");
			}
			if(getLanguage(player) == Language.ENGLISH){
				ChatUtils.sendMessageWithPrefix(Bukkit.getPlayer(player), "�7You got �c" + value + " �7new boosters!");
			}
		}
		
	}
      
	public void activateBooster(String player){
		
		if(isBoosterInUse()){ return; }
		
		setBoosters(player, getBoosters(player) - 1);
		
		FileConfiguration config = Main.getConfigFile();
		
		config.set("Booster.inUse", true);
		config.set("Booster.currentUser", player);
		config.set("Booster.timePassed.hours", 2);
		config.set("Booster.timePassed.minutes", 0);
		config.set("Booster.timePassed.seconds", 0);
		Main.getInstance().saveConfig();
		
		for(Player online : Bukkit.getOnlinePlayers()){
			if(getLanguage(online.getName()) == Language.FINNISH){
				ChatUtils.sendMessage(online, "�3�lBOOSTER � �7�lBoosteri on aloitettu! Boosterin aloitti �c�l" + config.getString("Booster.currentUser") + 
						"�7�l! Boosteri loppuu �c�l2 tunnin �7�lp��st�!");
				TextComponent message = new TextComponent("�3�lBOOSTER � �b�lKlikkaa kiitt��ksesi ja molemmat saatte �3�l10$!");
				message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/booster thank"));
				message.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("�7�oKlikkaa kiitt��ksesi!").create() ) );
				online.spigot().sendMessage(message);
			}
			else if(getLanguage(online.getName()) == Language.ENGLISH){
				ChatUtils.sendMessage(online, "�3�lBOOSTER � �7�lA booster has been started! The booster was started by �c�l" + config.getString("Booster.currentUser") + 
						"�7�l! The booster will end in �c�l2 hours�7�l!");
				TextComponent message = new TextComponent("�3�lBOOSTER � �b�lClick to thank, and you'll both receive �3�l10$!");
				message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/booster thank"));
				message.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("�7�oClick to thank!").create() ) );
				online.spigot().sendMessage(message);
			}	
		}
		
		startBoosterCountdown();
		
	}
	
	public void startBoosterCountdown(){
		
		FileConfiguration config = Main.getConfigFile();
		
		new BukkitRunnable(){

			@Override
			public void run() {
				
				int seconds = config.getInt("Booster.timePassed.seconds");
				int minutes = config.getInt("Booster.timePassed.minutes");
				int hours = config.getInt("Booster.timePassed.hours");
				
				seconds -= 1;
				config.set("Booster.timePassed.seconds", seconds);
				Main.getInstance().saveConfig();
				
				if(seconds <= 0){
					seconds = 60;
					minutes -= 1;
					config.set("Booster.timePassed.minutes", minutes);
					config.set("Booster.timePassed.seconds", seconds);
					Main.getInstance().saveConfig();
				}
				if(minutes <= 0){
					minutes = 59;
					hours -= 1;
					config.set("Booster.timePassed.minutes", minutes);
					config.set("Booster.timePassed.hours", hours);
					Main.getInstance().saveConfig();
				}
				if(hours <= 0){
					deActivateBooster();
					cancel();
					return;
				}
			}
			
		}.runTaskTimerAsynchronously(Main.getInstance(), 20, 20);
		
	}
	
	public void deActivateBooster(){
		
		if(!isBoosterInUse()){ return; }
		
		FileConfiguration config = Main.getConfigFile();
		
		for(Player online : Bukkit.getOnlinePlayers()){
			if(getLanguage(online.getName()) == Language.FINNISH){
				ChatUtils.sendMessageWithPrefix(online, "�7Nykyinen boosteri on loppunut! Boosterin aloitti �c" + config.getString("Booster.currentUser") + "�7!");
			}
			else if(getLanguage(online.getName()) == Language.ENGLISH){
				ChatUtils.sendMessageWithPrefix(online, "�7The current booster has ended! The booster was started by �c" + config.getString("Booster.currentUser") + "�7!");
			}	
		}
		
		config.set("Booster.inUse", false);
		config.set("Booster.currentUser", "");
		config.set("Booster.timePassed.hours", 0);
		config.set("Booster.timePassed.minutes", 0);
		config.set("Booster.timePassed.seconds", 0);
		Main.getInstance().saveConfig();
	}
	
	public String getCurrentBoosterUser(){
		if(!isBoosterInUse()) return "";
		return Main.getConfigFile().getString("Booster.currentUser");
	}
	
	public boolean hasPlayerStartedBooster(String target){
		if(Main.getConfigFile().getString("Booster.currentUser") == target) return true;
		return false;
	}
	
	public String getBoosterTimeLeft(){
		if(!isBoosterInUse()) return "0min";
		FileConfiguration config = Main.getConfigFile();
		
		int seconds = config.getInt("Booster.timePassed.seconds");
		int minutes = config.getInt("Booster.timePassed.minutes");
		int hours = config.getInt("Booster.timePassed.hours");
		
		return hours + "h " + minutes + "min " + seconds + "sec";
		
	}

	public void giveScoreboard(Player p){
		
		if(!Main.getDataFile().getBoolean(p.getUniqueId().toString() + ".scoreboard")){ return; }
		
		if(getLanguage(p.getName()) == Language.FINNISH){
			
			Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
			Objective obj = board.registerNewObjective("dash", "dummy");
			obj.setDisplaySlot(DisplaySlot.SIDEBAR);
			obj.setDisplayName("�c�lFinskaCraft");
			
			Team viiva = board.registerNewTeam("paska");
			viiva.addEntry(ChatColor.ITALIC.toString());
			viiva.setPrefix("�7�m------------");
			obj.getScore(ChatColor.ITALIC.toString()).setScore(14);
			
			Team pelaajat = board.registerNewTeam("online");
			pelaajat.addEntry(ChatColor.BOLD.toString());
			pelaajat.setPrefix("�7� Pelaajat: ");
			pelaajat.setSuffix("�c" + String.valueOf(Bukkit.getOnlinePlayers().size()));
			obj.getScore(ChatColor.BOLD.toString()).setScore(13);

			Team viiva1 = board.registerNewTeam("klffs");
			viiva1.addEntry(ChatColor.STRIKETHROUGH.toString());
			viiva1.setPrefix("�7�m------------");
			obj.getScore(ChatColor.STRIKETHROUGH.toString()).setScore(12);
			
			Team kills = board.registerNewTeam("kills");
			kills.addEntry(ChatColor.DARK_GRAY.toString());
			kills.setPrefix("�7� Tapot: ");
			kills.setSuffix("�c" + getKills(p.getName()));
			obj.getScore(ChatColor.DARK_GRAY.toString()).setScore(11);
			
			Team deaths = board.registerNewTeam("deaths");
			deaths.addEntry(ChatColor.BLACK.toString());
			deaths.setPrefix("�7� Kuolemat: ");
			deaths.setSuffix("�c" + getDeaths(p.getName()));
			obj.getScore(ChatColor.BLACK.toString()).setScore(10);

			Team kdr = board.registerNewTeam("kdr");
			kdr.addEntry(ChatColor.RESET.toString());
			kdr.setPrefix("�7� K/D: ");
			kdr.setSuffix("�c" + String.valueOf(getKD(p.getName())));
			obj.getScore(ChatColor.RESET.toString()).setScore(9);
			
			Team viiva2 = board.registerNewTeam("gvfnsabf");
			viiva2.addEntry(ChatColor.DARK_BLUE.toString());
			viiva2.setPrefix("�7�m------------");
			obj.getScore(ChatColor.DARK_BLUE.toString()).setScore(8);
			
			Team balance = board.registerNewTeam("balance");
			balance.addEntry(ChatColor.YELLOW.toString());
			balance.setPrefix("�7� Raha: ");
			balance.setSuffix("�c" + getBalance(p.getName()));
			obj.getScore(ChatColor.YELLOW.toString()).setScore(7);
			
			Team tokens = board.registerNewTeam("tokens");
			tokens.addEntry(ChatColor.LIGHT_PURPLE.toString());
			tokens.setPrefix("�7� Tokenit: ");
			tokens.setSuffix("�c0");
			obj.getScore(ChatColor.LIGHT_PURPLE.toString()).setScore(6);
			
			Team viiva3 = board.registerNewTeam("ezpz");
			viiva3.addEntry(ChatColor.DARK_PURPLE.toString());
			viiva3.setPrefix("�7�m------------");
			obj.getScore(ChatColor.DARK_PURPLE.toString()).setScore(5);
			
			Team lvl = board.registerNewTeam("lvl");
			lvl.addEntry(ChatColor.UNDERLINE.toString());
			lvl.setPrefix("�7� Lvl: ");
			lvl.setSuffix("�c" + getLevel(p.getName()));
			obj.getScore(ChatColor.UNDERLINE.toString()).setScore(4);
			
			Team xp = board.registerNewTeam("xp");
			xp.addEntry(ChatColor.DARK_GREEN.toString());
			xp.setPrefix("�7� XP: ");
			xp.setSuffix("�c" + getXp(p.getName()) + "/" + getLevel(p.getName()) * 100);
			obj.getScore(ChatColor.DARK_GREEN.toString()).setScore(3);
			
			new BukkitRunnable(){

				@Override
				public void run() {
					if(!Main.getDataFile().getBoolean(p.getUniqueId().toString() + ".scoreboard")){ 
						p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
						cancel();
						return; 
					}
					
					board.getTeam("online").setSuffix("�c" + String.valueOf(Bukkit.getOnlinePlayers().size()));
					board.getTeam("kills").setSuffix("�c" + String.valueOf(getKills(p.getName())));
					board.getTeam("deaths").setSuffix("�c" + String.valueOf(getDeaths(p.getName())));
					board.getTeam("kdr").setSuffix("�c" + String.valueOf(getKD(p.getName())));
					board.getTeam("balance").setSuffix("�c" + String.valueOf(getBalance(p.getName())));
					board.getTeam("lvl").setSuffix("�c" + String.valueOf(getLevel(p.getName())));
					board.getTeam("xp").setSuffix("�c" + String.valueOf(getXp(p.getName()) + "/" + getLevel(p.getName()) * 100));
				}
				
			}.runTaskTimerAsynchronously(Main.getInstance(), 20, 60);
			p.setScoreboard(board);
			return;
		}
		else if (getLanguage(p.getName()) == Language.ENGLISH){
			
			Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
			Objective obj = board.registerNewObjective("dash", "dummy");
			obj.setDisplaySlot(DisplaySlot.SIDEBAR);
			obj.setDisplayName("�c�lFinskaCraft");
			
			Team viiva = board.registerNewTeam("paska");
			viiva.addEntry(ChatColor.ITALIC.toString());
			viiva.setPrefix("�7�m------------");
			obj.getScore(ChatColor.ITALIC.toString()).setScore(14);
			
			Team pelaajat = board.registerNewTeam("online");
			pelaajat.addEntry(ChatColor.BOLD.toString());
			pelaajat.setPrefix("�7� Online: ");
			pelaajat.setSuffix("�c" + String.valueOf(Bukkit.getOnlinePlayers().size()));
			obj.getScore(ChatColor.BOLD.toString()).setScore(13);

			Team viiva1 = board.registerNewTeam("vdsfnsj");
			viiva1.addEntry(ChatColor.STRIKETHROUGH.toString());
			viiva1.setPrefix("�7�m------------");
			obj.getScore(ChatColor.STRIKETHROUGH.toString()).setScore(12);
			
			Team kills = board.registerNewTeam("kills");
			kills.addEntry(ChatColor.DARK_GRAY.toString());
			kills.setPrefix("�7� Kills: ");
			kills.setSuffix("�c" + getKills(p.getName()));
			obj.getScore(ChatColor.DARK_GRAY.toString()).setScore(11);
			
			Team deaths = board.registerNewTeam("deaths");
			deaths.addEntry(ChatColor.BLACK.toString());
			deaths.setPrefix("�7� Deaths: ");
			deaths.setSuffix("�c" + getDeaths(p.getName()));
			obj.getScore(ChatColor.BLACK.toString()).setScore(10);

			Team kdr = board.registerNewTeam("kdr");
			kdr.addEntry(ChatColor.RESET.toString());
			kdr.setPrefix("�7� K/D: ");
			kdr.setSuffix("�c" + String.valueOf(getKD(p.getName())));
			obj.getScore(ChatColor.RESET.toString()).setScore(9);
			
			Team viiva2 = board.registerNewTeam("klffs");
			viiva2.addEntry(ChatColor.DARK_PURPLE.toString());
			viiva2.setPrefix("�7�m------------");
			obj.getScore(ChatColor.DARK_PURPLE.toString()).setScore(8);
			
			Team balance = board.registerNewTeam("balance");
			balance.addEntry(ChatColor.UNDERLINE.toString());
			balance.setPrefix("�7� Balance: ");
			balance.setSuffix("�c" + getBalance(p.getName()));
			obj.getScore(ChatColor.UNDERLINE.toString()).setScore(7);
			
			Team tokens = board.registerNewTeam("tokens");
			tokens.addEntry(ChatColor.DARK_RED.toString());
			tokens.setPrefix("�7� Tokens: ");
			tokens.setSuffix("�c0");
			obj.getScore(ChatColor.DARK_RED.toString()).setScore(6);
			
			Team viiva3 = board.registerNewTeam("ezpz");
			viiva3.addEntry(ChatColor.DARK_AQUA.toString());
			viiva3.setPrefix("�7�m------------");
			obj.getScore(ChatColor.DARK_AQUA.toString()).setScore(5);
			
			Team lvl = board.registerNewTeam("lvl");
			lvl.addEntry(ChatColor.MAGIC.toString());
			lvl.setPrefix("�7� Lvl: ");
			lvl.setSuffix("�c" + getLevel(p.getName()));
			obj.getScore(ChatColor.MAGIC.toString()).setScore(4);
			
			Team xp = board.registerNewTeam("xp");
			xp.addEntry(ChatColor.DARK_GREEN.toString());
			xp.setPrefix("�7� XP: ");
			xp.setSuffix("�c" + getXp(p.getName()) + "/" + getLevel(p.getName()) * 100);
			obj.getScore(ChatColor.DARK_GREEN.toString()).setScore(3);
			
			new BukkitRunnable(){

				@Override
				public void run() {
					if(!Main.getDataFile().getBoolean(p.getUniqueId().toString() + ".scoreboard")){ 
						p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
						cancel();
						return; 
					}
					
					board.getTeam("online").setSuffix("�c" + String.valueOf(Bukkit.getOnlinePlayers().size()));
					board.getTeam("kills").setSuffix("�c" + String.valueOf(getKills(p.getName())));
					board.getTeam("deaths").setSuffix("�c" + String.valueOf(getDeaths(p.getName())));
					board.getTeam("kdr").setSuffix("�c" + String.valueOf(getKD(p.getName())));
					board.getTeam("balance").setSuffix("�c" + String.valueOf(getBalance(p.getName())));
					board.getTeam("lvl").setSuffix("�c" + String.valueOf(getLevel(p.getName())));
					board.getTeam("xp").setSuffix("�c" + String.valueOf(getXp(p.getName()) + "/" + getLevel(p.getName()) * 100));
				}
				
			}.runTaskTimerAsynchronously(Main.getInstance(), 20, 60);
			
			p.setScoreboard(board);
			return;
		}
	}
	
	public void removePotionEffects(Player p){
		for(PotionEffect effect:p.getActivePotionEffects()){
			p.removePotionEffect(effect.getType());
		}
	}
	
	public void resetData(String player){
		setBalance(player, 0);
		setBoosters(player, 0);
		setDeaths(player, 0);
		setKills(player, 0);
		setXp(player, 0);
		setlevel(player, 0);
		setNick(player, NameColor.DEFAULT);
	}
	
	public double getKD(String p){
		
		if(getKills(p) <= 0 || getDeaths(p) <= 0){ return 0.0D; }
		
		double absKD = ((double) getKills(p) / (double) getDeaths(p));
		
		double KD = Math.round(absKD * 10000.0D) / 10000.0D;
		
		return KD;
	}
	
	public int randInt(int min, int max) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
	
	public double getHealth(Player target){
		double rawHealth = target.getHealth();
		double health = Math.round(rawHealth * 10.0D) / 10.0D;
		return health;
	}
	
}
