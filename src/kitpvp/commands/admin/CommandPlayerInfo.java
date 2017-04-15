package kitpvp.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversation;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import kitpvp.Util.ChatUtils;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class CommandPlayerInfo implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender.isOp())){
			ChatUtils.sendPermissionMessageAdmin(sender);
			return true;
		}
		
		if(args.length != 1){
			sender.sendMessage("§cUsage: /playerinfo (player)");
			return true;
		}
		
		else if (args.length == 1){
			Player a = Bukkit.getPlayer(args[0]);
			
			if(a == null || !a.isOnline()){
				sender.sendMessage("§cThat player is not online!");
				return true;
			}
			
			CraftPlayer b = ((CraftPlayer) a);
			EntityPlayer c = b.getHandle();
			Location d = b.getLocation();
			
			sender.sendMessage("§c------ Info " + a.getName() + " ------");
			sender.sendMessage("§cPing: " + c.ping);
			sender.sendMessage("§cAddress: " + a.getAddress().getAddress().toString());
			sender.sendMessage("§cUUID: " + c.getUniqueID().toString());
			sender.sendMessage("§cLocale: " + c.locale);
			sender.sendMessage("§cDisplayname: " + ChatColor.translateAlternateColorCodes('&', b.getDisplayName()));
			sender.sendMessage("§cOp: " + b.isOp());
			sender.sendMessage("§c--------------------------------------");
			
		}
		
		return true;
	}

}
