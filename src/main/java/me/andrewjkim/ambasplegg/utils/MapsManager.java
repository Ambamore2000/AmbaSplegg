package me.andrewjkim.ambasplegg.utils;

import me.andrewjkim.ambasplegg.AmbaSplegg;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Objects;

public class MapsManager {

    AmbaSplegg plugin;

    private ArrayList<Map> mapList;

    public MapsManager(AmbaSplegg plugin) {
        this.plugin = plugin;
        initializeMapList();
    }

    private void initializeMapList() {
        mapList = new ArrayList<>();
        for (String map : Objects.requireNonNull(plugin.getConfig().getConfigurationSection("maps")).getKeys(false)) {
            ArrayList<Location> spawnLocations = new ArrayList<>();

            if (plugin.getConfig().contains("maps." + map + ".spawns")) {
                for (String mapIterator : Objects.requireNonNull(plugin.getConfig().getConfigurationSection("maps." + map + ".spawns")).getKeys(false)) {
                    int xCord = plugin.getConfig().getInt("maps." + map + ".spawns." + mapIterator + ".x");
                    int yCord = plugin.getConfig().getInt("maps." + map + ".spawns." + mapIterator + ".y");
                    int zCord = plugin.getConfig().getInt("maps." + map + ".spawns." + mapIterator + ".z");

                    spawnLocations.add(new Location(plugin.getWorldManager().getGameWorld(), xCord, yCord, zCord));
                }
            } else { spawnLocations.add(new Location(plugin.getWorldManager().getGameWorld(), 0, 100, 0)); }

            String mapName = plugin.getConfig().getString("maps." + map + ".name");
            mapList.add(new Map(map, mapName, spawnLocations));
        }
    }

    public ArrayList<Map> getMapList() { return mapList; }

}
