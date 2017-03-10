package kitpvp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import kitpvp.Info;

public class CommandCommands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender paramCommandSender, Command paramCommand, String paramString,
			String[] paramArrayOfString) {
		
		if(!(paramCommandSender instanceof Player)) return true;
		
		Info.sendCommandHelp((Player) paramCommandSender);
		
		return true;
	}

}
