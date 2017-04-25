package kitpvp.arena;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import kitpvp.Main;
import kitpvp.Util.ChatUtils;
import kitpvp.Util.Language;
import kitpvp.Util.TeleportManager;
import kitpvp.object.ArenaPlayer;
import net.md_5.bungee.api.ChatColor;

public class ArenaManager {
	
	public Arena getArena(){
		return new Arena();
	}
	
	public ArenaPlayer getArenaPlayer(String name){
		
		for(ArenaPlayer p : getArena().getPlayers()){
			if(name == p.getName()){
				return p;
			}
		}
		
		for(ArenaPlayer p : getArena().getPlayers()){
			if(p.getName().equals(Bukkit.getPlayer(name).getName())){
				return p;
			}
		}
		
		getArena().getPlayers().add(new ArenaPlayer(){

			private Player p = Bukkit.getPlayer(name);
			int killStreak = 0;
			
			@Override
			public Player getPlayer() {
				return p;
			}

			@Override
			public void sendMessage(String message) {
				ChatUtils.sendMessageWithPrefix(p, "§7" + message);
			}

			@Override
			public int getKillStreak() {
				return killStreak;
			}

			@Override
			public Location getLocation() {
				return p.getLocation();
			}

			@Override
			public String getName() {
				return p.getName();
			}

			@Override
			public void die() {
				if(p.getGameMode() != GameMode.SURVIVAL){
					p.setGameMode(GameMode.SURVIVAL);
					p.getInventory().clear();
					p.getInventory().setArmorContents(null);
					p.setHealth(0);
					new TeleportManager().teleportSpawn(p);
					p.setHealth(20);
				}
				else{
					p.getInventory().clear();
					p.getInventory().setArmorContents(null);
					p.setHealth(0);
					new TeleportManager().teleportSpawn(p);
					p.setHealth(20);
				}
			}

			@Override
			public boolean isInArena() {
				if(p.getWorld().getName().equals("Map")){
					return true;
				}
				return false;
			}

			@Override
			public PlayerInventory getInventory() {
				return p.getInventory();
			}

			@Override
			public boolean isInCombat() {
				//TODO Add this function
				return false;
			}

			@Override
			public void addKillToKillStreak() {
				killStreak += 1;
			}

			@Override
			public void setKillStreak(int value) {
				killStreak = value;
			}

			@Override
			public Language getLanguage() {
				return Main.getAPI().getLanguage(p.getName());
			}

			@Override
			public void sendTitle(String headtitle, String subtitle) {
				Main.getPacketUtils().sendTitle(p, ChatColor.translateAlternateColorCodes('&', headtitle),
						ChatColor.translateAlternateColorCodes('&', subtitle), 20, 40, 20);
			}
			
			@Override
			public void sendActionBar(String msg){
				Main.getPacketUtils().sendActionBar(p, ChatColor.translateAlternateColorCodes('&', msg));
			}
			
			@Override
			public double getHealth(){
				double rawHealth = p.getHealth();
				double health = Math.round(rawHealth * 10.0D) / 10.0D;
				return health;
			}
			
		});
		
		for(ArenaPlayer p : getArena().getPlayers()){
			if(p.getName() == name) {
				return p;
			}
		}
		throw new NullPointerException("That player does not exist!");
		
	}
	
}
