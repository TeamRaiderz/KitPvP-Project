package kitpvp.listeners;

import java.util.Iterator;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import kitpvp.Main;
import kitpvp.Util.ChatUtils;
import kitpvp.commands.LangCommand;
import kitpvp.commands.PrefixCommand.NameColor;

public class ConnectionListener implements Listener{

	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		
		Player p = e.getPlayer();
		
		//Silenting zombies
		Iterator meta = p.getWorld().getLivingEntities().iterator();
		while (meta.hasNext()) {
			Entity ex = (Entity) meta.next();
			if (ex instanceof Zombie) {
				Main.getAPI().Silent(ex);
			}
		}
		
		if(Main.getDataFile().get(p.getUniqueId().toString()) == null){
			ChatUtils.broadcastWithPrefix("§c§lWelcome §7" + p.getName() + " §c§lto the server!");
			
			FileConfiguration data = Main.getDataFile();
			String uuid = p.getUniqueId().toString();
			
			data.set(uuid + ".currentName", p.getName());
			data.set(uuid + ".ipAddress", p.getAddress().getAddress().toString());
			data.set(uuid + ".lang", "ENG");
			Main.getAPI().setNick(p.getName(), NameColor.DEFAULT);
			
			LangCommand.openLangGUI(p);
			
			Main.saveDataFile();
			
		}
		
	}
	
}
