package kitpvp.listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import kitpvp.Main;
import kitpvp.Util.ChatUtils;
import kitpvp.Util.Language;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
public class AbilityListener implements Listener {
	
	 public static HashMap<String, Double> flyCooldown = new HashMap<String, Double>();
	 public static HashMap<String, Double> archerCooldown = new HashMap<String, Double>();
	 public static HashMap<String, Double> bombArcherCooldown = new HashMap<String, Double>();
	 public static HashMap<String, Double> bomberCooldown = new HashMap<String, Double>();
	 public static HashMap<String, Double> protectorCooldown = new HashMap<String, Double>();
	 public static List<String> arrowShot = new ArrayList<String>();
	 public static List<Projectile> customArrow = new ArrayList<Projectile>();
	 public static HashMap<String, Location> bombArrowLoc = new HashMap<String, Location>();
	 public static List<String> bombArrowShot = new ArrayList<String>();
	 public static HashMap<String, Double> thunderGodCooldown = new HashMap<String, Double>();
	
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
			if(type == Material.SULPHUR && item.hasItemMeta() && item.getItemMeta().getDisplayName().equals("§7Protector")){
				
				if(!(protectorCooldown.containsKey(p.getName()))){
					protectorCooldown.put(p.getName(), 10.0D);

					p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 5, 1));
					for(Entity ent : p.getNearbyEntities(5, 5, 5)){
						if(p instanceof Player){
							if(ent == p) continue;
							Vector velocity = (p.getLocation().toVector().subtract(p.getLocation().toVector()).multiply(2.0).setY(1));
							p.setVelocity(p.getVelocity().add(velocity));
							((Player) ent).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 5, 2));
							((Player) ent).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 5, 3));
						}
					}
					new BukkitRunnable() {
						public void run() {
							
							if (protectorCooldown.containsKey(p.getName())) {

								Main.getPacketUtils().sendActionBar(p, "§a§lAbility §a- §c"
										+ (Math.round(protectorCooldown.get(p.getName()) * 10.0D) / 10.0D));

								protectorCooldown.put(p.getName(), protectorCooldown.get(p.getName()) - 0.1D);

								if (protectorCooldown.get(p.getName()) <= 0.0D) {
									protectorCooldown.remove(p.getName());

									if (Main.getAPI().getLanguage(p.getName()) == Language.FINNISH) {
										ChatUtils.sendMessageWithPrefix(p, "§7Voit nyt käyttää abilityäsi!");
									} else if (Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH) {
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
			if (type == Material.DIAMOND_AXE && item.hasItemMeta()
					&& item.getItemMeta().getDisplayName().equals("§7Thunder God")) {
				
				if(!(thunderGodCooldown.containsKey(p.getName()))){
					e.setCancelled(true);
					thunderGodCooldown.put(p.getName(), 10.0D);
					
					Block target = p.getTargetBlock((HashSet<Byte>) null, 100);
					
					if(target.getType() == Material.AIR){ return; }
					
					p.getWorld().strikeLightning(target.getLocation());
					
					if(p.getLastDamage() > 0.0D){ p.setLastDamage(0.0D); }
					for(Entity ent : p.getWorld().getNearbyEntities(target.getLocation(), 5, 5, 5)){
						if(ent instanceof Player){
							Player near = (Player) ent;
							if(near == p || ent == p) continue;
							near.setVelocity(p.getLocation().toVector().subtract(p.getLocation().toVector()).multiply(2.0).setY(1));
							near.damage(5.0);
						}
					}
					
					new BukkitRunnable() {
						public void run() {
							
							if (thunderGodCooldown.containsKey(p.getName())) {

								Main.getPacketUtils().sendActionBar(p, "§a§lAbility §a- §c"
										+ (Math.round(thunderGodCooldown.get(p.getName()) * 10.0D) / 10.0D));

								thunderGodCooldown.put(p.getName(), thunderGodCooldown.get(p.getName()) - 0.1D);

								if (thunderGodCooldown.get(p.getName()) <= 0.0D) {
									thunderGodCooldown.remove(p.getName());

									if (Main.getAPI().getLanguage(p.getName()) == Language.FINNISH) {
										ChatUtils.sendMessageWithPrefix(p, "§7Voit nyt käyttää abilityäsi!");
									} else if (Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH) {
										ChatUtils.sendMessageWithPrefix(p, "§7You can now use your ability!");
									}
									cancel();
								}

							}

						}
					}.runTaskTimerAsynchronously(Main.getInstance(), 0, 2);
				}
			}
			
		}
		//Archer ability
		else if(e.getAction() == Action.LEFT_CLICK_AIR 
				|| e.getAction() == Action.LEFT_CLICK_BLOCK){
			
			if(type == Material.BOW && item.hasItemMeta() && item.getItemMeta().getDisplayName().equals("§7Archer")){
				e.setCancelled(true);
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
				e.setCancelled(true);

				if (bombArrowLoc.containsKey(p.getName())) {
					
					Location arrowLoc = bombArrowLoc.get(p.getName());
					Location shooterLoc = p.getLocation();
					
					Vector velocity = arrowLoc.toVector().subtract(shooterLoc.toVector()).normalize().multiply(2.0).setY(1);
					p.setVelocity(p.getVelocity().add(velocity));
					
					p.playSound(p.getLocation(), Sound.NOTE_PIANO, 1f, 0.2f);
					bombArrowLoc.remove(p.getName());
				}

			}
			else if (type == Material.TNT && item.hasItemMeta() && item.getItemMeta().getDisplayName().equals("§7Bomber")){
				
				e.setCancelled(true);
				
				if(!(bomberCooldown.containsKey(p.getName()))){
					bomberCooldown.put(p.getName(), 10.0D);
					double speedFactor = 1.5D;
					Location handLocation = p.getLocation();
					handLocation.setY(handLocation.getY() + 1.0D);

					Vector direction = handLocation.getDirection();

					Entity entity = p.getWorld().spawn(handLocation, TNTPrimed.class);
					entity.setVelocity(direction.multiply(speedFactor));
					p.getInventory().removeItem(new ItemStack[] { new ItemStack(Material.TNT, 1) });
					
					//Cooldown
					new BukkitRunnable() {
						public void run() {

							if (bomberCooldown.containsKey(p.getName())) {

								Main.getPacketUtils().sendActionBar(p,
										"§a§lAbility §a- §c" + (Math.round(bomberCooldown.get(p.getName()) * 10.0D) / 10.0D));

								bomberCooldown.put(p.getName(), bomberCooldown.get(p.getName()) - 0.1D);

								if (bomberCooldown.get(p.getName()) <= 0.0D) {
									bomberCooldown.remove(p.getName());

									if (Main.getAPI().getLanguage(p.getName()) == Language.FINNISH) {
										ChatUtils.sendMessageWithPrefix(p, "§7Voit nyt käyttää abilityäsi!");
									} else if (Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH) {
										ChatUtils.sendMessageWithPrefix(p, "§7You can now use your ability!");
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
							Vector velocity = p.getLocation().toVector().subtract(p.getLocation().toVector()).normalize().multiply(2.0).setY(1);
							p.setVelocity(velocity);
							((Player) p).damage(4.0D);
						}
					}
					e.getEntity().remove();
					
				}
				
				e.getEntity().remove();

			}

		}

	 }
	 
	@EventHandler
	public void onArrowShoot(ProjectileLaunchEvent e) {
		if (e.getEntity() instanceof Arrow && e.getEntityType() == EntityType.ARROW) {
			if (e.getEntity().getShooter() != null && e.getEntity().getShooter() instanceof Player) {
				Player shooter = (Player) e.getEntity().getShooter();

				if(shooter.getItemInHand().getType() == Material.BOW && shooter.getItemInHand().hasItemMeta()
						&& shooter.getItemInHand().getItemMeta().getDisplayName().equals("§7Bomb Archer")){
					if (!bombArrowShot.contains(shooter.getName()) && !bombArcherCooldown.containsKey(shooter.getName())) {
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
					else if(bombArrowShot.contains(shooter.getName()) && bombArcherCooldown.containsKey(shooter.getName())){
						e.setCancelled(true);
					}
				}
			}
		}
	}
	
	public void executeSpeedAbility(Player p){
		
	}
}
