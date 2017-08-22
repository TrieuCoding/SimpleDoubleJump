package com.spigot.sdj.particles;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_11_R1.EnumParticle;
import net.minecraft.server.v1_11_R1.PacketPlayOutWorldParticles;

public class Particles {

	public PacketPlayOutWorldParticles packet;

	public Particles(EnumParticle particle, Location loc, float xOffset, float yOffset, float zOffset, float speed,
			int count) {
		float x = (float) loc.getX();
		float y = (float) loc.getY();
		float z = (float) loc.getZ();
		PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(particle, true, x, y, z, xOffset, yOffset,
				zOffset, speed, count, null);
		this.packet = packet;
	}
	
	public void sendToPlayer(Player p) { 
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}
	
}
