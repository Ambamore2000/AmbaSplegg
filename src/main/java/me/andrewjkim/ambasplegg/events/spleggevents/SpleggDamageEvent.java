package me.andrewjkim.ambasplegg.events.spleggevents;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class SpleggDamageEvent implements Listener {

    @EventHandler
    public void spleggDamageEvent(EntityDamageEvent e) {
        e.setCancelled(true);
    }
}
