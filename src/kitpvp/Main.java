package kitpvp;

import java.io.File;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import kitpvp.MySQL.MySQLManager;
import kitpvp.Util.DataYML;
import kitpvp.Util.KitAPI;
import kitpvp.Util.KitsYML;
import kitpvp.Util.PacketUtils;
import kitpvp.commands.CommandCommands;
import kitpvp.commands.CommandDB;
import kitpvp.commands.CommandDiscord;
import kitpvp.commands.CommandHelp;
import kitpvp.commands.CommandKill;
import kitpvp.commands.CommandLevelToggle;
import kitpvp.commands.CommandList;
import kitpvp.commands.CommandMsg;
import kitpvp.commands.CommandRename;
import kitpvp.commands.CommandStats;
import kitpvp.commands.CommandTest;
import kitpvp.commands.KitCommand;
import kitpvp.commands.LangCommand;
import kitpvp.commands.PrefixCommand;
import kitpvp.listeners.ChatEvent;
import kitpvp.listeners.ConnectionListener;
import kitpvp.listeners.DamageListener;
import kitpvp.listeners.PlayerListeners;
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
		
		registerListener(this, new ConnectionListener());
		registerListener(this, new PrefixCommand());
		registerListener(this, new ChatFormat());
	//	registerListener(this, new AbilityListener());
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
		
		for(Player online : Bukkit.getOnlinePlayers()){
			getAPI().startPlayTimeCount(online);
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
		
	}
	
	public void onDisable(){
		
		instance = null;
		
		MySQLManager.disable();
		
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
	
	public static FileConfiguration getDataFile(){
		return DataYML.getFile();
	}
	
	public static FileConfiguration getKitFile(){
		return KitsYML.getFile();
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
		KitsYML.saveFile();
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
	
}
