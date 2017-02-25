package kitpvp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class Prefix implements Listener, CommandExecutor {

	public void openPrefixGUI(Player p){
		
		
		
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)){ return true; }
		
		Player p = (Player) sender;
		
		return true;
	}
	
}
