package kitpvp.cosmetics;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import kitpvp.Main;
import net.minecraft.server.v1_8_R3.EnumParticle;

public class ArrowTrail {

	EnumParticle particle;
	
	public ArrowTrail(EnumParticle particle){
		this.particle = particle;
	}
	
	public String getName(){
		return String.valueOf(particle);
	}
	
	public String getPermission(){
		return "server.arrowtrail." + getName().toLowerCase();
	}
	
	public static String getPermission(EnumParticle particle){
		return "server.arrowtrail." + String.valueOf(particle).toLowerCase();
	}
	
	public String toString(){
		return "ArrowTrail: [Name: " + getName() + " Permission: " + getPermission() + " EnumParticle: " + String.valueOf(particle) + "]";
	}
	
	public void give(Player target) {

		FileConfiguration config = Main.getInstance().getConfig();
		String uuid = target.getUniqueId().toString();

		if (!(target.hasPermission(getPermission()))) {
			if (!(config.contains(uuid + ".arrowtrailAmount"))) {
				config.set(uuid + ".arrowtrailAmount", 1);
				Main.getInstance().saveConfig();
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "trail " + getName() + " " + target.getName());
			}
			else{
				int trailAmnt = config.getInt(uuid + ".arrowtrailAmount");
				config.set(uuid + ".arrowtrailAmount", trailAmnt + 1);
				Main.getInstance().saveConfig();
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "trail " + getName() + " " + target.getName());
			}
		} else {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "trail " + getName() + " " + target.getName());
			
		}
	}
	
	public static void give(Player target, EnumParticle particle) {
		FileConfiguration config = Main.getInstance().getConfig();
		String uuid = target.getUniqueId().toString();
		String name = String.valueOf(particle).toLowerCase();

		if (!(target.hasPermission(getPermission(particle)))) {
			if (!(config.contains(uuid + ".arrowtrailAmount"))) {
				config.set(uuid + ".arrowtrailAmount", 1);
				Main.getInstance().saveConfig();
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "trail " + name + " " + target.getName());
			}
			else{
				int trailAmnt = config.getInt(uuid + ".arrowtrailAmount");
				config.set(uuid + ".arrowtrailAmount", trailAmnt + 1);
				Main.getInstance().saveConfig();
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "trail " + name + " " + target.getName());
			}
		} else {
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "trail " + name + " " + target.getName());
			
		}
	}
	
	public static int getAmount(Player p){
		FileConfiguration config = Main.getInstance().getConfig();
		String uuid = p.getUniqueId().toString();
		if (!(config.contains(uuid + ".arrowtrailAmount"))) {
			return config.getInt(uuid + ".arrowTrailAmount");
		}
		else{
			return 0;
		}
	}
	
}
