package kitpvp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import static org.bukkit.ChatColor.*;

public class CommandDB implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(label.equalsIgnoreCase("db") || label.equalsIgnoreCase("database")){
			
			if(!(sender.isOp())){
				sender.sendMessage("§cNo permission");
				return true;
			}
			
			if(args.length == 0){
				
				sender.sendMessage("§c/db addBalance (player) (amount)");
				sender.sendMessage("§c/db setBalance (player) (amount)");
				sender.sendMessage("§c/db addXp (player) (amount)");
				sender.sendMessage("§c/db setXp (player) (amount)");
				
			}
			
		}
		
		return true;
	}

	
	
}
