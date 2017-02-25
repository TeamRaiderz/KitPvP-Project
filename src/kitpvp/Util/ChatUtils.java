package kitpvp.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import kitpvp.Main;

public class ChatUtils {

	private static String prefix = ChatColor.translateAlternateColorCodes('&', Main.getConfigFile().getString("MsgPrefix"));
	
	public static void sendMessage(Player p, String message){
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
	}
	
	public static void sendMessageWithPrefix(Player p, String message){
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " §r" + message));
	}

	public static String getPrefix() {
		return ChatColor.translateAlternateColorCodes('&', prefix);
	}
	
	public static void broadcast(String message){
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
	}
	
	public static void broadcastWithPrefix(String message){
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', prefix + " §r" + message));
	}
	
}
