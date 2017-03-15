package kitpvp.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import kitpvp.Main;
public class AbilityListener implements Listener {
	
	 public static List<String> FlyCooldown = new ArrayList();
	
	@EventHandler
	public void onAbility(PlayerInteractEvent e){
	
		Player p = e.getPlayer();
		
		ItemStack item = e.getItem();
		Material type = item.getType();
		
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR){
			
			if (type == Material.FEATHER && item.hasItemMeta()) {
				if (!FlyCooldown.contains(p.getName())) {
					e.setCancelled(true);
					FlyCooldown.add(p.getName());

					Vector v2 = p.getLocation().getDirection().multiply(3.0D).setY(1.5D);
					p.getWorld().playEffect(p.getLocation().add(0.0D, 0.0D, 0.0D), Effect.FIREWORKS_SPARK, 1);
					p.getWorld().playEffect(p.getLocation().add(0.0D, 0.5D, 0.0D), Effect.FIREWORKS_SPARK, 1);
					p.playSound(p.getLocation(), Sound.ENDERDRAGON_WINGS, 500.0F, 500.0F);
					p.getWorld().playEffect(p.getLocation().add(0.0D, 1.5D, 0.0D), Effect.FIREWORKS_SPARK, 1);
					p.setVelocity(v2);

					new BukkitRunnable() {
						public void run() {
							FlyCooldown.remove(p.getName());
						}
					}.runTaskLater(Main.getInstance(), 60L);
				}
			}
			if(e.getAction() == Action.RIGHT_CLICK_BLOCK && type == Material.SPIDER_EYE && item.hasItemMeta()){
				e.setCancelled(true);
				Vector v2 = p.getLocation().getDirection().multiply(0.0D).setY(1.0D);
				Location loc = p.getLocation();
				p.playSound(loc, Sound.SPIDER_IDLE, 1.0F, 0.2F);
				p.setVelocity(v2);
			}
			
		}
		
	}
	
	 @EventHandler
	  public void KalastajaAbility(PlayerFishEvent e) {
	    Player p = e.getPlayer();
	    if (!(e.getCaught() instanceof Player)) return;
	    Player caught = (Player)e.getCaught();

	    caught.teleport(p.getLocation());
	    p.getWorld().playEffect(p.getLocation().add(0.0D, 1.5D, 0.0D), Effect.ENDER_SIGNAL, 1);
	    p.getWorld().playSound(p.getLocation(), Sound.ENDERDRAGON_HIT, 1.0F, 0.2F);
	  }
	
}
