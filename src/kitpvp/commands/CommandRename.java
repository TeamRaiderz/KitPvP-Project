package kitpvp.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import kitpvp.Util.ChatUtils;

public class CommandRename implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command paramCommand, String label,
			String[] args) {
		
		if(!(sender instanceof Player)) return true;
		
		Player p = (Player) sender;
		
		if(!(p.hasPermission("item.rename"))){
			ChatUtils.sendPermissionMessageAdmin(sender);
			return true;
		}
		
		if(args.length >= 1){
			
			StringBuilder sb = new StringBuilder();
			for (int allArgs = 0; allArgs < args.length; ++allArgs) {
				sb.append(args[allArgs]).append(" ");
			}
			String msg = sb.toString().trim();
			
			if(p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR) return true;
			
			p.getItemInHand().getItemMeta().setDisplayName(ChatColor.translateAlternateColorCodes('&', msg));
			
		}
		
		return false;
	}

}
