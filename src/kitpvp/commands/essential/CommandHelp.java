package kitpvp.commands.essential;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import kitpvp.Main;
import kitpvp.Util.ChatUtils;
import kitpvp.Util.Language;

public class CommandHelp implements CommandExecutor{
	
	public static HashMap<String, String> question = new HashMap<String, String>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) return true;
		
		if(args.length < 1){
			if(Main.getAPI().getLanguage(sender.getName()) == Language.FINNISH){
				ChatUtils.sendMessageWithPrefix(sender, "§7Käyttötapa: §c/help (viesti)");
			} else if(Main.getAPI().getLanguage(sender.getName()) == Language.ENGLISH){
				ChatUtils.sendMessageWithPrefix(sender, "§7Usage: §c/help (message)");
			}
			return true;
		}else{
			
			StringBuilder sb = new StringBuilder();
			for (int allArgs = 0; allArgs < args.length; ++allArgs) {
				sb.append(args[allArgs]).append(" ");
			}
			String msg = sb.toString().trim();
			
			if(Main.getAPI().getLanguage(sender.getName()) == Language.FINNISH){
				ChatUtils.sendMessageWithPrefix(sender, "§7Lähetit ylläpidolle viestin: §a" + msg + "§7! He vastaavat sinulle mahdollisimman pian!");
			}
			else if (Main.getAPI().getLanguage(sender.getName()) == Language.ENGLISH){
				ChatUtils.sendMessageWithPrefix(sender, "§7You've sent a question to the staff: §a" + msg + "§7! They will answer it as soon as possible!");
			}
			
			question.put(sender.getName(), msg);
			
			for(Player staff : Bukkit.getOnlinePlayers()){
				
				if(staff.hasPermission("server.mod")){
					staff.sendMessage("§7[§d§lHELP§7] §c§o" + Main.getChat().getPrimaryGroup((Player) sender) + " " + sender.getName() + ":§d " + msg);
					staff.playSound(staff.getLocation(), Sound.NOTE_PLING, 1, 0.2f);
				}
				
			}
		}
		
		return true;
	}

	
	
}
