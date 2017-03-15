package kitpvp;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import kitpvp.Util.ChatUtils;
import kitpvp.Util.KitAPI;

public class Settings implements CommandExecutor, Listener {

	private FileConfiguration data = Main.getDataFile();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(label.equalsIgnoreCase("settings") || label.equalsIgnoreCase("asetukset")){
			
			if(!(sender instanceof Player)) return true;
			
			openSettingsGUI((Player) sender);
			
		}
		
		return true;
	}
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e){
		
		Player p = (Player) e.getWhoClicked();
		ItemStack item = e.getCurrentItem();
		Inventory inv = e.getInventory();
		String uuid = p.getUniqueId().toString();
		
		if(item == null || item.getType() == Material.AIR) return;
		
		if(inv.getName().equals("Asetukset") || inv.getName().equals("Settings")){
			
			e.setCancelled(true);
			
			if(item.getType() == Material.BOOK_AND_QUILL && (data.getBoolean(uuid + ".chatMention"))){
				data.set(uuid + ".chatMention", false);
				p.closeInventory();
				Main.saveDataFile();
				if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
					ChatUtils.sendMessageWithPrefix(p, "§7Chat-maininnat ovat nyt §cpois päältä§7!");
				} else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
					ChatUtils.sendMessageWithPrefix(p, "§7Chat mentions are now §coff§7!");
				}
			}
			else if(item.getType() == Material.BOOK_AND_QUILL && !(data.getBoolean(uuid + ".chatMention"))){
				data.set(uuid + ".chatMention", true);
				p.closeInventory();
				Main.saveDataFile();
				if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
					ChatUtils.sendMessageWithPrefix(p, "§7Chat-maininnat ovat nyt §apäällä§7!");
				} else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
					ChatUtils.sendMessageWithPrefix(p, "§7Chat mentions are now §aon§7!");
				}
			}
			else if(item.getType() == Material.PAPER && (data.getBoolean(uuid + ".scoreboard"))){
				data.set(uuid + ".scoreboard", false);
				p.closeInventory();
				Main.saveDataFile();
				if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
					ChatUtils.sendMessageWithPrefix(p, "§7Scoreboard on nyt §cpois päältä§7!");
				} else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
					ChatUtils.sendMessageWithPrefix(p, "§7Scroreboard is now §coff§7!");
				}
			}
			else if(item.getType() == Material.PAPER && !(data.getBoolean(uuid + ".scoreboard"))){
				data.set(uuid + ".scoreboard", true);
				p.closeInventory();
				Main.saveDataFile();
				if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
					ChatUtils.sendMessageWithPrefix(p, "§7Scoreboard on nyt §apäällä§7!");
				} else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
					ChatUtils.sendMessageWithPrefix(p, "§7Scoreboard is now §aon§7!");
				}
				Main.getAPI().giveScoreboard(p);
			}
			else if (item.getType() == Material.REDSTONE && item.hasItemMeta()){
				if(!p.hasPermission("settings.yt")){
					p.closeInventory();
					ChatUtils.sendPermissionMessageYT(p);
				}
				else{
					openSettingsYTGUI(p);
				}
			}
			else if (item.getType() == Material.GOLD_INGOT && item.hasItemMeta()){
				if(!p.hasPermission("settings.vip")){
					p.closeInventory();
					ChatUtils.sendPermissionMessageBooster(p);
				}
				else{
					openSettingsVIPGUI(p);
				}
			}
			
		}
		else if (inv.getName().equals("YT-Asetukset") || inv.getName().equals("YT-Settings")){
			
			e.setCancelled(true);
			
			if(item.getType() == Material.ARROW){
				openSettingsGUI(p);
			}
			 if(item.getType() == Material.PAPER && data.getBoolean(uuid + ".chat")){
				data.set(uuid + ".chat", false);
				p.closeInventory();
				Main.saveDataFile();
				if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
					ChatUtils.sendMessageWithPrefix(p, "§7Chat on nyt §cpois päältä§7!");
				} else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
					ChatUtils.sendMessageWithPrefix(p, "§7Chat is now §coff§7!");
				}
			}
			else if(item.getType() == Material.PAPER && !data.getBoolean(uuid + ".chat")){
				data.set(uuid + ".chat", true);
				p.closeInventory();
				Main.saveDataFile();
				if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
					ChatUtils.sendMessageWithPrefix(p, "§7Chat on nyt §apäällä§7!");
				} else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
					ChatUtils.sendMessageWithPrefix(p, "§7Chat is now §aon§7!");
				}
			}
			else if(item.getType() == Material.MAP && data.getBoolean(uuid + ".privateMsg")){
				data.set(uuid + ".privateMsg", false);
				p.closeInventory();
				Main.saveDataFile();
				if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
					ChatUtils.sendMessageWithPrefix(p, "§7Yksityisviestit ovat nyt §cpois päältä§7!");
				} else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
					ChatUtils.sendMessageWithPrefix(p, "§7Private messages are now §coff§7!");
				}
			}
			else if(item.getType() == Material.MAP && !data.getBoolean(uuid + ".privateMsg")){
				data.set(uuid + ".privateMsg", true);
				p.closeInventory();
				Main.saveDataFile();
				if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
					ChatUtils.sendMessageWithPrefix(p, "§7Yksityisviestit ovat nyt §apäällä§7!");
				} else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
					ChatUtils.sendMessageWithPrefix(p, "§7Private messages are now §aon§7!");
				}
			}
			else if(item.getType() == Material.NETHER_STAR && data.getBoolean(uuid + ".privateAccount")){
				data.set(uuid + ".privateAccount", false);
				p.closeInventory();
				Main.saveDataFile();
				if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
					ChatUtils.sendMessageWithPrefix(p, "§7Yksityisyys on nyt §cpois päältä§7!");
				} else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
					ChatUtils.sendMessageWithPrefix(p, "§7Private account is now §coff§7!");
				}
			}
			else if(item.getType() == Material.NETHER_STAR && !data.getBoolean(uuid + ".privateAccount")){
				data.set(uuid + ".privateAccount", true);
				p.closeInventory();
				Main.saveDataFile();
				if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
					ChatUtils.sendMessageWithPrefix(p, "§7Yksityisyys on nyt §apäällä§7!");
				} else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
					ChatUtils.sendMessageWithPrefix(p, "§7Private account is now §aon§7!");
				}
			}
			
		}
		else if (inv.getName().equals("VIP-Asetukset") || inv.getName().equals("VIP-Settings")){
			
			e.setCancelled(true);
			
			if(item.getType() == Material.ARROW){
				openSettingsGUI(p);
			}
			
			else if(item.getType() == Material.MAP && data.getBoolean(uuid + ".privateMsg")){
				data.set(uuid + ".privateMsg", false);
				p.closeInventory();
				Main.saveDataFile();
				if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
					ChatUtils.sendMessageWithPrefix(p, "§7Yksityisviestit ovat nyt §cpois päältä§7!");
				} else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
					ChatUtils.sendMessageWithPrefix(p, "§7Private messages are now §coff§7!");
				}
			}
			else if(item.getType() == Material.MAP && !data.getBoolean(uuid + ".privateMsg")){
				data.set(uuid + ".privateMsg", true);
				p.closeInventory();
				Main.saveDataFile();
				if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
					ChatUtils.sendMessageWithPrefix(p, "§7Yksityisviestit ovat nyt §apäällä§7!");
				} else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
					ChatUtils.sendMessageWithPrefix(p, "§7Private messages are now §aon§7!");
				}
			}
			else if(item.getType() == Material.NETHER_STAR && data.getBoolean(uuid + ".privateAccount")){
				data.set(uuid + ".privateAccount", false);
				p.closeInventory();
				Main.saveDataFile();
				if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
					ChatUtils.sendMessageWithPrefix(p, "§7Yksityisyys on nyt §cpois päältä§7!");
				} else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
					ChatUtils.sendMessageWithPrefix(p, "§7Private account is now §coff§7!");
				}
			}
			else if(item.getType() == Material.NETHER_STAR && !data.getBoolean(uuid + ".privateAccount")){
				data.set(uuid + ".privateAccount", true);
				p.closeInventory();
				Main.saveDataFile();
				if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
					ChatUtils.sendMessageWithPrefix(p, "§7Yksityisyys on nyt §apäällä§7!");
				} else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
					ChatUtils.sendMessageWithPrefix(p, "§7Private account is now §aon§7!");
				}
			}
			
		}
		
	}
	
	public void openSettingsGUI(Player p){
		
		String uuid = p.getUniqueId().toString();
		KitAPI api = Main.getAPI();
		
		if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
			
			Inventory inv = Bukkit.createInventory(null, 36, "Asetukset");
			
			if (data.getBoolean(uuid + ".chatMention")) {
				api.createItem(inv, 10, Material.BOOK_AND_QUILL, 1, "§b§lChat-maininnat", Arrays.asList("§7Jos tämä on pois päältä, et", "§7saa ilmoitusta kun sinut mainitaan",
						"§7chatissa!", "", "§7Tila: §a§lKäytössä"));
			} else if (!data.getBoolean(uuid + ".chatMention")) {
				api.createItem(inv, 10, Material.BOOK_AND_QUILL, 1, "§b§lChat-maininnat", Arrays.asList("§7Jos tämä on pois päältä, et", "§7saa ilmoitusta kun sinut mainitaan",
						"§7chatissa!", "", "§7Tila: §c§lEi käytössä"));
			}
			 if (data.getBoolean(uuid + ".scoreboard")) {
				api.createItem(inv, 12, Material.PAPER, 1, "§b§lScoreboard", Arrays.asList("§7Jos tämä on pois päältä, et", "§7nää scoreboardia näytön sivulla!",
						"", "§7Tila: §a§lKäytössä"));
			} else if (!data.getBoolean(uuid + ".scoreboard")) {
				api.createItem(inv, 12, Material.PAPER, 1, "§b§lScoreboard", Arrays.asList("§7Jos tämä on pois päältä, et", "§7nää scoreboardia näytön sivulla!",
						"", "§7Tila: §c§lEi käytössä"));
			}
			
			api.createItem(inv, 34, Material.GOLD_INGOT, 1, "§6§lVIP-Asetukset", Arrays.asList("§7Klikkaa päästäksesi §6VIP-", "§6asetuksiin§7!"));
			api.createItem(inv, 35, Material.REDSTONE, 1, "§c§lYT-Asetukset", Arrays.asList("§7Klikkaa päästäkesi §cYT-", "§casetuksiin§7!"));
			
			p.openInventory(inv);
		}
		else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
			
			Inventory inv = Bukkit.createInventory(null, 36, "Settings");
			
			if (data.getBoolean(uuid + ".chatMention")) {
				api.createItem(inv, 10, Material.BOOK_AND_QUILL, 1, "§b§lChat mentions", Arrays.asList("§7If this is not in use, you", "§7wont get notified when you get",
						"§7mentioned in the chat.", "", "§7State: §a§lUsing"));
			} else if (!data.getBoolean(uuid + ".chatMention")) {
				api.createItem(inv, 10, Material.BOOK_AND_QUILL, 1, "§b§lChat mentions", Arrays.asList("§7If this is not in use, you", "§7wont get notified when you get",
						"§7mentioned in the chat.", "", "§7State: §c§lNot using"));
			}
			 if (data.getBoolean(uuid + ".scoreboard")) {
				api.createItem(inv, 12, Material.PAPER, 1, "§b§lScoreboard", Arrays.asList("§7If this is not in use, you", "§7wont see the scoreboard at the right", "§7side of your screen.",
						"", "§7State: §a§lUsing"));
			} else if (!data.getBoolean(uuid + ".scoreboard")) {
				api.createItem(inv, 12, Material.PAPER, 1, "§b§lScoreboard", Arrays.asList("§7If this is not in use, you", "§7wont see the scoreboard at the right", "§7side of your screen.",
						"", "§7State: §c§lNot using"));
			}
			
			api.createItem(inv, 34, Material.GOLD_INGOT, 1, "§6§lVIP-Settings", Arrays.asList("§7Click to go to the", "§6VIP-Settings§7!"));
			api.createItem(inv, 35, Material.REDSTONE, 1, "§c§lYT-Settings", Arrays.asList("§7Click to go to the ", "§cYT-Settings§7!"));

			p.openInventory(inv);
		}
		
	}

	public void openSettingsYTGUI(Player p){
		
		String uuid = p.getUniqueId().toString();
		KitAPI api = Main.getAPI();
		
		if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
			
			Inventory inv = Bukkit.createInventory(null, 36, "YT-Asetukset");
			
			if (data.getBoolean(uuid + ".chat")) {
				api.createItem(inv, 10, Material.PAPER, 1, "§b§lChat", Arrays.asList("§7Jos tämä on käytössä, ", "§7et saa tulevia viestejä chattiin.", "", "§7Tila: §a§lKäytössä"));
			} else if (!data.getBoolean(uuid + ".chat")) {
				api.createItem(inv, 10, Material.PAPER, 1, "§b§lChat", Arrays.asList("§7Jos tämä on käytössä, ", "§7et saa tulevia viestejä chattiin.",
						"", "§7Tila: §c§lEi käytössä"));
			}
			 if (data.getBoolean(uuid + ".privateMsg")) {
				api.createItem(inv, 12, Material.MAP, 1, "§b§lYksityisviestit", Arrays.asList("§7Jos tämä on päällä, et", "§7saa tulevia yksityisviestejä!",
						"", "§7Tila: §a§lKäytössä"));
			} else if (!data.getBoolean(uuid + ".privateMsg")) {
				api.createItem(inv, 12, Material.MAP, 1, "§b§lYksityisviestit", Arrays.asList("§7Jos tämä on päällä, et", "§7saa tulevia yksityisviestejä!",
						"", "§7Tila: §c§lEi käytössä"));
			}
			 if (data.getBoolean(uuid + ".privateAccount")) {
				api.createItem(inv, 14, Material.NETHER_STAR, 1, "§b§lYksityinen", Arrays.asList("§7Jos tämä on päällä käyttäjäsi on", "§7yksityinen, eikä kukaan", 
						"§7voi nähdä statsejasi tai profiiliasi", "", "§7Tila: §a§lKäytössä"));
			} else if (!data.getBoolean(uuid + ".privateAccount")) {
				api.createItem(inv, 14, Material.NETHER_STAR, 1, "§b§lYksityinen", Arrays.asList("§7Jos tämä on päällä käyttäjäsi on", "§7yksityinen, eikä kukaan", 
						"§7voi nähdä statsejasi tai profiiliasi", "", "§7Tila: §c§lEi käytössä"));
			}
			
			api.createItem(inv, 27, Material.ARROW, 1, "§aTavalliset asetukset", Arrays.asList("§7Klikkaa päästäksesi takaisin", "§7tavallisiin asetuksiin!"));
			
			p.openInventory(inv);
		}
		else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
			
			Inventory inv = Bukkit.createInventory(null, 36, "YT-Settings");
			
			if (data.getBoolean(uuid + ".chat")) {
				api.createItem(inv, 10, Material.PAPER, 1, "§b§lChat", Arrays.asList("§7If this is in use you wont ", "recieve chat messages.", "", "§7State: §a§lUsing"));
			} else if (!data.getBoolean(uuid + ".chat")) {
				api.createItem(inv, 10, Material.PAPER, 1, "§b§lChat", Arrays.asList("§7If this is in use", "§7you wont receive chat messages.", "", "§7State: §c§lNot using"));
			}
			 if (data.getBoolean(uuid + ".privateMsg")) {
				api.createItem(inv, 12, Material.MAP, 1, "§b§lPrivate messages", Arrays.asList("§7If this is in use, you", "§7wont receive private messages.",
						"", "§7State: §a§lUsing"));
			} else if (!data.getBoolean(uuid + ".privateMsg")) {
				api.createItem(inv, 12, Material.MAP, 1, "§b§lPrivate messages", Arrays.asList("§7If this is not in use, you", "§7wont receive private messages.",
						"", "§7State: §c§lNot using"));
			}
			 if (data.getBoolean(uuid + ".privateAccount")) {
				api.createItem(inv, 14, Material.NETHER_STAR, 1, "§b§lPrivate account", Arrays.asList("§7If this in use, your account will be", "§7private and nobody can see your stats or profile.",
						"", "§7State: §a§lUsing"));
			} else if (!data.getBoolean(uuid + ".privateAccount")) {
				api.createItem(inv, 14, Material.NETHER_STAR, 1, "§b§lPrivate account", Arrays.asList("§7If this in use, your account will be", "§7private and nobody can see your stats or profile.",
						"", "§7State: §c§lNot using"));
			}
			
			api.createItem(inv, 27, Material.ARROW, 1, "§aNormal settings", Arrays.asList("§7Click to go to the ", "§7normal settings!"));
			
			p.openInventory(inv);
		}
		
	}
	
	public void openSettingsVIPGUI(Player p){
		
		String uuid = p.getUniqueId().toString();
		KitAPI api = Main.getAPI();
		
		if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
			
			Inventory inv = Bukkit.createInventory(null, 36, "VIP-Asetukset");

			 if (data.getBoolean(uuid + ".privateMsg")) {
				api.createItem(inv, 10, Material.MAP, 1, "§b§lYksityisviestit", Arrays.asList("§7Jos tämä on päällä, et", "§7saa tulevia yksityisviestejä!",
						"", "§7Tila: §a§lKäytössä"));
			} else if (!data.getBoolean(uuid + ".privateMsg")) {
				api.createItem(inv, 10, Material.MAP, 1, "§b§lYksityisviestit", Arrays.asList("§7Jos tämä on päällä, et", "§7saa tulevia yksityisviestejä!",
						"", "§7Tila: §c§lEi käytössä"));
			}
			 if (data.getBoolean(uuid + ".privateAccount")) {
				api.createItem(inv, 12, Material.NETHER_STAR, 1, "§b§lYksityinen", Arrays.asList("§7Jos tämä on päällä käyttäjäsi on", "§7yksityinen, eikä kukaan", 
						"§7voi nähdä statsejasi tai profiiliasi", "", "§7Tila: §a§lKäytössä"));
			} else if (!data.getBoolean(uuid + ".privateAccount")) {
				api.createItem(inv, 12, Material.NETHER_STAR, 1, "§b§lYksityinen", Arrays.asList("§7Jos tämä on päällä käyttäjäsi on", "§7yksityinen, eikä kukaan", 
						"§7voi nähdä statsejasi tai profiiliasi.", "", "§7Tila: §c§lEi käytössä"));
			}
			
			api.createItem(inv, 27, Material.ARROW, 1, "§aTavalliset asetukset", Arrays.asList("§7Klikkaa päästäksesi takaisin", "§7tavallisiin asetuksiin!"));
			
			p.openInventory(inv);
		}
		else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
			
			Inventory inv = Bukkit.createInventory(null, 36, "VIP-Settings");
			
			if (data.getBoolean(uuid + ".privateMsg")) {
				api.createItem(inv, 10, Material.MAP, 1, "§b§lPrivate messages", Arrays.asList("§7If this is in use, you", "§7wont receive chat messages.",
						"", "§7State: §a§lUsing"));
			} else if (!data.getBoolean(uuid + ".privateMsg")) {
				api.createItem(inv, 10, Material.MAP, 1, "§b§lBroadcasts", Arrays.asList("§7If this is not in use, you", "§7wont receive chat messages.",
						"", "§7State: §c§lNot using"));
			}
			 if (data.getBoolean(uuid + ".privateAccount")) {
				api.createItem(inv, 12, Material.NETHER_STAR, 1, "§b§lPrivate account", Arrays.asList("§7If this in use, your account will be", "§7private and nobody can see your stats or profile.",
						"", "§7State: §a§lUsing"));
			} else if (!data.getBoolean(uuid + ".privateAccount")) {
				api.createItem(inv, 12, Material.NETHER_STAR, 1, "§b§lPrivate account", Arrays.asList("§7If this in use, your account will be", "§7private and nobody can see your stats or profile.",
						"", "§7State: §c§lNot using"));
			}
			
			
			api.createItem(inv, 27, Material.ARROW, 1, "§aNormal settings", Arrays.asList("§7Click to go to the ", "§7normal settings!"));
			
			p.openInventory(inv);
		}
		
	}
	
}
