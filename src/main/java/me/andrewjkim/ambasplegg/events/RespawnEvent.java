package me.andrewjkim.ambasplegg.events;

import me.andrewjkim.ambasplegg.AmbaSplegg;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnEvent implements Listener {

    //TODO Get rid of constructor??

    AmbaSplegg plugin;

    public RespawnEvent(AmbaSplegg plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void playerRespawnEvent(PlayerRespawnEvent e) {
        //e.getPlayer().teleport(new Location(Bukkit.getWorld("game_world"), 0, 100, 0));
    }
}
