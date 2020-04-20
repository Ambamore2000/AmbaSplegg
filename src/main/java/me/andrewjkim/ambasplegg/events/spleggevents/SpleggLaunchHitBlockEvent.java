package me.andrewjkim.ambasplegg.events.spleggevents;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.TNT;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.BlockIterator;

public class SpleggLaunchHitBlockEvent implements Listener {

    @EventHandler
    public void spleggLaunchHitBlockEvent(ProjectileHitEvent e) {
        BlockIterator iterator = new BlockIterator(e.getEntity().getWorld(), e.getEntity().getLocation().toVector(), e.getEntity().getVelocity().normalize(), 0.0D, 4);
        Block hitBlock = null;

        while (iterator.hasNext()) {
            hitBlock = iterator.next();

            if (!hitBlock.getType().isAir()) {
                break;
            }
        }

        boolean isTNTBlock = hitBlock.getType().equals(Material.TNT);

        hitBlock.setType(Material.AIR);
        if (isTNTBlock) {
            TNTPrimed tntPrimed = e.getHitBlock().getWorld().spawn(e.getHitBlock().getLocation(), TNTPrimed.class);
            tntPrimed.setFuseTicks(0);
        }
    }
}
