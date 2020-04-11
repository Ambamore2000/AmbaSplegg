package me.andrewjkim.ambasplegg.events.spleggevents;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEggThrowEvent;

public class SpleggEggEvent implements Listener {

    @EventHandler
    public void spleggEggEvent(PlayerEggThrowEvent e) { e.setHatching(false); }
}
