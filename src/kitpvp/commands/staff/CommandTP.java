package kitpvp.commands.staff;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import kitpvp.Util.ChatUtils;

public class CommandTP implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(label.equalsIgnoreCase("tp")){
			
			if(!(sender.hasPermission("server.mod"))){
				ChatUtils.sendPermissionMessageMod(sender);
				return true;
			}
			
			if(args.length == 1){
				
				if(!(sender instanceof Player)) return true;
				
				Player target = Bukkit.getPlayer(args[0]);
				
				if(target == null || !target.isOnline()){
					ChatUtils.sendMessageWithPrefix(sender, "§7That player is not online!");
					return true;
				}
				
				((Player) sender).teleport(target.getLocation());
				
				ChatUtils.sendMessageWithPrefix(sender, "§7Teleporting to §c" + target.getName() + "§7!");
				
			}
			else if  (args.length == 2){
				
				Player firstTarget = Bukkit.getPlayer(args[0]);
				Player secondTarget = Bukkit.getPlayer(args[1]);
				
				if(firstTarget == null || !firstTarget.isOnline() || secondTarget == null || !secondTarget.isOnline()){
					ChatUtils.sendMessageWithPrefix(sender, "§7One of those players is not online!");
					return true;
				}
				
				firstTarget.teleport(secondTarget.getLocation());
				
				ChatUtils.sendMessageWithPrefix(sender, "§7Teleported §c" + firstTarget.getName() + " §7to the player §c" + secondTarget.getName() + "§7!");
				
			}
			else{
				ChatUtils.sendMessageWithPrefix(sender, "§7Usage: §c/tp (player) [player2]");
				return true;
			}
		}
		
		return true;
	}

}
