package me.andrewjkim.ambasplegg.events;

import me.andrewjkim.ambasplegg.AmbaSplegg;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class GameChatEvent implements Listener {

    AmbaSplegg plugin;

    public GameChatEvent(AmbaSplegg plugin) { this.plugin = plugin; }

    @EventHandler
    public void gameChatEvent(AsyncPlayerChatEvent e) {
        e.setMessage("&8» &r" + e.getMessage());
    }
}
