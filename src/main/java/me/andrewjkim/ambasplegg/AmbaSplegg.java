package me.andrewjkim.ambasplegg;

import me.andrewjkim.ambasplegg.commands.VoteCommand;
import me.andrewjkim.ambasplegg.events.GameChatEvent;
import me.andrewjkim.ambasplegg.events.LobbyJoinEvent;
import me.andrewjkim.ambasplegg.events.LobbyQuitEvent;
import me.andrewjkim.ambasplegg.events.spleggevents.*;
import me.andrewjkim.ambasplegg.utils.*;

import org.bukkit.plugin.java.JavaPlugin;

public class AmbaSplegg extends JavaPlugin {

    private GameManager gameManager;
    private MessageManager messageManager;
    private WorldManager worldManager;
    private MapsManager mapsManager;
    private VoteManager voteManager;
    private GPlayerManager gPlayerManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        registerClasses();
        registerEvents();
        registerCommands();

        worldManager.disableGameRules(worldManager.getMainWorld());
    }

    @Override
    public void onDisable() { getWorldManager().deletePlayerData(); }

    private void registerClasses() {
        worldManager = new WorldManager(this);
        gameManager = new GameManager(this, getConfig().getInt("game.minimum-players"), getConfig().getInt("game.duration"));
        messageManager = new MessageManager(this);
        gPlayerManager = new GPlayerManager(this);
        mapsManager = new MapsManager(this);
        voteManager = new VoteManager(this, getConfig().getInt("game.vote-amount"));
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
        getServer().getPluginManager().registerEvents(new SpleggDamageEvent(), this);
    }

    private void registerCommands() {
        getCommand("v").setExecutor(new VoteCommand(this));
    }

    public GameManager getGameManager() { return gameManager; }
    public MessageManager getMessageManager() { return messageManager; }
    public WorldManager getWorldManager() { return worldManager; }
    public GPlayerManager getgPlayerManager() { return gPlayerManager; }
    public MapsManager getMapsManager() { return mapsManager; }
    public VoteManager getVoteManager() { return voteManager; }

}
