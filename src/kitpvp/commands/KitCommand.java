package kitpvp.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import kitpvp.Kit;
import kitpvp.Language;
import kitpvp.Main;
import kitpvp.Util.ChatUtils;
import kitpvp.Util.DataYML;
import kitpvp.Util.KitsYML;

public class KitCommand implements CommandExecutor{

	private String kitName;
	List<ItemStack> items = new ArrayList<ItemStack>();
	List<ItemStack> armors = new ArrayList<ItemStack>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender.isOp())){ return true; }
		
		if(args.length == 0){
			sender.sendMessage("§cDo /kit ? to see command help.");
			return true;
		}
		else if (args.length == 1){
			if(args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")){
				sender.sendMessage("§7§m----------§c§l KitPvP Command Help §7§----------");
				sender.sendMessage("§c/kit (kit) create §7Create a kit.");
				sender.sendMessage("§c/kit (kit) delete §7Remove a kit.");
				sender.sendMessage("§c/kit (kit) setability (ability) §7Set a kit's ability");
				sender.sendMessage("§c/kit (kit) removeability (ability) §7Remove an ability from a kit.");
				sender.sendMessage("§c/kit (kit) give (player) §7Give a kit to a player.");
				sender.sendMessage("§c/kit reload §7Reload the files.");
				sender.sendMessage("§7§m-----------------------------------------");
			}
			else if (args[0].equalsIgnoreCase("reload")){
				DataYML.reloadFile();
				KitsYML.reloadFile();
				Main.getInstance().reloadConfig();
				Main.reloadMessagesFile();
				
				if (Main.getAPI().getLanguage(sender.getName()) == Language.FINNISH) {
					ChatUtils.sendMessageWithPrefix(sender, "§7Reloadasit pluginin!");
				} else if (Main.getAPI().getLanguage(sender.getName()) == Language.ENGLISH) {
					ChatUtils.sendMessageWithPrefix(sender, "§7Reloaded the plugin");
				}
				
			}
			else{
				sender.sendMessage("§cDo /kit ? to see command help.");
				return true;
			}
		}
		else if (args.length == 2){
			
			if(args[1].equalsIgnoreCase("create") || args[1].equalsIgnoreCase("delete")){
			
				if(!(sender instanceof Player)){
					ChatUtils.sendConsoleMessageWithPrefix("§cOnly for players!");
					return true;
				}
				
				Player p = (Player) sender;
				
				if(args[1].equalsIgnoreCase("create")){
					
					for(ItemStack item : p.getInventory().getContents()){
						if (item != null && item.getItemMeta() != null) {
							ItemMeta meta = item.getItemMeta();
							meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', args[0]));
							item.setItemMeta(meta);
							items.add(item);
						}
					}
					
					for(ItemStack armor : p.getInventory().getArmorContents()){
						if (armor != null && armor.getItemMeta() != null) {
							ItemMeta meta = armor.getItemMeta();
							meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', args[0]));
							armor.setItemMeta(meta);
							armors.add(armor);
						}
					}
					
					Kit kit = new Kit(ChatColor.translateAlternateColorCodes('&', args[0]), 
							items, armors, null);
					
					this.kitName = args[0];
					
					kit.loadToFile();
					ChatUtils.sendMessageWithPrefix(p, "§7You created a new kit §c" + kit.getName() + "§7!");
					
				}
				else if(args[1].equalsIgnoreCase("delete")){
					
					Kit.removeFromFile();
					
				}
				
			}
			
		}
		else if (args.length == 3){
			
			if(args[1].equalsIgnoreCase("give") || args[1].equalsIgnoreCase("setability") || args[1].equalsIgnoreCase("removeability")){
				
				if(args[1].equalsIgnoreCase("give")){
					
					Player target = Bukkit.getPlayer(args[2]);
					
					if(target == null || !target.isOnline()){
						ChatUtils.sendMessageWithPrefix(sender, "§cThat player is not online!");
						return true;
					}
					
					//Give the kit
					
					Kit kit = new Kit(args[0], items, armors, null);
					
					kit.giveKit(target);
					
					if (Main.getAPI().getLanguage(sender.getName()) == Language.FINNISH) {
						ChatUtils.sendMessageWithPrefix(sender,
								"§7Annoit kitin §c" + kit.getName() + " §7pelaajalle §c" + target.getName() + "§7!");
					} else if (Main.getAPI().getLanguage(sender.getName()) == Language.ENGLISH) {
						ChatUtils.sendMessageWithPrefix(sender, "§7You gave the kit §c" + kit.getName()
								+ " §7to the player §c" + target.getName() + "§7!");
					}
					
				}
				
			}
			
		}
		return true;
	}

}
