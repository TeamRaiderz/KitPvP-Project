package kitpvp.listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import kitpvp.Main;
import kitpvp.Util.ChatUtils;
import kitpvp.Util.KitAPI;
import kitpvp.Util.Language;
import kitpvp.arena.events.ArenaKillEvent;

public class LevelListeners implements Listener {

	public static HashMap<String, Integer> kill = new HashMap();
	public static HashMap<String, String> killed = new HashMap();
	public List<String> noXpForKill = new ArrayList();

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onKill(PlayerDeathEvent e) {
		final Player victim = e.getEntity();
		final Player killer = e.getEntity().getKiller();
		KitAPI api = new KitAPI();

		if (killer == null)
			return;
		if (victim == null)
			return;

		final int minuteInTicks = 1200;

		if (!this.noXpForKill.contains(killer.getName())) {
			if (kill.get(killer.getName()) == null) {
				kill.put(killer.getName(), Integer.valueOf(1));
			}
			kill.put(killer.getName(), Integer.valueOf(((Integer) kill.get(killer.getName())).intValue() + 1));
			killed.put(killer.getName(), victim.getName());

			api.addKills(killer.getName(), 1);
			api.addDeaths(victim.getName(), 1);

			api.addKillToKillStreak(killer);
			api.clearKillStreak(victim);

			killer.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));

			e.setDeathMessage(null);
			e.getDrops().clear();

			ArenaKillEvent arenaKillEvent = new ArenaKillEvent(killer.getName(), victim.getName());
			
			Bukkit.getServer().getPluginManager().callEvent(arenaKillEvent);

			e.setDeathMessage("The player " + killer.getName() + " has killed the player " + victim.getName() + " ßc" + kill.get(killer.getName()) + " ßftimes!");
		}

		new BukkitRunnable() {
			public void run() {
				kill.put(killer.getName(), Integer.valueOf(0));
				killed.remove(killer.getName());
				noXpForKill.remove(killer.getName());
				cancel();
			}

		}.runTaskLater(Main.getInstance(), minuteInTicks * 10);

		new BukkitRunnable() {
			public void run() {
				if ((((Integer) kill.get(killer.getName())).intValue() > 10)
						&& (killed.get(killer.getName()) == victim.getName())) {

					if (Main.getAPI().getLanguage(killer.getName()) == Language.FINNISH) {
						ChatUtils.sendMessageWithPrefix(killer,
								"ß7Olet tappanut pelaajan ßc" + victim.getName()
										+ " ß7liian monta kertaa, etk‰ saa en‰‰ XP:t‰ h‰nen tappamisestaan"
										+ " ßc5 ß7minuuttiin!");
					} else if (Main.getAPI().getLanguage(killer.getName()) == Language.ENGLISH) {
						ChatUtils.sendMessageWithPrefix(killer,
								"ß7You've killed the player ßc" + victim.getName()
										+ " ß7too many times, and you won't get any XP after killing the player"
										+ " in ßc5 ß7minutes!");
					}

					noXpForKill.add(killer.getName());
					cancel();

					new BukkitRunnable() {
						public void run() {
							kill.put(killer.getName(), Integer.valueOf(0));
							killed.remove(killer.getName());
							noXpForKill.remove(killer.getName());
							if (Main.getAPI().getLanguage(killer.getName()) == Language.FINNISH) {
								ChatUtils.sendMessageWithPrefix(killer,
										"ß7Voit taas saada XP:t‰ tappamalla pelaajan ßc" + victim.getName() + "ß7!");
							} else if (Main.getAPI().getLanguage(killer.getName()) == Language.ENGLISH) {
								ChatUtils.sendMessageWithPrefix(killer,
										"ß7You can now get XP after killing ßc" + victim.getName() + "ß7!");
							}

							cancel();
						}
					}.runTaskLaterAsynchronously(Main.getInstance(), minuteInTicks * 5);
					return;
				}
			}
		}.runTaskTimerAsynchronously(Main.getInstance(), 0L, 20L);
	}

}
