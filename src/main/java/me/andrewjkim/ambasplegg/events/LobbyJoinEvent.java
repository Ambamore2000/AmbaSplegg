package me.andrewjkim.ambasplegg.events;

import me.andrewjkim.ambasplegg.AmbaSplegg;
import me.andrewjkim.ambasplegg.enums.GameStatus;
import org.bukkit.GameMode;
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
        e.getPlayer().setDisplayName("§9" + e.getPlayer().getDisplayName());
        e.getPlayer().setGameMode(GameMode.ADVENTURE);
        e.setJoinMessage(null);
        plugin.getMessageManager().printJoinMessage(e.getPlayer().getDisplayName());
        if (plugin.getGameManager().isInLobby()) {
            plugin.getgPlayerManager().teleportPlayerToCenter(e.getPlayer());
        }
        else { plugin.getgPlayerManager().setSpectator(e.getPlayer()); }
    }

}
