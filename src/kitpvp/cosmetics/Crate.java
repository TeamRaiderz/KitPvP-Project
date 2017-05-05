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
	public static HashMap<String, Crate> crates = new HashMap<>();
	
	public ArrayList<Prize> getPrizes(){
		
		ArrayList<Prize> prizes = new ArrayList<>();
		
		// JACKPOT 1% - 3% and 5%, RARE 6% - 15%, UNCOMMON 16% - 60%, COMMON 60% - 100%, PRICELESS 4%
		
		prizes.add(new Prize("Jackpot", 1, Rarity.LEGENDRAY));
		prizes.add(new Prize("Fly cmd", 1, Rarity.LEGENDRAY));
		
		prizes.add(new Prize("Particle effect", 10, Rarity.RARE));
		
		prizes.add(new Prize("Arrow trail", 20, Rarity.UNCOMMON));
		prizes.add(new Prize("Token", 25, Rarity.UNCOMMON));
		
		prizes.add(new Prize("Money", 70, Rarity.COMMON));
		prizes.add(new Prize("Arrow trail", 60, Rarity.COMMON));
		
		prizes.add(new Prize("Nothing", 4, Rarity.PRICELESS));
		
		return prizes;
	}
	
	public Prize pickPrize(Player player){
		
		ArrayList prizes = new ArrayList();
		
		for(int stop = 0; (prizes.size() == 0) && (stop <= 2000); stop++){
			
			int counter = 0;
			
			for(Iterator localIterator = getPrizes().iterator(); localIterator.hasNext();){
				Prize prize = (Prize)localIterator.next();
				double chance = prize.getChance();
				
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
		if(prize.getRarity() == Rarity.LEGENDRAY){
			if(prize.getName().equals("Jackpot")){
				p.sendMessage("§f§lVOITIT §6§lJACKPOTIN§f§l!!! §610,000 €§f§l!!");
				Main.getAPI().addBalance(p.getName(), 10000);
			}
			else if(prize.getName().equals("Fly cmd")){
				p.sendMessage("§f§lVOITIT §6§l/fly §f§lKOMENNON!!");
			}
			else{
				p.sendMessage("§cVoitit parhaan palkinnon! Arvokkuus: " + prize.getRarity().toString() + " ("  + prize.getChance() + "%)");
			}
		}
		else if(prize.getRarity() == Rarity.RARE){
			if(prize.getName().equals("Particle effect")){
				p.sendMessage("§f§lVoitit §cpartikkeli efektin§f§l!");
			}
			else{
				p.sendMessage("§cVoitit toiseksi palkinnon! Arvokkuus: " + prize.getRarity().toString() + " ("  + prize.getChance() + "%)");
			}
		}
		else if(prize.getRarity() == Rarity.UNCOMMON){
			if(prize.getName().equals("Arrow trail")){
				p.sendMessage("§f§lVoitit §5nuolivanan§f§l!");
			}
			else if(prize.getName().equals("Token")){
				p.sendMessage("§f§lVoitit §5tokenin§f§l!");
				Main.getCosmeticManager().addTokens(p.getName(), 1);
			}
			else{
				p.sendMessage("§cVoitit toiseksi huonoimman palkinnon! Arvokkuus: " + prize.getRarity().toString() + " ("  + prize.getChance() + "%)");
			}
		}
		else if(prize.getRarity() == Rarity.COMMON){
			if(prize.getName().equals("Money")){
				p.sendMessage("§f§lVoitit §9rahaa§f§l! §950€§f§l!");
				Main.getAPI().addBalance(p.getName(), 50);
			}
			else if(prize.getName().equals("Arrow trail")){
				p.sendMessage("§f§lVoitit §9nuolivanan§f§l!");
			}
			else{
				p.sendMessage("§cVoitit huonoimman palkinnon! Arvokkuus: " + prize.getRarity().toString() + " ("  + prize.getChance() + "%)");
			}
		}
		else if(prize.getRarity() == Rarity.PRICELESS){
			p.sendMessage("§cEt voittanut tällä kertaa mitään! " + prize.getChance() + "%");
		}
	}
	
}
