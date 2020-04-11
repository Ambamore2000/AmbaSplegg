package me.andrewjkim.ambasplegg.events.spleggevents;

import org.bukkit.Material;
import org.bukkit.block.data.type.TNT;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class SpleggLaunchHitBlockEvent implements Listener {

    @EventHandler
    public void spleggLaunchHitBlockEvent(ProjectileHitEvent e) {
        if (e.getHitBlock() != null && e.getEntity().getType().equals(EntityType.EGG)) {
            if (e.getHitBlock().getType().equals(Material.TNT)) {
                e.getHitBlock().setType(Material.AIR);
                e.getHitBlock().getWorld().createExplosion(e.getHitBlock().getLocation(), 3F, false);
            }
            e.getHitBlock().setType(Material.AIR);
        }
    }
}
