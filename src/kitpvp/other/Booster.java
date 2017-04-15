package kitpvp.other;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import kitpvp.Main;
import kitpvp.Util.ChatUtils;
import kitpvp.Util.Language;
import kitpvp.cosmetics.CosmeticManager;

public class Booster implements Listener, CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) return true;
		
		Player p = (Player) sender;
		
		if(args.length == 0){
			
			openBoosterMenu(p);
			
		}
		else if (args.length == 1){
			if(args[0].equalsIgnoreCase("?") || args[0].equalsIgnoreCase("help")){
				if(!(sender.isOp())){
					ChatUtils.sendPermissionMessageAdmin(sender);
					return true;
				}
				
				sender.sendMessage("§c----------------------");
				sender.sendMessage("§cBooster Admin commands:");
				sender.sendMessage("§c/booster give (player) (amount)");
				sender.sendMessage("§c/booster set (player) (amount)");
				sender.sendMessage("§c/booster get (player)");
				sender.sendMessage("§c/booster thank");
				sender.sendMessage("§c----------------------");
				
			}
			else if(args[0].equalsIgnoreCase("thank")){
				
				if(!Main.getAPI().isBoosterInUse()){ 
					if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
						ChatUtils.sendMessageWithPrefix(p, "§7Boosteri ei ole käynnissä.");
					}
					else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
						ChatUtils.sendMessageWithPrefix(p, "§7A booster is not in use!");
					}
					return true; 
				}
				
				if(Main.getAPI().getCurrentBoosterUser() == sender.getName()){
					if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
						ChatUtils.sendMessageWithPrefix(p, "§7Et voi kiittää itseäsi!");
					}
					else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
						ChatUtils.sendMessageWithPrefix(p, "§7You can't thank yourself!");
					}
					return true;
				}
				
				if(Main.getConfigFile().getStringList("Booster.thankedPlayers").contains(p.getName())){
					if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
						ChatUtils.sendMessageWithPrefix(p, "§7Olet jo kiittänyt tätä pelaajaa!");
					}
					else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
						ChatUtils.sendMessageWithPrefix(p, "§7You've already thanked this player!");
					}
				}
				
				Main.getConfigFile().getStringList("Booster.thankedPlayers").add(p.getName());
				Main.getInstance().saveConfig();
				
				if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
					ChatUtils.sendMessageWithPrefix(p, "§7Kiitit pelaajaa §c" + Main.getAPI().getCurrentBoosterUser() + " §7ja sait siitä §c10$§7!");
				}
				else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
					ChatUtils.sendMessageWithPrefix(p, "§7You thanked the player §c" + Main.getAPI().getCurrentBoosterUser() + " §7and you received §c10$§7!");
				}
				Main.getAPI().addBalance(Main.getAPI().getCurrentBoosterUser(), 10);
				Main.getAPI().addBalance(p.getName(), 10);
				
			}
			else{
				sender.sendMessage("§c/booster ?");
				return true;
			}
		}
		else if (args.length == 2){
			
			if(!(sender.isOp())){
				ChatUtils.sendPermissionMessageAdmin(sender);
				return true;
			}
			
			OfflinePlayer target = Bukkit.getPlayer(args[1]);
			
			if(args[0].equalsIgnoreCase("get")){
				CosmeticManager cm = new CosmeticManager();
				sender.sendMessage("§cThe player " + target.getName() + " has " + cm.getBoosters(target.getName()) + " boosters!");
			}
			
		}
		else if (args.length == 3){
			
			if(!(sender.isOp())){
				ChatUtils.sendPermissionMessageAdmin(sender);
				return true;
			}
			
			
			OfflinePlayer target = Bukkit.getPlayer(args[1]);
			int amount = 0;

			try {
				amount = Integer.parseInt(args[2]);
			} catch (NumberFormatException e) {
				sender.sendMessage("That argument must be an integer!");
				return true;
			}
			
			if(args[0].equalsIgnoreCase("give")){
				Main.getAPI().addBoosters(target.getName(), amount);
				sender.sendMessage("§cYou gave " + amount + " boosters to " + target.getName());
			}
			else if(args[0].equalsIgnoreCase("set")){
				Main.getAPI().setBoosters(target.getName(), amount);
				sender.sendMessage("§cYou set " + amount + " boosters to " + target.getName());
			}
			else{
				sender.sendMessage("§c/booster ?");
				return true;
			}
			
		}
		else{
			sender.sendMessage("§c/booster ?");
			return true;
		}
		
		return true;
	}
	
	public void openBoosterMenu(Player p){
		
		CosmeticManager cm = new CosmeticManager();
		
		int boosters = cm.getBoosters(p.getName());
		
		if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
			
			Inventory inv = Bukkit.createInventory(null, 27, "Booster");
			if(Main.getAPI().isBoosterInUse()){
				Main.getAPI().createItem(inv, 13, Material.EMERALD, 1, "§a§lBooster", Arrays.asList("", "§fSinulla on §a" + boosters + " §fboosteria!",
						"§7Nykyinen booster: §a" + Main.getAPI().getCurrentBoosterUser(),
						"§7Aikaa jäljellä: §a" + Main.getAPI().getBoosterTimeLeft(),
						"", "§7Kun boosteri on käytössä, kaikki", "§7serverin pelaajat saavat §a2 tunnin", "§7ajan tupla rahat ja XP:n!", "", "§7Voit ostaa boosterin nettikaupastamme:",
						"§astore.finska.com"));
			}
			else if(!Main.getAPI().isBoosterInUse()) {
				Main.getAPI().createItem(inv, 13, Material.EMERALD, 1, "§a§lBooster", Arrays.asList("", "§fSinulla on §a" + Main.getCosmeticManager().getBoosters(p.getName()) + " §fboosteria!",
						"", "§7Kun boosteri on käytössä, kaikki", "§7serverin pelaajat saavat §a2 tunnin", "§7ajan tupla rahat ja XP:n!", "", "§7Voit ostaa boosterin nettikaupastamme:",
						"§astore.finska.com"));
			}
			
			p.openInventory(inv);
			
		}
		else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
			
			Inventory inv = Bukkit.createInventory(null, 27, "Booster");
			
			if(Main.getAPI().isBoosterInUse()){
				Main.getAPI().createItem(inv, 13, Material.EMERALD, 1, "§a§lBooster", Arrays.asList("", "§fYou have §a" + boosters + " §fboosters!",
						"§7Current booster: §a" + Main.getAPI().getCurrentBoosterUser(),
						"§7Time left: §a" + Main.getAPI().getBoosterTimeLeft(),
						"", "§7When a booster is in use, everyone", "§7on the server gets double money and XP", "§7for §a2 hours§7!", "", "§7You can buy boosters at our store:",
						"§astore.finska.com"));
			}
			else if(!Main.getAPI().isBoosterInUse()){
				Main.getAPI().createItem(inv, 13, Material.EMERALD, 1, "§a§lBooster", Arrays.asList("", "§fYou have §a" + Main.getCosmeticManager().getBoosters(p.getName()) + " §fboosters!",
						"", "§7When a booster is in use, everyone", "§7on the server gets double money and XP", "§7for §a2 hours§7!", "", "§7You can buy boosters at our store:",
						"§astore.finska.com"));
			}
			
			p.openInventory(inv);
			
		}
		
	}
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e){
		
		if(e.getCurrentItem() == null || e.getCurrentItem() == null) return;
		
		Player p = (Player) e.getWhoClicked();
		
		if(e.getInventory().getName().contains("Booster")){
			
			e.setCancelled(true);
			
			if(e.getCurrentItem().getType() == Material.EMERALD){
				
				if(Main.getAPI().isBoosterInUse()){
					p.closeInventory();
					if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
						ChatUtils.sendMessageWithPrefix(p, "§7Boosteri on jo päällä!");
					}
					if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
						ChatUtils.sendMessageWithPrefix(p, "§7A booster is already in use!");
					}
					return;
				}
				
				if(Main.getAPI().getBoosters(p.getName()) <= 0){
					p.closeInventory();
					if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
						ChatUtils.sendMessageWithPrefix(p, "§7Sinulla ei ole tarpeeksi boostereita! Voit ostaa niitä täältä: §astore.finska.com§7!");
					}
					if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
						ChatUtils.sendMessageWithPrefix(p, "§7You don't have enough boosters! You can buy them here: §astore.finska.com§7!");
					}
					return;
				}
				
				p.closeInventory();
				Main.getAPI().activateBooster(p.getName());
				
				
			}
			
		}
		
	}

}
