package kitpvp.listeners;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import kitpvp.Main;
import kitpvp.Util.ChatUtils;
import kitpvp.commands.PrefixCommand.NameColor;

public class ConnectionListener implements Listener{

	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		
		Player p = e.getPlayer();
		
		if(Main.getDataFile().get(p.getUniqueId().toString()) == null){
			ChatUtils.broadcastWithPrefix("§c§lWelcome §7" + p.getName() + " §c§lto the server!");
			
			FileConfiguration data = Main.getDataFile();
			String uuid = p.getUniqueId().toString();
			
			data.set(uuid + ".currentName", p.getName());
			data.set(uuid + ".ipAddress", p.getAddress().getAddress().toString());
			data.set(uuid + ".lang", "ENG");
			Main.getAPI().setNick(p.getName(), NameColor.DEFAULT);
			
			Main.saveDataFile();
			
		}
		
	}
	
}
