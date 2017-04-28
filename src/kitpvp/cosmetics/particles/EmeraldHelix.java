package kitpvp.cosmetics.particles;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import kitpvp.Main;
import net.minecraft.server.v1_8_R3.EnumParticle;

public class EmeraldHelix extends Particle{

	public EmeraldHelix(EnumParticle particle) {
		super(particle);
	}

	@Override
	public void startEffect(Player player) {
		
		new BukkitRunnable() {
			double phi = 0;

			public void run() {
				phi = phi + Math.PI / 8;
				double x, y, z;

				Location location1 = player.getLocation();
				for (double t = 0; t <= 2 * Math.PI; t = t + Math.PI / 16) {
					for (double i = 0; i <= 1; i = i + 1) {
						x = 0.4 * (2 * Math.PI - t) * 0.5 * Math.cos(t + phi + i * Math.PI);
						y = 0.5 * t;
						z = 0.4 * (2 * Math.PI - t) * 0.5 * Math.sin(t + phi + i * Math.PI);
						location1.add(x, y, z);
						sendPacket(player);
						location1.subtract(x, y, z);
					}

				}

				if (phi > 10 * Math.PI) {
					this.cancel();
				}
			}
		}.runTaskTimer(Main.getInstance(), 0, 3);

	}

	@Override
	public void sendPacket(Player p){
		super.sendPacket(p);
	}
	
}
