package kitpvp.Util;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import kitpvp.Main;

public class TeleportManager {

	public static HashMap<String, Double> spawnCooldown = new HashMap<String, Double>();
	
	public void teleportSpawn(Player target){
		Location spawn = new Location(getSpawnWorld(), getSpawnX(), getSpawnY(), getSpawnZ(), getSpawnYaw(), getSpawnPitch());
		target.teleport(spawn);
	}
	
	public void teleportSpawn(World mapWorld, Player target){
		Location spawn = new Location(getSpawnWorld(), getSpawnX(), getSpawnY(), getSpawnZ(), getSpawnYaw(), getSpawnPitch());
		if(target.getWorld() == mapWorld){
			
			if(!(spawnCooldown.containsKey(target.getName()))){
				spawnCooldown.put(target.getName(), 5.0D);
				
				new BukkitRunnable(){

					@Override
					public void run() {
						
						
						if(spawnCooldown.containsKey(target.getName())){
							
							if(spawnCooldown.get(target.getName()) < 0.1D){
								Main.getPacketUtils().sendActionBar(target, "§a§lTeleporting...");
								spawnCooldown.remove(target.getName());
								target.teleport(spawn);
								cancel();
							}
							
							spawnCooldown.put(target.getName(), spawnCooldown.get(target.getName()) - 0.1D);
							Main.getPacketUtils().sendActionBar(target, "§a§lTeleporting in §c"
									+ (Math.round(spawnCooldown.get(target.getName()) * 10.0D) / 10.0D) + "§a§l...");
						}
						
					}
					
				}.runTaskTimerAsynchronously(Main.getInstance(), 2, 2);
			}
			
		}
		else{
			target.teleport(spawn);
		}
	}
	
	public void teleport(Player p, Player target){
		p.teleport(target.getLocation());
	}
	
	public void teleport(Player target, double x, double y, double z){
		Location loc = new Location(target.getWorld(), x, y, z);
		target.teleport(loc);
	}
	
	public void teleport(Player target, World world, double x, double y, double z){
		Location loc = new Location(world, x, y, z);
		target.teleport(loc);
	}
	
	public World getSpawnWorld(){
		return Bukkit.getWorld(Main.getConfigFile().getString("Spawn.World"));
	}
	
	public double getSpawnX(){
		return Double.parseDouble(Main.getInstance().getConfig().getString("Spawn.X"));
	}
	
	public double getSpawnY(){
		return Double.parseDouble(Main.getInstance().getConfig().getString("Spawn.Y"));
	}
	
	public double getSpawnZ(){
		return Double.parseDouble(Main.getInstance().getConfig().getString("Spawn.Z"));
	}
	
	public float getSpawnPitch(){
		return Float.parseFloat(Main.getInstance().getConfig().getString("Spawn.PITCH"));
	}
	
	public float getSpawnYaw(){
		return Float.parseFloat(Main.getInstance().getConfig().getString("Spawn.YAW"));
	}
	
	public Location getSpawnLocation(){
		Location spawn = new Location(getSpawnWorld(), getSpawnX(), getSpawnY(), getSpawnZ(), getSpawnYaw(), getSpawnPitch());
		return spawn;
	}
	
}
