package me.andrewjkim.ambasplegg.events;

import me.andrewjkim.ambasplegg.AmbaSplegg;
import me.andrewjkim.ambasplegg.enums.GameStatus;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class LobbyJoinEvent implements Listener {

    AmbaSplegg plugin;

    public LobbyJoinEvent(AmbaSplegg plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void lobbyJoinEvent(PlayerJoinEvent e) {
        if (plugin.getGameManager().getGameStatus().toString().contains("LOBBY")) {
            e.getPlayer().teleport(new Location(Bukkit.getWorld("game_world"), 0, 100, 0));
        }
    }

}
