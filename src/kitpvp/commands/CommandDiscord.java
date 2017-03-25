package kitpvp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import kitpvp.Language;
import kitpvp.Main;
import kitpvp.Util.ChatUtils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class CommandDiscord implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		
		if(arg0 instanceof Player){
		
			Player p = (Player) arg0;
			
			if(Main.getAPI().getLanguage(p.getName()) == Language.FINNISH){
				TextComponent format = new TextComponent(ChatUtils.getPrefix() + " �7Klikkaa p��st�ksesi meid�n �9Discordiin�7!");
				format.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/eBe6nGe"));
				format.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("�7�oKlikkaa p��st�ksesi discordiin!").create() ) );
				p.spigot().sendMessage(format);
			}
			else if(Main.getAPI().getLanguage(p.getName()) == Language.ENGLISH){
				TextComponent format = new TextComponent(ChatUtils.getPrefix() + " �7Click to get to our �9Discord�7!");
				format.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/eBe6nGe"));
				format.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("�7�oClick to get to discord!").create() ) );
				p.spigot().sendMessage(format);
			}
			
		}
		
		return true;
	}
	

}
