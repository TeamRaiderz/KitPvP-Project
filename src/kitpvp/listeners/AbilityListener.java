package kitpvp.listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
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
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
public class AbilityListener implements Listener {
	
	 public static HashMap<String, Double> flyCooldown = new HashMap<String, Double>();
	 public static HashMap<String, Double> archerCooldown = new HashMap<String, Double>();
	 public static HashMap<String, Double> bombArcherCooldown = new HashMap<String, Double>();
	 public static List<String> arrowShot = new ArrayList<String>();
	 public static List<Projectile> customArrow = new ArrayList<Projectile>();
	 public static HashMap<String, Location> bombArrowLoc = new HashMap<String, Location>();
	 public static List<String> bombArrowShot = new ArrayList<String>();
	
	@EventHandler
	public void onAbility(PlayerInteractEvent e){
	
		Player p = e.getPlayer();
		
		if(e.getItem() == null) return;
		
		ItemStack item = e.getItem();
		Material type = item.getType();
		
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR){
			
			//Airman ability
			if (type == Material.FEATHER && item.hasItemMeta()) {
				if (!flyCooldown.containsKey(p.getName())) {
					e.setCancelled(true);
					flyCooldown.put(p.getName(), 5.0D);

					Vector v2 = p.getLocation().getDirection().multiply(2.5D).setY(1.0D);
					p.getWorld().playEffect(p.getLocation().add(0.0D, 0.0D, 0.0D), Effect.FIREWORKS_SPARK, 1);
					p.getWorld().playEffect(p.getLocation().add(0.0D, 0.5D, 0.0D), Effect.FIREWORKS_SPARK, 1);
					p.playSound(p.getLocation(), Sound.ENDERDRAGON_WINGS, 500.0F, 500.0F);
					p.getWorld().playEffect(p.getLocation().add(0.0D, 1.5D, 0.0D), Effect.FIREWORKS_SPARK, 1);
					p.setVelocity(v2);

					//Cooldown
					new BukkitRunnable() {
						public void run() {
							
							if(flyCooldown.containsKey(p.getName())){
								
								Main.getPacketUtils().sendActionBar(p, "§a§lAbility §a- §c" + (Math.round(flyCooldown.get(p.getName()) * 10.0D) / 10.0D));
								
								flyCooldown.put(p.getName(), flyCooldown.get(p.getName()) - 0.1D);
								
								if(flyCooldown.get(p.getName()) <= 0.0D){
									flyCooldown.remove(p.getName());
									
									if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
										ChatUtils.sendMessageWithPrefix(p, "§7Voit nyt käyttää abilityäsi!");
									}
									else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
										ChatUtils.sendMessageWithPrefix(p, "§7You can now use your ability!");
									}
									cancel();
								}
								
							}
							
						}
					}.runTaskTimerAsynchronously(Main.getInstance(), 0, 2);
				}
			}
			//Spider ability
			if(e.getAction() == Action.RIGHT_CLICK_BLOCK && type == Material.SPIDER_EYE && item.hasItemMeta()){
				e.setCancelled(true);
				Vector v2 = p.getLocation().getDirection().multiply(0.0D).setY(1.0D);
				Location loc = p.getLocation();
				p.playSound(loc, Sound.SPIDER_IDLE, 1.0F, 0.2F);
				p.setVelocity(v2);
			}
			
		}
		//Archer ability
		else if(e.getAction() == Action.LEFT_CLICK_AIR 
				|| e.getAction() == Action.LEFT_CLICK_BLOCK){
			e.setCancelled(true);
			
			if(type == Material.BOW && item.hasItemMeta() && item.getItemMeta().getDisplayName().equals("§7Archer")){
				if(!(archerCooldown.containsKey(p.getName()))){
					shootCustomArrow(p);
					archerCooldown.put(p.getName(), 3.0D);
					
					new BukkitRunnable() {
						public void run() {
							
							if(archerCooldown.containsKey(p.getName())){
								
								Main.getPacketUtils().sendActionBar(p, "§a§lAbility §a- §c" + (Math.round(archerCooldown.get(p.getName()) * 10.0D) / 10.0D));
								
								archerCooldown.put(p.getName(), archerCooldown.get(p.getName()) - 0.1D);
								
								if(archerCooldown.get(p.getName()) <= 0.0D){
									archerCooldown.remove(p.getName());
									
									if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
										ChatUtils.sendMessageWithPrefix(p, "§7Voit nyt käyttää abilityäsi!");
									}
									else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
										ChatUtils.sendMessageWithPrefix(p, "§7You can now use your ability!");
									}
									cancel();
								}
								
							}
							
						}
					}.runTaskTimerAsynchronously(Main.getInstance(), 0, 2);
				}
			}
			else if (type == Material.BOW && item.hasItemMeta()
					&& item.getItemMeta().getDisplayName().equals("§7Bomb Archer")) {
				if (!(bombArcherCooldown.containsKey(p.getName()))) {

					if (bombArrowLoc.containsKey(p.getName())) {
						p.teleport(bombArrowLoc.get(p.getName()));
						p.playSound(p.getLocation(), Sound.NOTE_PIANO, 1f, 0.2f);
						bombArrowLoc.remove(p.getName());
					}
				}
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
	 
	public void shootCustomArrow(Player shooter) {

		if (shooter.getItemInHand().getType() == Material.BOW && shooter.getItemInHand().hasItemMeta()) {

			if (shooter.getItemInHand().getItemMeta().getDisplayName().equals("§7Archer")) {
				if (!arrowShot.contains(shooter.getName())) {
					arrowShot.add(shooter.getName());
					Arrow arrow = shooter.launchProjectile(Arrow.class);
					customArrow.add(arrow);
				}
			}
		}

	}
	 
	 @EventHandler
	 public void onArrowLand(ProjectileHitEvent e){
		 
		if (e.getEntity() instanceof Arrow && e.getEntityType() == EntityType.ARROW) {
			if (e.getEntity().getShooter() != null && e.getEntity().getShooter() instanceof Player) {
				Player shooter = (Player) e.getEntity().getShooter();

				if (arrowShot.contains(shooter.getName()) && customArrow.contains(e.getEntity())) {
					arrowShot.remove(shooter.getName());
					customArrow.remove(e.getEntity());
					
					Location arrowLoc = e.getEntity().getLocation();
					Location shooterLoc = shooter.getLocation();

					Vector velocity = arrowLoc.toVector().subtract(shooterLoc.toVector()).normalize().multiply(2.0).setY(1);
					shooter.setVelocity(shooter.getVelocity().add(velocity));

					e.getEntity().remove();
					
				}
				else if (bombArrowShot.contains(shooter.getName())){
					bombArrowShot.remove(shooter.getName());
					
					bombArrowLoc.put(shooter.getName(), e.getEntity().getLocation());
					
					PacketPlayOutWorldParticles packetParticles = new PacketPlayOutWorldParticles(EnumParticle.LAVA, true, 
							(float) (e.getEntity().getLocation().getX()), (float) (e.getEntity().getLocation().getY()), 
							(float) (e.getEntity().getLocation().getZ()), 0, 0, 0, 20, 40);
					
					for(Player pl : Bukkit.getOnlinePlayers()){
						((CraftPlayer) pl).getHandle().playerConnection.sendPacket(packetParticles);
					}
					
					e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.EXPLODE, 1, 0.2f);
					for(Entity p : e.getEntity().getNearbyEntities(5, 5, 5)){
						if(p instanceof Player){
							if(p == shooter) continue;
							p.setVelocity(new Vector(0, 1, 0));
						}
					}
					e.getEntity().remove();
					
				}
				
				e.getEntity().remove();

			}

		}

	 }
	 
	 @EventHandler
	 public void onArrowShoot(ProjectileLaunchEvent e){
		 if (e.getEntity() instanceof Arrow && e.getEntityType() == EntityType.ARROW) {
			if (e.getEntity().getShooter() != null && e.getEntity().getShooter() instanceof Player) {
				Player shooter = (Player) e.getEntity().getShooter();

				if (!bombArrowShot.contains(shooter.getName())) {
					bombArrowShot.add(shooter.getName());

					bombArcherCooldown.put(shooter.getName(), 8.0D);
					
					new BukkitRunnable() {
						public void run() {

							if (bombArcherCooldown.containsKey(shooter.getName())) {

								Main.getPacketUtils().sendActionBar(shooter, "§a§lAbility §a- §c"
										+ (Math.round(bombArcherCooldown.get(shooter.getName()) * 10.0D) / 10.0D));

								bombArcherCooldown.put(shooter.getName(),
										bombArcherCooldown.get(shooter.getName()) - 0.1D);

								if (bombArcherCooldown.get(shooter.getName()) <= 0.0D) {
									bombArcherCooldown.remove(shooter.getName());

									if (Main.getAPI().getLanguage(shooter.getName()) == Language.FINNISH) {
										ChatUtils.sendMessageWithPrefix(shooter, "§7Voit nyt käyttää abilityäsi!");
									} else if (Main.getAPI().getLanguage(shooter.getName()) == Language.ENGLISH) {
										ChatUtils.sendMessageWithPrefix(shooter, "§7You can now use your ability!");
									}
									cancel();
								}

							}

						}
					}.runTaskTimerAsynchronously(Main.getInstance(), 0, 2);

				}

			}

			}
	 }
	 
}
