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
import kitpvp.commands.LangCommand;
import kitpvp.commands.PrefixCommand;
import kitpvp.listeners.AbilityListener;
import kitpvp.listeners.ConnectionListener;

public class Main extends JavaPlugin{
	
	public static Main instance;
	public static Main getInstance(){ return instance; }
	
	public void onEnable(){
		
		instance = this;
		
		getMySQLManager().openConnection();
		
		saveDefaultConfig();
		
		registerCommand("kit", new KitCommand());
		registerCommand("prefix", new PrefixCommand());
		registerCommand("nick", new PrefixCommand());
		registerCommand("lang", new LangCommand());
		registerCommand("kieli", new LangCommand());
		
		registerListener(this, new ConnectionListener());
		registerListener(this, new PrefixCommand());
		registerListener(this, new ChatFormat());
	//	registerListener(this, new AbilityListener());
		
	}
	
	public void onDisable(){
		
		instance = null;
		
		MySQLManager.disable();
		
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
	
}
