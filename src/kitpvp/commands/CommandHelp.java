package kitpvp.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import kitpvp.Language;
import kitpvp.Main;
import kitpvp.Util.ChatUtils;

public class CommandHelp implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
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
			
			for(Player staff : Bukkit.getOnlinePlayers()){
				
				if(staff.hasPermission("server.mod")){
					staff.sendMessage("§7[§d§lHELP§7] §c§o" + sender.getName() + ":§d " + msg);
				}
				
			}
			
		}
		
		return true;
	}

	
	
}
