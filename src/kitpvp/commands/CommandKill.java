package kitpvp.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import kitpvp.Language;
import kitpvp.Main;
import kitpvp.Util.ChatUtils;

public class CommandKill implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender.isOp())) { return true; }
		
		//pvpkill sendMessage (Player)
		
		if(args.length != 4){
			sender.sendMessage("§c/pvpkill sendMessage (Killer) (Victim) (xp)");
			return true;
		}
		else{
			Player killer = Bukkit.getPlayer(args[1]);
			Player victim = Bukkit.getPlayer(args[2]);
			
			if(victim == null || !victim.isOnline()){
				sender.sendMessage("§cThat player is not online!");
				return true;
			}
			
			if(killer == null || !killer.isOnline()){
				sender.sendMessage("§cThat player is not online!");
				return true;
			}
			
			int amount = 0;
			
			try{
				amount = Integer.parseInt(args[3]);
			} catch(NumberFormatException e){
				sender.sendMessage("That argument must be an integer!");
				return true;
			}
			
			sender.sendMessage("§cSent the kill message to " + killer.getName() + "!");
			
			if(Main.getAPI().getLanguage(killer.getName()) == Language.FINNISH){
				ChatUtils.sendMessageWithPrefix(killer, "§7Tapoit pelaajan §c" + victim.getName() + " §7ja sait §c" + amount + " §7xp:tä!");
			}
			else if(Main.getAPI().getLanguage(killer.getName()) == Language.ENGLISH){
				ChatUtils.sendMessageWithPrefix(killer, "§7You killed the player §c" + victim.getName() + " §7and got §c" + amount + " §7XP from it.");
			}
			
		}
		
		return true;
	}

}
