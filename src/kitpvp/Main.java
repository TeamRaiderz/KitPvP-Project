package kitpvp;

import java.io.File;
import java.io.IOException;

import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import kitpvp.MySQL.MySQLManager;
import kitpvp.Util.KitAPI;
import kitpvp.commands.KitCommand;

public class Main extends JavaPlugin{

	private static File messagesFile = null;
	private static File dataFile = null;
	private static File kitFile = null;
	public static Main instance;
	public static Main getInstance(){ return instance; }
	
	public void onEnable(){
		
		instance = this;
		
		saveDefaultConfig();
		saveDefaultMsgFile();
		saveDefaultDataFile();
		saveDefaultKitFile();
		
		getMySQLManager().setupDataBase();
		
		registerCommand("kit", new KitCommand());
		
	}
	
	public void onDisable(){
		
		instance = null;
		
	}
	
	public static FileConfiguration getMsgFile(){
		return YamlConfiguration.loadConfiguration(messagesFile);
	}
	
	public static FileConfiguration getDataFile(){
		return YamlConfiguration.loadConfiguration(dataFile);
	}
	
	public static FileConfiguration getKitFile(){
		return YamlConfiguration.loadConfiguration(kitFile);
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
	
	public static void saveMsgFile(){
		try {
			getMsgFile().save(messagesFile);
		} catch (IOException e) {
			System.err.println("Could not save messages.yml file");
			e.printStackTrace();
		}
	}
	
	public static void saveKitFile(){
		try {
			getMsgFile().save(kitFile);
		} catch (IOException e) {
			System.err.println("Could not save kits.yml file");
			e.printStackTrace();
		}
	}
	
	public static void saveDataFile(){
		try {
			getMsgFile().save(dataFile);
		} catch (IOException e) {
			System.err.println("Could not save data.yml file");
			e.printStackTrace();
		}
	}
	
	private void saveDefaultMsgFile(){
		if(messagesFile == null){
			messagesFile = new File(getDataFolder(), "messages.yml");
		}
		if(!(messagesFile.exists())){
			saveResource("messages.yml", false);
		}
		
	}
	
	private void saveDefaultDataFile(){
		if(messagesFile == null){
			messagesFile = new File(getDataFolder(), "data.yml");
		}
		if(!(messagesFile.exists())){
			saveResource("messages.yml", false);
		}
		
	}
	
	private void saveDefaultKitFile(){
		if(messagesFile == null){
			messagesFile = new File(getDataFolder(), "kits.yml");
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
