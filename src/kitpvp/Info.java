package kitpvp;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

public class Info implements Listener, CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) return true;
		
		Player p = (Player) sender;
		
		openInfoMenu(p);
		
		return true;
	}
	
	//TODO Finish this
	public void openInfoMenu(Player p){
		
		if (Main.getAPI().getLanguage(p.getName()) == Language.FINNISH) {

			Inventory inv = Bukkit.createInventory(null, 27, "Info");
			
			p.openInventory(inv);
		} else if (Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH) {

			Inventory inv = Bukkit.createInventory(null, 27, "Info");

			p.openInventory(inv);
		}
		
	}

}
