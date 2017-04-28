package kitpvp.commands.vip;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import kitpvp.Util.ChatUtils;

public class CommandFly implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)){
			sender.sendMessage("§cVain pelaajille!");
			return true;
		}
		
		if(!(sender.hasPermission("server.booster"))){
			ChatUtils.sendPermissionMessageBooster(sender);
			return true;
		}
		
		return true;
	}

	
	
}
