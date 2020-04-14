package me.andrewjkim.ambasplegg.events.spleggevents;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class SpleggTntDamageEvent implements Listener {

    @EventHandler
    public void spleggTntDamageEvent(EntityDamageEvent e) {
        if (e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)) { e.setCancelled(true); }
    }
}
