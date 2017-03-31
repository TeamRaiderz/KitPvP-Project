package kitpvp.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import kitpvp.kits.KitManager;
import kitpvp.kits.KitManager.Kit;

public class KitMenuListener implements Listener{

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e){
		
		Player p = (Player) e.getWhoClicked();
		
		if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
		
		if(!(e.getCurrentItem().hasItemMeta())) return;
		
		if(e.getInventory().getName() == "Pakkaukset" || e.getInventory().getName() == "Kits"){
			
			e.setCancelled(true);
			
			ItemStack item = e.getCurrentItem();
			String displayName = item.getItemMeta().getDisplayName();
			KitManager km = new KitManager();
			
			if(displayName.equals(km.SPIDER_NAME)){
				p.closeInventory();
				km.giveKit(p, Kit.SPIDER);
			}
			else if(displayName.equals(km.FISHER_NAME)){
				p.closeInventory();
				km.giveKit(p, Kit.FISHER);
			}
			else if(displayName.equals(km.AIRMAN_NAME)){
				p.closeInventory();
				km.giveKit(p, Kit.AIRMAN);
			}
			else if(displayName.equals(km.ARCHER_NAME)){
				p.closeInventory();
				km.giveKit(p, Kit.ARCHER);
			}
			else if(displayName.equals(km.GHOST_NAME)){
				p.closeInventory();
				km.giveKit(p, Kit.GHOST);
			}
			else if(displayName.equals(km.TANK_NAME)){
				p.closeInventory();
				km.giveKit(p, Kit.TANK);
			}
			else if(displayName.equals(km.PYRO_NAME)){
				p.closeInventory();
				km.giveKit(p, Kit.PYRO);
			}
			else if(displayName.equals(km.BOMB_ARCHER_NAME)){
				p.closeInventory();
				km.giveKit(p, Kit.BOMB_ARCHER);
			}
			else if(displayName.equals(km.THUNDER_GOD_NAME)){
				p.closeInventory();
				km.giveKit(p, Kit.THUNDER_GOD);
			}
			else if(displayName.equals(km.KNIGHT_NAME)){
				p.closeInventory();
				km.giveKit(p, Kit.KNIGHT);
			}
			else if(displayName.equals(km.BOMBER_NAME)){
				p.closeInventory();
				km.giveKit(p, Kit.BOMBER);
			}
			else if(displayName.equals(km.PROTECTOR_NAME)){
				p.closeInventory();
				km.giveKit(p, Kit.PROTECTOR);
			}
			else if(displayName.equals(km.HULK_NAME)){
				p.closeInventory();
				km.giveKit(p, Kit.HULK);
			}
			else{
				p.closeInventory();
				return;
			}
			
		}
		
	}
	
}
