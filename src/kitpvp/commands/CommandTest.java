package kitpvp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandTest implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender.isOp())){
			return true;
		}
		
		if(args.length == 0){
			sender.sendMessage("§c/test cps");
			sender.sendMessage("§c/test bow");
		}
		
		return true;
	}

}
