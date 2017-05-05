package kitpvp.Util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class AchievementsYML {

	public static YamlConfiguration pData = null;
	public static File pDataFile = null;
	
	@SuppressWarnings("deprecation")
	public static void reloadFile(){
		
	
	if(pDataFile == null){
		pDataFile = new File(Bukkit.getPluginManager().getPlugin("KitPvP").getDataFolder(), "achievements.yml");
	}
	
	pData = YamlConfiguration.loadConfiguration(pDataFile);
	
	InputStream defConfigStream = Bukkit.getPluginManager().getPlugin("KitPvP").getResource("achievements.yml");
	if(defConfigStream != null){
		YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
		if(!(pDataFile.exists() || pDataFile.length() == 0L)){
			pData.setDefaults(defConfig);
		}
	}
	}
	
	public static FileConfiguration getFile(){
		
		if(pData == null){
			
			reloadFile();
		}
		return pData;
	}
	
	public static void saveFile(){
		if(pData == null || pDataFile == null){
			return;
		}
		try{
			getFile().save(pDataFile);
		}catch(IOException ex){
			ex.printStackTrace();
			Bukkit.getLogger().log(Level.SEVERE, "Could not save config " + pDataFile, ex);
		}
	}
}