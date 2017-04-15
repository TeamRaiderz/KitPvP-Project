package kitpvp.StaffMode;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import kitpvp.Main;
import kitpvp.Util.ChatUtils;

public class StaffModeCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		
		if(args.length == 0){
			
			if(!(sender.hasPermission("server.mod"))){
				ChatUtils.sendPermissionMessageMod(sender);
				return true;
			}
			
			
			if(!(sender instanceof Player)){ return true; }
			
			Player p = (Player) sender;
			
			if(!(StaffMode.staffMode.contains(p.getName()))){
				Main.getStaffModeManager().joinStaffMode(p);
			}
			else{
				Main.getStaffModeManager().leaveStaffMode(p);
			}
			
		}
		else if (args.length == 1){
			
			if(!(sender.isOp())){
				ChatUtils.sendPermissionMessageAdmin(sender);
				return true;
			}
			
			Player target = Bukkit.getPlayer(args[0]);
			
			if(target == null || !target.isOnline()){
				ChatUtils.sendMessageWithPrefix(sender, "§7That player is not online!");
				return true;
			}
			
			if(!(StaffMode.staffMode.contains(target.getName()))){
				Main.getStaffModeManager().joinStaffMode(target);
			}
			else{
				Main.getStaffModeManager().leaveStaffMode(target);
			}
			
		}
		else{
			return true;
		}
		
		return true;
	}

}
