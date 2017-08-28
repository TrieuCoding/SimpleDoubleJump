package com.spigot.sdj.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.spigot.sdj.Main;

public class Commands implements CommandExecutor {

	public static Main plugin;

	@SuppressWarnings("static-access")
	public Commands(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (args.length < 1) {
			if (sender instanceof Player) {
				sender.sendMessage("/simpledoublejump toggle");
				sender.sendMessage("/simpledoublejump reload");
				sender.sendMessage("Aliases: /sdj");
			} else {
				sender.sendMessage(plugin.cslprefix + ChatColor.WHITE + "/simpledoublejump reload");
				sender.sendMessage(plugin.cslprefix + ChatColor.WHITE + "Aliases: /sdj");
			}
			return true;
		} else if (args.length > 1) {
			if (sender instanceof Player) {
				sender.sendMessage(ChatColor.RED + "Unknow args! Please use /simpledoublejump for help!");
			} else {
				sender.sendMessage(plugin.cslprefix + ChatColor.RED + "Unknow args! Please use /simpledoublejump for help!");
			}
			return true;
		} else if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
			if (sender instanceof Player) {
				if (sender.hasPermission("sdj.reloadcmd")) {
					plugin.reloadingConfiguration();
					sender.sendMessage(plugin.getConfig().getString("prefix").replace("&", "§")
							+ plugin.getConfig().getString("reload").replace("&", "§"));
				} else {
					sender.sendMessage(plugin.getConfig().getString("prefix").replace("&", "§")
							+ plugin.getConfig().getString("no-permission").replace("&", "§"));
					return true;
				}
			} else {
				plugin.reloadingConfiguration();
				sender.sendMessage(plugin.cslprefix + ChatColor.GREEN + "Reload config!");
			}
		} else if (args.length == 1 && args[0].equalsIgnoreCase("toggle")) {
			if (sender instanceof Player) {
				if (sender.hasPermission("sdj.toggle")) {
					if (!plugin.list.contains(((Player) sender).getUniqueId())) {
						plugin.list.add(((Player) sender).getUniqueId());
						sender.sendMessage(plugin.getConfig().getString("prefix").replace("&", "§")
									+ ChatColor.GREEN + "Double-jump mode has been enabled!");
					} else {
						plugin.list.remove(((Player) sender).getUniqueId());
						((Player) sender).setAllowFlight(false);
						((Player) sender).setFlying(false);
						sender.sendMessage(plugin.getConfig().getString("prefix").replace("&", "§")
									+ ChatColor.RED + "Double-jump mode has been disabled!");
					}
				} else {
					sender.sendMessage(plugin.getConfig().getString("prefix").replace("&", "§")
							+ plugin.getConfig().getString("no-permission").replace("&", "§"));
					return true;
				}
			} else {
				sender.sendMessage(plugin.cslprefix + ChatColor.RED + "Only player can use this command!");
				return true;
			}
		} else {
			if (sender instanceof Player) {
				sender.sendMessage(ChatColor.RED + "Unknow args! Please use /simpledoublejump for help!");
			} else {
				sender.sendMessage(plugin.cslprefix + ChatColor.RED + "Unknow args! Please use /simpledoublejump for help!");
			}
			return true;
		}

		return true;
	}

}
