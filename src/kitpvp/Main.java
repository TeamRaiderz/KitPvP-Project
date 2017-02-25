package kitpvp;

import java.io.File;
import java.io.IOException;

import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

	private static File messagesFile = null;
	public static Main instance;
	public static Main getInstance(){ return instance; }
	
	public void onEnable(){
		
		instance = this;
		
		saveDefaultConfig();
		saveDefaultMsgFile();
		
	}
	
	public void onDisable(){
		
		instance = null;
		
	}
	
	public static FileConfiguration getMsgFile(){
		return YamlConfiguration.loadConfiguration(messagesFile);
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
	
	private void saveDefaultMsgFile(){
		if(messagesFile == null){
			messagesFile = new File(getDataFolder(), "messages.yml");
		}
		if(!(messagesFile.exists())){
			saveResource("messages.yml", false);
		}
		
	}
	
}
