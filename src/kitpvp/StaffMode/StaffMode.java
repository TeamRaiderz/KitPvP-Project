package kitpvp.StaffMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import kitpvp.Util.ChatUtils;
import kitpvp.Util.KitAPI;

public class StaffMode {

	private static HashMap<String, ItemStack[]> invItems = new HashMap<String, ItemStack[]>();
	private static HashMap<String, ItemStack[]> invArmor = new HashMap<String, ItemStack[]>();
	public static HashMap<String, Boolean> vanished = new HashMap<String, Boolean>();
	public static HashMap<String, Boolean> spectating = new HashMap<String, Boolean>();
	public static List<String> staffMode = new ArrayList<String>();
	private KitAPI api = new KitAPI();
	
	public void joinStaffMode(Player p) {
		
		vanished.put(p.getName(), true);
		spectating.put(p.getName(), false);
		
		p.setFlying(true);
		p.setAllowFlight(true);
		
		invItems.put(p.getName(), p.getInventory().getContents());
		invArmor.put(p.getName(), p.getInventory().getArmorContents());
		
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		
		ChatUtils.sendMessageWithPrefix(p, "§7Staffmode §aenabled§7!");
	}
	
	public void leaveStaffMode(Player p) {
		
		vanished.remove(p.getName());
		spectating.remove(p.getName());
		
		p.setGameMode(GameMode.ADVENTURE);
		
		if(invItems.containsKey(p.getName())){
			p.getInventory().setContents(invItems.get(p.getName()));
		}
		if(invArmor.containsKey(p.getName())){
			p.getInventory().setArmorContents(invArmor.get(p.getName()));
		}
		
		ChatUtils.sendMessageWithPrefix(p, "§7Staffmode §cdisabled§7!");
	}
	
	public void giveStaffModeItems(Player p){
		
		Inventory inv = p.getInventory();
		
		api.createItem(inv, 0, Material.BLAZE_POWDER, 1, "§bBoost", Arrays.asList("§7Click for a little boost!"));
		api.createItem(inv, 1, Material.LEASH, 1, "§bSpectate", Arrays.asList("§7Click a player to", "§7spectate the player!"));
		api.createItem(inv, 2, Material.ANVIL, 1, "§bPunishments", Arrays.asList("§7Click a player to", "§7punish the player!"));
		api.createItem(inv, 3, Material.ICE, 1, "§bFreeze", Arrays.asList("§7Click a player to", "§7freeze the player!"));
		
		api.createItem(inv, 5, Material.SIGN, 1, "§bChat Options", Arrays.asList("§7Click to see the", "§7chat options!"));
		api.createItem(inv, 6, Material.BLAZE_ROD, 1, "§bRandom Teleport", Arrays.asList("§7Click to teleport to", "§7a random player!"));
		api.createItem(inv, 7, Material.BEACON, 1, "§bToggle Visibility", Arrays.asList("§7Click to toggle visibility!"));
		api.createItem(inv, 8, Material.PAPER, 1, "§bPlayer Options", Arrays.asList("§7Click a player to", "§7open the player options", "§7for the player!"));
		
	}
	
	public static FreezeManager getFreezeManager(){
		return new FreezeManager();
	}
	
}
