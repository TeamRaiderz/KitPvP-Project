package kitpvp.other;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.SkullType;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import kitpvp.Main;
import kitpvp.Util.ChatUtils;
import kitpvp.Util.KitAPI;
import kitpvp.Util.Language;
import kitpvp.cosmetics.CosmeticManager;

public class Profile implements Listener, CommandExecutor {

	private KitAPI api = Main.getAPI();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) return true;
		Player p = (Player) sender;
		
		if(args.length == 0){
			
			openProfileMenu(p);
			
		}
		else if (args.length == 1){
			
			Player target = Bukkit.getPlayer(args[0]);
			
			if(target == null || !target.isOnline()){
				ChatUtils.sendPlayerNotFoundMsg(sender, args[0]);
			}
			
			if(Main.getDataFile().get(target.getUniqueId().toString()) == null){
				
				if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
					ChatUtils.sendMessageWithPrefix(p, "§7En löytänyt kyseistä pelaajaa tietokannastamme.");
				}
				else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
					ChatUtils.sendMessageWithPrefix(p, "§7Couldn't find that player from our database.");
				}
				return true;
			}
			
			if(Main.getDataFile().getBoolean(target.getUniqueId().toString() + ".privateAccount")){
				if(api.getLanguage(p.getName()) == Language.FINNISH){
					ChatUtils.sendMessageWithPrefix(p, "§7Tuolla pelaajalla on käyttäjä yksityisenä!");
					return true;
				}
				else if(api.getLanguage(p.getName()) == Language.ENGLISH){
					ChatUtils.sendMessageWithPrefix(p, "§7That account is private!");
					return true;
				}
				return true;
			}
			openProfileMenu(p, target.getName());
			
		}
		else return true;
		
		return true;
	}

	public void openProfileMenu(Player opener, String target){
		
		CosmeticManager cm = new CosmeticManager();
		
		int tokens = cm.getTokens(target);
		int chests =  cm.getChests(target);
		int boosters =  cm.getBoosters(target);
		
		if(Main.getAPI().getLanguage(opener.getName()) == Language.FINNISH){
			
			Inventory inv = Bukkit.createInventory(null, 27, "Profiili " + target);
			
			api.createSkullItem(inv, 10, target, "§e" + target, Arrays.asList(
					"",
					"§7Rank: §a" + Main.getPermissions().getPrimaryGroup(Bukkit.getPlayer(target)),
					"",
					"§7Tokenit: §6" + tokens,
					"§7Chestit: §6" + chests,
					"§7Boosterit: §6" + boosters,
					"§7Raha: §6" + api.getBalance(target),
					"",
					"§7Level: §b" + api.getLevel(target),
					"§7XP: §b" + api.getXp(target) + "/" + (api.getLevel(target) * 100)));
			
			api.createItem(inv, 12, Material.BOOK, 1, "§eTilastot", Arrays.asList("§7Klikkaa nähdäksesi pelaajan", "§e" + target + " §7tilastot!"));
			api.createItem(inv, 13, Material.PAPER, 1, "§eSaavutukset", Arrays.asList("§7Klikkaa nähdäksesi pelaajan", "§e" + target + " §7saavutukset!"));
			
			opener.openInventory(inv);
		}
		else if(Main.getAPI().getLanguage(opener.getName()) == Language.ENGLISH){
			
			Inventory inv = Bukkit.createInventory(null, 27,  "Profile " + target);
			
			api.createSkullItem(inv, 10, target, "§e" + target, Arrays.asList(
					"",
					"§7Rank: §a" + Main.getPermissions().getPrimaryGroup(Bukkit.getPlayer(target)),
					"",
					"§7Tokens: §6" + tokens,
					"§7Chests: §6" + chests,
					"§7Boosters: §6" + boosters,
					"§7Balance: §6" + api.getBalance(target),
					"",
					"§7Level: §b" + api.getLevel(target),
					"§7XP: §b" + api.getXp(target) + "/" + (api.getLevel(target) * 100)));
			
			api.createItem(inv, 12, Material.BOOK, 1, "§eStatistics", Arrays.asList("§7Click to see the stats of", "§e" + target + "§7!"));
			api.createItem(inv, 13, Material.PAPER, 1, "§eAchievements", Arrays.asList("§7Click to see the achievements", "§7of the player §e" + target + "§7!"));
			
			opener.openInventory(inv);
		}
		
	}
	
	public void openProfileMenu(Player p){
		
		CosmeticManager cm = new CosmeticManager();
		
		int tokens = cm.getTokens(p.getName());
		int chests = cm.getChests(p.getName());
		int boosters = cm.getBoosters(p.getName());
		
		if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
			
			Inventory inv = Bukkit.createInventory(null, 27, "Profiili");
			
			api.createSkullItem(inv, 10, p.getName(), "§e" + p.getName(), Arrays.asList(
					"",
					"§7Rank: §a" + Main.getPermissions().getPrimaryGroup(Bukkit.getPlayer(p.getName())),
					"",
					"§7Tokenit: §6" + tokens,
					"§7Chestit: §6" + chests,
					"§7Boosterit: §6" + boosters,
					"§7Raha: §6" + api.getBalance(p.getName()),
					"",
					"§7Level: §b" + api.getLevel(p.getName()),
					"§7XP: §b" + api.getXp(p.getName()) + "/" + (api.getLevel(p.getName()) * 100)));
			
			api.createItem(inv, 12, Material.BOOK, 1, "§eTilastot", Arrays.asList("§7Klikkaa nähdäksesi tilastosi!"));
			api.createItem(inv, 13, Material.PAPER, 1, "§eSaavutukset", Arrays.asList("§7Klikkaa nähdäksesi saavutuksesi!"));
			api.createItem(inv, 14, Material.STORAGE_MINECART, 1, "§eTilaukset", Arrays.asList("§7Klikkaa nähdäksesi tilauksesi!"));
			api.createItem(inv, 15, Material.REDSTONE_COMPARATOR, 1, "§eAsetukset", Arrays.asList("§7Klikkaa päästäksesi asetuksiin!"));
			
			p.openInventory(inv);
		}
		else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
			
			Inventory inv = Bukkit.createInventory(null, 27, "Profile");
			
			api.createSkullItem(inv, 10, p.getName(), "§e" + p.getName(), Arrays.asList(
					"",
					"§7Rank: §a" + Main.getPermissions().getPrimaryGroup(Bukkit.getPlayer(p.getName())),
					"",
					"§7Tokens: §6" + tokens,
					"§7Chests: §6" + chests,
					"§7Boosters: §6" + boosters,
					"§7Balance: §6" + api.getBalance(p.getName()),
					"",
					"§7Level: §b" + api.getLevel(p.getName()),
					"§7XP: §b" + api.getXp(p.getName()) + "/" + (api.getLevel(p.getName()) * 100)));
			
			api.createItem(inv, 12, Material.BOOK, 1, "§eStatistics", Arrays.asList("§7Click to see your stats!"));
			api.createItem(inv, 13, Material.PAPER, 1, "§eAchievements", Arrays.asList("§7Click to see the your achievements!"));
			api.createItem(inv, 14, Material.STORAGE_MINECART, 1, "§eDeliveries", Arrays.asList("§7Click to see your deliveries!"));
			api.createItem(inv, 15, Material.REDSTONE_COMPARATOR, 1, "§eSettings", Arrays.asList("§7Click to got to settings!"));
			
			p.openInventory(inv);
		}
		
	}
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e){
		
		Player p = (Player) e.getWhoClicked();
		
		ItemStack item = e.getCurrentItem();
		
		if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
		
		if (e.getInventory().getName().contains("Profiili ")) {

			OfflinePlayer off = Bukkit.getOfflinePlayer(e.getInventory().getName().replace("Profiili ", ""));

			e.setCancelled(true);

			if (item.getType() == Material.BOOK) {
				p.closeInventory();
				Main.getAPI().sendPlayerStats(p, off.getName());
			}

		} else if (e.getInventory().getName().contains("Profile ")) {

			OfflinePlayer off = Bukkit.getOfflinePlayer(e.getInventory().getName().replace("Profile ", ""));

			e.setCancelled(true);

			if (item.getType() == Material.BOOK) {
				p.closeInventory();
				Main.getAPI().sendPlayerStats(p, off.getName());
			}

		} else if (e.getInventory().getName().equals("Profile")) {

			e.setCancelled(true);

			if (item.getType() == Material.BOOK) {
				p.closeInventory();
				Main.getAPI().sendPlayerStats(p, p.getName());
			}
			else if(item.getType() == Material.STORAGE_MINECART){
				Mail mail = new Mail();
				mail.openMenu(p);
			}
			else if(item.getType() == Material.REDSTONE_COMPARATOR){
				Settings settings = new Settings();
				settings.openSettingsGUI(p);
			}
		} else if (e.getInventory().getName().equals("Profiili")) {

			e.setCancelled(true);

			if (item.getType() == Material.BOOK) {
				p.closeInventory();
				Main.getAPI().sendPlayerStats(p, p.getName());
			}
			else if(item.getType() == Material.STORAGE_MINECART){
				Mail mail = new Mail();
				mail.openMenu(p);
			}
			else if(item.getType() == Material.REDSTONE_COMPARATOR){
				Settings settings = new Settings();
				settings.openSettingsGUI(p);
			}
		}
	}
	
}
