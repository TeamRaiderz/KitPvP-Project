package kitpvp.cosmetics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
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
import kitpvp.Util.KitAPI;

public class Crate {
	
	private Player opener;
	private Inventory openingInv;
	private Inventory prizeInv;
	public static HashMap<String, Crate> crates = new HashMap<>();
	
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
	
	public ArrayList<Prize> getPrizes(){
		
		ArrayList<Prize> prizes = new ArrayList<>();
		prizes.add(new Prize("Jackpot", KitAPI.makeItem(Material.GOLD_INGOT, 1, 0, "§6§lJackpot"), 1));
		prizes.add(new Prize("Particle", KitAPI.makeItem(Material.REDSTONE, 1, 0, "§d§lParticle effect"), 25));
		prizes.add(new Prize("Archer", KitAPI.makeItem(Material.BOW, 1, 0, "§9§lArcher kit"), 74));
		
		return prizes;
	}
	
	public Prize pickPrize(Player player){
		
		ArrayList prizes = new ArrayList();
		
		for(int stop = 0; (prizes.size() == 0) && (stop <= 2000); stop++){
			
			int counter = 0;
			
			for(Iterator localIterator = getPrizes().iterator(); localIterator.hasNext();){
				Prize prize = (Prize)localIterator.next();
				int chance = prize.getChance();
				
				counter = 1;
		        int num = 1 + new Random().nextInt(100);
		        if ((num >= 1) && (num <= chance))
		          prizes.add(prize);
		        counter++;
			}
			
		}
		
		Prize prize = (Prize)prizes.get(new Random().nextInt(prizes.size()));
		return prize;
	}
	
	public static void getReward(Player p, Prize prize){
		if(prize.getName().equals("Jackpot")){
			p.sendMessage("§cVoitit parhaan palkinnon! " + prize.getChance() + "%");
		}
		else if(prize.getName().equals("Particle")){
			p.sendMessage("§cVoitit toiseksi palkinnon! " + prize.getChance() + "%");
		}
		else if(prize.getName().equals("Archer")){
			p.sendMessage("§cVoitit huonoimman palkinnon! " + prize.getChance() + "%");
		}
	}
	
}
