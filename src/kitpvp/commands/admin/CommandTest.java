package kitpvp.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import kitpvp.Main;
import kitpvp.Util.ChatUtils;
import kitpvp.Util.KitAPI;
import kitpvp.Util.Language;

public class CommandTest implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender.isOp())){
			ChatUtils.sendPermissionMessageAdmin(sender);
			return true;
		}
		
		KitAPI api = Main.getAPI();
		
		if(args.length == 0){
			sender.sendMessage("�c/test cps");
			sender.sendMessage("�c/test bow");
		}
		else if(args.length == 1){
			if(args[0].equalsIgnoreCase("cps")){
				sender.sendMessage("�c/test cps (Player) (Score)");
				sender.sendMessage("�c/test cps startMsg (Player)");
			}
			else if(args[0].equalsIgnoreCase("bow")){
				sender.sendMessage("�c/test bow (Player) (Score)");
				sender.sendMessage("�c/test bow startMsg (Player)");
			}
			else{
				sender.sendMessage("�c/test cps");
				sender.sendMessage("�c/test bow");
			}
		}
		else if(args.length == 2){
			if(args[0].equalsIgnoreCase("cps")){
				sender.sendMessage("�c/test cps (Player) (Score)");
				sender.sendMessage("�c/test cps startMsg (Player)");
			}
			else if(args[0].equalsIgnoreCase("bow")){
				sender.sendMessage("�c/test bow (Player) (Score)");
				sender.sendMessage("�c/test bow startMsg (Player)");
			}
			else{
				sender.sendMessage("�c/test cps");
				sender.sendMessage("�c/test bow");
			}
		}
		else if (args.length == 3) {
			if (args[0].equalsIgnoreCase("cps")) {


				if (args[1].equalsIgnoreCase("startMsg")) {
					
					Player target = Bukkit.getPlayer(args[2]);

					if (target == null || !target.isOnline()) {
						sender.sendMessage("�cThat player is not online!");
						return true;
					}
					
					if (api.getLanguage(target.getName()) == Language.FINNISH) {
						ChatUtils.sendMessageWithPrefix(target, "�7CPS-testi on alkanut!");
					} else if (api.getLanguage(target.getName()) == Language.ENGLISH) {
						ChatUtils.sendMessageWithPrefix(target, "�7The CPS-test has started!");
					}

				} else {

					Player target = Bukkit.getPlayer(args[1]);

					if (target == null || !target.isOnline()) {
						sender.sendMessage("�cThat player is not online!");
						return true;
					}
					
					int score = 0;

					try {
						score = Integer.parseInt(args[2]);
					} catch (NumberFormatException e) {
						sender.sendMessage("That argument must be an integer!");
					}

					if (api.getLanguage(target.getName()) == Language.FINNISH) {
						ChatUtils.sendMessageWithPrefix(target, "�7L�p�isit CPS-testin! Klikkaukset testin aikana: �c" + score + "�7! Keskiarvosi oli: �c" + (double) score / 10 + " CPS�7!");
					} else if (api.getLanguage(target.getName()) == Language.ENGLISH) {
						ChatUtils.sendMessageWithPrefix(target, "�7Completed the CPS-test! You had �c" + score  + " �7clicks in the test! Your average was �c" + (double) score / 10 + " CPS�7!");
					}

				}
				
			} 
			else if (args[0].equalsIgnoreCase("bow")) {

				if (args[1].equalsIgnoreCase("startMsg")) {
					
					Player target = Bukkit.getPlayer(args[2]);

					if (target == null || !target.isOnline()) {
						sender.sendMessage("�cThat player is not online!");
						return true;
					}
					
					if (api.getLanguage(target.getName()) == Language.FINNISH) {
						ChatUtils.sendMessageWithPrefix(target, "�7Ampumatesti on alkanut!");
					} else if (api.getLanguage(target.getName()) == Language.ENGLISH) {
						ChatUtils.sendMessageWithPrefix(target, "�7The shooting-test has started!");
					}

				} else {

					Player target = Bukkit.getPlayer(args[1]);

					if (target == null || !target.isOnline()) {
						sender.sendMessage("�cThat player is not online!");
						return true;
					}
					
					int score = 0;

					try {
						score = Integer.parseInt(args[2]);
					} catch (NumberFormatException e) {
						sender.sendMessage("That argument must be an integer!");
					}

					if (api.getLanguage(target.getName()) == Language.FINNISH) {
						ChatUtils.sendMessageWithPrefix(target, "�7L�p�isit ampumatestin! Pisteesi olivat �c" + score + "/15�7!");
					} else if (api.getLanguage(target.getName()) == Language.ENGLISH) {
						ChatUtils.sendMessageWithPrefix(sender, "�7Completed the shooting-test! Your score was �c" + score  + "/15�7!");
					}

				}

			} else {
				sender.sendMessage("�c/test cps");
				sender.sendMessage("�c/test bow");
			}
			}
		
		
		return true;
	}

}
