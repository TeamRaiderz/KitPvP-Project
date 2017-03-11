package kitpvp.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import kitpvp.Language;
import kitpvp.Main;
import kitpvp.commands.LangCommand;

public class PlayerListeners implements Listener{

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
		}
		
	}
	
	@EventHandler
	public void onChatSend(AsyncPlayerChatEvent e){
		
		Player p = e.getPlayer();
		
		if(Main.getAPI().getLanguage(p.getName()) == Language.DEFAULT){
			e.setCancelled(true);
			LangCommand.openLangGUI(p);
		}
		
	}
	
}
