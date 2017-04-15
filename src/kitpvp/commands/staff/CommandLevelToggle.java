package kitpvp.commands.staff;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import kitpvp.Main;
import kitpvp.Util.ChatUtils;
import kitpvp.Util.Language;

public class CommandLevelToggle implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) return true;
		
		Player p = (Player) sender;
		
		if(!(p.hasPermission("server.mod"))){
			ChatUtils.sendPermissionMessageMod(sender);
			return true;
		}
		
		if(args.length == 0){
			p.sendMessage("ßc/lvl toggle");
		}
		else if (args.length == 1){
			if(args[0].equalsIgnoreCase("toggle")){
				
				if(!Main.getDataFile().getBoolean(p.getUniqueId().toString() + ".levelInChat")){
					Main.getDataFile().set(p.getUniqueId().toString() + ".levelInChat", true);
					Main.saveDataFile();
					if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
						ChatUtils.sendMessageWithPrefix(sender, "ß7Levelisi n‰kyy nyt chatiss‰!");
					}
					else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
						ChatUtils.sendMessageWithPrefix(sender, "ß7Your level will show up in the chat again!");
					}
				}
				else if(Main.getDataFile().getBoolean(p.getUniqueId().toString() + ".levelInChat")){
					Main.getDataFile().set(p.getUniqueId().toString() + ".levelInChat", false);
					Main.saveDataFile();
					if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
						ChatUtils.sendMessageWithPrefix(sender, "ß7Levelisi ei en‰‰ n‰y chatissa!");
					}
					else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
						ChatUtils.sendMessageWithPrefix(sender, "ß7Your level won't show up in the chat!");
					}
				}
				
			}
			else {
				p.sendMessage("ßc/lvl toggle");
				return true;
			}
		}
		
		return true;
	}

	
	
}
