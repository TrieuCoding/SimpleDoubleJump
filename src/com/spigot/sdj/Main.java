package com.spigot.sdj;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.spigot.sdj.commands.Commands;
import com.spigot.sdj.events.Events;
import com.spigot.updater.Updater;

public class Main extends JavaPlugin {

	private static Plugin plugin;
	
	public static Plugin getPlugin() { return plugin; }
	
	public static Main instance;
	
	public String cslprefix = "[SimpleDoubleJump] ";
	
	public List<UUID> list = new ArrayList<>();
	public List<UUID> list2 = new ArrayList<>();
	
	String i = getServer().getClass().getPackage().getName();
	String version = i.substring(i.lastIndexOf(".") + 1);
	
	@SuppressWarnings("unused")
	private Boolean outdated;
	
	@Override
	public void onEnable() {
		ConsoleCommandSender console = getServer().getConsoleSender();
		if (version.equalsIgnoreCase("v1_11_R1")) {
			plugin = this;
			instance = this;
			Events.plugin = this;
			Commands.plugin = this;
			Updater.plugin = this;
			registerListeners();
			registerCommands();
			loadingConfiguration();
			console.sendMessage(cslprefix + "Server version: " + version);
			console.sendMessage(cslprefix + "Plugin has been enabled!");
			console.sendMessage(cslprefix + "If you have any bug or error, please upload your bug to my project!");
			if (plugin.getConfig().getBoolean("check-update")) {
				Updater.print();
			}
		} else {
			console.sendMessage(cslprefix + "Server version: " + plugin.getServer().getVersion());
			console.sendMessage(cslprefix + "This plugin isn't support this version! Please use 1.11 or 1.11.2!");
			plugin.getServer().getPluginManager().disablePlugin(this);
		}
	}
	
	private void registerListeners() { getServer().getPluginManager().registerEvents(new Events(this), this); }
	
	private void registerCommands() { getCommand("simpledoublejump").setExecutor(new Commands(this)); }
	
	private void loadingConfiguration() {
		String prefix = "prefix";
		plugin.getConfig().addDefault(prefix, "&7[&cSimple Doublejump&7]&r ");
		
		String update = "check-update";
		plugin.getConfig().addDefault(update, Boolean.valueOf(true));
		
		String noperm = "no-permission";
		plugin.getConfig().addDefault(noperm, "&cYou dont have permission to do this!");
		String reload = "reload";
		plugin.getConfig().addDefault(reload, "&aReload config!");
		
		String particle = "particle";
		plugin.getConfig().addDefault(particle, "FIREWORKS_SPARK");
		
		String sound = "sound";
		plugin.getConfig().addDefault(sound, "ENTITY_WITHER_SHOOT");
		
		String cooldown = "cooldown";
		plugin.getConfig().addDefault(cooldown, "3");
		
		plugin.getConfig().options().copyDefaults(true);
		plugin.saveDefaultConfig();
	}
	
	public void reloadingConfiguration() {
		super.reloadConfig();
	}
	
}
