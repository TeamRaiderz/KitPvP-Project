package kitpvp.commands.admin;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import kitpvp.Main;
import kitpvp.Util.ChatUtils;

public class CommandSetspawn implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		
		if(!(arg0 instanceof Player)) return true;

		if(!(arg0.isOp())){
			ChatUtils.sendPermissionMessageAdmin(arg0);
			return true;
		}
		
		Player arg4 = (Player) arg0;
		
		FileConfiguration arg5 = Main.getInstance().getConfig();
		Location arg6 = arg4.getLocation();
		
		arg5.set("Spawn.X", String.valueOf(arg6.getX()));
		arg5.set("Spawn.Y", String.valueOf(arg6.getY()));
		arg5.set("Spawn.Z", String.valueOf(arg6.getZ()));
		arg5.set("Spawn.PITCH", String.valueOf(arg6.getPitch()));
		arg5.set("Spawn.YAW", String.valueOf(arg6.getYaw()));
		arg5.set("Spawn.World", arg6.getWorld().getName());
		
		Main.getInstance().saveConfig();
		
		arg4.sendMessage("§cSpawn set to:" + " World: " + arg6.getWorld().getName() + " X: " + (int) arg6.getX() + " Y: " + (int) arg6.getY() +
				" Z: " + (int) arg6.getZ() + " Pitch: " + arg6.getPitch() + " Yaw: " + arg6.getYaw());
		
		return false;
	}

}
