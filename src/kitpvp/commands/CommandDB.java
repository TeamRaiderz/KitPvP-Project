package kitpvp.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import kitpvp.Main;
import kitpvp.Util.ChatUtils;
import kitpvp.Util.KitAPI;

public class CommandDB implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		KitAPI api = Main.getAPI();
		
		if(label.equalsIgnoreCase("db") || label.equalsIgnoreCase("database")){
			
			if(!(sender.isOp())){
				ChatUtils.sendPermissionMessageAdmin(sender);
				return true;
			}
			
			if(args.length == 0){
				
				sender.sendMessage("§c--------------------------------");
				sender.sendMessage("§cDatabase commands:");
				sender.sendMessage("§c/db addBalance (player) (amount)");
				sender.sendMessage("§c/db setBalance (player) (amount)");
				sender.sendMessage("§c/db addXp (player) (amount)");
				sender.sendMessage("§c/db setXp (player) (amount)");
				sender.sendMessage("§c/db setLevel (player) (amount)");
				sender.sendMessage("§c/db addLevel (player) (amount)");
				sender.sendMessage("§c/db getters");
				sender.sendMessage("§c--------------------------------");
				
			}
			else if (args.length == 1){
				
				if(args[0].equalsIgnoreCase("getters")){
					sender.sendMessage("§c--------------------------------");
					sender.sendMessage("§cDatabase getters:");
					sender.sendMessage("§c/db getLevel (player)");
					sender.sendMessage("§c/db getXP (player)");
					sender.sendMessage("§c/db getBalance (player)");
					sender.sendMessage("§c/db getDeaths (player)");
					sender.sendMessage("§c/db getKills (player)");
					sender.sendMessage("§c/db getKD (player)");
					sender.sendMessage("§c/db getLanguage (player)");
					sender.sendMessage("§c/db getNick (player)");
					sender.sendMessage("§c--------------------------------");
					return true;
				}
				else{
					
					sender.sendMessage("§c--------------------------------");
					sender.sendMessage("§cDatabase commands:");
					sender.sendMessage("§c/db addBalance (player) (amount)");
					sender.sendMessage("§c/db setBalance (player) (amount)");
					sender.sendMessage("§c/db addXp (player) (amount)");
					sender.sendMessage("§c/db setXp (player) (amount)");
					sender.sendMessage("§c/db setLevel (player) (amount)");
					sender.sendMessage("§c/db addLevel (player) (amount)");
					sender.sendMessage("§c/db getters");
					sender.sendMessage("§c--------------------------------");
					return true;
					
				}
				
			}
			else if(args.length == 2){
				
				OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
				
				if(args[0].equalsIgnoreCase("getLevel")){
					sender.sendMessage("§cThe level of the player " + target.getName() + " is: " + api.getlevel(target.getName()));
				}
				else if(args[0].equalsIgnoreCase("getXP")){
					sender.sendMessage("§cThe XP of the player " + target.getName() + " is: " + api.getXp(target.getName()));
				}
				else if(args[0].equalsIgnoreCase("getBalance")){
					sender.sendMessage("§cThe balance of the player " + target.getName() + " is: " + api.getBalance(target.getName()));
				}
				else if(args[0].equalsIgnoreCase("getDeaths")){
					sender.sendMessage("§cThe deaths of the player " + target.getName() + " is: " + api.getDeaths(target.getName()));
				}
				else if(args[0].equalsIgnoreCase("getKills")){
					sender.sendMessage("§cThe kills of the player " + target.getName() + " is: " + api.getKills(target.getName()));
				}
				else if(args[0].equalsIgnoreCase("getKD")){
					sender.sendMessage("§cThe K/D of the player " + target.getName() + " is: " + (double) api.getKills(target.getName()) / (double) api.getDeaths(target.getName()));
				}
				else if(args[0].equalsIgnoreCase("getLanguage")){
					sender.sendMessage("§cThe language of the player " + target.getName() + " is: " + String.valueOf(api.getLanguage(target.getName())));
				}
				else if(args[0].equalsIgnoreCase("getNick")){
					sender.sendMessage("§cThe nick of the player " + target.getName() + " is: " + ChatColor.translateAlternateColorCodes('&', api.getNick(target.getName())));
				}
				else{
					sender.sendMessage("§c--------------------------------");
					sender.sendMessage("§cDatabase getters:");
					sender.sendMessage("§c/db getLevel (player)");
					sender.sendMessage("§c/db getXP (player)");
					sender.sendMessage("§c/db getBalance (player)");
					sender.sendMessage("§c/db getDeaths (player)");
					sender.sendMessage("§c/db getKills (player)");
					sender.sendMessage("§c/db getKD (player)");
					sender.sendMessage("§c/db getLanguage (player)");
					sender.sendMessage("§c/db getNick (player)");
					sender.sendMessage("§c--------------------------------");
				}
				
			}
			else if (args.length == 3){
				
				OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
				
				int amount = 0;
				
				try{
					amount = Integer.parseInt(args[2]);
				} catch (NumberFormatException e){
					sender.sendMessage("That argument must be an integer!");
					return true;
				}
				
				if(args[0].equalsIgnoreCase("addBalance")){
					
					api.addBalance(target.getName(), amount);
					sender.sendMessage("§cYou added " + amount + " to " + target.getName() + "'s balance!");
					return true;
					
				}
				else if(args[0].equalsIgnoreCase("setBalance")){
					
					api.setBalance(target.getName(), amount);
					sender.sendMessage("§cYou set the balance of the player " + target.getName() + " to " + amount);
					return true;
					
				}
				else if(args[0].equalsIgnoreCase("addXp")){
					
					api.addXp(target.getName(), amount);
					sender.sendMessage("§cYou added " + amount + " XP to the player " + target.getName());
					return true;
					
				}
				else if(args[0].equalsIgnoreCase("setXp")){
				
					api.setXp(target.getName(), amount);
					sender.sendMessage("§cYou set the XP of the player " + target.getName() + " to: " + amount);
					return true;
					
				}
				else if(args[0].equalsIgnoreCase("setLevel")){
					
					api.setlevel(target.getName(), amount);
					sender.sendMessage("§cYou set the level of the player " + target.getName() + " to: " + amount);
					return true;
					
				}
				else if(args[0].equalsIgnoreCase("addLevel")){
					
					api.addLevel(target.getName(), amount);
					sender.sendMessage("§cYou added " + amount + " levels to the player " + target.getName());
					return true;
					
				}
				
			}
			
		}
		
		return true;
	}

	
	
}
