package kitpvp.listeners;

import java.util.Iterator;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import kitpvp.Language;
import kitpvp.Main;
import kitpvp.Util.ChatUtils;
import kitpvp.commands.LangCommand;
import kitpvp.commands.PrefixCommand.NameColor;
import net.minecraft.server.v1_8_R3.Container;
import net.minecraft.server.v1_8_R3.EntityPlayer;

public class ConnectionListener implements Listener{

	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		
		Player p = e.getPlayer();

		if(p.hasPermission("online.staff")){
			Main.getAPI().getOnlineStaffMembers().add(p.getName());
		}
		
		//Silenting zombies
		Iterator meta = p.getWorld().getLivingEntities().iterator();
		while (meta.hasNext()) {
			Entity ex = (Entity) meta.next();
			if (ex instanceof Zombie) {
				Main.getAPI().Silent(ex);
			}
		}
		
		EntityPlayer cont;
		
		if(Main.getDataFile().get(p.getUniqueId().toString()) == null){
			ChatUtils.broadcastWithPrefix("§c§lWelcome §7" + p.getName() + " §c§lto the server!");
			
			FileConfiguration data = Main.getDataFile();
			String uuid = p.getUniqueId().toString();
			
			data.set(uuid + ".currentName", p.getName());
			data.set(uuid + ".ipAddress", p.getAddress().getAddress().toString());
			
			//Default settings
			
			data.set(uuid + ".chatMention", true);
			data.set(uuid + ".scoreboard", true);
			data.set(uuid + ".chat", true);
			data.set(uuid + ".privateMsg", true);
			data.set(uuid + ".privateAccount", false);
			data.set(uuid + ".levelInChat", true);
			data.set(uuid + ".boosters", 0);
			
			data.set(p.getName(), "ENG");
			Main.getAPI().setNick(p.getName(), NameColor.DEFAULT);
			
			Main.saveDataFile();
			
			//LangCommand.openLangGUI(p);
		}
		
		FileConfiguration data = Main.getDataFile();
		String uuid = p.getUniqueId().toString();
		
		data.set(uuid + ".currentName", p.getName());
		data.set(uuid + ".ipAddress", p.getAddress().getAddress().toString());
		Main.saveDataFile();
		
		//Sending join title
		if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
			Main.getPacketUtils().sendTitle(p, "§a§lFINSKACRAFT!", "§7Suomen paskin servu!", 20, 40, 20);
		}
		else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
			Main.getPacketUtils().sendTitle(p, "§a§lFINSKACRAFT!", "§7Shittiest server in Finland!", 20, 40, 20);
		}
		
		Main.getAPI().startPlayTimeCount(p);
		
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		
		Player p = e.getPlayer();
		
		Main.getAPI().setLastLogin(p.getName());
		
	}
	
}
