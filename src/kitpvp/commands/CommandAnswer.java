package kitpvp.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import kitpvp.Language;
import kitpvp.Main;
import kitpvp.Util.ChatUtils;

public class CommandAnswer implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender.hasPermission("server.mod"))){
			ChatUtils.sendPermissionMessageMod(sender);
			return true;
		}
		
		if(args.length < 2){
			ChatUtils.sendMessageWithPrefix(sender, "§7Usage: §c/answer (player) (answer)");
			return true;
		}
		else if (args.length >= 2){
			
			Player target = Bukkit.getPlayer(args[0]);
			
			if(target == null || !target.isOnline()){
				ChatUtils.sendMessageWithPrefix(sender, "§7That player is not online!");
				return true;
			}
			
			if(!CommandHelp.question.containsKey(target.getName())){
				ChatUtils.sendMessageWithPrefix(sender, "§7That player has not asked a question!");
				return true;
			}
			
			StringBuilder sb = new StringBuilder();
			for (int allArgs = 1; allArgs < args.length; ++allArgs) {
				sb.append(args[allArgs]).append(" ");
			}
			String msg = sb.toString().trim();
			
			ChatUtils.sendMessageWithPrefix(sender, "§7You answered to the question §a" + CommandHelp.question.get(target.getName()) + " §7from §a" + target.getName() + 
					" §7with the answer: §a" + msg + "§7!");
			
			if(Main.getAPI().getLanguage(target.getName()) == Language.FINNISH){
				ChatUtils.sendMessageWithPrefix(target, "§7Kysymykseesi §a" + CommandHelp.question.get(target.getName()) + " §7on vastannut §a" + sender.getName() +
						" §7vastauksella: §a" + msg  + "§7!");
			}
			else if(Main.getAPI().getLanguage(target.getName()) == Language.ENGLISH){
				ChatUtils.sendMessageWithPrefix(target, "§7Your question §a" + CommandHelp.question.get(target.getName()) + " §7has been answered by §a" + sender.getName() +
						" §7with the answer: §a" + msg  + "§7!");
			}
			
			CommandHelp.question.remove(target.getName());
			
		}
		
		return true;
	}

}
