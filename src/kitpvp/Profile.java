package kitpvp;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.SkullType;
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

import kitpvp.Util.ChatUtils;
import kitpvp.Util.KitAPI;

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
			
			OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
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
					ChatUtils.sendMessageWithPrefix(p, "§7That player's account is private!");
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
		
		OfflinePlayer offTarget = Bukkit.getOfflinePlayer(target);
		
		if(Main.getAPI().getLanguage(opener.getName()) == Language.FINNISH){
			
			Inventory inv = Bukkit.createInventory(null, 36, "Profiili " + offTarget.getName());
			
			ItemStack user = new ItemStack(Material.SKULL_ITEM, 1, (byte) SkullType.PLAYER.ordinal());
			SkullMeta userMeta = (SkullMeta) user.getItemMeta();
			userMeta.setOwner(target);
			userMeta.setDisplayName("§e" + target);
			userMeta.setLore(Arrays.asList("§7§oSinun profiili."));
			user.setItemMeta(userMeta);
			
			inv.setItem(13, user);
			
			api.createItem(inv, 19, Material.BOOK, 1, "§a§lTilastot", Arrays.asList("§7Klikkaa nähdäksesi sinun", "§7tilastot!"));
			api.createItem(inv, 21, Material.NETHER_STAR, 1, "§b§lInfo", Arrays.asList("§7Rank: §a" + Main.getPermissions().getPrimaryGroup(opener.getWorld().getName(), offTarget), 
					"§7Paikalla: §a" + offTarget.isOnline(), "§7Peliaika: §a" + Main.getAPI().getPlayTime(target), "§7Viim. kirjautuminen: §a" + Main.getAPI().getLastLogin(target)));
			api.createItem(inv, 23, Material.PAPER, 1, "§e§lSaavutukset", Arrays.asList("§7Klikkaa nähdäksesi hänen", "§7saavutuksensa!"));
			
			opener.openInventory(inv);
		}
		else if(Main.getAPI().getLanguage(opener.getName()) == Language.ENGLISH){
			
			Inventory inv = Bukkit.createInventory(null, 36,  "Profile " + offTarget.getName());
			
			ItemStack user = new ItemStack(Material.SKULL_ITEM, 1, (byte) SkullType.PLAYER.ordinal());
			SkullMeta userMeta = (SkullMeta) user.getItemMeta();
			userMeta.setOwner(target);
			userMeta.setDisplayName("§e" + target);
			userMeta.setLore(Arrays.asList("§7§oYour profile."));
			user.setItemMeta(userMeta);
			
			inv.setItem(13, user);
			
			api.createItem(inv, 19, Material.BOOK, 1, "§a§lStats", Arrays.asList("§7Click to see your stats"));
			api.createItem(inv, 21, Material.NETHER_STAR, 1, "§b§lInfo", Arrays.asList("§7Rank: §a" + Main.getPermissions().getPrimaryGroup(opener.getWorld().getName(), offTarget), 
					"§7Online: §a" + offTarget.isOnline(), "§7Playtime: §a" + Main.getAPI().getPlayTime(target), "§7Last login: §a" + Main.getAPI().getLastLogin(target)));
			api.createItem(inv, 23, Material.PAPER, 1, "§e§lAchievements", Arrays.asList("§7Click to see the achievements", "§7of " + target + "!"));
			
			opener.openInventory(inv);
		}
		
	}
	
	public void openProfileMenu(Player p){
		
		if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
			
			Inventory inv = Bukkit.createInventory(null, 36, "Profiili");
			
			ItemStack user = new ItemStack(Material.SKULL_ITEM, 1, (byte) SkullType.PLAYER.ordinal());
			SkullMeta userMeta = (SkullMeta) user.getItemMeta();
			userMeta.setOwner(p.getName());
			userMeta.setDisplayName("§e" + p.getName());
			userMeta.setLore(Arrays.asList("§7§oSinun profiili."));
			user.setItemMeta(userMeta);
			
			inv.setItem(13, user);
			
			api.createItem(inv, 19, Material.BOOK, 1, "§a§lTilastot", Arrays.asList("§7Klikkaa nähdäksesi sinun", "§7tilastot!"));
			api.createItem(inv, 21, Material.NETHER_STAR, 1, "§b§lInfo", Arrays.asList("§7Rank: §a" + Main.getPermissions().getPrimaryGroup(p), 
					"§7Paikalla: §a" + p.isOnline(), "§7Peliaika: §a" + Main.getAPI().getPlayTime(p.getName())));
			api.createItem(inv, 23, Material.PAPER, 1, "§e§lSaavutukset", Arrays.asList("§7Klikkaa nähdäksesi sinun", "§7saavutukset!"));
			
			p.openInventory(inv);
		}
		else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
			
			Inventory inv = Bukkit.createInventory(null, 36, "Profile");
			
			ItemStack user = new ItemStack(Material.SKULL_ITEM, 1, (byte) SkullType.PLAYER.ordinal());
			SkullMeta userMeta = (SkullMeta) user.getItemMeta();
			userMeta.setOwner(p.getName());
			userMeta.setDisplayName("§e" + p.getName());
			userMeta.setLore(Arrays.asList("§7§oYour profile."));
			user.setItemMeta(userMeta);
			
			inv.setItem(13, user);
			
			api.createItem(inv, 19, Material.BOOK, 1, "§a§lStats", Arrays.asList("§7Click to see your stats"));
			api.createItem(inv, 21, Material.NETHER_STAR, 1, "§b§lInfo", Arrays.asList("§7Rank: §a" + Main.getPermissions().getPrimaryGroup(p), 
					"§7Online: §a" + p.isOnline(), "§7Playtime: §a" + Main.getAPI().getPlayTime(p.getName())));
			api.createItem(inv, 23, Material.PAPER, 1, "§e§lAchievements", Arrays.asList("§7Click to see your achievements!"));
			
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
		} else if (e.getInventory().getName().equals("Profiili")) {

			e.setCancelled(true);

			if (item.getType() == Material.BOOK) {
				p.closeInventory();
				Main.getAPI().sendPlayerStats(p, p.getName());
			}
		}
	}
	
}
