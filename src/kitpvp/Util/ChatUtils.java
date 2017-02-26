package kitpvp.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import kitpvp.Main;

public class ChatUtils {

	private static String prefix = ChatColor.translateAlternateColorCodes('&', Main.getConfigFile().getString("MsgPrefix"));
	
	public static void sendMessage(CommandSender sender, String message){
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
	}
	
	public static void sendMessageWithPrefix(CommandSender sender, String message){
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " §r" + message));
	}
	
	public static void sendConsoleMessage(String message){
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
	}
	
	public static void sendConsoleMessageWithPrefix(String message){
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " §r" + message));
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
