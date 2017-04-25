package kitpvp.cosmetics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import kitpvp.Main;
import kitpvp.Util.KitAPI;

public class CSGOCrate implements Listener {
	public static HashMap<Player, Integer> roll = new HashMap();

	private static void setGlass(Inventory inv) {
		Random r = new Random();
		HashMap Glass = new HashMap();
		for (int i = 0; i < 10; i++) {
			if ((i < 9) && (i != 3)) {
				Glass.put(Integer.valueOf(i), inv.getItem(i));
			}
		}
		for (Iterator localIterator = Glass.keySet().iterator(); localIterator.hasNext();) {
			int i = ((Integer) localIterator.next()).intValue();
			if (inv.getItem(i) == null) {
				int color = r.nextInt(15);
				inv.setItem(i, KitAPI.makeItem(Material.STAINED_GLASS_PANE, 1, color, " "));
				inv.setItem(i + 18, KitAPI.makeItem(Material.STAINED_GLASS_PANE, 1, color, " "));
			}
		}
		for (int i = 1; i < 10; i++) {
			if ((i < 9) && (i != 4)) {
				Glass.put(Integer.valueOf(i), inv.getItem(i));
			}
		}
		int color = r.nextInt(15);
		if (color == 8)
			color = 1;
		inv.setItem(0, (ItemStack) Glass.get(Integer.valueOf(1)));
		inv.setItem(18, (ItemStack) Glass.get(Integer.valueOf(1)));
		inv.setItem(1, (ItemStack) Glass.get(Integer.valueOf(2)));
		inv.setItem(19, (ItemStack) Glass.get(Integer.valueOf(2)));
		inv.setItem(2, (ItemStack) Glass.get(Integer.valueOf(3)));
		inv.setItem(20, (ItemStack) Glass.get(Integer.valueOf(3)));
		inv.setItem(3, (ItemStack) Glass.get(Integer.valueOf(5)));
		inv.setItem(21, (ItemStack) Glass.get(Integer.valueOf(5)));
		inv.setItem(4, KitAPI.makeItem(Material.STAINED_GLASS, 1, 15, " "));
		inv.setItem(22, KitAPI.makeItem(Material.STAINED_GLASS, 1, 15, " "));
		inv.setItem(5, (ItemStack) Glass.get(Integer.valueOf(6)));
		inv.setItem(23, (ItemStack) Glass.get(Integer.valueOf(6)));
		inv.setItem(6, (ItemStack) Glass.get(Integer.valueOf(7)));
		inv.setItem(24, (ItemStack) Glass.get(Integer.valueOf(7)));
		inv.setItem(7, (ItemStack) Glass.get(Integer.valueOf(8)));
		inv.setItem(25, (ItemStack) Glass.get(Integer.valueOf(8)));
		inv.setItem(8, KitAPI.makeItem(Material.STAINED_GLASS_PANE, 1, color, " "));
		inv.setItem(26, KitAPI.makeItem(Material.STAINED_GLASS_PANE, 1, color, " "));
	}

	public static void openCSGO(Player player) {
		Crate c = new Crate(player);
		Inventory inv = Bukkit.createInventory(null, 27, "Rolling CSGO...");
		setGlass(inv);
		for (int i = 9; (i > 8) && (i < 18); i++) {
			inv.setItem(i, c.pickPrize(player).getDisplayItem());
		}
		player.openInventory(inv);
		startCSGO(player, inv);
	}

	private static void startCSGO(final Player player, Inventory inv) {

		roll.put(player, Integer.valueOf(
				Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
					int time = 1;
					int full = 0;
					int open = 0;

					public void run() {
						if (this.full <= 50) {
							CSGOCrate.moveItems(inv, player);
							CSGOCrate.setGlass(inv);
							player.playSound(player.getLocation(), Sound.valueOf("CLICK"), 1, 1);
						}
						this.open++;
						if (this.open >= 5) {
							player.openInventory(inv);
							this.open = 0;
						}
						this.full++;
						if (this.full > 51) {
							if (slowSpin().contains(this.time)) {
								moveItems(inv, player);
								setGlass(inv);
								player.playSound(player.getLocation(), Sound.valueOf("CLICK"), 1, 1);
							}
							this.time++;
							if (this.time >= 60) {
								player.playSound(player.getLocation(), Sound.LEVEL_UP, 1f, 1f);
								Bukkit.getScheduler().cancelTask(((Integer) roll.get(player)));
								roll.remove(player);
								Prize prize = null;
								Crate c = new Crate(player);
								for (Prize p : (c.getPrizes())) {
									if (inv.getItem(13).isSimilar(p.getDisplayItem())) {
										prize = p;
									}
								}
								Crate.getReward(player, prize);
								return;
							}
						}

					}
				}, 1L, 1L)));
	}

	private static ArrayList<Integer> slowSpin() {
		ArrayList slow = new ArrayList();
		int full = 120;
		int cut = 15;
		for (int i = 120; cut > 0; full--) {
			if ((full <= i - cut) || (full >= i - cut)) {
				slow.add(Integer.valueOf(i));
				i -= cut;
				cut--;
			}
		}
		return slow;
	}

	private static void moveItems(Inventory inv, Player player) {
		Crate c = new Crate(player);
		ArrayList items = new ArrayList();
		for (int i = 9; (i > 8) && (i < 17); i++) {
			items.add(inv.getItem(i));
		}
		inv.setItem(9, c.pickPrize(player).getDisplayItem());
		for (int i = 0; i < 8; i++)
			inv.setItem(i + 10, (ItemStack) items.get(i));
	}

	// @EventHandler
	// public void onLeave(PlayerQuitEvent e)
	// {
	// Player player = e.getPlayer();
	// if (roll.containsKey(player)) {
	// Bukkit.getScheduler().cancelTask(((Integer)roll.get(player)).intValue());
	// roll.remove(player);
	// }
	// if (GUI.crates.containsKey(player))
	// GUI.crates.remove(player);
	// }
	// }
}
