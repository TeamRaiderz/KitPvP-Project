package kitpvp.commands.essential;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import kitpvp.Util.ChatUtils;
import kitpvp.Util.TeleportManager;

public class CommandSpawn implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(args.length == 0){
			
			if(!(sender instanceof Player)) return true;
			
			new TeleportManager().teleportSpawn(Bukkit.getWorld("kitukka"), ((Player)sender));
			
			
		}
		else if (args.length == 1){
			
			if(!(sender.hasPermission("server.mod"))){
				ChatUtils.sendPermissionMessageMod(sender);
				return true;
			}
			
			Player target = Bukkit.getPlayer(args[0]);
			
			if(target == null || !target.isOnline()){
				ChatUtils.sendMessageWithPrefix(sender, "§7That player is not online!");
				return true;
			}
			
			TeleportManager tm = new TeleportManager();
			tm.teleportSpawn(target);
			
		}
		else{ return true; }
		
		return true;
	}

}
