package com.spigot.sdj.versions;

import org.bukkit.Bukkit;

public class ServerVersion {

    public static boolean isMC111(){
        return Bukkit.getBukkitVersion().contains("1.11");
    }

}
