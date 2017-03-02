package kitpvp.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import kitpvp.Language;
import kitpvp.Main;
import kitpvp.commands.PrefixCommand.NameColor;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.NBTTagCompound;

public class KitAPI {
	
	private Connection connection = Main.getMySQLManager().getConnection();
	
	public int getKills(String player){
		try {
			
			if(Main.getMySQLManager().playerDataContainsPlayer(Bukkit.getPlayer(player))){

				PreparedStatement sql = connection.prepareStatement("SELECT kills FROM `player_data` WHERE player = ?;");
				sql.setString(1, player);
				
				ResultSet result = sql.executeQuery();
				result.next();
				
				int kill = result.getInt("kills");
				
				return kill;
			}
			else{
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `player_data` values(?,0,0,0,0)");
				newPlayer.setString(1, player);
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
			
			if(Main.getMySQLManager().playerDataContainsPlayer(Bukkit.getPlayer(player))){
				
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
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `player_data` values(?,0,0,0,0)");
				newPlayer.setString(1, player);
				newPlayer.execute();
				newPlayer.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	public void addKills(String player, int kills){
		try {
			
			if(Main.getMySQLManager().playerDataContainsPlayer(Bukkit.getPlayer(player))){
				
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
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `player_data` values(?,0,0,0,0)");
				newPlayer.setString(1, player);
				newPlayer.execute();
				newPlayer.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public int getDeaths(String player){
		try {
			
			if(Main.getMySQLManager().playerDataContainsPlayer(Bukkit.getPlayer(player))){

				PreparedStatement sql = connection.prepareStatement("SELECT deaths FROM `player_data` WHERE player = ?;");
				sql.setString(1, player);
				
				ResultSet result = sql.executeQuery();
				result.next();
				
				int kill = result.getInt("deaths");
				
				return kill;
			}
			else{
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `player_data` values(?,0,0,0,0)");
				newPlayer.setString(1, player);
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
			
			if(Main.getMySQLManager().playerDataContainsPlayer(Bukkit.getPlayer(player))){
				
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
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `player_data` values(?,0,0,0,0)");
				newPlayer.setString(1, player);
				newPlayer.execute();
				newPlayer.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	public void addDeaths(String player, int deaths){
		try {
			
			if(Main.getMySQLManager().playerDataContainsPlayer(Bukkit.getPlayer(player))){
				
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
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `player_data` values(?,0,0,0,0)");
				newPlayer.setString(1, player);
				newPlayer.execute();
				newPlayer.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public int getBalance(String player){
		try {
			
			if(Main.getMySQLManager().playerDataContainsPlayer(Bukkit.getPlayer(player))){

				PreparedStatement sql = connection.prepareStatement("SELECT balance FROM `player_data` WHERE player = ?;");
				sql.setString(1, player);
				
				ResultSet result = sql.executeQuery();
				result.next();
				
				int kill = result.getInt("balance");
				
				return kill;
			}
			else{
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `player_data` values(?,0,0,0,0)");
				newPlayer.setString(1, player);
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
			
			if(Main.getMySQLManager().playerDataContainsPlayer(Bukkit.getPlayer(player))){
				
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
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `player_data` values(?,0,0,0,0)");
				newPlayer.setString(1, player);
				newPlayer.execute();
				newPlayer.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void addBalance(String player, int money) {
		
		try {

			if (Main.getMySQLManager().playerDataContainsPlayer(Bukkit.getPlayer(player))) {

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
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `player_data` values(?,0,0,0,0)");
				newPlayer.setString(1, player);
				newPlayer.execute();
				newPlayer.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public int getXp(String player){
		try {
			
			if(Main.getMySQLManager().playerDataContainsPlayer(Bukkit.getPlayer(player))){

				PreparedStatement sql = connection.prepareStatement("SELECT xp FROM `player_data` WHERE player = ?;");
				sql.setString(1, player);
				
				ResultSet result = sql.executeQuery();
				result.next();
				
				int kill = result.getInt("xp");
				
				return kill;
			}
			else{
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `player_data` values(?,0,0,0,0)");
				newPlayer.setString(1, player);
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
			
			if(Main.getMySQLManager().playerDataContainsPlayer(Bukkit.getPlayer(player))){
				
				PreparedStatement sql = connection.prepareStatement("SELECT xp FROM `player_data` WHERE player = ?;");
				sql.setString(1, player);
				
				ResultSet result = sql.executeQuery();
				result.next();
				
				PreparedStatement updateKills = connection.prepareStatement("UPDATE `player_data` SET xp=? WHERE player = ?;");
				updateKills.setInt(1, money);
				updateKills.setString(2, player);
				updateKills.executeUpdate();
			
				new BukkitRunnable(){

					@Override
					public void run() {
							
						if(getXp(player) >= getlevel(player) * 100){
							try {
								updateKills.setInt(1, 0);
								addLevel(player, 1);
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
						
					}
					
				}.runTaskTimerAsynchronously(Main.getInstance(), 0, 1);
				
				updateKills.close();
				sql.close();
				result.close();
				
			}
			else{
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `player_data` values(?,0,0,0,0)");
				newPlayer.setString(1, player);
				newPlayer.execute();
				newPlayer.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void addXp(String player, int money) {
		
		try {

			if (Main.getMySQLManager().playerDataContainsPlayer(Bukkit.getPlayer(player))) {

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
			} else {
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `player_data` values(?,0,0,0,0)");
				newPlayer.setString(1, player);
				newPlayer.execute();
				newPlayer.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public int getlevel(String player){
		try {
			
			if(Main.getMySQLManager().playerDataContainsPlayer(Bukkit.getPlayer(player))){

				PreparedStatement sql = connection.prepareStatement("SELECT level FROM `player_data` WHERE player = ?;");
				sql.setString(1, player);
				
				ResultSet result = sql.executeQuery();
				result.next();
				
				int kill = result.getInt("level");
				
				return kill;
			}
			else{
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `player_data` values(?,0,0,0,0)");
				newPlayer.setString(1, player);
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
			
			if(Main.getMySQLManager().playerDataContainsPlayer(Bukkit.getPlayer(player))){
				
				PreparedStatement sql = connection.prepareStatement("SELECT level FROM `player_data` WHERE player = ?;");
				sql.setString(1, player);
				
				ResultSet result = sql.executeQuery();
				result.next();
				
				PreparedStatement updateKills = connection.prepareStatement("UPDATE `player_data` SET level=? WHERE player = ?;");
				updateKills.setInt(1, money);
				updateKills.setString(2, player);
				updateKills.executeUpdate();
			
				updateKills.close();
				sql.close();
				result.close();
				
			}
			else{
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `player_data` values(?,0,0,0,0)");
				newPlayer.setString(1, player);
				newPlayer.execute();
				newPlayer.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void addLevel(String player, int money) {
		
		try {

			if (Main.getMySQLManager().playerDataContainsPlayer(Bukkit.getPlayer(player))) {

				PreparedStatement sql = connection
						.prepareStatement("SELECT level FROM `player_data` WHERE player = ?;");
				sql.setString(1, player);

				ResultSet result = sql.executeQuery();
				result.next();

				int kill = result.getInt("level");

				PreparedStatement updateKills = connection
						.prepareStatement("UPDATE `player_data` SET level=? WHERE player = ?;");
				updateKills.setInt(1, kill + money);
				if(money >= 100 || kill >= 100){
					updateKills.setInt(1, 100);
				}
				updateKills.setString(2, player);
				updateKills.executeUpdate();
				
				updateKills.close();
				sql.close();
				result.close();
				
			} else {
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `player_data` values(?,0,0,0,0)");
				newPlayer.setString(1, player);
				newPlayer.execute();
				newPlayer.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

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
	
}
