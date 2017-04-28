package kitpvp.cosmetics.particles;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

public abstract class Particle {

	private EnumParticle particle;
	
	public Particle(EnumParticle particle){
		this.particle = particle;
	}
	
	public EnumParticle getParticle(){
		return particle;
	}
	
	public void startEffect(Player target){
		
		double radius = 2;
		double maxHeight = 5;
		
		for(double y = 0; y < maxHeight; y+= 0.05){
			
			double x = Math.sin(y * radius);
			double z = Math.cos(y * radius);
			
			sendPacket(target, x, y, z);
		}
		
	}
	
	public void sendPacket(Player p){
		for(Player online : Bukkit.getOnlinePlayers()){
			 PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(particle, true, (float) p.getLocation().getX(), (float) p.getLocation().getY(),
					 (float) p.getLocation().getZ(), (float) p.getLocation().getX(), 0, 0, 1, 1);
		        ((CraftPlayer) online).getHandle().playerConnection.sendPacket(packet);
		}
	}
	
	public void sendPacket(Player p, double x, double y, double z){
		for(Player online : Bukkit.getOnlinePlayers()){
			 PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(particle, true, (float) p.getLocation().getX() + (float) x, (float) p.getLocation().getY() + (float) y,
					 (float) p.getLocation().getZ() + (float) z, (float) p.getLocation().getX(), 0, 0, 1, 1);
		        ((CraftPlayer) online).getHandle().playerConnection.sendPacket(packet);
		}
	}
	
}
