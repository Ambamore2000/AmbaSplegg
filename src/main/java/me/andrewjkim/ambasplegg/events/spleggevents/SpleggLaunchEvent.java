package me.andrewjkim.ambasplegg.events.spleggevents;

import org.bukkit.Material;
import org.bukkit.entity.Egg;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class SpleggLaunchEvent implements Listener {

    @EventHandler
    public void spleggLaunchEvent(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            if (e.getPlayer().getInventory().getItemInMainHand().getType().toString().contains("SHOVEL")) {
                e.getPlayer().launchProjectile(Egg.class);
                ItemStack playerSpleggItem = e.getPlayer().getInventory().getItemInMainHand().clone();
                e.getPlayer().getInventory().getItemInMainHand().setType(Material.AIR);
                e.getPlayer().getInventory().setItemInMainHand(playerSpleggItem);
            }
        }
    }
}
