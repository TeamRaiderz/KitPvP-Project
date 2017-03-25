package kitpvp.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import kitpvp.Language;
import kitpvp.Main;
import kitpvp.Util.ChatUtils;
public class AbilityListener implements Listener {
	
	 public static List<String> FlyCooldown = new ArrayList();
	 public static List<String> arrowShot = new ArrayList<String>();
	 public static List<Projectile> customArrow = new ArrayList<Projectile>();
	
	@EventHandler
	public void onAbility(PlayerInteractEvent e){
	
		Player p = e.getPlayer();
		
		if(e.getItem() == null) return;
		
		ItemStack item = e.getItem();
		Material type = item.getType();
		
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR){
			
			if (type == Material.FEATHER && item.hasItemMeta()) {
				if (!FlyCooldown.contains(p.getName())) {
					e.setCancelled(true);
					FlyCooldown.add(p.getName());

					Vector v2 = p.getLocation().getDirection().multiply(2.5D).setY(1.0D);
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
			if(type == Material.BOW && item.hasItemMeta() && arrowShot.contains(p.getName())){
				e.setCancelled(true); 
				if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
					 ChatUtils.sendMessageWithPrefix(p, "§7Et voi ampua juuri nyt!");
				 }
				 else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
					 ChatUtils.sendMessageWithPrefix(p, "§7You cannot shoot an arrow right now!");
				 }
			}
			
		}
		else if(e.getAction() == Action.LEFT_CLICK_AIR && type == Material.BOW && item.hasItemMeta() 
				|| e.getAction() == Action.LEFT_CLICK_BLOCK && type == Material.BOW && item.hasItemMeta()){
			e.setCancelled(true);
			shootCustomArrow(p);
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
	 
	public void shootCustomArrow(Player shooter) {

		if (shooter.getItemInHand().getType() == Material.BOW && shooter.getItemInHand().hasItemMeta()
				&& shooter.getItemInHand().getItemMeta().getDisplayName().equals("§7Archer")) {

			if (!arrowShot.contains(shooter.getName())) {
				arrowShot.add(shooter.getName());
				shooter.launchProjectile(Arrow.class);
			}
		}

	}
	 
	 @EventHandler
	 public void onArrowLand(ProjectileHitEvent e){
		 
		if (e.getEntity() instanceof Arrow && e.getEntityType() == EntityType.ARROW) {
			if (e.getEntity().getShooter() != null && e.getEntity().getShooter() instanceof Player) {
				Player shooter = (Player) e.getEntity().getShooter();

				if (arrowShot.contains(shooter.getName())) {
					arrowShot.remove(shooter.getName());

					Location arrowLoc = e.getEntity().getLocation();
					Location shooterLoc = shooter.getLocation();

					Vector velocity = arrowLoc.toVector().subtract(shooterLoc.toVector()).normalize().multiply(2.0).setY(1);
					shooter.setVelocity(shooter.getVelocity().add(velocity));

				}

			}

		}

	 }
	 
	
}
