package me.andrewjkim.ambasplegg.utils;

import me.andrewjkim.ambasplegg.AmbaSplegg;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class GPlayerManager {

    private ArrayList<String> gPlayerList;
    private AmbaSplegg plugin;

    public GPlayerManager(AmbaSplegg plugin) { gPlayerList = new ArrayList<>(); this.plugin = plugin;}

    public void initializegPlayerList() { for (Player p : Bukkit.getOnlinePlayers()) { gPlayerList.add(p.getUniqueId().toString()); }
    if (Bukkit.getOnlinePlayers().size() == 1)
        gPlayerList.add(gPlayerList.get(0));
    }

    public void checkIfWinnerFound() { if (gPlayerList.size() == 1) { plugin.getGameManager().setGameFinished();} }
    public Player getWinner() { return Bukkit.getPlayer(UUID.fromString(gPlayerList.get(0))); }
    public boolean isPlaying(Player player) { return gPlayerList.contains(player.getUniqueId().toString()); }

    public void setSpectator(Player spectator) {
        removePlayer(spectator);
        spectator.setGameMode(GameMode.ADVENTURE);
        spectator.setAllowFlight(true);
        spectator.setFlying(true);
        for (Player others : Bukkit.getServer().getOnlinePlayers()) {
            others.hidePlayer(plugin, spectator);
        }
        teleportPlayerToCenter(spectator);
    }
    public void teleportPlayerToCenter(Player player) {
        Location centerLocation = player.getWorld().getHighestBlockAt(0, 0).getLocation();
        centerLocation.setX(0.5);
        centerLocation.setY(centerLocation.getY() + 1);
        centerLocation.setZ(0.5);
        player.teleport(centerLocation);
    }

    public void removePlayer(Player player) { gPlayerList.remove(player.getUniqueId().toString()); }

}
