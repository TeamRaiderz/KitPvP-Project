package kitpvp.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import kitpvp.Language;
import kitpvp.Main;
import kitpvp.Util.ChatUtils;

public class CommandMsg implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(label.equalsIgnoreCase("msg") || label.equalsIgnoreCase("tell") || label.equalsIgnoreCase("whisper")){
			
			if(args.length == 0){
				if(Main.getAPI().getLanguage(sender.getName()) == Language.FINNISH){
					ChatUtils.sendMessageWithPrefix(sender, "§7Käyttötapa: §c/msg (pelaaja) (viesti)");
				} else if(Main.getAPI().getLanguage(sender.getName()) == Language.ENGLISH){
					ChatUtils.sendMessageWithPrefix(sender, "§7Usage: §c/msg (player) (msg)");
				}
				return true;
			}
			else if (args.length == 1){
				
				Player target = Bukkit.getPlayer(args[0]);
				
				if(Main.getAPI().getLanguage(sender.getName()) == Language.FINNISH){
					ChatUtils.sendMessageWithPrefix(sender, "§7Käyttötapa: §c/msg " + target.getName() + " (viesti)");
				} else if(Main.getAPI().getLanguage(sender.getName()) == Language.ENGLISH){
					ChatUtils.sendMessageWithPrefix(sender, "§7Usage: §c/msg " + target.getName() + " (viesti)");
				}
				return true;
			}
			else if (args.length >= 2){
				
				Player target = Bukkit.getPlayer(args[0]);
				
				if(target == null){
					if(Main.getAPI().getLanguage(sender.getName()) == Language.FINNISH){
						ChatUtils.sendMessageWithPrefix(sender, "§7En löytänyt kyseistä pelaajaa...");
					} else if(Main.getAPI().getLanguage(sender.getName()) == Language.ENGLISH){
						ChatUtils.sendMessageWithPrefix(sender, "§7Couldn't find the player you were looking for...");
					}
					return true;
				}
				
				if(!Main.getDataFile().getBoolean(target.getUniqueId().toString() + ".privateMsg")){
					if(Main.getAPI().getLanguage(sender.getName()) == Language.FINNISH){
						ChatUtils.sendMessageWithPrefix(sender, "§7Tuolla pelaajalla on yksityisviestit §cpoissa käytöstä§7!");
					} else if(Main.getAPI().getLanguage(sender.getName()) == Language.ENGLISH){
						ChatUtils.sendMessageWithPrefix(sender, "§7That player has turned private messages §coff§7!");
					}
					return true;
				}
				
				StringBuilder sb = new StringBuilder();
				for (int allArgs = 1; allArgs < args.length; ++allArgs) {
					sb.append(args[allArgs]).append(" ");
				}
				String msg = sb.toString().trim();
				
				if(Main.getAPI().getLanguage(sender.getName()) == Language.FINNISH){
					ChatUtils.sendMessage(target, "§2§lMinä §a-> §2§l" + target.getName() + " §a: " + msg);
				} else if(Main.getAPI().getLanguage(sender.getName()) == Language.ENGLISH){
					ChatUtils.sendMessage(target, "§2§lMe §a-> §2§l" + target.getName() + " §a: " + msg);
				}
				
				if(Main.getAPI().getLanguage(target.getName()) == Language.FINNISH){
					ChatUtils.sendMessageWithPrefix(target, "§2§l" + sender.getName() + " §a-> §2§lMinä §a: " + msg);
				} else if(Main.getAPI().getLanguage(target.getName()) == Language.ENGLISH){
					ChatUtils.sendMessageWithPrefix(target, "§2§l" + sender.getName() + " §a-> §2§lMe §a: " + msg);
				}
				
				
			}
			
		}
		return true;
	}
	
}
