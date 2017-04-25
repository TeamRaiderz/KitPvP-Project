package kitpvp.cosmetics;

import java.util.Arrays;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import kitpvp.Main;

public class Crate {
	
	private Player opener;
	private Inventory openingInv;
	private Inventory prizeInv;
	
	public Crate(Player opener){
		this.opener = opener;
	}
	
	public void activateCrate(){
		
		Inventory inv = Bukkit.createInventory(null, InventoryType.CHEST, "Rolling...");
		opener.openInventory(inv);
		startInventory(inv, opener);
	}
	
	public void startInventory(final Inventory inv, final Player p){
		startFrame((short) 5, 0L, ChatColor.RED, inv, opener);
		startFrame((short) 6, 10L, ChatColor.GREEN, inv, opener);
		startFrame((short) 7, 15L, ChatColor.AQUA, inv, opener);
		startFrame((short) 1, 20L, ChatColor.BLUE, inv, opener);
		startFrame((short) 2, 25L, ChatColor.DARK_GREEN, inv, opener);
		startFrame((short) 3, 30L, ChatColor.LIGHT_PURPLE, inv, opener);
		startFrame((short) 4, 35L, ChatColor.DARK_PURPLE, inv, opener);
		startFrame((short) 9, 40L, ChatColor.GOLD, inv, opener);
		startFrame((short) 10, 45L, ChatColor.YELLOW, inv, opener);
		selectPrize(opener, inv);
	}
	
	public void startFrame(final short sh, final long delay, final ChatColor chatColor, final Inventory inv, final Player p){
		final Sound sound = Sound.ORB_PICKUP;
		new BukkitRunnable(){
			public void run(){
				
				ItemStack rolling = new ItemStack(Material.STAINED_GLASS_PANE, 1, sh);
				ItemMeta rollingMeta = rolling.getItemMeta();
				rollingMeta.setDisplayName(" ");
				rolling.setItemMeta(rollingMeta);
				
				for (int x = 0;x<inv.getSize(); x++){
					inv.setItem(x, new ItemStack(rolling));
					
				}
				
				ItemStack is = new ItemStack(Material.PAPER);
				ItemMeta im = is.getItemMeta();
				im.setDisplayName(chatColor + "?");
				is.setItemMeta(im);
				inv.setItem(13, is);
				opener.playSound(opener.getLocation(), sound, 1, 1);
				cancel();
			}
		}.runTaskLater(Main.getInstance(), delay);		
	}
	
	public void selectPrize(final Player p, final Inventory inv){
		
		Random rn = new Random();
	    int chance = rn.nextInt(100);
	    int roundedChance = (int) (Math.round(chance * 10.0D) / 10.0D);
	    
		new BukkitRunnable(){
			public void run() {
				if(chance <= 1){
					opener.sendMessage("§eVoitit §6Helvetin Iso Läjä Karkkia " + roundedChance + "% §emahdollisuuksilla!");
					Main.getAPI().createItem(inv, 13, Material.NAME_TAG, 1, "§6§lPASKAA", Arrays.asList(""));
					opener.closeInventory();
					System.out.println("VIP 6");
				} else if (chance <= 5) {
					opener.sendMessage("§eVoitit §6Todella Iso Läjä Karkkia " + roundedChance + "% §emahdollisuuksilla!");
					Main.getAPI().createItem(inv, 13, Material.NAME_TAG, 1, "§6§lPASKAA", Arrays.asList(""));
					opener.closeInventory();
					System.out.println("VIP 4");
				} else if (chance <= 15) {
					opener.sendMessage("§eVoitit §6Hyvin Iso Läjä Karkkia " + roundedChance + "% §emahdollisuuksilla!");
					Main.getAPI().createItem(inv, 13, Material.NAME_TAG, 1, "§6§lPASKAA", Arrays.asList(""));
					opener.closeInventory();
					System.out.println("VIP 3");
				} else if (chance <= 25) {
					opener.sendMessage("§eVoitit §6Iso Läjä Karkkia " + roundedChance + "% §emahdollisuuksilla!");
					Main.getAPI().createItem(inv, 13, Material.NAME_TAG, 1, "§6§lPASKAA", Arrays.asList(""));
					opener.closeInventory();
					System.out.println("VIP 7");
				} else if (chance <= 35) {
					opener.sendMessage("§eVoitit §6Keskikokoista Hieman Isompi Läjä Karkkia " + roundedChance + "% §emahdollisuuksilla!");
					Main.getAPI().createItem(inv, 13, Material.NAME_TAG, 1, "§6§lPASKAA", Arrays.asList(""));
					opener.closeInventory();
					System.out.println("VIP 8");
				} else if (chance <= 45) {
					opener.sendMessage("§eVoitit §6Keskikokoisen Läjän Karkkia " + roundedChance + "% §emahdollisuuksilla!");
					Main.getAPI().createItem(inv, 13, Material.NAME_TAG, 1, "§6§lPASKAA", Arrays.asList(""));
					opener.closeInventory();
					System.out.println("VIP 2");
				} else if (chance <= 55) {
					opener.sendMessage("§eVoitit §6Pienen Läjän Karkkia " + roundedChance + "% §emahdollisuuksilla!");
					Main.getAPI().createItem(inv, 13, Material.NAME_TAG, 1, "§6§lPASKAA", Arrays.asList(""));
					opener.closeInventory();
					System.out.println("VIP 1");
				} else {
					opener.sendMessage("§eVoitit §6Hyvin Pienen Läjän Karkkia " + roundedChance + "% §emahdollisuuksilla!");
					Main.getAPI().createItem(inv, 13, Material.NAME_TAG, 1, "§6§lPASKAA", Arrays.asList(""));
					System.out.println("VIP 0");
					opener.closeInventory();
				}
							
				Firework firework = (Firework) opener.getWorld().spawnEntity(opener.getLocation(), EntityType.FIREWORK);
				FireworkMeta fireworkMeta = firework.getFireworkMeta();
				fireworkMeta.setPower(10);
				fireworkMeta.addEffects(FireworkEffect.builder().trail(true).flicker(true).with(FireworkEffect.Type.BALL_LARGE).withColor(Color.NAVY).withColor(Color.FUCHSIA).build());
				firework.setFireworkMeta(fireworkMeta);				
				firework.detonate();
			}
		}.runTaskLater(Main.getInstance(), 55L);
	}
}
