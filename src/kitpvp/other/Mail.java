package kitpvp.other;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;

import kitpvp.Main;
import kitpvp.Util.ChatUtils;
import kitpvp.Util.KitAPI;
import kitpvp.Util.Language;
import kitpvp.cosmetics.CosmeticManager;

public class Mail implements Listener, CommandExecutor{

	private KitAPI api = new KitAPI();
	private CosmeticManager cm = new CosmeticManager();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender instanceof Player){
			openMenu((Player)sender);
		}
		
		return true;
	}

	public void openMenu(Player p){
		
		if(api.getLanguage(p.getName()) == Language.FINNISH){
			
			Inventory inv = Bukkit.createInventory(null, 27, "Toimitukset");
			
			if(isDailyCollected(p)){
				api.createItem(inv, 12, Material.STORAGE_MINECART, 1, "§cPäivittäinen", Arrays.asList("§7Tämä toimitus sisältää:", 
						"§6- 10 Xp", 
						"§6- 20 Rahaa", "", "§cKlikkaa saadaksesi!"));
			}
			else{
				api.createItem(inv, 12, Material.STORAGE_MINECART, 1, "§aPäivittäinen", Arrays.asList("§7Tämä toimitus sisältää:", 
						"§6- 10 Xp", 
						"§6- 20 Rahaa", "", "§aKlikkaa saadaksesi!"));
			}
			
			if(isVIPCollected(p)){
				if(!(p.hasPermission("server.booster"))){
					api.createItem(inv, 14, Material.HOPPER_MINECART, 1, "§cV.I.P", Arrays.asList("§7Tämä toimitus sisältää:",
							"§6- 30 Xp", 
							"§6- 100 Rahaa", 
							"§6- 1 Token", "", "§cSinulla täytyy olla rankki §3§lBOOSTER§c!"));
				}
				else{
					api.createItem(inv, 14, Material.HOPPER_MINECART, 1, "§cV.I.P", Arrays.asList("§7Tämä toimitus sisältää:",
							"§6- 30 Xp", 
							"§6- 100 Rahaa", 
							"§6- 1 Token", "", "§cKlikkaa saadaksesi!"));
				}
			}
			else{
				if(!(p.hasPermission("server.booster"))){
					api.createItem(inv, 14, Material.HOPPER_MINECART, 1, "§cV.I.P", Arrays.asList("§7Tämä toimitus sisältää:",
							"§6- 30 Xp", 
							"§6- 100 Rahaa", 
							"§6- 1 Token", "", "§cSinulla täytyy olla rankki §3§lBOOSTER§c!"));
				}
				else{
					api.createItem(inv, 14, Material.HOPPER_MINECART, 1, "§cV.I.P", Arrays.asList("§7Tämä toimitus sisältää:",
							"§6- 30 Xp", 
							"§6- 100 Rahaa", 
							"§6- 1 Token", "", "§cKlikkaa saadaksesi!"));
				}
			}
			
			p.openInventory(inv);
		}
		else if(api.getLanguage(p.getName()) == Language.ENGLISH){
			
			Inventory inv = Bukkit.createInventory(null, 27, "Deliveries");
			
			if(isDailyCollected(p)){
				api.createItem(inv, 12, Material.STORAGE_MINECART, 1, "§cDaily", Arrays.asList("§7This delivery contains:", 
						"§6- 10 Xp", 
						"§6- 20 Money", "", "§cClick to receive!"));
			}
			else{
				api.createItem(inv, 12, Material.STORAGE_MINECART, 1, "§aDaily", Arrays.asList("§7This delivery contains:", 
						"§6- 10 Xp", 
						"§6- 20 Money", "", "§aClick to receive!"));
			}
			
			if(isVIPCollected(p)){
				if(!(p.hasPermission("server.booster"))){
					api.createItem(inv, 14, Material.HOPPER_MINECART, 1, "§cV.I.P", Arrays.asList("§7This delivery contains:",
							"§6- 30 Xp", 
							"§6- 100 Money", 
							"§6- 1 Token", "", "§cYou must be the rank §3§lBOOSTER§c!"));
				}
				else{
					api.createItem(inv, 14, Material.HOPPER_MINECART, 1, "§cV.I.P", Arrays.asList("§7This delivery contains:",
							"§6- 30 Xp", 
							"§6- 100 Money", 
							"§6- 1 Token", "", "§cClick to receive!"));
				}
			}
			else{
				if(!(p.hasPermission("server.booster"))){
					api.createItem(inv, 14, Material.HOPPER_MINECART, 1, "§aV.I.P", Arrays.asList("§7This delivery contains:",
							"§6- 30 Xp", 
							"§6- 100 Money", 
							"§6- 1 Token", "", "§cYou must be the rank §3§lBOOSTER§c!"));
				}
				else{
					api.createItem(inv, 14, Material.HOPPER_MINECART, 1, "§aV.I.P", Arrays.asList("§7This delivery contains:",
							"§6- 30 Xp", 
							"§6- 100 Money", 
							"§6- 1 Token", "", "§aClick to receive!"));
				}
			}
			
			p.openInventory(inv);
		}
		
	}
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e){
		
		Player p = (Player) e.getWhoClicked();
		
		if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
		
		if(e.getInventory().getName().equals("Toimitukset") || e.getInventory().getName().equals("Deliveries")){
			
			e.setCancelled(true);
			
			if(e.getCurrentItem().getType() == Material.STORAGE_MINECART && e.getCurrentItem().hasItemMeta()){
				p.closeInventory();
				
				if(isDailyCollected(p)){
					p.closeInventory();
					if(api.getLanguage(p.getName()) == Language.FINNISH){
						ChatUtils.sendMessageWithPrefix(p, "§7Et voi vielä lunastaa palkintoasi! Aikaa jäljellä: §c" + getDailyCooldownTimeLeft(p.getName()) + "§7!");
					}
					else if (api.getLanguage(p.getName()) == Language.ENGLISH){
						ChatUtils.sendMessageWithPrefix(p, "§7You can't collect this reward yet! Time left: §c" + getDailyCooldownTimeLeft(p.getName()) + "§7!");
					}
				}
				else{
					giveDailyReward(p.getName(), 1, 86400000);
					api.addBalance(p.getName(), 20);
					api.addXp(p.getName(), 10);
					
					if(api.getLanguage(p.getName()) == Language.FINNISH){
						ChatUtils.sendMessageWithPrefix(p, "§7Lunastit palkintosi! Voit lunastaa tämä tämän uudelleen huomenna!");
					}
					else if (api.getLanguage(p.getName()) == Language.ENGLISH){
						ChatUtils.sendMessageWithPrefix(p, "§7You collected your reward! Come back tomorrow to collect it again!");
					}
				}
				
			}
			else if(e.getCurrentItem().getType() == Material.HOPPER_MINECART && e.getCurrentItem().hasItemMeta()){
				p.closeInventory();
				
				if(isVIPCollected(p)){
					if(api.getLanguage(p.getName()) == Language.FINNISH){
						ChatUtils.sendMessageWithPrefix(p, "§7Et voi vielä lunastaa palkintoasi! Aikaa jäljellä: §c" + getVIPCooldownTimeLeft(p.getName()) + "§7!");
					}
					else if (api.getLanguage(p.getName()) == Language.ENGLISH){
						ChatUtils.sendMessageWithPrefix(p, "§7You can't collect this reward yet! Time left: §c" + getVIPCooldownTimeLeft(p.getName()) + "§7!");
					}
				}
				else{
					giveVIPReward(p.getName(), 30, 86400000);
					api.addBalance(p.getName(), 100);
					api.addXp(p.getName(), 30);
					cm.addTokens(p.getName(), 1);
					
					if(api.getLanguage(p.getName()) == Language.FINNISH){
						ChatUtils.sendMessageWithPrefix(p, "§7Lunastit palkintosi! Voit lunastaa tämä tämän uudelleen §c30 §7päivän kuluttua!");
					}
					else if (api.getLanguage(p.getName()) == Language.ENGLISH){
						ChatUtils.sendMessageWithPrefix(p, "§7You collected your reward! Come back in §c30 §7days to collect it again!");
					}
				}
				
			}
			
		}
		
	}
	
	public void giveDailyReward(String target, int time, int multi) {
		Player p = Bukkit.getPlayer(target);
		
		if(p == null){ return; }

		long endOfBan = (System.currentTimeMillis() / 1000) + (24 * 60 * 60) * time;

		Main.getDataFile().set(p.getUniqueId().toString() + ".dailyReward.collected", true);
		Main.getDataFile().set(p.getUniqueId().toString() + ".dailyReward.cooldown", endOfBan);
		Main.saveDataFile();

	}
	
	private boolean isDailyCollected(Player p){
		return Main.getDataFile().getBoolean(p.getUniqueId().toString() + ".dailyReward.collected");
	}
	
	public Long getEndOfDailyCooldown(String player){
		OfflinePlayer p = Bukkit.getOfflinePlayer(player);
		return Long.valueOf(Main.getDataFile().getLong(p.getUniqueId().toString() + ".dailyReward.cooldown"));
	}
	
	public String getDailyCooldownTimeLeft(String player) {
		OfflinePlayer p = Bukkit.getOfflinePlayer(player);
		
		long current = (System.currentTimeMillis()/1000);
	    long endOfban = getEndOfDailyCooldown(p.getName()).longValue();
	    long millis = endOfban - current;

	    int seconds = 0;
	    int minutes = 0;
	    int hours = 0;

	    while (millis > 1L) {
	      millis -= 1L;
	      seconds++;
	    }
	    while (seconds > 60) {
	      seconds -= 60;
	      minutes++;
	    }
	    while (minutes > 60) {
	      minutes -= 60;
	      hours++;
	    }
		    
		    return hours + "h " + minutes + "min " + seconds + "s";
	}
	
	public void giveVIPReward(String target, int time, int multi) {
		Player p = Bukkit.getPlayer(target);
		
		if(p == null){ return; }

		long endOfBan = (System.currentTimeMillis() / 1000) + (24 * 60 * 60) * time;

		Main.getDataFile().set(p.getUniqueId().toString() + ".VIPReward.collected", true);
		Main.getDataFile().set(p.getUniqueId().toString() + ".VIPReward.cooldown", endOfBan);
		Main.saveDataFile();

	}
	
	private boolean isVIPCollected(Player p){
		return Main.getDataFile().getBoolean(p.getUniqueId().toString() + ".VIPReward.collected");
	}
	
	public Long getEndOfVIPCooldown(String player){
		OfflinePlayer p = Bukkit.getOfflinePlayer(player);
		return Long.valueOf(Main.getDataFile().getLong(p.getUniqueId().toString() + ".VIPReward.cooldown"));
	}
	
	public String getVIPCooldownTimeLeft(String player) {
		OfflinePlayer p = Bukkit.getOfflinePlayer(player);

		long current = (System.currentTimeMillis() / 1000);
		long endOfban = getEndOfVIPCooldown(p.getName()).longValue();
		long millis = endOfban - current;

		int seconds = 0;
		int minutes = 0;
		int hours = 0;
		int days = 0;

		while (millis > 1L) {
			millis -= 1L;
			seconds++;
		}
		while (seconds > 60) {
			seconds -= 60;
			minutes++;
		}
		while (minutes > 60) {
			minutes -= 60;
			hours++;
		}
		while (hours > 24) {
			hours -= 24;
			days++;
		}

		return days + "d " + hours + "h " + minutes + "min " + seconds + "s";
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		
		Player p = e.getPlayer();
		
		long current = System.currentTimeMillis() / 1000;
		long endDaily = getEndOfDailyCooldown(p.getName()).longValue();
		long endVIP = getEndOfVIPCooldown(p.getName()).longValue();
		
		if(endDaily < current){
			Main.getDataFile().set(p.getUniqueId().toString() + ".dailyReward.collected", false);
			Main.saveDataFile();
		}
		if(endVIP < current){
			Main.getDataFile().set(p.getUniqueId().toString() + ".VIPReward.collected", false);
			Main.saveDataFile();
		}
		
	}
	
}
