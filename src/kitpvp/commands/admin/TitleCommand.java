package kitpvp.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import kitpvp.Main;

public class TitleCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender.isOp())) {
			return true;
		}

		if (label.equalsIgnoreCase("title")) {
			if (args.length >= 2) {
				Player target = Bukkit.getPlayer(args[0]);

				if (target == null || !target.isOnline()) {
					return true;
				}

				StringBuilder sb = new StringBuilder();
				for (int allArgs = 1; allArgs < args.length; ++allArgs) {
					sb.append(args[allArgs]).append(" ");
				}
				String msg = sb.toString().trim();

				Main.getPacketUtils().sendTitle(target, ChatColor.translateAlternateColorCodes('&', msg), "", 20, 40,
						20);
			} else {
				sender.sendMessage("§cUsage: /title (player) (text)");
			}
		} else if (label.equalsIgnoreCase("subtitle")) {

			if (args.length >= 2) {

				Player target = Bukkit.getPlayer(args[0]);

				if (target == null || !target.isOnline()) {
					return true;
				}

				StringBuilder sb = new StringBuilder();
				for (int allArgs = 1; allArgs < args.length; ++allArgs) {
					sb.append(args[allArgs]).append(" ");
				}
				String msg = sb.toString().trim();

				Main.getPacketUtils().sendTitle(target, "", ChatColor.translateAlternateColorCodes('&', msg), 20, 40,
						20);

			} else {
				sender.sendMessage("§cUsage: /subtitle (player) (text)");
			}

		}

		return false;
	}

}
