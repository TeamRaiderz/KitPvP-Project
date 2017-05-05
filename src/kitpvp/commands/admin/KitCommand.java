package kitpvp.commands.admin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import kitpvp.Main;
import kitpvp.Util.ChatUtils;
import kitpvp.Util.DataYML;
import kitpvp.Util.AchievementsYML;
import kitpvp.Util.Language;
import kitpvp.kits.KitManager.Kit;

public class KitCommand implements CommandExecutor{
	
	List<ItemStack> items = new ArrayList<ItemStack>();
	List<ItemStack> armors = new ArrayList<ItemStack>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender.isOp())){
			ChatUtils.sendPermissionMessageAdmin(sender);
			return true;
		}
		
		if(args.length == 0){
			sender.sendMessage("§cDo /kit ? to see command help.");
			return true;
		}
		else if (args.length == 1){
			if(args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")){
				sender.sendMessage("§7§m----------§c§l KitPvP Command Help §7§----------");
				sender.sendMessage("§c/kit (kit) [player] §7Give a kit to a player");
				sender.sendMessage("§c/kit reload §7Reload the files.");
				sender.sendMessage("§c/kit openMenu [player]");
				sender.sendMessage("§7§m-----------------------------------------");
			}
			else if (args[0].equalsIgnoreCase("reload")){
				DataYML.reloadFile();
				Main.getInstance().reloadConfig();
				AchievementsYML.reloadFile();
				
				if (Main.getAPI().getLanguage(sender.getName()) == Language.FINNISH) {
					ChatUtils.sendMessageWithPrefix(sender, "§7Reloadasit pluginin!");
				} else if (Main.getAPI().getLanguage(sender.getName()) == Language.ENGLISH) {
					ChatUtils.sendMessageWithPrefix(sender, "§7Reloaded the plugin");
				}
				
			}
			else if (args[0].equalsIgnoreCase("openMenu")){
				if(!(sender instanceof Player)){
					ChatUtils.sendConsoleMessageWithPrefix("§cOnly for players!");
					return true;
				}
				
				Main.getKitManager().openKitMenu((Player) sender);
			}
			else{
				
				if(!(sender instanceof Player)){
					ChatUtils.sendConsoleMessageWithPrefix("§cOnly for players!");
					return true;
				}
				
				Player p = (Player) sender;
				
				String rawKitName = args[0].toUpperCase();
				try{
					Kit kit = Kit.valueOf(rawKitName);
					
					Main.getKitManager().giveKit(p, kit);
				} catch(Exception e){
					
					p.sendMessage("§cCould not give kit \"" + rawKitName + "\", because it doesn't exist! Error: " + e.toString());
				}
				
				return true;
			}
		}
		else if (args.length == 2) {
			
			if (args[0].equalsIgnoreCase("openMenu")) {
				
				Player target = Bukkit.getPlayer(args[1]);

				if (target == null || !target.isOnline()) {
					return true;
				}
				
				Main.getKitManager().openKitMenu(target);
			} 
			else if(args[0].equalsIgnoreCase("openInv")){
				
				if(!(sender instanceof Player)){
					ChatUtils.sendConsoleMessageWithPrefix("§cOnly for players!");
					return true;
				}
				
				Player p = (Player) sender;
				int size = 0;
				try{
					size = Integer.parseInt(args[1]);
				} catch(NumberFormatException ex){
					ex.printStackTrace();
				}
				Inventory inv = Bukkit.createInventory(null, size, "Inv Size: " + size);
				p.openInventory(inv);
			} else {
				
				Player target = Bukkit.getPlayer(args[1]);

				if (target == null || !target.isOnline()) {
					return true;
				}
				
				String rawKitName = args[0].toUpperCase();
				Kit kit = Kit.valueOf(rawKitName);

				Main.getKitManager().giveKit(target, kit);
			}
		}
		return true;
	}

}
