package com.spigot.sdj.utils;

import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_11_R1.IChatBaseComponent;
import net.minecraft.server.v1_11_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_11_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_11_R1.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_11_R1.PlayerConnection;

public class Utils {
	
	public static void sendTitleToPlayer(Player p, String title, String subTitle, int fadeIn, int stay, int fadeOut) {
		PlayerConnection pl = ((CraftPlayer) p).getHandle().playerConnection;
		
		IChatBaseComponent textTitle = ChatSerializer.a("{\"text\":\"" + title.replace("&", "§") + "\"}");
		IChatBaseComponent textSubtitle = ChatSerializer.a("{\"text\":\"" + subTitle.replace("&", "§") + "\"}");
		PacketPlayOutTitle pTitle = new PacketPlayOutTitle(EnumTitleAction.TITLE, textTitle, fadeIn, stay, fadeOut);
		PacketPlayOutTitle pSubtitle = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, textSubtitle, fadeIn, stay, fadeOut);
		
		pl.sendPacket(pTitle);
		pl.sendPacket(pSubtitle);
	}
	
	public static void sendActionbarToPlayer(Player p, String text) {
		PlayerConnection pl = ((CraftPlayer) p).getHandle().playerConnection;
		
		IChatBaseComponent textActionbar = ChatSerializer.a("{\"text\":\"" + text.replace("&", "§") + "\"}");
		PacketPlayOutTitle action = new PacketPlayOutTitle(EnumTitleAction.ACTIONBAR, textActionbar);
		
		pl.sendPacket(action);
	}
	
}
