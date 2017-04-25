package kitpvp.other;

import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import kitpvp.Main;
import kitpvp.Util.KitAPI;
import kitpvp.Util.Language;
import kitpvp.cosmetics.CosmeticBox;
import kitpvp.kits.KitManager;

public class SpawnItems implements Listener {

	public static HashMap<String, Boolean> playersHidden = new HashMap<String, Boolean>();	
	private static ItemStack pHidden, pShown;
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		giveItems(e.getPlayer(), e.getPlayer().getInventory());
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e){
		giveItems(e.getPlayer(), e.getPlayer().getInventory());
	}
	
	public static void giveItems(Player p, Inventory inv){
		
		KitAPI api = Main.getAPI();
		
		if(api.getLanguage(p.getName()) == Language.FINNISH){
			api.createItem(inv, 0, Material.BOOK, 1, "§bInfo", Arrays.asList("§7Täältä löydät tärkeää", "§7infoa serveristämme!"));
			
			api.createItem(inv, 3, Material.CHEST, 1, "§bPakkaukset", Arrays.asList("§7Klikkaa saadaksesi listan", "§7pakkauksista!"));
			
			api.createItem(inv, 5, Material.ENDER_CHEST, 1, "§bKosmetiikka", Arrays.asList("§7Löydä uusia partikkeleita,", "§7nuolivanoja, nimen värejä", "§7ja paljon muuta täältä!"));
			
			api.createSkullItem(inv, 8, p.getName(), "§bProfiili", Arrays.asList("§7Klikkaa päästäksesi", "§7sinun profiiliisi!"));
			
		}
		else if (api.getLanguage(p.getName()) == Language.ENGLISH){
			api.createItem(inv, 0, Material.BOOK, 1, "§bInfo", Arrays.asList("§7Here you can find a lot", "§7of important info about", "§7this server!"));

			api.createItem(inv, 3, Material.CHEST, 1, "§bKits", Arrays.asList("§7Click to see the list of", "§7available kits!"));
			
			api.createItem(inv, 5, Material.ENDER_CHEST, 1, "§bCosmetics", Arrays.asList("§7Find new particles,", "§7arrow trails and colored nicknames", "§7and much more from here!"));
			
			api.createSkullItem(inv, 8, p.getName(), "§bProfile", Arrays.asList("§7Click to get to", "§7your profile!"));
		}
		
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		
		Player p = e.getPlayer();
		
		if(e.getItem() == null || e.getItem().getType() == Material.AIR) return;
		
		ItemStack item = e.getItem();
		ItemMeta meta = e.getItem().getItemMeta();
		Inventory inv = p.getInventory();
		
		if(item.getType() == Material.INK_SACK && item.hasItemMeta() && playersHidden.get(p.getName())){
			e.setCancelled(true);
			if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
				pShown = new ItemStack(Material.INK_SACK, 1, (byte) 10);
				ItemMeta shownMeta = pShown.getItemMeta();
				shownMeta.setDisplayName("§7Pelaajat: §aNäkyvissä");
				shownMeta.setLore(Arrays.asList("§7Vaihda pelaajien näkyvyyttä spawnilla!"));
				pShown.setItemMeta(shownMeta);
				inv.setItem(5, pShown);
				playersHidden.put(p.getName(), true);
				for(Player online : Bukkit.getOnlinePlayers()){
					p.showPlayer(online);
				}
			}
			else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
				pShown = new ItemStack(Material.INK_SACK, 1, (byte) 10);
				ItemMeta shownMeta = pShown.getItemMeta();
				shownMeta.setDisplayName("§7Players: §aShown");
				shownMeta.setLore(Arrays.asList("§7Change players visibility at the spawn!"));
				pShown.setItemMeta(shownMeta);
				inv.setItem(5, pShown);
				playersHidden.put(p.getName(), false);
				for(Player online : Bukkit.getOnlinePlayers()){
					p.showPlayer(online);
				}
			}
		}
		else if(item.getType() == Material.INK_SACK && item.hasItemMeta() && !playersHidden.get(p.getName())){
			e.setCancelled(true);
			if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
				pHidden = new ItemStack(Material.INK_SACK, 1, (byte) 8);
				ItemMeta hiddenMeta = pHidden.getItemMeta();
				hiddenMeta.setDisplayName("§7Pelaajat: §cPiilotettu");
				hiddenMeta.setLore(Arrays.asList("§7Vaihda pelaajien näkyvyyttä spawnilla!"));
				pHidden.setItemMeta(hiddenMeta);
				
				inv.setItem(5, pHidden);
				playersHidden.put(p.getName(), true);
				for(Player online : Bukkit.getOnlinePlayers()){
					p.hidePlayer(online);
				}
			}
			else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
				pHidden = new ItemStack(Material.INK_SACK, 1, (byte) 8);
				ItemMeta hiddenMeta = pHidden.getItemMeta();
				hiddenMeta.setDisplayName("§7Players: §cHidden");
				hiddenMeta.setLore(Arrays.asList("§7Change players visibility at the spawn!"));
				pHidden.setItemMeta(hiddenMeta);
				
				inv.setItem(5, pHidden);
				playersHidden.put(p.getName(), true);
				
				for(Player online : Bukkit.getOnlinePlayers()){
					p.hidePlayer(online);
				}
			}
		}
		else if(item.getType() == Material.BOOK && item.hasItemMeta() && item.hasItemMeta()){
			e.setCancelled(true);
			Bukkit.dispatchCommand(p, "?");
		}
		else if(item.getType() == Material.ENDER_CHEST && item.hasItemMeta()){
			e.setCancelled(true);
			new CosmeticBox().open(p);
		}
		else if(item.getType() == Material.CHEST && item.hasItemMeta()){
			e.setCancelled(true);
			KitManager km = new KitManager();
			km.openKitMenu(p);
		}
		else if(item.getType() == Material.SKULL_ITEM && item.hasItemMeta()){
			Profile profile = new Profile();
			profile.openProfileMenu(p);
		}
	}

	public static boolean isPlayersHidden(Player p) {
		return playersHidden.get(p.getName());
	}
	
	public static void setPlayersHidden(Player p, boolean value){
		SpawnItems.playersHidden.put(p.getName(), value);
	}
	
}
