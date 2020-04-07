package me.andrewjkim.ambasplegg.events;

import me.andrewjkim.ambasplegg.AmbaSplegg;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class LobbyQuitEvent implements Listener {

    AmbaSplegg plugin;

    public LobbyQuitEvent(AmbaSplegg plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void lobbyQuitEvent(PlayerQuitEvent e) {
        if (plugin.getGameManager().getGameStatus().toString().contains("LOBBY")) {
            if (Bukkit.getOnlinePlayers().size() < plugin.getGameManager().getMinRequired())
                plugin.getGameManager().setLobbyPending();
        }
    }
}
