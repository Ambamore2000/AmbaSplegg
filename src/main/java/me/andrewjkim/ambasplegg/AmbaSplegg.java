package me.andrewjkim.ambasplegg;

import me.andrewjkim.ambasplegg.events.LobbyQuitEvent;
import me.andrewjkim.ambasplegg.events.RespawnEvent;
import me.andrewjkim.ambasplegg.utils.GameManager;

import me.andrewjkim.ambasplegg.utils.WorldManager;

import org.bukkit.plugin.java.JavaPlugin;

public class AmbaSplegg extends JavaPlugin {

    private GameManager gameManager;
    private WorldManager worldManager;

    @Override
    public void onEnable() {
        registerClasses();
        registerEvents();
        registerCommands();
    }

    private void registerClasses() {
        worldManager = new WorldManager(this);
        gameManager = new GameManager(this, 1, 60*10);
    }


    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new LobbyQuitEvent(this), this);
        getServer().getPluginManager().registerEvents(new RespawnEvent(this), this);
    }


    private void registerCommands() {

    }

    public GameManager getGameManager() { return gameManager; }
    public WorldManager getWorldManager() { return worldManager; }

    @Override
    public void onDisable() {

    }

}
