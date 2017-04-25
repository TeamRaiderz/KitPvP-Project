package kitpvp.commands.essential;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import kitpvp.Main;
import kitpvp.Util.ChatUtils;
import kitpvp.Util.KitAPI;
import kitpvp.Util.Language;

public class CommandStats implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		KitAPI api = Main.getAPI();
		
		if(args.length == 0){
			
			if(!(sender instanceof Player)){ return true; }
			
			Player p = (Player) sender;
			
			api.sendPlayerStats(p, p.getName());
			
		}
		else if(args.length == 1){
			
			Player target = Bukkit.getPlayer(args[0]);
			
			if(target == null || !target.isOnline()){
				ChatUtils.sendPlayerNotFoundMsg(sender, args[0]);
				return true;
			}
			
			api.sendPlayerStats(sender, target.getName());
			
		}
		else{
			return true;
		}
		
		return true;
	}

}
