package kitpvp.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import kitpvp.Main;
import net.minecraft.server.v1_8_R3.EntityPlayer;

public class ChatUtils {

	private static String prefix = ChatColor.translateAlternateColorCodes('&', Main.getConfigFile().getString("MsgPrefix"));
	
	public static void sendMessage(CommandSender sender, String message){
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
	}
	
	public static void sendMessageWithPrefix(CommandSender sender, String message){
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " �r" + message));
	}
	
	public static void sendConsoleMessage(String message){
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
	}
	
	public static void sendConsoleMessageWithPrefix(String message){
		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " �r" + message));
	}

	public static String getPrefix() {
		return ChatColor.translateAlternateColorCodes('&', prefix);
	}
	
	public static void broadcast(String message){
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
	}
	
	public static void broadcastWithPrefix(String message){
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', prefix + " �r" + message));
	}
	
	public static void sendPermissionMessageAdmin(CommandSender sender){
		if(Main.getAPI().getLanguage(sender.getName()) == Language.FINNISH){
			ChatUtils.sendMessageWithPrefix(sender, "�7T�h�n toimintoon tarvitset rankin �4�lADMIN�7!");
		}
		else if(Main.getAPI().getLanguage(sender.getName()) == Language.ENGLISH){
			ChatUtils.sendMessageWithPrefix(sender, "�7You'll need the rank �4�lADMIN �7to do this!");
		}
	}
	
	public static void sendPermissionMessageMod(CommandSender sender){
		if(Main.getAPI().getLanguage(sender.getName()) == Language.FINNISH){
			ChatUtils.sendMessageWithPrefix(sender, "�7T�h�n toimintoon tarvitset rankin �b�lMOD�7!");
		}
		else if(Main.getAPI().getLanguage(sender.getName()) == Language.ENGLISH){
			ChatUtils.sendMessageWithPrefix(sender, "�7You'll need the rank �b�lMOD �7to do this!");
		}
	}
	
	public static void sendPermissionMessageBooster(CommandSender sender){
		if(Main.getAPI().getLanguage(sender.getName()) == Language.FINNISH){
			ChatUtils.sendMessageWithPrefix(sender, "�7T�h�n toimintoon tarvitset rankin �3�lBOOSTER�7!");
		}
		else if(Main.getAPI().getLanguage(sender.getName()) == Language.ENGLISH){
			ChatUtils.sendMessageWithPrefix(sender, "�7You'll need the rank �3�lBOOSTER �7to do this!");
		}
	}
	
	public static void sendPermissionMessageYT(CommandSender sender){
		if(Main.getAPI().getLanguage(sender.getName()) == Language.FINNISH){
			ChatUtils.sendMessageWithPrefix(sender, "�7T�h�n toimintoon tarvitset rankin �c�lYOUTUBE�7!");
		}
		else if(Main.getAPI().getLanguage(sender.getName()) == Language.ENGLISH){
			ChatUtils.sendMessageWithPrefix(sender, "�7You'll need the rank �c�lYOUTUBE �7to do this!");
		}
	}
	
	public static void sendPlayerNotFoundMsg(CommandSender sender, String target){
		if(Main.getAPI().getLanguage(sender.getName()) == Language.FINNISH){
			ChatUtils.sendMessageWithPrefix(sender, "�7En l�yt�nyt pelaajaa �c" + target + ".");
		}
		else if(Main.getAPI().getLanguage(sender.getName()) == Language.ENGLISH){
			ChatUtils.sendMessageWithPrefix(sender, "�7Couldn't find the player �c" + target  + ".");
		}
	}
	
}
