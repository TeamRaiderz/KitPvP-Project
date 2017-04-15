package kitpvp.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import kitpvp.Main;
import kitpvp.Util.Language;

public class ChatEvent implements Listener{

	@EventHandler
	public void onAsyncChat(AsyncPlayerChatEvent e){
		
		//The player who sent the player
		Player p = e.getPlayer();
	
		e.setCancelled(true);
		
		String msg = e.getMessage().toLowerCase();
		
		for(Player recipient : Bukkit.getOnlinePlayers()){
			
			if(recipient == p){ continue; }
			
			if(msg.contains(recipient.getName().toLowerCase())){
				
				if((Main.getDataFile().getBoolean(recipient.getUniqueId().toString() + ".chatMention"))){
					recipient.playSound(recipient.getLocation(), Sound.NOTE_PLING, 1, 0.2f);
					
					if(Main.getAPI().getLanguage(recipient.getName()) == Language.FINNISH){
						Main.getPacketUtils().sendActionBar(recipient, "§2§l" + p.getName() + " §amainitsi sinut chatissa!");
					}
					else if(Main.getAPI().getLanguage(recipient.getName()) == Language.ENGLISH){
						Main.getPacketUtils().sendActionBar(recipient, "§2§l" + p.getName() + " §amentioned you in the chat!");
					}
					
				}
				
			}
		}
		
	}
	
}
