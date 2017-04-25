package kitpvp.listeners;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import kitpvp.Main;
import kitpvp.MySQL.MySQLManager;
import kitpvp.Util.ChatUtils;
import kitpvp.Util.KitAPI;
import kitpvp.Util.Language;
import kitpvp.Util.TeleportManager;
import kitpvp.commands.vip.PrefixCommand.NameColor;
import kitpvp.cosmetics.CosmeticManager;
import kitpvp.other.SpawnItems;
import kitpvp.punishment.PunishmentManager;

public class ConnectionListener implements Listener{

	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		
		Player p = e.getPlayer();
		TeleportManager tm = new TeleportManager();
		
		tm.teleportSpawn(p);
		
		//Silenting zombies
		Iterator meta = p.getWorld().getLivingEntities().iterator();
		while (meta.hasNext()) {
			Entity ex = (Entity) meta.next();
			if (ex instanceof Zombie) {
				Main.getAPI().Silent(ex);
			}
		}
		
		
		if(Main.getDataFile().get(p.getUniqueId().toString()) == null){
			ChatUtils.broadcastWithPrefix("§c§lWelcome §7" + p.getName() + " §c§lto the server!");
			
			FileConfiguration data = Main.getDataFile();
			String uuid = p.getUniqueId().toString();
			
			data.set(uuid + ".currentName", p.getName());
			data.set(uuid + ".ipAddress", p.getAddress().getAddress().toString());
			
			//Default settings
			
			data.set(uuid + ".chatMention", true);
			data.set(uuid + ".scoreboard", true);
			data.set(uuid + ".chat", true);
			data.set(uuid + ".privateMsg", true);
			data.set(uuid + ".privateAccount", false);
			data.set(uuid + ".levelInChat", true);
			data.set(uuid + ".boosters", 0);
			
			Main.getAPI().setNick(p.getName(), NameColor.DEFAULT);
			
			Main.getDataFile().set(p.getName(), "DEF");
			
			Main.saveDataFile();
		}
		
		FileConfiguration data = Main.getDataFile();
		String uuid = p.getUniqueId().toString();
		
		data.set(uuid + ".currentName", p.getName());
		data.set(uuid + ".ipAddress", p.getAddress().getAddress().toString());
		Main.saveDataFile();
		
		if(!(SpawnItems.playersHidden.containsKey(p.getName()))){
			SpawnItems.playersHidden.put(p.getName(), false);
		}
		
		Main.getAPI().giveScoreboard(p);
		
		//Sending join title
		if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
			Main.getPacketUtils().sendTitle(p, "§a§lFINSKACRAFT!", "§7Suomen paskin servu!", 20, 40, 20);
		}
		else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
			Main.getPacketUtils().sendTitle(p, "§a§lFINSKACRAFT!", "§7Shittiest server in Finland!", 20, 40, 20);
		}
		
		new BukkitRunnable(){

			@Override
			public void run() {
				Main.getPacketUtils().sendTabHF(p, "§c§lFinskaCraft", "§7§o-- §c" + Bukkit.getOnlinePlayers().size() + "§7/§c" + Bukkit.getMaxPlayers() + " §7§o--"
						+ "\n §7IP: §cmc.finska.com"
						+ "\n §7Store: §cstore.finska.com");
			}
			
		}.runTaskTimerAsynchronously(Main.getInstance(), 20, 60);
		
//		Main.getAPI().startPlayTimeCount(p);
		
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		
		Player p = e.getPlayer();
		String name = p.getName();
		
		CosmeticManager cm = new CosmeticManager();
		KitAPI api = new KitAPI();
		PunishmentManager pm = new PunishmentManager();
		
		Main.getAPI().setLastLogin(p.getName());
		
		new BukkitRunnable(){

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
				
				//Put cached data of player to database ->
				
				try{
					
					if(m.cosmeticContainsPlayer(name) && m.playerDataContainsPlayer(name) && m.punishmentContainsPlayer(name)){

						PreparedStatement playerdata = m.getConnection().prepareStatement(
								"REPLACE INTO player_data VALUES (?, ?, ?, ?, ?, ?);");
						playerdata.setString(1, name);
						playerdata.setInt(2, api.getKills(name));
						playerdata.setInt(3, api.getDeaths(name));
						playerdata.setInt(4, api.getBalance(name));
						playerdata.setInt(5, api.getLevel(name));
						playerdata.setInt(6, api.getXp(name));
						playerdata.execute();
						playerdata.close();
						 
			            PreparedStatement cosmetic = m.getConnection().prepareStatement(
								"REPLACE INTO cosmetic_data VALUES (?, ?, ?, ?);");
						cosmetic.setString(1, name);
						cosmetic.setInt(2, cm.getTokens(name));
						cosmetic.setInt(3, cm.getChests(name));
						cosmetic.setInt(4, cm.getBoosters(name));
						cosmetic.execute();
			            cosmetic.close();
			            
			            PreparedStatement punishment = m.getConnection().prepareStatement(
								"REPLACE INTO punishments VALUES (?, ?, ?, ?);");
						punishment.setString(1, name);
						punishment.setInt(2, pm.getWarnings(name));
						punishment.setInt(3, pm.getBans(name));
						punishment.setInt(4, pm.getKicks(name));
						punishment.execute();
			            punishment.close();
			            
			            PreparedStatement lang = m.getConnection().prepareStatement(
								"REPLACE INTO language VALUES (?, ?);");
			            lang.setString(1, name);
			            lang.setString(2, api.getRawLanguage(name));
			            lang.execute();
			            lang.close();
			            
			            m.getConnection().close();
			            
			            System.out.println(name + "-> Tokens: " + cm.getTokens(name) + " Boosters: " + cm.getBoosters(name) + " Chests: " + cm.getChests(name) + " Balance: " + api.getBalance(name)
			   		 + " Deaths: " + api.getDeaths(name) + " Kills: " + api.getKills(name) + " Level: " + api.getLevel(name) + " Xp: " + api.getXp(name)
			   		  + " Bans: " + pm.getBans(name) + " Kicks: " + pm.getKicks(name) + " Warnings: " + pm.getWarnings(name));
					}
					else{
						m.putPlayerToCosmeticData(name);
						m.putPlayerToPlayerData(name);
						m.putPlayerToPunishments(name);
					}
					
				} catch(Exception ex){
					ex.printStackTrace();
				}
				
			}
			
		}.runTaskAsynchronously(Main.getInstance());
		
	}
	
	@EventHandler
	public void onAsyncLogin(AsyncPlayerPreLoginEvent e){
		
		MySQLManager m = new MySQLManager();
		try {
			if(m.getConnection().isClosed() || m.getConnection() == null){
				m.openConnection();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		CosmeticManager cm = new CosmeticManager();
		KitAPI api = new KitAPI();
		PunishmentManager pm = new PunishmentManager();
		
		String p = e.getName();
		
		cm.getBoostersDB(e.getName());
		cm.getChestsDB(e.getName());
		cm.getTokensDB(e.getName());
		api.getBalanceDB(e.getName());
		api.getDeathsDB(e.getName());
		api.getKillsDB(e.getName());
		api.getLevelDB(e.getName());
		api.getXpDB(e.getName());
		api.getLanguageDB(e.getName());
		pm.getBansDB(e.getName());
		pm.getKicksDB(e.getName());
		pm.getWarningsDB(e.getName());
		
		System.out.println(e.getName() + "-> Tokens: " + cm.getTokens(p) + " Boosters: " + cm.getBoosters(p) + " Chests: " + cm.getChests(p) + " Balance: " + api.getBalance(e.getName())
		 + " Deaths: " + api.getDeaths(e.getName()) + " Kills: " + api.getKills(e.getName()) + " Level: " + api.getLevel(e.getName()) + " Xp: " + api.getXp(e.getName())
		  + " Bans: " + pm.getBans(e.getName()) + " Kicks: " + pm.getKicks(e.getName()) + " Warnings: " + pm.getWarnings(e.getName()));
		
	}
	
}
