package com.spigot.sdj.events;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.spigot.sdj.Main;
import com.spigot.sdj.particles.Particles;
import com.spigot.sdj.utils.Utils;

import net.minecraft.server.v1_11_R1.EnumParticle;

public class Events implements Listener {

	public static Main plugin;

	@SuppressWarnings("static-access")
	public Events(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (plugin.list.contains(p.getUniqueId())) {
			if (p.getGameMode().equals(GameMode.CREATIVE) || p.getGameMode().equals(GameMode.SPECTATOR)) {
				return;
			}
			if (p.isOnGround()) {
				if (!p.getAllowFlight()) {
					p.setAllowFlight(true);
				}
			}
		}
	}

	@EventHandler
	public void onFallDamage(EntityDamageEvent e) {
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		Player p = (Player) e.getEntity();
		if (p.getGameMode().equals(GameMode.CREATIVE) || p.getGameMode().equals(GameMode.SPECTATOR)) {
			return;
		}
		if (e.getCause().equals(DamageCause.FALL)) {
			if (!plugin.list2.contains(p.getUniqueId())) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onPlayerFlying(PlayerToggleFlightEvent e) {
		Player p = e.getPlayer();
		Location loc = p.getLocation();
		if (plugin.list.contains(p.getUniqueId())) {
			if (p.getGameMode().equals(GameMode.CREATIVE) || p.getGameMode().equals(GameMode.SPECTATOR)) {
				return;
			}
			if (!plugin.list2.contains(p.getUniqueId())) {
				e.setCancelled(true);
				p.setFlying(false);
				p.setAllowFlight(false);
				Vector v = loc.getDirection().multiply(1.2f).setY(1.2f);
				p.setVelocity(v);
				p.playSound(loc, Sound.valueOf(plugin.getConfig().getString("sound")), 4F, 1F);
				Particles packet = new Particles(EnumParticle.valueOf(plugin.getConfig().getString("particle")), loc,
						0.5f, 0.5f, 0.5f, 0.07f, 80);
				packet.sendToPlayer(p);
				plugin.list2.add(p.getUniqueId());
				if (plugin.getConfig().getBoolean("food.take")) {
					if (p.getFoodLevel() >= plugin.getConfig().getInt("food.amount")) {
						p.setFoodLevel((p.getFoodLevel() - plugin.getConfig().getInt("food.amount")));
					} else {
						p.setFoodLevel((p.getFoodLevel() - p.getFoodLevel()));
					}
				}

				new BukkitRunnable() {
					int cooldown = plugin.getConfig().getInt("cooldown");
					int cooldown2 = plugin.getConfig().getInt("cooldown");
					@Override
					public void run() {
						if (cooldown > 0) {
							Utils.sendActionbarToPlayer(p, "&cPlease wait &f" + cooldown + " &cto double-jump!");
							p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 4F, 1F);
							cooldown--;
						} else {
							Utils.sendActionbarToPlayer(p, "&aYou can double-jump now!");
							p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 3F, 1F);
							plugin.list2.remove(p.getUniqueId());
							cancel();
							plugin.getServer().getScheduler().cancelTask(cooldown);
							cooldown += cooldown2;
						}
					}
				}.runTaskTimerAsynchronously(plugin, 0L, 20L);

			} else {
				e.setCancelled(true);
				p.setFlying(false);
				p.setAllowFlight(false);
			}

		}
	}

}
