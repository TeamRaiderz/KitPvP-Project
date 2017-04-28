package kitpvp.cosmetics;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;

public enum Rarity {

	COMMON(DyeColor.BLUE, ChatColor.BLUE), UNCOMMON(DyeColor.PURPLE, ChatColor.DARK_PURPLE), RARE(DyeColor.RED, ChatColor.RED), LEGENDRAY(DyeColor.ORANGE, ChatColor.GOLD),
	PRICELESS(DyeColor.GRAY, ChatColor.GRAY);
	
	ChatColor chatColor;
	DyeColor color;
	
	Rarity(DyeColor color, ChatColor chatColor){
		this.color = color;
		this.chatColor = chatColor;
	}
	
	public String getName(){
		String name = "";
		switch(this){
		case COMMON:
			name = chatColor + "§lCOMMON";
			break;
		case LEGENDRAY:
			name = chatColor + "§lLEGENDARY";
			break;
		case RARE:
			name = chatColor + "§lRARE";
			break;
		case UNCOMMON:
			name = chatColor + "§lUNCOMMON";
			break;
		case PRICELESS:
			name = chatColor + "§lNOTHING";
			break;
		default:
			break;
		}
		return name;
	}
	
}
