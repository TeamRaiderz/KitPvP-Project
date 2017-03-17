package kitpvp.listeners;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import kitpvp.Language;
import kitpvp.Main;
import kitpvp.Util.ChatUtils;
import kitpvp.commands.LangCommand;

public class PlayerListeners implements Listener{

	private HashMap<Player, Integer> cmdCooldown = new HashMap<Player, Integer>();
	private HashMap<Player, Integer> chatCooldown = new HashMap<Player, Integer>();
	private HashMap<String, String> prevMsg = new HashMap<String, String>();
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e){
		
		Player p = e.getPlayer();
		
		if(Main.getAPI().getLanguage(p.getName()) == Language.DEFAULT){
			p.teleport(e.getFrom());
			LangCommand.openLangGUI(p);
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
		
		if(!cmdCooldown.containsKey(p) || cmdCooldown.get(p) == null && !p.hasPermission("server.cmdcooldown")){
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
				
			}.runTaskTimer(Main.getInstance(), 20, 20);
			
		}
		else if (cmdCooldown.get(p) != null || cmdCooldown.containsKey(p)){
			e.setCancelled(true);
			if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
				ChatUtils.sendMessageWithPrefix(p, "§7Voit käyttää komentoja §c" + cmdCooldown.get(p) + " §7sekunnin päästä!");
			}
			else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
				ChatUtils.sendMessageWithPrefix(p, "§7You may use commands after §c" + cmdCooldown.get(p) + " §7seconds!");
			}
			return;
		}
	}
	
	@EventHandler
	public void onChatSend(AsyncPlayerChatEvent e){
		
		Player p = e.getPlayer();
		
		if(Main.getAPI().getLanguage(p.getName()) == Language.DEFAULT){
			e.setCancelled(true);
			LangCommand.openLangGUI(p);
			return;
		}
		
		if (prevMsg.containsKey(p.getUniqueId().toString())) {
		      if ((((String)prevMsg.get(p.getUniqueId().toString())).equalsIgnoreCase(e.getMessage())) && !(p.hasPermission("server.spambypass"))) {
		        e.setCancelled(true);
		        if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
					ChatUtils.sendMessageWithPrefix(p, "§7Tuo viesti muistuttaa liikaa sinun aikaisempaa viestiäsi!");
				}
				else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
					ChatUtils.sendMessageWithPrefix(p, "§7That message is too similiar to your previous message!");
				}
		        return;
		      }
		      else {
		    	  prevMsg.remove(p.getUniqueId().toString());
		    	  prevMsg.put(p.getUniqueId().toString(), e.getMessage());
		      }
		    }
		    else if(!prevMsg.containsKey(p.getUniqueId().toString()) && !p.hasPermission("server.spambypass") && !p.isOp())
		      prevMsg.put(p.getUniqueId().toString(), e.getMessage());
		
		if(!chatCooldown.containsKey(p) && !p.hasPermission("server.chatcooldown") && !p.isOp()){
			chatCooldown.put(p, 3);
			
			new BukkitRunnable(){

				@Override
				public void run() {
					
					chatCooldown.put(p, chatCooldown.get(p) - 1);
					
					if(chatCooldown.get(p) <= 0){
						chatCooldown.remove(p);
						cancel();
					}
					
				}
				
			}.runTaskTimer(Main.getInstance(), 20, 20);
			return;
		}
		if (chatCooldown.containsKey(p) && !p.hasPermission("server.chatcooldown") && !p.isOp()){
			e.setCancelled(true);
			if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
				ChatUtils.sendMessageWithPrefix(p, "§7Voit lähettää viestejä §c" + chatCooldown.get(p) + " §7sekunnin päästä!");
			}
			else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
				ChatUtils.sendMessageWithPrefix(p, "§7You may send chat messages after §c" + chatCooldown.get(p) + " §7seconds!");
			}
			return;
		}
		
	}
	
}
