package me.andrewjkim.ambasplegg.utils;

import me.andrewjkim.ambasplegg.AmbaSplegg;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class WorldManager {

    AmbaSplegg plugin;

    final String mainWorldFileName = "world";
    final String gameWorldFileName = "game_world";

    final String mapsWorldFileName = "maps";

    public WorldManager(AmbaSplegg plugin) {
        this.plugin = plugin;
    }

    public void deletePlayerData() {
        for (File playerFile : Objects.requireNonNull(new File(plugin.getServer().getWorldContainer().getPath() + "/" + mainWorldFileName + "/playerdata").listFiles())) {
            playerFile.delete();
        }
    }

    private void deleteWorld(File target) {
        if(target.exists()) {
            File[] files = target.listFiles();
            for (int i = 0; i < Objects.requireNonNull(files).length; i++) {
                if (files[i].isDirectory()) { deleteWorld(files[i]); }
                else { files[i].delete(); }
            }
        }
    }
    private void copyWorld(File source, File target) {
        try {
            ArrayList<String> ignore = new ArrayList<>(Arrays.asList("uid.dat", "session.dat"));
            if (!ignore.contains(source.getName())) {
                if (source.isDirectory()) {
                    if (!target.exists())
                        target.mkdirs();
                    for (String file : Objects.requireNonNull(source.list())) {
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
            Bukkit.shutdown();
        }
    }

    public void initializeGameWorld(String votedMap) {
        String serverPath = plugin.getServer().getWorldContainer().getPath();

        File sourceWorldFile = new File(serverPath + "/" + mapsWorldFileName + "/" + votedMap);
        File gameWorldFile = new File(serverPath + "/" + gameWorldFileName);

        deleteWorld(gameWorldFile);
        gameWorldFile = new File(serverPath + "/" + gameWorldFileName);

        copyWorld(sourceWorldFile, gameWorldFile);
        new WorldCreator(gameWorldFileName).createWorld();

        disableGameRules(getGameWorld());

        plugin.getgPlayerManager().teleportAllPlayers();
    }

    public World getMainWorld() { return plugin.getServer().getWorld(mainWorldFileName); }
    public World getGameWorld() { return plugin.getServer().getWorld(gameWorldFileName); }
    public String getMainWorldFileName() { return mainWorldFileName; }
    public String getGameWorldFileName() { return gameWorldFileName; }

    public void disableGameRules(World world) {
        world.setTime(6000);
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, Boolean.FALSE);
        world.setGameRule(GameRule.DO_ENTITY_DROPS, Boolean.FALSE);
        world.setGameRule(GameRule.DO_MOB_SPAWNING, Boolean.FALSE);
        world.setGameRule(GameRule.DO_PATROL_SPAWNING, Boolean.FALSE);
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, Boolean.FALSE);
        world.setGameRule(GameRule.DO_TILE_DROPS, Boolean.FALSE);
        world.setGameRule(GameRule.FALL_DAMAGE, Boolean.FALSE);
        world.setGameRule(GameRule.NATURAL_REGENERATION, Boolean.FALSE);
    }

}
