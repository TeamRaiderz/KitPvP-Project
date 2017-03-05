package kitpvp.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import kitpvp.Language;
import kitpvp.Main;

public class CommandList implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
			
		for(Player online : Bukkit.getOnlinePlayers()){
			if(online.hasPermission("online.staff") && !Main.getAPI().getOnlineStaffMembers().contains(online.getName())){
				Main.getAPI().getOnlineStaffMembers().add(online.getName());
			}
		}
		
			List<String> staffMembers = Main.getAPI().getOnlineStaffMembers();
			String sm = Arrays.asList(staffMembers).toString();
			sm = sm.replace("[", "");
			sm = sm.replace("]", "");
			sm = sm.replace(",", "§7,§c");
			
			if(!(sender instanceof Player)){
			sender.sendMessage("§7Online: §c" + Bukkit.getOnlinePlayers().size() + " §7Online staff: §c" + Main.getAPI().onlineStaff());
			sender.sendMessage("§7Staff: §c" + sm);
		} else {
			if (Main.getAPI().getLanguage(sender.getName()) == Language.FINNISH) {
				sender.sendMessage("§7Pelaajia: §c" + Bukkit.getOnlinePlayers().size() + " §7Ylläpitoa: §c"
						+ Main.getAPI().onlineStaff());
				sender.sendMessage("§7Ylläpito: §c" + sm);
			} else if (Main.getAPI().getLanguage(sender.getName()) == Language.ENGLISH) {
				sender.sendMessage("§7Online: §c" + Bukkit.getOnlinePlayers().size() + " §7Online staff: §c"
						+ Main.getAPI().onlineStaff());
				sender.sendMessage("§7Staff: §c" + sm);

			}
		}
		
		return true;
	}

}
