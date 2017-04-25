package kitpvp.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import kitpvp.Main;
import kitpvp.MySQL.MySQLManager;
import kitpvp.commands.vip.PrefixCommand.NameColor;
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
	public static  HashMap<String, String> lang = new HashMap<String, String>();
	
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

				Statement statement = connection.createStatement();
				ResultSet result = statement.executeQuery("SELECT * FROM `player_data` WHERE player = '" + player + "';");
				
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
		
		if(kills.containsKey(p)){
			return kills.get(p);
		}
		return 0;
	}
	
	public void setKills(String player, int value){
		kills.put(player, value);
	}
	
	public void addKills(String player, int value){
		if(kills.containsKey(player)){
			kills.put(player, kills.get(player) + value);
		}
		else{
			kills.put(player, value);
		}
	}
	
	public void getDeathsDB(String player) {
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

				Statement statement = connection.createStatement();
				ResultSet result = statement
						.executeQuery("SELECT * FROM `player_data` WHERE player = '" + player + "';");

				while (result.next()) {
					int kill = result.getInt("deaths");
					deaths.put(player, kill);
				}
			} else {
				m.putPlayerToPlayerData(player);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getDeaths(String p){
		if(deaths.containsKey(p)){
			return deaths.get(p);
		}
		return 0;
	}
	
	public void setDeaths(String player, int value){
		
		deaths.put(player, value);
		
	}
	
	public void addDeaths(String player, int value){

		if(deaths.containsKey(player)){
			deaths.put(player, deaths.get(player) + value);
		}
		else{
			deaths.put(player, value);
		}
	}
	
	public void getBalanceDB(String player) {
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

				Statement statement = connection.createStatement();
				ResultSet result = statement
						.executeQuery("SELECT * FROM `player_data` WHERE player = '" + player + "';");

				while (result.next()) {
					int kill = result.getInt("balance");

					balance.put(player, kill);
				}
			} else {
				m.putPlayerToPlayerData(player);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public int getBalance(String p){
		if(balance.containsKey(p)){
			return balance.get(p);
		}
		return 0;
	}
	
	public void setBalance(String player, int value){
		balance.put(player, value);
	}
	
	public void addBalance(String player, int value) {
		if(balance.containsKey(player)){
			balance.put(player, balance.get(player) + value);
		}
		else{
			balance.put(player, value);
		}
	}
	
	public void getXpDB(String player) {
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

				Statement statement = connection.createStatement();
				ResultSet result = statement
						.executeQuery("SELECT * FROM `player_data` WHERE player = '" + player + "';");

				while (result.next()) {
					int kill = result.getInt("xp");
					xp.put(player, kill);
				}

			} else {
				m.putPlayerToPlayerData(player);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getXp(String p){
		if(xp.containsKey(p)){
			return xp.get(p);
		}
		return 0;
	}
	
	public void setXp(String player, int value){
		
		xp.put(player, value);
		
		new BukkitRunnable(){

			@Override
			public void run() {
				
				if(xp.get(player) >= level.get(player) * 100){
					levelUp(player);
					xp.put(player, xp.get(player) - (level.get(player) * 100));
					
					if(xp.get(player) <= 0){
						xp.put(player, 0);
						cancel();
					}
					
				}
				else if(xp.get(player) < level.get(player)  * 100){
					cancel();
				}
				
			}
			
		}.runTaskTimer(Main.getInstance(), 20, 20);
		
	}
	
	public void addXp(String player, int value) {
		
		if(xp.containsKey(player)){
			xp.put(player, xp.get(player) + value);
			
			new BukkitRunnable(){

				@Override
				public void run() {
					if(xp.get(player) >= level.get(player) * 100){
						levelUp(player);
						xp.put(player, xp.get(player) - (level.get(player) * 100));
						
						if(xp.get(player) <= 0){
							xp.put(player, 0);
							cancel();
						}
						
					}
					else if(xp.get(player) < level.get(player)  * 100){
						cancel();
					}
				}
				
			}.runTaskTimer(Main.getInstance(), 20, 20);
			
		}
		else{
			setXp(player, value);
		}
		
	}
	
	public void getLevelDB(String player) {
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

				Statement statement = connection.createStatement();
				ResultSet result = statement
						.executeQuery("SELECT * FROM `player_data` WHERE player = '" + player + "';");

				while (result.next()) {
					level.put(player, result.getInt("levels"));
				}

			} else {
				m.putPlayerToPlayerData(player);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getLevel(String p){
		if(level.containsKey(p)){
			return level.get(p);
		}
		return 0;
	}
	
	public void setlevel(String player, int value){
		
		level.put(player, value);
		
		if(value >= 50){
			level.put(player, 50);
		}
		else if(level.get(player) >= 50){
			level.put(player, 50);
		}
		
	}
	
	public void addLevel(String player, int value) {
		
		if(level.containsKey(player)){
			level.put(player, level.get(player) + value);
			
			if(value >= 50){
				level.put(player, 50);
			}
			else if(level.get(player) >= 50){
				level.put(player, 50);
			}
			
		}
		else{
			level.put(player, value);
			
			if(value >= 50){
				level.put(player, 50);
			}
			else if(level.get(player) >= 50){
				level.put(player, 50);
			}
			
		}
		
	}
	
	public void getLanguageDB(String player){
		MySQLManager m = new MySQLManager();
		try {
			if (m.getConnection().isClosed() || m.getConnection() == null) {
				m.openConnection();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {

			if (m.languageContainsPlayer(player)) {

				Statement statement = connection.createStatement();
				ResultSet result = statement
						.executeQuery("SELECT * FROM `language` WHERE player = '" + player + "';");

				while (result.next()) {
					lang.put(player, result.getString("lang"));
				}

			} else {
				m.putPlayerToLang(player);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setLanguage(String player, Language language){
		
		switch(language){
		
		case FINNISH:
			lang.put(player, "FIN");
			break;
		case ENGLISH:
			lang.put(player, "ENG");
			break;
		case DEFAULT:
			lang.put(player, "DEF");
			break;
		
		}
		
	}
	
	public Language getLanguage(String player){
		
		if(lang.get(player).equalsIgnoreCase("ENG")){
			return Language.ENGLISH;
		}
		else if(lang.get(player).equalsIgnoreCase("FIN")){
			return Language.FINNISH;
		}
		else if(lang.get(player).equalsIgnoreCase("DEF")){
			return Language.DEFAULT;
		}
		return null;
	}
	
	public String getRawLanguage(String player){
		Language language = getLanguage(player);
		String raw = "";
		
		switch (language) {
		case FINNISH:
			raw = "FIN";
			break;
		case ENGLISH:
			raw = "ENG";
			break;
		case DEFAULT:
			raw = "DEF";
			break;

		}
		
		return raw;
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

				receiver.sendMessage("ß7ßm------ßaßl Sinun tilastot ß7ßm------");
				receiver.sendMessage("ß7Tapot: ßa" + kills);
				receiver.sendMessage("ß7Kuolemat: ßa" + deaths);
				receiver.sendMessage("ß7K/D: ßa" + KD);
				receiver.sendMessage("ß7Rahat: ßa" + balance);
				receiver.sendMessage("ß7Level: ßa" + level + "/50");
				receiver.sendMessage("ß7XP: ßa" + xp + "/" + getLevel(target) * 100);
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
				receiver.sendMessage("ß7XP: ßa" + xp + "/" + getLevel(target) * 100);
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
			
			double KD = getKD(offTarget.getName());
			int balance = getBalance(offTarget.getName());
			int level = getLevel(offTarget.getName());
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
				receiver.sendMessage("ß7XP: ßa" + xp + "/" + getLevel(target) * 100);
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
				receiver.sendMessage("ß7XP: ßa" + xp + "/" + getLevel(offTarget.getName()) * 100);
				receiver.sendMessage("ß7Language: ßa" + String.valueOf(getLanguage(offTarget.getName())));
				receiver.sendMessage("ß7Playtime: ßa" + getPlayTime(offTarget.getName())); // Add this later on.
				receiver.sendMessage("ß7ßm---------------------------------------------");
			}
		}
		
	}
	
	public void levelUp(String player){
		
		addLevel(player, 1);
		
		if(getLevel(player) >= 50){
			setlevel(player, 50);
		}
		
		Player p = Bukkit.getPlayer(player);
		
		if(p.isOnline() && p != null){
			
			if(getLevel(player) == 10){
				if(getLanguage(p.getName()) == Language.FINNISH){
					ChatUtils.sendMessageWithPrefix(p, "ß7Ansaitsit ßc1 ß7tokenin!");
				}
				else if (getLanguage(p.getName()) == Language.ENGLISH){
					ChatUtils.sendMessageWithPrefix(p, "ß7You were given ßc1 ß7token!");
				}
				Main.getCosmeticManager().addTokens(player, 1);
			}
			else if(getLevel(player) == 20){
				if(getLanguage(p.getName()) == Language.FINNISH){
					ChatUtils.sendMessageWithPrefix(p, "ß7Ansaitsit ßc1 ß7tokenin!");
				}
				else if (getLanguage(p.getName()) == Language.ENGLISH){
					ChatUtils.sendMessageWithPrefix(p, "ß7You were given ßc1 ß7token!");
				}
				Main.getCosmeticManager().addTokens(player, 1);
			}
			else if(getLevel(player) == 30){
				if(getLanguage(p.getName()) == Language.FINNISH){
					ChatUtils.sendMessageWithPrefix(p, "ß7Ansaitsit ßc1 ß7tokenin!");
				}
				else if (getLanguage(p.getName()) == Language.ENGLISH){
					ChatUtils.sendMessageWithPrefix(p, "ß7You were given ßc1 ß7token!");
				}
				Main.getCosmeticManager().addTokens(player, 1);
			}
			else if(getLevel(player) == 40){
				if(getLanguage(p.getName()) == Language.FINNISH){
					ChatUtils.sendMessageWithPrefix(p, "ß7Ansaitsit ßc1 ß7tokenin!");
				}
				else if (getLanguage(p.getName()) == Language.ENGLISH){
					ChatUtils.sendMessageWithPrefix(p, "ß7You were given ßc1 ß7token!");
				}
				Main.getCosmeticManager().addTokens(player, 1);
			}
			else if(getLevel(player) == 50){
				if(getLanguage(p.getName()) == Language.FINNISH){
					ChatUtils.sendMessageWithPrefix(p, "ß7Ansaitsit ßc1 ß7tokenin!");
				}
				else if (getLanguage(p.getName()) == Language.ENGLISH){
					ChatUtils.sendMessageWithPrefix(p, "ß7You were given ßc1 ß7token!");
				}
				Main.getCosmeticManager().addTokens(player, 1);
			}
			
			if(getLanguage(p.getName()) == Language.FINNISH){
				ChatUtils.sendMessageWithPrefix(p, "ß7Ansaitsit uuden levelin! (ßc" + level.get(player) + "ß7)");
			}
			else if (getLanguage(p.getName()) == Language.ENGLISH){
				ChatUtils.sendMessageWithPrefix(p, "ß7You unlocked a new level! (ßc" + level.get(player) + "ß7)");
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
				ChatUtils.sendMessageWithPrefix(Bukkit.getPlayer(player), "ß7Sin‰ sait ßc" + value + " ß7uutta boosteria!");
			}
			if(getLanguage(player) == Language.ENGLISH){
				ChatUtils.sendMessageWithPrefix(Bukkit.getPlayer(player), "ß7You got ßc" + value + " ß7new boosters!");
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

	public void giveScoreboard(Player p){
		
		if(getLanguage(p.getName()) == Language.FINNISH){
			
			Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
			Objective obj = board.registerNewObjective("dash", "dummy");
			obj.setDisplaySlot(DisplaySlot.SIDEBAR);
			obj.setDisplayName("ßcßlFinskaCraft");
			
			Team viiva = board.registerNewTeam("paska");
			viiva.addEntry(ChatColor.ITALIC.toString());
			viiva.setPrefix("ß7ßm------------");
			obj.getScore(ChatColor.ITALIC.toString()).setScore(14);
			
			Team pelaajat = board.registerNewTeam("online");
			pelaajat.addEntry(ChatColor.BOLD.toString());
			pelaajat.setPrefix("ß7ï Pelaajat: ");
			pelaajat.setSuffix("ßc" + String.valueOf(Bukkit.getOnlinePlayers().size()));
			obj.getScore(ChatColor.BOLD.toString()).setScore(13);

			Team viiva1 = board.registerNewTeam("klffs");
			viiva1.addEntry(ChatColor.STRIKETHROUGH.toString());
			viiva1.setPrefix("ß7ßm------------");
			obj.getScore(ChatColor.STRIKETHROUGH.toString()).setScore(12);
			
			Team kills = board.registerNewTeam("kills");
			kills.addEntry(ChatColor.DARK_GRAY.toString());
			kills.setPrefix("ß7ï Tapot: ");
			kills.setSuffix("ßc" + getKills(p.getName()));
			obj.getScore(ChatColor.DARK_GRAY.toString()).setScore(11);
			
			Team deaths = board.registerNewTeam("deaths");
			deaths.addEntry(ChatColor.BLACK.toString());
			deaths.setPrefix("ß7ï Kuolemat: ");
			deaths.setSuffix("ßc" + getDeaths(p.getName()));
			obj.getScore(ChatColor.BLACK.toString()).setScore(10);

			Team kdr = board.registerNewTeam("kdr");
			kdr.addEntry(ChatColor.RESET.toString());
			kdr.setPrefix("ß7ï K/D: ");
			kdr.setSuffix("ßc" + String.valueOf(getKD(p.getName())));
			obj.getScore(ChatColor.RESET.toString()).setScore(9);
			
			Team viiva2 = board.registerNewTeam("gvfnsabf");
			viiva2.addEntry(ChatColor.DARK_BLUE.toString());
			viiva2.setPrefix("ß7ßm------------");
			obj.getScore(ChatColor.DARK_BLUE.toString()).setScore(8);
			
			Team balance = board.registerNewTeam("balance");
			balance.addEntry(ChatColor.YELLOW.toString());
			balance.setPrefix("ß7ï Raha: ");
			balance.setSuffix("ßc" + getBalance(p.getName()));
			obj.getScore(ChatColor.YELLOW.toString()).setScore(7);
			
			Team tokens = board.registerNewTeam("tokens");
			tokens.addEntry(ChatColor.LIGHT_PURPLE.toString());
			tokens.setPrefix("ß7ï Tokenit: ");
			tokens.setSuffix("ßc0");
			obj.getScore(ChatColor.LIGHT_PURPLE.toString()).setScore(6);
			
			Team viiva3 = board.registerNewTeam("ezpz");
			viiva3.addEntry(ChatColor.DARK_PURPLE.toString());
			viiva3.setPrefix("ß7ßm------------");
			obj.getScore(ChatColor.DARK_PURPLE.toString()).setScore(5);
			
			Team lvl = board.registerNewTeam("lvl");
			lvl.addEntry(ChatColor.UNDERLINE.toString());
			lvl.setPrefix("ß7ï Lvl: ");
			lvl.setSuffix("ßc" + getLevel(p.getName()));
			obj.getScore(ChatColor.UNDERLINE.toString()).setScore(4);
			
			Team xp = board.registerNewTeam("xp");
			xp.addEntry(ChatColor.DARK_GREEN.toString());
			xp.setPrefix("ß7ï XP: ");
			xp.setSuffix("ßc" + getXp(p.getName()) + "/" + getLevel(p.getName()) * 100);
			obj.getScore(ChatColor.DARK_GREEN.toString()).setScore(3);
			
			new BukkitRunnable(){

				@Override
				public void run() {
					if(!Main.getDataFile().getBoolean(p.getUniqueId().toString() + ".scoreboard")){ 
						p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
						cancel();
						return; 
					}
					
					board.getTeam("online").setSuffix("ßc" + String.valueOf(Bukkit.getOnlinePlayers().size()));
					board.getTeam("kills").setSuffix("ßc" + String.valueOf(getKills(p.getName())));
					board.getTeam("deaths").setSuffix("ßc" + String.valueOf(getDeaths(p.getName())));
					board.getTeam("kdr").setSuffix("ßc" + String.valueOf(getKD(p.getName())));
					board.getTeam("balance").setSuffix("ßc" + String.valueOf(getBalance(p.getName())));
					board.getTeam("lvl").setSuffix("ßc" + String.valueOf(getLevel(p.getName())));
					board.getTeam("xp").setSuffix("ßc" + String.valueOf(getXp(p.getName()) + "/" + getLevel(p.getName()) * 100));
					board.getTeam("tokens").setSuffix("ßc" + String.valueOf(Main.getCosmeticManager().getTokens(p.getName())));
					
					
				}
				
			}.runTaskTimerAsynchronously(Main.getInstance(), 20, 60);
			p.setScoreboard(board);
			return;
		}
		else if (getLanguage(p.getName()) == Language.ENGLISH){
			
			Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
			Objective obj = board.registerNewObjective("dash", "dummy");
			obj.setDisplaySlot(DisplaySlot.SIDEBAR);
			obj.setDisplayName("ßcßlFinskaCraft");
			
			Team viiva = board.registerNewTeam("paska");
			viiva.addEntry(ChatColor.ITALIC.toString());
			viiva.setPrefix("ß7ßm------------");
			obj.getScore(ChatColor.ITALIC.toString()).setScore(14);
			
			Team pelaajat = board.registerNewTeam("online");
			pelaajat.addEntry(ChatColor.BOLD.toString());
			pelaajat.setPrefix("ß7ï Online: ");
			pelaajat.setSuffix("ßc" + String.valueOf(Bukkit.getOnlinePlayers().size()));
			obj.getScore(ChatColor.BOLD.toString()).setScore(13);

			Team viiva1 = board.registerNewTeam("vdsfnsj");
			viiva1.addEntry(ChatColor.STRIKETHROUGH.toString());
			viiva1.setPrefix("ß7ßm------------");
			obj.getScore(ChatColor.STRIKETHROUGH.toString()).setScore(12);
			
			Team kills = board.registerNewTeam("kills");
			kills.addEntry(ChatColor.DARK_GRAY.toString());
			kills.setPrefix("ß7ï Kills: ");
			kills.setSuffix("ßc" + getKills(p.getName()));
			obj.getScore(ChatColor.DARK_GRAY.toString()).setScore(11);
			
			Team deaths = board.registerNewTeam("deaths");
			deaths.addEntry(ChatColor.BLACK.toString());
			deaths.setPrefix("ß7ï Deaths: ");
			deaths.setSuffix("ßc" + getDeaths(p.getName()));
			obj.getScore(ChatColor.BLACK.toString()).setScore(10);

			Team kdr = board.registerNewTeam("kdr");
			kdr.addEntry(ChatColor.RESET.toString());
			kdr.setPrefix("ß7ï K/D: ");
			kdr.setSuffix("ßc" + String.valueOf(getKD(p.getName())));
			obj.getScore(ChatColor.RESET.toString()).setScore(9);
			
			Team viiva2 = board.registerNewTeam("klffs");
			viiva2.addEntry(ChatColor.DARK_PURPLE.toString());
			viiva2.setPrefix("ß7ßm------------");
			obj.getScore(ChatColor.DARK_PURPLE.toString()).setScore(8);
			
			Team balance = board.registerNewTeam("balance");
			balance.addEntry(ChatColor.UNDERLINE.toString());
			balance.setPrefix("ß7ï Balance: ");
			balance.setSuffix("ßc" + getBalance(p.getName()));
			obj.getScore(ChatColor.UNDERLINE.toString()).setScore(7);
			
			Team tokens = board.registerNewTeam("tokens");
			tokens.addEntry(ChatColor.DARK_RED.toString());
			tokens.setPrefix("ß7ï Tokens: ");
			tokens.setSuffix("ßc0");
			obj.getScore(ChatColor.DARK_RED.toString()).setScore(6);
			
			Team viiva3 = board.registerNewTeam("ezpz");
			viiva3.addEntry(ChatColor.DARK_AQUA.toString());
			viiva3.setPrefix("ß7ßm------------");
			obj.getScore(ChatColor.DARK_AQUA.toString()).setScore(5);
			
			Team lvl = board.registerNewTeam("lvl");
			lvl.addEntry(ChatColor.MAGIC.toString());
			lvl.setPrefix("ß7ï Lvl: ");
			lvl.setSuffix("ßc" + getLevel(p.getName()));
			obj.getScore(ChatColor.MAGIC.toString()).setScore(4);
			
			Team xp = board.registerNewTeam("xp");
			xp.addEntry(ChatColor.DARK_GREEN.toString());
			xp.setPrefix("ß7ï XP: ");
			xp.setSuffix("ßc" + getXp(p.getName()) + "/" + getLevel(p.getName()) * 100);
			obj.getScore(ChatColor.DARK_GREEN.toString()).setScore(3);
			
			new BukkitRunnable(){

				@Override
				public void run() {
					if(!Main.getDataFile().getBoolean(p.getUniqueId().toString() + ".scoreboard")){ 
						p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
						cancel();
						return; 
					}
					
					board.getTeam("online").setSuffix("ßc" + String.valueOf(Bukkit.getOnlinePlayers().size()));
					board.getTeam("kills").setSuffix("ßc" + String.valueOf(getKills(p.getName())));
					board.getTeam("deaths").setSuffix("ßc" + String.valueOf(getDeaths(p.getName())));
					board.getTeam("kdr").setSuffix("ßc" + String.valueOf(getKD(p.getName())));
					board.getTeam("balance").setSuffix("ßc" + String.valueOf(getBalance(p.getName())));
					board.getTeam("lvl").setSuffix("ßc" + String.valueOf(getLevel(p.getName())));
					board.getTeam("xp").setSuffix("ßc" + String.valueOf(getXp(p.getName()) + "/" + getLevel(p.getName()) * 100));
					board.getTeam("tokens").setSuffix("ßc" + String.valueOf(Main.getCosmeticManager().getTokens(p.getName())));
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
	
	public void createSkullItem(Inventory inv, int pos, String targetName, String displayName, List<String> lore){
		ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setOwner(targetName);
		meta.setDisplayName(displayName);
		meta.setLore(lore);
		item.setItemMeta(meta);
		inv.setItem(pos, item);
	}
	
}
