package me.andrewjkim.ambasplegg.utils;

import me.andrewjkim.ambasplegg.AmbaSplegg;
import org.bukkit.Bukkit;

public class ConfigManager {

    AmbaSplegg plugin;

    public ConfigManager(AmbaSplegg plugin) {
        this.plugin = plugin;
    }

    public String getString(String path) {
        return plugin.getConfig().getString(path);
    }
}
