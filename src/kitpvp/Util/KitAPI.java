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
import org.bukkit.scheduler.BukkitRunnable;

import kitpvp.Language;
import kitpvp.Main;
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
	private Connection connection = Main.getMySQLManager().getConnection();
	private HashMap<String, Integer> killStreak = new HashMap<String, Integer>();
	
	public boolean isBoosterInUse() {
		return Main.getConfigFile().getBoolean("Booster.inUse");
	}
	public int getKills(String player){
		
		try {
			if(Main.getMySQLManager().getConnection().isClosed()){
				Main.getMySQLManager().openConnection();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			
			if(Main.getMySQLManager().playerDataContainsPlayer(Bukkit.getOfflinePlayer(player).getName())){

				PreparedStatement sql = connection.prepareStatement("SELECT kills FROM `player_data` WHERE player = ?;");
				sql.setString(1, player);
				
				ResultSet result = sql.executeQuery();
				result.next();
				
				int kill = result.getInt("kills");
				
				return kill;
			}
			else{
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `player_data` values(?,0,0,0,0,0)");
				newPlayer.setString(1, Bukkit.getOfflinePlayer(player).getName());
				newPlayer.execute();
				newPlayer.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void setKills(String player, int kills){
		
		try {
			if(Main.getMySQLManager().getConnection().isClosed()){
				Main.getMySQLManager().openConnection();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			
			if(Main.getMySQLManager().playerDataContainsPlayer(Bukkit.getOfflinePlayer(player).getName())){
				
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
				
			}
			else{
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `player_data` values(?,0,0,0,0,0)");
				newPlayer.setString(1, Bukkit.getOfflinePlayer(player).getName());
				newPlayer.execute();
				newPlayer.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	public void addKills(String player, int kills){
		
		try {
			if(Main.getMySQLManager().getConnection().isClosed()){
				Main.getMySQLManager().openConnection();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			
			if(Main.getMySQLManager().playerDataContainsPlayer(Bukkit.getOfflinePlayer(player).getName())){
				
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
			}
			else{
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `player_data` values(?,0,0,0,0,0)");
				newPlayer.setString(1, Bukkit.getOfflinePlayer(player).getName());
				newPlayer.execute();
				newPlayer.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public int getDeaths(String player){
		
		try {
			if(Main.getMySQLManager().getConnection().isClosed()){
				Main.getMySQLManager().openConnection();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			
			if(Main.getMySQLManager().playerDataContainsPlayer(Bukkit.getOfflinePlayer(player).getName())){

				PreparedStatement sql = connection.prepareStatement("SELECT deaths FROM `player_data` WHERE player = ?;");
				sql.setString(1, player);
				
				ResultSet result = sql.executeQuery();
				result.next();
				
				int kill = result.getInt("deaths");
				
				return kill;
			}
			else{
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `player_data` values(?,0,0,0,0,0)");
				newPlayer.setString(1, Bukkit.getOfflinePlayer(player).getName());
				newPlayer.execute();
				newPlayer.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void setDeaths(String player, int deaths){
		
		try {
			if(Main.getMySQLManager().getConnection().isClosed()){
				Main.getMySQLManager().openConnection();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			
			if(Main.getMySQLManager().playerDataContainsPlayer(Bukkit.getOfflinePlayer(player).getName())){
				
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
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `player_data` values(?,0,0,0,0,0)");
				newPlayer.setString(1, Bukkit.getOfflinePlayer(player).getName());
				newPlayer.execute();
				newPlayer.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	public void addDeaths(String player, int deaths){
		
		try {
			if(Main.getMySQLManager().getConnection().isClosed()){
				Main.getMySQLManager().openConnection();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			
			if(Main.getMySQLManager().playerDataContainsPlayer(Bukkit.getOfflinePlayer(player).getName())){
				
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
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `player_data` values(?,0,0,0,0,0)");
				newPlayer.setString(1, Bukkit.getOfflinePlayer(player).getName());
				newPlayer.execute();
				newPlayer.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public int getBalance(String player){
		
		try {
			if(Main.getMySQLManager().getConnection().isClosed()){
				Main.getMySQLManager().openConnection();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			
			if(Main.getMySQLManager().playerDataContainsPlayer(Bukkit.getOfflinePlayer(player).getName())){

				PreparedStatement sql = connection.prepareStatement("SELECT balance FROM `player_data` WHERE player = ?;");
				sql.setString(1, player);
				
				ResultSet result = sql.executeQuery();
				result.next();
				
				int kill = result.getInt("balance");
				
				return kill;
			}
			else{
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `player_data` values(?,0,0,0,0,0)");
				newPlayer.setString(1, Bukkit.getOfflinePlayer(player).getName());
				newPlayer.execute();
				newPlayer.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void setBalance(String player, int money){
		
		try {
			if(Main.getMySQLManager().getConnection().isClosed()){
				Main.getMySQLManager().openConnection();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		try {
			
			if(Main.getMySQLManager().playerDataContainsPlayer(Bukkit.getOfflinePlayer(player).getName())){
				
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
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `player_data` values(?,0,0,0,0,0)");
				newPlayer.setString(1, Bukkit.getOfflinePlayer(player).getName());
				newPlayer.execute();
				newPlayer.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void addBalance(String player, int money) {
		
		try {
			if(Main.getMySQLManager().getConnection().isClosed()){
				Main.getMySQLManager().openConnection();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		try {

			if(Main.getMySQLManager().playerDataContainsPlayer(Bukkit.getOfflinePlayer(player).getName())){

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
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `player_data` values(?,0,0,0,0,0)");
				newPlayer.setString(1, Bukkit.getOfflinePlayer(player).getName());
				newPlayer.execute();
				newPlayer.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public int getXp(String player){
		
		try {
			if(Main.getMySQLManager().getConnection().isClosed()){
				Main.getMySQLManager().openConnection();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			
			if(Main.getMySQLManager().playerDataContainsPlayer(Bukkit.getOfflinePlayer(player).getName())){

				PreparedStatement sql = connection.prepareStatement("SELECT xp FROM `player_data` WHERE player = ?;");
				sql.setString(1, player);
				
				ResultSet result = sql.executeQuery();
				result.next();
				
				int kill = result.getInt("xp");
				
				return kill;
			}
			else{
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `player_data` values(?,0,0,0,0,0)");
				newPlayer.setString(1, Bukkit.getOfflinePlayer(player).getName());
				newPlayer.execute();
				newPlayer.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void setXp(String player, int money){
		
		try {
			if(Main.getMySQLManager().getConnection().isClosed()){
				Main.getMySQLManager().openConnection();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		try {
			
			if(Main.getMySQLManager().playerDataContainsPlayer(Bukkit.getOfflinePlayer(player).getName())){
				
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
				else{
					
					new BukkitRunnable(){

						@Override
						public void run() {
							
							if(money < getXPToNextLVL(player)){
								cancel();
								return;
							}
							
							try{
							
								if(getXp(player) < 0){
									
									PreparedStatement sql = connection.prepareStatement("SELECT xp FROM `player_data` WHERE player = ?;");
									sql.setString(1, player);
									
									ResultSet result = sql.executeQuery();
									result.next();
									
									PreparedStatement updateKills = connection.prepareStatement("UPDATE `player_data` SET xp=? WHERE player = ?;");
									updateKills.setInt(1, 0);
									updateKills.setString(2, player);
									updateKills.executeUpdate();
									
									updateKills.close();
									sql.close();
									result.close();
									
									cancel();
									return;
								}
								
								PreparedStatement sql = connection.prepareStatement("SELECT xp FROM `player_data` WHERE player = ?;");
								sql.setString(1, player);
								
								ResultSet result = sql.executeQuery();
								result.next();
								
								PreparedStatement updateKills = connection.prepareStatement("UPDATE `player_data` SET xp=? WHERE player = ?;");
								updateKills.setInt(1, money - getlevel(player) * 100);
								updateKills.setString(2, player);
								updateKills.executeUpdate();
								
								updateKills.close();
								sql.close();
								result.close();
								
								addLevel(player, 1);
								levelUp(player);
								
							} catch (Exception e){
								e.printStackTrace();
							}
							
						}
						
					}.runTaskTimer(Main.getInstance(), 1, 1);
				}
				
			}
			else{
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `player_data` values(?,0,0,0,0,0)");
				newPlayer.setString(1, Bukkit.getOfflinePlayer(player).getName());
				newPlayer.execute();
				newPlayer.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void addXp(String player, int money) {
		
		try {
			if(Main.getMySQLManager().getConnection().isClosed()){
				Main.getMySQLManager().openConnection();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		try {

			if(Main.getMySQLManager().playerDataContainsPlayer(Bukkit.getOfflinePlayer(player).getName())){

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
					
					
					
				}
				else{
					
					new BukkitRunnable(){

						@Override
						public void run() {
							
							if(money < getXPToNextLVL(player)){
								cancel();
								return;
							}
							
							if(getXp(player) < 0){
								setXp(player, 0);
								cancel();
								return;
							}
							
							try{
								
								PreparedStatement sql = connection
										.prepareStatement("SELECT xp FROM `player_data` WHERE player = ?;");
								sql.setString(1, player);

								ResultSet result = sql.executeQuery();
								result.next();
								PreparedStatement updateKills = connection
										.prepareStatement("UPDATE `player_data` SET xp=? WHERE player = ?;");
								updateKills.setInt(1, money - getlevel(player) * 100);
								updateKills.setString(2, player);
								updateKills.executeUpdate();
								
								updateKills.close();
								sql.close();
								result.close();
								
								addLevel(player, 1);
								levelUp(player);
								
							} catch (Exception e){ }
						}
						
					}.runTaskTimer(Main.getInstance(), 1, 1);
					
					return;
				}
				
			} else {
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `player_data` values(?,0,0,0,0,0)");
				newPlayer.setString(1, Bukkit.getOfflinePlayer(player).getName());
				newPlayer.execute();
				newPlayer.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public int getlevel(String player){
		
		try {
			if(Main.getMySQLManager().getConnection().isClosed()){
				Main.getMySQLManager().openConnection();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			
			if(Main.getMySQLManager().playerDataContainsPlayer(Bukkit.getOfflinePlayer(player).getName())){

				PreparedStatement sql = connection.prepareStatement("SELECT levels FROM `player_data` WHERE player = ?;");
				sql.setString(1, player);
				
				ResultSet result = sql.executeQuery();
				result.next();
				
				int kill = result.getInt("levels");
				
				return kill;
			}
			else{
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `player_data` values(?,0,0,0,0,0)");
				newPlayer.setString(1, Bukkit.getOfflinePlayer(player).getName());
				newPlayer.execute();
				newPlayer.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void setlevel(String player, int money){
		
		try {
			if(Main.getMySQLManager().getConnection().isClosed()){
				Main.getMySQLManager().openConnection();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		try {
			
			if(Main.getMySQLManager().playerDataContainsPlayer(Bukkit.getOfflinePlayer(player).getName())){
				
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
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `player_data` values(?,0,0,0,0,0)");
				newPlayer.setString(1, Bukkit.getOfflinePlayer(player).getName());
				newPlayer.execute();
				newPlayer.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void addLevel(String player, int money) {
		
		try {
			if(Main.getMySQLManager().getConnection().isClosed()){
				Main.getMySQLManager().openConnection();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		try {

			if(Main.getMySQLManager().playerDataContainsPlayer(Bukkit.getOfflinePlayer(player).getName())){
				
				if(money >= 50){
					

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
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `player_data` values(?,0,0,0,0,0)");
				newPlayer.setString(1, Bukkit.getOfflinePlayer(player).getName());
				newPlayer.execute();
				newPlayer.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public Language getLanguage(String player){
		
		if(Main.getDataFile().getString(player).equalsIgnoreCase("ENG")){
			return Language.ENGLISH;
		}
		else if(Main.getDataFile().getString(player).equalsIgnoreCase("FIN")){
			return Language.FINNISH;
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
			
			double absKD = (double) getKills(receiver.getName()) / (double) getDeaths(receiver.getName());
			
			double KD = Math.round(absKD * 10000.0D) / 10000.0D;
			int balance = getBalance(receiver.getName());
			int level = getlevel(receiver.getName());
			int xp = getXp(receiver.getName());
			
			if(Double.isInfinite(KD)) KD = 0.0D;
			if(Double.isNaN(KD)) KD = 0.0D;
			
			if(getLanguage(receiver.getName()) == Language.FINNISH){

				receiver.sendMessage("ß7ßm------ßaßl Sinun tilastot ß7ßm------");
				receiver.sendMessage("ß7Tapot: ßa" + kills);
				receiver.sendMessage("ß7Kuolemat: ßa" + deaths);
				receiver.sendMessage("ß7K/D: ßa" + KD);
				receiver.sendMessage("ß7Rahat: ßa" + balance);
				receiver.sendMessage("ß7Level: ßa" + level + "/50");
				receiver.sendMessage("ß7XP: ßa" + xp + "/" + getlevel(target) * 100);
				receiver.sendMessage("ß7Kieli: ßa" + String.valueOf(getLanguage(target)));
				receiver.sendMessage("ß7Peliaika: ßa" + getPlayTime(target));
				receiver.sendMessage("ß7ßm---------------------------------------------");
				
			}
			else if (getLanguage(receiver.getName()) == Language.ENGLISH){
				receiver.sendMessage("ß7ßm------ ßaßl Your stats ß7ßm------");
				receiver.sendMessage("ß7Kills: ßa" + kills);
				receiver.sendMessage("ß7Deaths: ßa" + deaths);
				receiver.sendMessage("ß7K/D: ßa" + KD);
				receiver.sendMessage("ß7Balance: ßa" + balance);
				receiver.sendMessage("ß7Level: ßa" + level + "/50");
				receiver.sendMessage("ß7XP: ßa" + xp + "/" + getlevel(target) * 100);
				receiver.sendMessage("ß7Language: ßa" + String.valueOf(getLanguage(target)));
				receiver.sendMessage("ß7Playtime: ßa" + getPlayTime(target)); 
				receiver.sendMessage("ß7ßm---------------------------------------------");
			}
		}
		else{
			
			OfflinePlayer offTarget = Bukkit.getOfflinePlayer(target);
			
			if(Main.getDataFile().getBoolean(offTarget.getUniqueId().toString() + ".privateAccount") && !receiver.hasPermission("privacy.bypass")){
				if(getLanguage(receiver.getName()) == Language.FINNISH){
					ChatUtils.sendMessageWithPrefix(receiver, "ß7Tuolla pelaajalla on k‰ytt‰j‰ yksityisen‰!");
					return;
				}
				else if(getLanguage(receiver.getName()) == Language.ENGLISH){
					ChatUtils.sendMessageWithPrefix(receiver, "ß7That player's account is private!");
					return;
				}
			}
			
			int kills = getKills(offTarget.getName());
			int deaths = getDeaths(offTarget.getName());
			
			double absKD = (double) getKills(offTarget.getName()) / (double) getDeaths(offTarget.getName());
			
			double KD = Math.round(absKD * 10000.0D) / 10000.0D;
			int balance = getBalance(offTarget.getName());
			int level = getlevel(offTarget.getName());
			int xp = getXp(offTarget.getName());
			
			if(Double.isInfinite(KD)) KD = 0.0D;
			if(Double.isNaN(KD)) KD = 0.0D;
			
			if(getLanguage(receiver.getName()) == Language.FINNISH){

				receiver.sendMessage("ß7ßm------ßaßl Pelaajan " + offTarget.getName() + " tilastot ß7ßm------");
				receiver.sendMessage("ß7Tapot: ßa" + kills);
				receiver.sendMessage("ß7Kuolemat: ßa" + deaths);
				receiver.sendMessage("ß7K/D: ßa" + KD);
				receiver.sendMessage("ß7Rahat: ßa" + balance);
				receiver.sendMessage("ß7Level: ßa" + level + "/50");
				receiver.sendMessage("ß7XP: ßa" + xp + "/" + getlevel(target) * 100);
				receiver.sendMessage("ß7Kieli: ßa" + String.valueOf(getLanguage(target)));
				receiver.sendMessage("ß7Peliaika: ßa" + getPlayTime(target)); // Add this later on.
				receiver.sendMessage("ß7ßm---------------------------------------------");
				
			}
			else if (getLanguage(receiver.getName()) == Language.ENGLISH){
				receiver.sendMessage("ß7ßm------ßaßl Stats of " + offTarget.getName() + " ß7ßm------");
				receiver.sendMessage("ß7Kills: ßa" + kills);
				receiver.sendMessage("ß7Deaths: ßa" + deaths);
				receiver.sendMessage("ß7K/D: ßa" + KD);
				receiver.sendMessage("ß7Balance: ßa" + balance);
				receiver.sendMessage("ß7Level: ßa" + level + "/50");
				receiver.sendMessage("ß7XP: ßa" + xp + "/" + getlevel(offTarget.getName()) * 100);
				receiver.sendMessage("ß7Language: ßa" + String.valueOf(getLanguage(offTarget.getName())));
				receiver.sendMessage("ß7Playtime: ßa" + getPlayTime(offTarget.getName())); // Add this later on.
				receiver.sendMessage("ß7ßm---------------------------------------------");
			}
		}
		
	}
	
	public void levelUp(String player){
		
		OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(player);
		
		if(offPlayer.isOnline()){
			Player p = offPlayer.getPlayer();
			
			if(getLanguage(p.getName()) == Language.FINNISH){
				ChatUtils.sendMessageWithPrefix(p, "ß7Ansaitsit uuden levelin! (ßc" + getlevel(p.getName()) + "ß7)");
			}
			else if (getLanguage(p.getName()) == Language.ENGLISH){
				ChatUtils.sendMessageWithPrefix(p, "ß7You unlocked a new level! (ßc" + getlevel(p.getName()) + "ß7)");
			}
		}
		
	}
	
	public int getXPToNextLVL(String player){
		return (getlevel(player) * 100) - getXp(player);
	}
	
	public int getKillStreak(Player p){
		if(killStreak.get(p.getName()) == null) return 0;
		return killStreak.get(p.getName());
	}
	
	public void addKillToKillStreak(Player p){
		if(!killStreak.containsKey(p.getName())){
			killStreak.put(p.getName(), 1);
			return;
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
		return onlineStaff;
	}
	
	public List<String> getOnlineStaffMembers(){
		
		for(Player p : Bukkit.getOnlinePlayers()){
			if(p.hasPermission("staff.online")){
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
				
			}.runTaskTimerAsynchronously(Main.getInstance(), 0, 1200);
		}
      
      public int getBoosters(String player){
    	  OfflinePlayer p = Bukkit.getOfflinePlayer(player);
    	  String uuid = p.getUniqueId().toString();
    	  return Main.getDataFile().getInt(uuid + ".boosters");
      }
      
      public void setBoosters(String player, int value){
		OfflinePlayer p = Bukkit.getOfflinePlayer(player);
		String uuid = p.getUniqueId().toString();
		Main.getDataFile().set(uuid + ".boosters", value);
		DataYML.saveFile();
      }
      
	public void addBoosters(String player, int value) {
		OfflinePlayer p = Bukkit.getOfflinePlayer(player);
		String uuid = p.getUniqueId().toString();
		int boosters = Main.getDataFile().getInt(uuid + ".boosters");
		Main.getDataFile().set(uuid + ".boosters", boosters + value);
		DataYML.saveFile();
		
		if(p.isOnline()){
			if(getLanguage(p.getName()) == Language.FINNISH){
				ChatUtils.sendMessageWithPrefix(p.getPlayer(), "ß7Sin‰ sait ßc" + value + " ß7uutta boosteria!");
			}
			if(getLanguage(p.getName()) == Language.ENGLISH){
				ChatUtils.sendMessageWithPrefix(p.getPlayer(), "ß7You got ßc" + value + " ß7new boosters!");
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
				ChatUtils.sendMessage(online, "ß3ßlBOOSTER ª ß7ßlBoosteri on aloitettu! Boosterin aloitti ßcßl" + config.getString("Booster.currentUser") + 
						"ß7ßl! Boosteri loppuu ßcßl2 tunnin ß7ßlp‰‰st‰!");
				TextComponent message = new TextComponent("ß3ßlBOOSTER ª ßbßlKlikkaa kiitt‰‰ksesi ja molemmat saatte ß3ßl10$!");
				message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/booster thank"));
				message.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("ß7ßoKlikkaa kiitt‰‰ksesi!").create() ) );
				online.spigot().sendMessage(message);
			}
			else if(getLanguage(online.getName()) == Language.ENGLISH){
				ChatUtils.sendMessage(online, "ß3ßlBOOSTER ª ß7ßlA booster has been started! The booster was started by ßcßl" + config.getString("Booster.currentUser") + 
						"ß7ßl! The booster will end in ßcßl2 hoursß7ßl!");
				TextComponent message = new TextComponent("ß3ßlBOOSTER ª ßbßlClick to thank, and you'll both receive ß3ßl10$!");
				message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/booster thank"));
				message.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("ß7ßoClick to thank!").create() ) );
				online.spigot().sendMessage(message);
			}	
		}
		
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
				ChatUtils.sendMessageWithPrefix(online, "ß7Nykyinen boosteri on loppunut! Boosterin aloitti ßc" + config.getString("Booster.currentUser") + "ß7!");
			}
			else if(getLanguage(online.getName()) == Language.ENGLISH){
				ChatUtils.sendMessageWithPrefix(online, "ß7The current booster has ended! The booster was started by ßc" + config.getString("Booster.currentUser") + "ß7!");
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
}
