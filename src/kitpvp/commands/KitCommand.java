package kitpvp.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import kitpvp.Kit;
import kitpvp.Util.ChatUtils;

public class KitCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender.isOp())){ return true; }
		
		if(args.length == 0){
			sender.sendMessage("§cDo /kit ? to see command help.");
			return true;
		}
		else if (args.length == 1 || args.length == 2){
			if(args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")){
				sender.sendMessage("§7§m----------§c§l KitPvP Command Help §7§----------");
				sender.sendMessage("§c/kit (kit) create §7Create a kit.");
				sender.sendMessage("§c/kit (kit) delete §7Remove a kit.");
				sender.sendMessage("§c/kit (kit) setability (ability) §7Set a kit's ability");
				sender.sendMessage("§c/kit (kit) removeability (ability) §7Remove an ability from a kit.");
				sender.sendMessage("§c/kit (kit) give (player) §7Give a kit to a player.");
				sender.sendMessage("§7§m-----------------------------------------");
			}
			else{
				sender.sendMessage("§cDo /kit ? to see command help.");
				return true;
			}
		}
		else if (args.length == 3){
			
			if(args[1].equalsIgnoreCase("create") || args[1].equalsIgnoreCase("delete")){
			
				if(!(sender instanceof Player)){
					ChatUtils.sendConsoleMessageWithPrefix("§cOnly for players!");
					return true;
				}
				
				Player p = (Player) sender;
				
				if(args[1].equalsIgnoreCase("create")){
					
					Kit kit = new Kit(ChatColor.translateAlternateColorCodes('&', args[0]), 
							p.getInventory().getContents(), p.getInventory().getArmorContents(), null);
					
					kit.loadToFile();
					ChatUtils.sendMessageWithPrefix(p, "§7You created a new kit §c" + kit.getName() + "§7!");
					
				}
				else if(args[1].equalsIgnoreCase("delete")){
					
					Kit.removeFromFile();
					
				}
				
			}
			
		}
		else if (args.length == 4){
			
			if(args[1].equalsIgnoreCase("give") || args[1].equalsIgnoreCase("setability") || args[1].equalsIgnoreCase("removeability")){
				
				if(args[1].equalsIgnoreCase("give")){
					
					Player target = Bukkit.getPlayer(args[2]);
					
					if(target == null || !target.isOnline()){
						ChatUtils.sendMessageWithPrefix(sender, "§cThat player is not online!");
						return true;
					}
					
					Kit kit = Kit.getKit(args[0]);
					
					kit.giveKit(target);
					
				}
				
			}
			
		}
		return true;
	}

}
