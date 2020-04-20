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
        e.setQuitMessage(null);
        plugin.getMessageManager().printQuitMessage(e.getPlayer().getDisplayName());
        plugin.getgPlayerManager().removePlayer(e.getPlayer());
        if (plugin.getGameManager().isInLobby()) {
            if (plugin.getServer().getOnlinePlayers().size() < plugin.getGameManager().getMinRequired())
                plugin.getGameManager().setLobbyPending();
        } else if (plugin.getGameManager().isInGame()) {
            plugin.getgPlayerManager().checkIfWinnerFound();
        }
    }
}
