package me.andrewjkim.ambasplegg.events.spleggevents;

import me.andrewjkim.ambasplegg.AmbaSplegg;
import me.andrewjkim.ambasplegg.enums.GameStatus;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class SpleggFallEvent implements Listener {

    AmbaSplegg plugin;

    public SpleggFallEvent(AmbaSplegg plugin) { this.plugin = plugin; }

    @EventHandler
    public void spleggFallEvent(PlayerMoveEvent e) {
        if (e.getFrom() != e.getTo() && e.getTo().getY() <= 0) {
            if (plugin.getGameManager().isInGame() && plugin.getgPlayerManager().isPlaying(e.getPlayer()) && !plugin.getGameManager().isGameFinished()) {
                plugin.getgPlayerManager().setSpectator(e.getPlayer());
                plugin.getMessageManager().printEliminatedMessage(e.getPlayer().getDisplayName());
                plugin.getgPlayerManager().checkIfWinnerFound();
            } else { plugin.getgPlayerManager().teleportPlayerToCenter(e.getPlayer(), plugin.getWorldManager().getGameWorldFileName()); }
        }
    }
}
