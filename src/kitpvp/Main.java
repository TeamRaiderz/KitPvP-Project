package kitpvp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import kitpvp.MySQL.MySQLManager;
import kitpvp.Util.DataYML;
import kitpvp.Util.KitAPI;
import kitpvp.Util.KitsYML;
import kitpvp.commands.KitCommand;
import kitpvp.listeners.ConnectionListener;

public class Main extends JavaPlugin{

	private static File messagesFile = null;
	private static FileConfiguration messagesConfig = null;
	public static Main instance;
	public static Main getInstance(){ return instance; }
	
	public void onEnable(){
		
		instance = this;
		
		saveDefaultConfig();
		saveDefaultMsgFile();
		
		getMySQLManager().setupDataBase();
		
		registerCommand("kit", new KitCommand());
		registerListener(this, new ConnectionListener());
		
	}
	
	public void onDisable(){
		
		instance = null;
		
	}
	
	public static FileConfiguration getMsgFile(){
		if (messagesConfig == null) {

			reloadMessagesFile();
		}
		return messagesConfig;
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
	
	public static void saveMsgFile(){
		try {
			getMsgFile().save(messagesFile);
		} catch (IOException e) {
			System.err.println("Could not save messages.yml file");
			e.printStackTrace();
		}
	}
	
	public static void reloadMessagesFile(){
		if(messagesFile == null){
			messagesFile = new File(Bukkit.getPluginManager().getPlugin("KitPvP").getDataFolder(), "messages.yml");
		}
		
		messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
		
		InputStream defConfigStream = Bukkit.getPluginManager().getPlugin("KitPvP").getResource("messages.yml");
		if(defConfigStream != null){
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			if(!(messagesFile.exists() || messagesFile.length() == 0L)){
				messagesConfig.setDefaults(defConfig);
			}
		}
	}
	
	public static void saveKitFile(){
		KitsYML.saveFile();
	}
	
	public static void saveDataFile(){
		DataYML.saveFile();
	}
	
	private void saveDefaultMsgFile(){
		if(messagesFile == null){
			messagesFile = new File(getDataFolder(), "messages.yml");
		}
		if(!(messagesFile.exists())){
			saveResource("messages.yml", false);
		}
		
	}
	
	public static MySQLManager getMySQLManager(){
		return new MySQLManager();
	}
	
	public static KitAPI getAPI(){
		return new KitAPI();
	}
	
}
