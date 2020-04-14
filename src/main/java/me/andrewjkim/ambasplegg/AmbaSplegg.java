package me.andrewjkim.ambasplegg;

import me.andrewjkim.ambasplegg.events.GameChatEvent;
import me.andrewjkim.ambasplegg.events.LobbyJoinEvent;
import me.andrewjkim.ambasplegg.events.LobbyQuitEvent;
import me.andrewjkim.ambasplegg.events.spleggevents.*;
import me.andrewjkim.ambasplegg.utils.GPlayerManager;
import me.andrewjkim.ambasplegg.utils.GameManager;

import me.andrewjkim.ambasplegg.utils.MessageManager;
import me.andrewjkim.ambasplegg.utils.WorldManager;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class AmbaSplegg extends JavaPlugin {

    private GameManager gameManager;
    private MessageManager messageManager;
    private WorldManager worldManager;
    private GPlayerManager gPlayerManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        registerClasses();
        registerEvents();

        worldManager.disableGameRules(Bukkit.getWorld("world"));
    }

    @Override
    public void onDisable() { getWorldManager().deletePlayerData(); }

    private void registerClasses() {
        worldManager = new WorldManager(this);
        gameManager = new GameManager(this, 1, 60*10);
        messageManager = new MessageManager(this);
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
        getServer().getPluginManager().registerEvents(new SpleggTntDamageEvent(), this);
    }

    public GameManager getGameManager() { return gameManager; }
    public MessageManager getMessageManager() { return messageManager; }
    public WorldManager getWorldManager() { return worldManager; }
    public GPlayerManager getgPlayerManager() { return gPlayerManager; }

}
