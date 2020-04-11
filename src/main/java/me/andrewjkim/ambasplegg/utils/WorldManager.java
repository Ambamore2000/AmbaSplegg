package me.andrewjkim.ambasplegg.utils;

import me.andrewjkim.ambasplegg.AmbaSplegg;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class WorldManager {

    AmbaSplegg plugin;

    public WorldManager(AmbaSplegg plugin) {
        this.plugin = plugin;
    }

    public void deletePlayerData() {
        for (File playerFile : new File(Bukkit.getWorldContainer().getPath() + "/world/playerdata").listFiles()) {
            playerFile.delete();
        }
    }

    private void deleteWorld(File target) {
        if(target.exists()) {
            File files[] = target.listFiles();
            for(int i=0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteWorld(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
    }

    private void copyWorld(File source, File target){
        try {
            ArrayList<String> ignore = new ArrayList<String>(Arrays.asList("uid.dat", "session.dat"));
            if(!ignore.contains(source.getName())) {
                if(source.isDirectory()) {
                    if(!target.exists()) //TODO dlete?
                        target.mkdirs();
                    String files[] = source.list();
                    for (String file : files) {
                        File srcFile = new File(source, file);
                        File destFile = new File(target, file);
                        copyWorld(srcFile, destFile);
                    }
                } else {
                    InputStream in = new FileInputStream(source);
                    OutputStream out = new FileOutputStream(target);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = in.read(buffer)) > 0)
                        out.write(buffer, 0, length);
                    in.close();
                    out.close();
                }
            }
        } catch (IOException e) {
            Bukkit.broadcastMessage("ERROR: Failed to read files: "
                    + "\n" + source.getPath()
                    + "\n" + target.getPath());
        }
    }

    public void initializeGameWorld(String votedMap) {
        String serverPath = Bukkit.getWorldContainer().getPath();
        File sourceWorldFile = new File(serverPath + "/maps/" + votedMap);
        File gameWorldFile = new File(serverPath + "/game_world");

        try {
            deleteWorld(gameWorldFile);
            gameWorldFile = new File(serverPath + "/game_world");
        } catch (NullPointerException ex) { /* do nothing */ }

        copyWorld(sourceWorldFile, gameWorldFile);
        new WorldCreator("game_world").createWorld();

        //TODO config teleport locations
        ArrayList<Location> locations = new ArrayList<>();
        locations.add(Bukkit.getWorld("game_world").getSpawnLocation());
        teleportPlayers(locations);
    }

    private void teleportPlayers(ArrayList<Location> teleportLocations) {
        int index = 0;
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.teleport(teleportLocations.get(index));
            index++;
            if (teleportLocations.size() >= index)
                index = 0;
        }
    }

}
