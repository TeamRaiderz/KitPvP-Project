package kitpvp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import kitpvp.Util.ChatUtils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class ChatFormat implements Listener{

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e){
	
		Player p = e.getPlayer();
		String uuid = p.getUniqueId().toString();
		
		String prefix = ChatColor.translateAlternateColorCodes('&', Main.getChat().getGroupPrefix(p.getWorld(), Main.getChat().getPrimaryGroup(p)));
		String nick = ChatColor.translateAlternateColorCodes('&', Main.getDataFile().getString(uuid + ".nick"));
		String formatString = ChatColor.translateAlternateColorCodes('&', getLevelInChat(p) + " §r" + prefix + " §r" + nick + " §a§l>> §r" + e.getMessage());
		String formatString1 = ChatColor.translateAlternateColorCodes('&', getLevelInChat(p) + " §r" + prefix + " §r" + nick + " §a§l>> §r") + e.getMessage();
		String formatString2 = ChatColor.translateAlternateColorCodes('&', getLevelInChat(p) + " §r" + nick + " §a§l>> §r"  + e.getMessage());
		
		TextComponent format = new TextComponent(formatString);
		format.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/stats " + p.getName()));
		format.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7§oClick to see the stats!").create() ) );
		
		TextComponent format1 = new TextComponent(formatString1);
		format.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/stats " + p.getName()));
		format.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7§oClick to see the stats!").create() ) );
		
		TextComponent format2 = new TextComponent(formatString2);
		format.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/stats " + p.getName()));
		format.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7§oClick to see the stats!").create() ) );
		
		if(p.hasPermission("chat.color") && Main.getPermissions().getPrimaryGroup(p) != null){
			e.setCancelled(true);
			for(Player online : Bukkit.getOnlinePlayers()){
				online.spigot().sendMessage(format);
			}
			ChatUtils.sendConsoleMessage("<" + p.getName() + "> " + e.getMessage());
		}
		else if(!p.hasPermission("chat.color") && Main.getPermissions().getPrimaryGroup(p) == null){
			e.setCancelled(true);
			for(Player online : Bukkit.getOnlinePlayers()){
				online.spigot().sendMessage(format1);
			}
			ChatUtils.sendConsoleMessage("<" + p.getName() + "> " + e.getMessage());
		}
		else if(p.hasPermission("chat.color") && Main.getPermissions().getPrimaryGroup(p) != null){
			e.setCancelled(true);
			for(Player online : Bukkit.getOnlinePlayers()){
				online.spigot().sendMessage(format2);
			}
			ChatUtils.sendConsoleMessage("<" + p.getName() + "> " + e.getMessage());
		}
		
	}
	
	public String getLevelInChat(Player p){
		
		int lvl = Main.getAPI().getlevel(p.getName());
		
		if(lvl >= 0 && lvl <= 10){
			return "§7" + lvl;
		}
		else if(lvl >= 11 && lvl <= 20){
			return "§9" + lvl;
		}
		else if(lvl >= 21 && lvl <= 30){
			return "§a" + lvl;
		}
		else if(lvl >= 31 && lvl <= 40){
			return "§6" + lvl;
		}
		else if(lvl >= 41 && lvl <= 50){
			return "§c" + lvl;
		}
		
		return null;
	}
	
}
