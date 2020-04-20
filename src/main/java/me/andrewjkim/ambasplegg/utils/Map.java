package me.andrewjkim.ambasplegg.utils;

import org.bukkit.Location;

import java.util.ArrayList;

public class Map {

    private String fileName;
    private String mapName;
    private ArrayList<Location> spawnLocations;

    public Map(String fileName, String mapName, ArrayList<Location> spawnLocations) {
        this.fileName = fileName;
        this.mapName = mapName;
        this.spawnLocations = spawnLocations;
    }

    public String getFileName() { return fileName; }
    public String getMapName() { return mapName; }
    public ArrayList<Location> getSpawnLocations() { return spawnLocations; }

}
