package kitpvp;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatFormat implements Listener{

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e){
	
		Player p = e.getPlayer();
		String uuid = p.getUniqueId().toString();
		String nickname = ChatColor.translateAlternateColorCodes('&', Main.getDataFile().getString(uuid + ".nick"));
		
		e.setFormat(nickname + " §a§l>> §r" + e.getMessage());
		
	}
	
}
