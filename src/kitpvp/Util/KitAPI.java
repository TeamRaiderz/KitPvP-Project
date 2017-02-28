package kitpvp.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import kitpvp.Language;
import kitpvp.Main;
import kitpvp.commands.PrefixCommand.NameColor;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.NBTTagCompound;

public class KitAPI {

	public int getKills(String player){
		Statement s = null;
		ResultSet res = null;
		try { 
			s = Main.getMySQLManager().getConnection().createStatement();
			res = s.executeQuery("SELECT * FROM kills WHERE PlayerName = '" + player + "';");
			if(res.getString("PlayerName") == null){
				return 0;
			}
			else{
				return res.getInt("kills");
			}
		} catch (SQLException e) { e.printStackTrace(); }
		return 0;
	}
	
	public void setKills(String player, int kills){
		Statement s = null;
		ResultSet res = null;
		try { 
			s = Main.getMySQLManager().getConnection().createStatement();
			s.executeUpdate("INSERT INTO kills (`PlayerName`, `kills`) VALUES ('" + player + "', '" + kills + "');");
			System.out.println("The player's " + player + " kills are now " + kills);
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public int getBalance(String player){
		Statement s = null;
		ResultSet res = null;
		try { 
			s = Main.getMySQLManager().getConnection().createStatement();
			res = s.executeQuery("SELECT * FROM balance WHERE PlayerName = '" + player + "';");
			if(res.getString("PlayerName") == null){
				return 0;
			}
			else{
				return res.getInt("balance");
			}
		} catch (SQLException e) { e.printStackTrace(); }
		return 0;
	}
	
	public void setBalance(String player, int kills){
		Statement s = null;
		ResultSet res = null;
		try { 
			s = Main.getMySQLManager().getConnection().createStatement();
			s.executeUpdate("INSERT INTO kills (`PlayerName`, `balance`) VALUES ('" + player + "', '" + kills + "');");
			System.out.println("The player's " + player + " balance is now " + kills);
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public Language getLanguage(String player){
		
		OfflinePlayer p = Bukkit.getOfflinePlayer(player);
		String uuid = p.getUniqueId().toString();
		
		if(Main.getDataFile().getString(uuid + ".lang").equalsIgnoreCase("ENG")){
			return Language.ENGLISH;
		}
		else if(Main.getDataFile().getString(uuid + ".lang").equalsIgnoreCase("FIN")){
			return Language.FINNISH;
		}
		return null;
	}
	
	public void setLanguage(String player, Language language){
		
		OfflinePlayer p = Bukkit.getOfflinePlayer(player);
		String uuid = p.getUniqueId().toString();
		
		if (Main.getDataFile().get(uuid) == null) {
			System.out.println("That player is not in our database.");
			return;
		}
		
		switch (language) {
		case FINNISH:
			Main.getDataFile().set(uuid + ".lang", "FIN");
			Main.saveDataFile();
			break;
		case ENGLISH:
			Main.getDataFile().set(uuid + ".lang", "ENG");
			Main.saveDataFile();
			break;
		}
		
	}
	
	public void setNick(String player, NameColor color){
		String uuid = Bukkit.getOfflinePlayer(player).getUniqueId().toString();
		FileConfiguration file = Main.getDataFile();
		
		switch(color){
		case RED:
			file.set(uuid + ".nick", "&c" + player);
			Main.saveDataFile();
			break;
		case GREEN:
			file.set(uuid + ".nick", "&a" + player);
			Main.saveDataFile();
			break;
		case AQUA:
			file.set(uuid + ".nick", "&b" + player);
			Main.saveDataFile();
			break;
		case CYAN:
			file.set(uuid + ".nick", "&3" + player);
			Main.saveDataFile();
			break;
		case BLUE:
			file.set(uuid + ".nick", "&9" + player);
			Main.saveDataFile();
			break;
		case YELLOW:
			file.set(uuid + ".nick", "&e" + player);
			Main.saveDataFile();
			break;
		case WHITE:
			file.set(uuid + ".nick", "&f" + player);
			Main.saveDataFile();
			break;
		case GOLD:
			file.set(uuid + ".nick", "&6" + player);
			Main.saveDataFile();
			break;
		case PURPLE:
			file.set(uuid + ".nick", "&5" + player);
			Main.saveDataFile();
			break;
		case PINK:
			file.set(uuid + ".nick", "&d" + player);
			Main.saveDataFile();
			break;
		case DEFAULT:
			file.set(uuid + ".nick", "&7" + player);
			Main.saveDataFile();
			break;
		}
		
	}
	
	public String getNick(String player){
		OfflinePlayer p = Bukkit.getOfflinePlayer(player);
		String uuid = p.getUniqueId().toString();
		return ChatColor.translateAlternateColorCodes('&', Main.getDataFile().getString(uuid + ".nick"));
	}
	
	public void createItem(Inventory inv, int pos, Material mat, int amount, String displayName, List<String> lore){
		ItemStack item = new ItemStack(mat);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
		meta.setLore(lore);
		item.setAmount(amount);
		item.setItemMeta(meta);
		inv.setItem(pos, item);
	}
	
	public void createWoolItem(Inventory inv, int pos, DyeColor color, String displayName, List<String> lore){
		ItemStack wool = new ItemStack(Material.WOOL, 1, color.getData());
		ItemMeta meta = wool.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
		meta.setLore(lore);
		wool.setItemMeta(meta);
		inv.setItem(pos, wool);
	}
	
	public void Silent(Entity YourEntity) {
		net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity) YourEntity).getHandle();
		NBTTagCompound tag = nmsEntity.getNBTTag();
		if (tag == null) {
			tag = new NBTTagCompound();
		}

		nmsEntity.c(tag);
		tag.setInt("Silent", 1);
		nmsEntity.f(tag);
	}
	
}
