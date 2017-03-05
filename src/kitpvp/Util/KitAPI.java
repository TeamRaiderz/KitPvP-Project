package kitpvp.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import net.minecraft.server.v1_8_R3.NBTTagCompound;

public class KitAPI {
	
	private Connection connection = Main.getMySQLManager().getConnection();
	private HashMap<String, Integer> killStreak = new HashMap<String, Integer>();
	
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
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `player_data` values(?,0,0,0,0,0)");
				newPlayer.setString(1, Bukkit.getPlayer(player).getName());
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
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `player_data` values(?,0,0,0,0,0)");
				newPlayer.setString(1, Bukkit.getPlayer(player).getName());
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
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `player_data` values(?,0,0,0,0,0)");
				newPlayer.setString(1, Bukkit.getPlayer(player).getName());
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
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `player_data` values(?,0,0,0,0,0)");
				newPlayer.setString(1, Bukkit.getPlayer(player).getName());
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
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `player_data` values(?,0,0,0,0,0)");
				newPlayer.setString(1, Bukkit.getPlayer(player).getName());
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
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `player_data` values(?,0,0,0,0,0)");
				newPlayer.setString(1, Bukkit.getPlayer(player).getName());
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
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `player_data` values(?,0,0,0,0,0)");
				newPlayer.setString(1, Bukkit.getPlayer(player).getName());
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
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `player_data` values(?,0,0,0,0,0)");
				newPlayer.setString(1, Bukkit.getPlayer(player).getName());
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
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `player_data` values(?,0,0,0,0,0)");
				newPlayer.setString(1, Bukkit.getPlayer(player).getName());
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
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `player_data` values(?,0,0,0,0,0)");
				newPlayer.setString(1, Bukkit.getPlayer(player).getName());
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
				newPlayer.setString(1, Bukkit.getPlayer(player).getName());
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
				newPlayer.setString(1, Bukkit.getPlayer(player).getName());
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

				PreparedStatement sql = connection.prepareStatement("SELECT levels FROM `player_data` WHERE player = ?;");
				sql.setString(1, player);
				
				ResultSet result = sql.executeQuery();
				result.next();
				
				int kill = result.getInt("levels");
				
				return kill;
			}
			else{
				PreparedStatement newPlayer = connection.prepareStatement("INSERT `player_data` values(?,0,0,0,0,0)");
				newPlayer.setString(1, Bukkit.getPlayer(player).getName());
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
				newPlayer.setString(1, Bukkit.getPlayer(player).getName());
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
				newPlayer.setString(1, Bukkit.getPlayer(player).getName());
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
	
	public void sendPlayerStats(CommandSender receiver, Player target){
		
		if(receiver == target){
			if(getLanguage(receiver.getName()) == Language.FINNISH){

				receiver.sendMessage("§7§m------§a§l Sinun statsit §7§m------");
				receiver.sendMessage("§7Tapot: §a" + getKills(target.getName()));
				receiver.sendMessage("§7Kuolemat: §a" + getDeaths(target.getName()));
				receiver.sendMessage("§7K/D: §a" + (double) getKills(target.getName()) / (double) getDeaths(target.getName()));
				receiver.sendMessage("§7Rahat: §a" + getBalance(target.getName()));
				receiver.sendMessage("§7Level: §a" + getlevel(target.getName()) + "/50");
				receiver.sendMessage("§7XP: §a" + getXp(target.getName()) + "/" + getlevel(target.getName()) * 100);
				receiver.sendMessage("§7Kieli: §a" + String.valueOf(getLanguage(target.getName())));
				receiver.sendMessage("§7Peliaika: §a0y 0m 0d 0h 0min 0s"); // Add this later on.
				receiver.sendMessage("§7§m---------------------------------------------");
				
			}
			else if (getLanguage(receiver.getName()) == Language.ENGLISH){
				receiver.sendMessage("§7§m------ §a§l Your stats §7§m------");
				receiver.sendMessage("§7Kills: §a" + getKills(target.getName()));
				receiver.sendMessage("§7Deaths: §a" + getDeaths(target.getName()));
				receiver.sendMessage("§7K/D: §a" + (double) getKills(target.getName()) / (double) getDeaths(target.getName()));
				receiver.sendMessage("§7Balance: §a" + getBalance(target.getName()));
				receiver.sendMessage("§7Level: §a" + getlevel(target.getName()) + "/50");
				receiver.sendMessage("§7XP: §a" + getXp(target.getName()) + "/" + getlevel(target.getName()) * 100);
				receiver.sendMessage("§7Language: §a" + String.valueOf(getLanguage(target.getName())));
				receiver.sendMessage("§7Playtime: §a0y 0m 0d 0h 0min 0s"); // Add this later on.
				receiver.sendMessage("§7§m---------------------------------------------");
			}
		}
		else{
			if(getLanguage(receiver.getName()) == Language.FINNISH){

				receiver.sendMessage("§7§m------§a§l Pelaajan " + target.getName() + " statsit §7§m------");
				receiver.sendMessage("§7Tapot: §a" + getKills(target.getName()));
				receiver.sendMessage("§7Kuolemat: §a" + getDeaths(target.getName()));
				receiver.sendMessage("§7K/D: §a" + (double) getKills(target.getName()) / (double) getDeaths(target.getName()));
				receiver.sendMessage("§7Rahat: §a" + getBalance(target.getName()));
				receiver.sendMessage("§7Level: §a" + getlevel(target.getName()) + "/50");
				receiver.sendMessage("§7XP: §a" + getXp(target.getName()) + "/" + getlevel(target.getName()) * 100);
				receiver.sendMessage("§7Kieli: §a" + String.valueOf(getLanguage(target.getName())));
				receiver.sendMessage("§7Peliaika: §a0y 0m 0d 0h 0min 0s"); // Add this later on.
				receiver.sendMessage("§7§m---------------------------------------------");
				
			}
			else if (getLanguage(receiver.getName()) == Language.ENGLISH){
				receiver.sendMessage("§7§m------§a§l Stats of " + target.getName() + " §7§m------");
				receiver.sendMessage("§7Kills: §a" + getKills(target.getName()));
				receiver.sendMessage("§7Deaths: §a" + getDeaths(target.getName()));
				receiver.sendMessage("§7K/D: §a" + (double) getKills(target.getName()) / (double) getDeaths(target.getName()));
				receiver.sendMessage("§7Balance: §a" + getBalance(target.getName()));
				receiver.sendMessage("§7Level: §a" + getlevel(target.getName()) + "/50");
				receiver.sendMessage("§7XP: §a" + getXp(target.getName()) + "/" + getlevel(target.getName()) * 100);
				receiver.sendMessage("§7Language: §a" + String.valueOf(getLanguage(target.getName())));
				receiver.sendMessage("§7Playtime: §a0y 0m 0d 0h 0min 0s"); // Add this later on.
				receiver.sendMessage("§7§m---------------------------------------------");
			}
		}
		
	}
	
	public void levelUp(String player){
		
		OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(player);
		
		if(offPlayer.isOnline()){
			Player p = offPlayer.getPlayer();
			
			if(getLanguage(p.getName()) == Language.FINNISH){
				ChatUtils.sendMessageWithPrefix(p, "§7Ansaitsit uuden levelin! (§c" + getlevel(p.getName()) + "§7)");
			}
			else if (getLanguage(p.getName()) == Language.ENGLISH){
				ChatUtils.sendMessageWithPrefix(p, "§7You unlocked a new level! (§c" + getlevel(p.getName()) + "§7)");
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
	
}
