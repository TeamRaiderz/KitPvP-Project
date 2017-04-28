package kitpvp.listeners;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import kitpvp.Main;
import kitpvp.Util.ChatUtils;
import kitpvp.Util.Language;
import kitpvp.Util.TeleportManager;
import kitpvp.arena.events.ArenaEnterEvent;
import kitpvp.commands.essential.LangCommand;
import kitpvp.other.SpawnItems;

public class PlayerListeners implements Listener{

	private HashMap<Player, Integer> cmdCooldown = new HashMap<Player, Integer>();
	private HashMap<Player, Integer> chatCooldown = new HashMap<Player, Integer>();
	private HashMap<String, String> prevMsg = new HashMap<String, String>();
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e){
		
		Player p = e.getPlayer();
		
		double fromX = e.getFrom().getX(), fromY = e.getFrom().getY(), fromZ = e.getFrom().getZ();
		double toX = e.getTo().getX(), toY = e.getTo().getY(), toZ = e.getTo().getZ();
				
		
		if(Main.getAPI().getLanguage(p.getName()) == Language.DEFAULT){
			p.teleport(e.getFrom());
			LangCommand.openLangGUI(p);
			return;
		}
		
		if(e.getTo().getY() < 0.0D){
			p.teleport(p.getWorld().getSpawnLocation());
		}
		
		if(fromX - toX > 0 || fromY - toY > 0|| fromZ - toZ > 0){
			if(TeleportManager.spawnCooldown.containsKey(p.getName())){
				TeleportManager.spawnCooldown.remove(p.getName());
				p.teleport(e.getFrom());
			}
		}
		
	}
	
	@EventHandler
	public void onCommandPreProcess(PlayerCommandPreprocessEvent e){
		
		Player p = e.getPlayer();
		
		if(Main.getAPI().getLanguage(p.getName()) == Language.DEFAULT){
			e.setCancelled(true);
			LangCommand.openLangGUI(p);
			return;
		}
		
		if(!cmdCooldown.containsKey(p) && !p.hasPermission("server.cmdcooldown")){
			cmdCooldown.put(p, 3);
			
			new BukkitRunnable(){

				@Override
				public void run() {
					
					cmdCooldown.put(p, cmdCooldown.get(p) - 1);
					
					if(cmdCooldown.get(p) <= 0){
						cmdCooldown.remove(p);
						cancel();
					}
					
				}
				
			}.runTaskTimerAsynchronously(Main.getInstance(), 20, 20);
			
		}
		else if (cmdCooldown.containsKey(p)){
			e.setCancelled(true);
			if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
				ChatUtils.sendMessageWithPrefix(p, "§7Voit käyttää komentoja §c" + cmdCooldown.get(p) + " §7sekunnin päästä!");
			}
			else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
				ChatUtils.sendMessageWithPrefix(p, "§7You may use commands after §c" + cmdCooldown.get(p) + " §7seconds!");
			}
		}
	}
	
	@EventHandler
	public void onChatSend(AsyncPlayerChatEvent e) {

		Player p = e.getPlayer();

		e.setCancelled(true);

		if (Main.getAPI().getLanguage(p.getName()) == Language.DEFAULT) {
			e.setCancelled(true);
			LangCommand.openLangGUI(p);
			return;
		}

		if (prevMsg.containsKey(p.getUniqueId().toString())) {
			if ((((String) prevMsg.get(p.getUniqueId().toString())).equalsIgnoreCase(e.getMessage()))
					&& !(p.hasPermission("server.spambypass"))) {
				e.setCancelled(true);
				if (Main.getAPI().getLanguage(p.getName()) == Language.FINNISH) {
					ChatUtils.sendMessageWithPrefix(p, "§7Tuo viesti muistuttaa liikaa sinun aikaisempaa viestiäsi!");
				} else if (Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH) {
					ChatUtils.sendMessageWithPrefix(p, "§7That message is too similiar to your previous message!");
				}
				return;
			} else {
				prevMsg.remove(p.getUniqueId().toString());
				prevMsg.put(p.getUniqueId().toString(), e.getMessage());
			}
		} else if (!prevMsg.containsKey(p.getUniqueId().toString()) && !p.hasPermission("server.spambypass")
				&& !p.isOp())
			prevMsg.put(p.getUniqueId().toString(), e.getMessage());

		else if (!chatCooldown.containsKey(p) && !p.hasPermission("server.chatcooldown")) {
			chatCooldown.put(p, 3);

			new BukkitRunnable() {

				@Override
				public void run() {

					chatCooldown.put(p, chatCooldown.get(p) - 1);

					if (chatCooldown.get(p) <= 0) {
						chatCooldown.remove(p);
						cancel();
					}

				}

			}.runTaskTimer(Main.getInstance(), 20, 20);
			return;
		} else if (chatCooldown.containsKey(p) && !p.hasPermission("server.chatcooldown") && !p.isOp()) {
			e.setCancelled(true);
			if (Main.getAPI().getLanguage(p.getName()) == Language.FINNISH) {
				ChatUtils.sendMessageWithPrefix(p,
						"§7Voit lähettää viestejä §c" + chatCooldown.get(p) + " §7sekunnin päästä!");
			} else if (Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH) {
				ChatUtils.sendMessageWithPrefix(p,
						"§7You may send chat messages after §c" + chatCooldown.get(p) + " §7seconds!");
			}
			return;
		}

	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getAction() == Action.PHYSICAL && e.getClickedBlock().getType() == Material.SOIL)
			e.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPortalEnter(EntityPortalEnterEvent e){
		
		if(e.getEntity() instanceof Player){
			Player p = (Player) e.getEntity();
			ArenaEnterEvent arenaEnterEvent = new ArenaEnterEvent(p.getName());
			Bukkit.getServer().getPluginManager().callEvent(arenaEnterEvent);
		}
		
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e){
		SpawnItems.giveItems(e.getPlayer(), e.getPlayer().getInventory());
	}
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e){
		Player p = (Player) e.getWhoClicked();
		if(e.getInventory().getName().equals("Oston varmistus") || e.getInventory().getName().equals("Confirm purchase")){
			e.setCancelled(true);
			
			int price = Integer.valueOf(e.getInventory().getItem(13).getItemMeta().getDisplayName().charAt(11));
			
			if(e.getCurrentItem() == new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)5) && e.getCurrentItem().hasItemMeta()){
				Main.getAPI().setBalance(p.getName(), Main.getAPI().getBalance(p.getName()) - price);
			}
			else if(e.getCurrentItem() == new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)14) && e.getCurrentItem().hasItemMeta()){
				p.closeInventory();
			}
			
		}
	}
	
}
