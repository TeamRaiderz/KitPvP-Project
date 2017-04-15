package kitpvp.StaffMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import kitpvp.Util.ChatUtils;
import kitpvp.punishment.PunishCommand;

public class StaffModeListeners implements Listener{

	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		if(StaffMode.getFreezeManager().isFrozen(e.getPlayer())){
			e.setCancelled(true);
			StaffMode.getFreezeManager().sendFreezeDenyMsg(e.getPlayer());
		}
		else if(StaffMode.staffMode.contains(e.getPlayer().getName())){
			
			Player p = e.getPlayer();
			ItemStack item = e.getItem();
			
			if(item.getType() == Material.BLAZE_POWDER && item.hasItemMeta()){
				Vector v = p.getLocation().getDirection().multiply(2);
				p.setVelocity(v);
			}
			else if(item.getType() == Material.SIGN && item.hasItemMeta()){
				ChatSettings cs = new ChatSettings();
				cs.openGui(p);
			}
			else if(item.getType() == Material.BLAZE_ROD && item.hasItemMeta()){
				Random random = new Random();
				
				List<Player> players = new ArrayList<>();
				
				for(Player online : Bukkit.getOnlinePlayers()){
					players.add(online);
					players.remove(p);
				}
				
				if(players.size() <= 0){
					ChatUtils.sendMessageWithPrefix(p, "§7You're the only one on the server §9:(");
					return;
				}
				
				int index = random.nextInt(players.size());
				Player randomPlayer = players.get(index);
				p.teleport(randomPlayer.getLocation());
			}
			else if(item.getType() == Material.BEACON && item.hasItemMeta()){
				if(StaffMode.vanished.get(p.getName())){
					StaffMode.vanished.put(p.getName(), false);
					for(Player online : Bukkit.getOnlinePlayers()){
						if(online == p) continue;
						online.showPlayer(p);
					}
				}
				else{
					StaffMode.vanished.put(p.getName(), true);
					for(Player online : Bukkit.getOnlinePlayers()){
						if(online == p) continue;
						online.hidePlayer(p);
					}
				}
			}
			
		}
	}
	
	@EventHandler
	public void onInteractAtEntity(PlayerInteractAtEntityEvent e){
		if(StaffMode.getFreezeManager().isFrozen(e.getPlayer())){
			e.setCancelled(true);
			StaffMode.getFreezeManager().sendFreezeDenyMsg(e.getPlayer());
		}
		else if(StaffMode.staffMode.contains(e.getPlayer().getName())){
			
			if(!(e.getRightClicked() instanceof Player)){ return; }
			
			Player p = e.getPlayer();
			Player target = (Player) e.getRightClicked();
			ItemStack item = p.getItemInHand();
			
			if(item.getType() == Material.LEASH && item.hasItemMeta()){
				p.setGameMode(GameMode.SPECTATOR);
				p.setSpectatorTarget(target);
			}
			else if(item.getType() == Material.PAPER && item.hasItemMeta()){
				
			}
			else if(item.getType() == Material.ICE && item.hasItemMeta()){
				if(StaffMode.getFreezeManager().isFrozen(target)){
					StaffMode.getFreezeManager().unFreeze(target);
				}
				else{
					StaffMode.getFreezeManager().freeze(target);
				}
			}
			else if(item.getType() == Material.ANVIL && item.hasItemMeta()){
				PunishCommand pcmd = new PunishCommand();
				pcmd.openPunishmentMenu(p, target.getName(), "Using cheats/breaking rules!");
			}
		}
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e){
		if(StaffMode.getFreezeManager().isFrozen(e.getPlayer())){
			e.setCancelled(true);
			e.getPlayer().teleport(e.getFrom());
		}
	}
	
	@EventHandler
	public void onAsyncChat(AsyncPlayerChatEvent e){
		if(StaffMode.getFreezeManager().isFrozen(e.getPlayer())){
			e.setCancelled(true);
			StaffMode.getFreezeManager().sendFreezeDenyMsg(e.getPlayer());
		}
	}
	
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e){
		if(StaffMode.getFreezeManager().isFrozen(e.getPlayer())){
			if(!(e.getMessage().startsWith("msg")) && !(e.getMessage().startsWith("tell")) && e.getMessage().startsWith("whisper")){
				e.setCancelled(true);
				StaffMode.getFreezeManager().sendFreezeDenyMsg(e.getPlayer());
			}
		}
	}
	
	@EventHandler
	public void onFoodChange(FoodLevelChangeEvent e){
		if(StaffMode.staffMode.contains(e.getEntity().getName())){
			e.setCancelled(true);
			e.setFoodLevel(20);
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent e){
		
		if(e.getEntity() instanceof Player){
			Player p = (Player) e.getEntity();
			
			if(StaffMode.staffMode.contains(p.getName())){
				e.setCancelled(true);
				e.setDamage(0);
				p.setHealth(p.getMaxHealth());
			}
		}
		
	}
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e){
		
	}
	
}
