package kitpvp;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import kitpvp.MySQL.MySQLManager;
import kitpvp.StaffMode.StaffMode;
import kitpvp.StaffMode.StaffModeCommand;
import kitpvp.StaffMode.StaffModeListeners;
import kitpvp.Util.DataYML;
import kitpvp.Util.KitAPI;
import kitpvp.Util.AchievementsYML;
import kitpvp.Util.PacketUtils;
import kitpvp.commands.admin.CommandDB;
import kitpvp.commands.admin.CommandKill;
import kitpvp.commands.admin.CommandPlayerInfo;
import kitpvp.commands.admin.CommandSetspawn;
import kitpvp.commands.admin.CommandTest;
import kitpvp.commands.admin.KitCommand;
import kitpvp.commands.admin.TitleCommand;
import kitpvp.commands.essential.CommandCommands;
import kitpvp.commands.essential.CommandDiscord;
import kitpvp.commands.essential.CommandHelp;
import kitpvp.commands.essential.CommandList;
import kitpvp.commands.essential.CommandMsg;
import kitpvp.commands.essential.CommandSpawn;
import kitpvp.commands.essential.CommandStats;
import kitpvp.commands.essential.LangCommand;
import kitpvp.commands.other.CommandRename;
import kitpvp.commands.staff.CommandAnswer;
import kitpvp.commands.staff.CommandLevelToggle;
import kitpvp.commands.staff.CommandTP;
import kitpvp.commands.vip.PrefixCommand;
import kitpvp.cosmetics.CSGOCrate;
import kitpvp.cosmetics.CosmeticBox;
import kitpvp.cosmetics.CosmeticManager;
import kitpvp.cosmetics.menus.ArrowtrailMenu;
import kitpvp.cosmetics.menus.CrateCaveMenu;
import kitpvp.cosmetics.menus.DeatheffectMenu;
import kitpvp.cosmetics.menus.ParticleMenu;
import kitpvp.kits.KitManager;
import kitpvp.listeners.AbilityListener;
import kitpvp.listeners.ArenaEvents;
import kitpvp.listeners.ChatEvent;
import kitpvp.listeners.ConnectionListener;
import kitpvp.listeners.DamageListener;
import kitpvp.listeners.KitMenuListener;
import kitpvp.listeners.LevelListeners;
import kitpvp.listeners.ParticleListeners;
import kitpvp.listeners.PlayerListeners;
import kitpvp.other.Booster;
import kitpvp.other.ChatFormat;
import kitpvp.other.Info;
import kitpvp.other.Mail;
import kitpvp.other.Profile;
import kitpvp.other.Settings;
import kitpvp.other.SpawnItems;
import kitpvp.other.Staff;
import kitpvp.punishment.BlacklistCommand;
import kitpvp.punishment.PunishCommand;
import kitpvp.punishment.PunishmentManager;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class Main extends JavaPlugin implements Plugin{
	
	public static Main instance;
	public static Main getInstance(){ return instance; }
	private static Economy econ;
	private static Permission permission;
	private static Chat chat;
	
	public void onEnable(){
		
		instance = this;
		
		getMySQLManager().openConnection();
		
		saveDefaultConfig();
		
		setupEconomy();
		setupPermissions();
		setupChat();
		
		registerCommand("kit", new KitCommand());
		registerCommand("prefix", new PrefixCommand());
		registerCommand("nick", new PrefixCommand());
		registerCommand("lang", new LangCommand());
		registerCommand("kieli", new LangCommand());
		registerCommand("test", new CommandTest());
		registerCommand("stats", new CommandStats());
		registerCommand("db", new CommandDB());
		registerCommand("pvpkill", new CommandKill());
		registerCommand("settings", new Settings());
		registerCommand("asetukset", new Settings());
		registerCommand("msg", new CommandMsg());
		registerCommand("tell", new CommandMsg());
		registerCommand("whisper", new CommandMsg());
		registerCommand("list", new CommandList());
		registerCommand("profile", new Profile());
		registerCommand("serverinfo", new Info());
		registerCommand("?", new Info());
		registerCommand("booster", new Booster());
		registerCommand("punish", new PunishCommand());
		registerCommand("blacklist", new BlacklistCommand());
		registerCommand("unblacklist", new BlacklistCommand());
		registerCommand("rename", new CommandRename());
		registerCommand("lvl", new CommandLevelToggle());
		registerCommand("discord", new CommandDiscord());
		registerCommand("help", new CommandHelp());
		registerCommand("commands", new CommandCommands());
		registerCommand("playerinfo", new CommandPlayerInfo());
		registerCommand("answer", new CommandAnswer());
		registerCommand("spawn", new CommandSpawn());
		registerCommand("setspawn", new CommandSetspawn());
		registerCommand("tp", new CommandTP());
		registerCommand("staff", new Staff());
		registerCommand("mail", new Mail());
		registerCommand("staffmode", new StaffModeCommand());
		registerCommand("subtitle", new TitleCommand());
		registerCommand("title", new TitleCommand());
		registerCommand("crates", new CrateCaveMenu());
		
		registerListener(this, new ConnectionListener());
		registerListener(this, new PrefixCommand());
		registerListener(this, new ChatFormat());
		registerListener(this, new AbilityListener());
		registerListener(this, new DamageListener());
		registerListener(this, new LangCommand());
		registerListener(this, new SpawnItems());
		registerListener(this, new Settings());
		registerListener(this, new ChatEvent());
		registerListener(this, new Profile());
		registerListener(this, new Info());
		registerListener(this, new Booster());
		registerListener(this, new PunishmentManager());
		registerListener(this, new PunishCommand());
		registerListener(this, new PlayerListeners());
		registerListener(this, new KitMenuListener());
		registerListener(this, new Staff());
		registerListener(this, new Mail());
		registerListener(this, new StaffModeListeners());
		registerListener(this, new LevelListeners());
		registerListener(this, new CosmeticBox());
		registerListener(this, new ArenaEvents());
		registerListener(this, new CSGOCrate());
		registerListener(this, new ParticleListeners());
		registerListener(this, new ArrowtrailMenu());
		registerListener(this, new CrateCaveMenu());
		registerListener(this, new ParticleMenu());
		registerListener(this, new DeatheffectMenu());
		
		for(Player online : Bukkit.getOnlinePlayers()){
//			getAPI().startPlayTimeCount(online);
			
			if(Main.getDataFile().getBoolean(online.getUniqueId().toString() + ".scoreboard")){ 
				getAPI().giveScoreboard(online);
			}
		}
		
		new BukkitRunnable(){

			@Override
			public void run() {
				if(getMySQLManager().getConnection() == null){
					getMySQLManager().openConnection();
				}
			}
			
		}.runTaskTimerAsynchronously(this, 20, 20 * 60 * 5);
		
		if(getAPI().isBoosterInUse()){
			getAPI().startBoosterCountdown();
		}
	
		for(World w : Bukkit.getWorlds()){
			while(w.getTime() < 1000 || w.getTime() > 1000){
				w.setTime(1000);
			}
		}
		
		
	}
	
	
	public void onDisable(){
		
		MySQLManager.disable();
		
		instance = null;
		
	}
	
	public static StaffMode getStaffModeManager(){
		return new StaffMode();
	}
	
	private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
        	System.out.println("This plugin needs the plugin Vault to work!");
            return false;
            
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
	private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }
	 private boolean setupChat()
	    {
	        RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
	        if (chatProvider != null) {
	            chat = chatProvider.getProvider();
	        }

	        return (chat != null);
	    }
	
	public static Economy getEconomy(){
		return econ;
	}
	
	public static Permission getPermissions(){
		return permission;
	}
	
	public static Chat getChat(){
		return chat;
	}
	
	public static KitManager getKitManager(){
		return new KitManager();
	}
	
	public static FileConfiguration getDataFile(){
		return DataYML.getFile();
	}
	
	public static FileConfiguration getKitFile(){
		return AchievementsYML.getFile();
	}
	public static FileConfiguration getConfigFile(){
		return Main.getInstance().getConfig();
	}
	
	public static File getPluginFolder(){
		return Main.getInstance().getDataFolder();
	}
	
	public static void registerCommand(String command, CommandExecutor cmdClass){
		Main.getInstance().getCommand(command).setExecutor(cmdClass);
	}
	
	public static void registerListener(Plugin mainClass, Listener eventClass){
		Main.getInstance().getServer().getPluginManager().registerEvents(eventClass, mainClass);
	}
	
	public static void saveKitFile(){
		AchievementsYML.saveFile();
	}
	
	public static void saveDataFile(){
		DataYML.saveFile();
	}
	
	public static MySQLManager getMySQLManager(){
		return new MySQLManager();
	}
	
	public static KitAPI getAPI(){
		return new KitAPI();
	}
	
	public static PacketUtils getPacketUtils(){
		return new PacketUtils();
	}
	
	public static PunishmentManager getPunishmentManager(){
		return new PunishmentManager();
	}
	
	public static CosmeticManager getCosmeticManager(){
		return new CosmeticManager();
	}
	
	public void sendErrorMessage(Exception e){
		System.err.println("(!) An error happened in the plugin. Here's the stack trace ->");
		System.err.println("(!) Msg: " + e.getMessage() + " Caused by: " + e.getCause() +
				" Happened at: " + e.getStackTrace()[0].getClassName() + ":" +  e.getStackTrace()[0].getLineNumber() + " (" + e.getStackTrace()[0].getMethodName() + ")");
	}
	
	public static FileConfiguration getAchievementsFile(){
		return AchievementsYML.getFile();
	}
	
	public static void saveAchievementsFile(){
		AchievementsYML.saveFile();
	}
	
}
