package me.andrewjkim.ambasplegg;

import me.andrewjkim.ambasplegg.events.GameChatEvent;
import me.andrewjkim.ambasplegg.events.LobbyJoinEvent;
import me.andrewjkim.ambasplegg.events.LobbyQuitEvent;
import me.andrewjkim.ambasplegg.events.spleggevents.SpleggEggEvent;
import me.andrewjkim.ambasplegg.events.spleggevents.SpleggFallEvent;
import me.andrewjkim.ambasplegg.events.spleggevents.SpleggLaunchEvent;
import me.andrewjkim.ambasplegg.events.spleggevents.SpleggLaunchHitBlockEvent;
import me.andrewjkim.ambasplegg.utils.GPlayerManager;
import me.andrewjkim.ambasplegg.utils.GameManager;

import me.andrewjkim.ambasplegg.utils.WorldManager;

import org.bukkit.plugin.java.JavaPlugin;

public class AmbaSplegg extends JavaPlugin {

    private GameManager gameManager;
    private WorldManager worldManager;
    private GPlayerManager gPlayerManager;

    @Override
    public void onEnable() {
        registerClasses();
        registerEvents();
    }

    @Override
    public void onDisable() { getWorldManager().deletePlayerData(); }

    private void registerClasses() {
        worldManager = new WorldManager(this);
        gameManager = new GameManager(this, 1, 60*10);
        gPlayerManager = new GPlayerManager(this);
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new LobbyJoinEvent(this), this);
        getServer().getPluginManager().registerEvents(new LobbyQuitEvent(this), this);
        getServer().getPluginManager().registerEvents(new GameChatEvent(this), this);
        registerSpleggEvents();
    }

    private void registerSpleggEvents() {
        getServer().getPluginManager().registerEvents(new SpleggFallEvent(this), this);
        getServer().getPluginManager().registerEvents(new SpleggLaunchEvent(), this);
        getServer().getPluginManager().registerEvents(new SpleggLaunchHitBlockEvent(), this);
        getServer().getPluginManager().registerEvents(new SpleggEggEvent(), this);
    }

    public GameManager getGameManager() { return gameManager; }
    public WorldManager getWorldManager() { return worldManager; }
    public GPlayerManager getgPlayerManager() { return gPlayerManager; }

}
