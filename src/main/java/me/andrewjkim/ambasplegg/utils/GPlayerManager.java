package me.andrewjkim.ambasplegg.utils;

import me.andrewjkim.ambasplegg.AmbaSplegg;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class GPlayerManager {

    private ArrayList<String> gPlayerList;
    private AmbaSplegg plugin;

    public GPlayerManager(AmbaSplegg plugin) { gPlayerList = new ArrayList<>(); this.plugin = plugin;}

    public void initializegPlayerList() { for (Player p : plugin.getServer().getOnlinePlayers()) { gPlayerList.add(p.getUniqueId().toString()); }
    if (plugin.getServer().getOnlinePlayers().size() == 1)
        gPlayerList.add(gPlayerList.get(0));
    }

    public void checkIfWinnerFound() { if (gPlayerList.size() == 1) { plugin.getGameManager().setGameFinished();} }
    public Player getWinner() { return plugin.getServer().getPlayer(UUID.fromString(gPlayerList.get(0))); }
    public boolean isPlaying(Player player) { return gPlayerList.contains(player.getUniqueId().toString()); }

    public void setSpectator(Player spectator) {
        teleportPlayer(spectator);
        removePlayer(spectator);
        spectator.setGameMode(GameMode.ADVENTURE);
        spectator.setAllowFlight(true);
        spectator.setFlying(true);
        for (Player others : plugin.getServer().getOnlinePlayers()) { others.hidePlayer(plugin, spectator); }
    }

    public void teleportAllPlayers() {
        ArrayList<Location> locations = plugin.getVoteManager().getVotedMap().getSpawnLocations();
        int index = 0;
        for (Player p : plugin.getServer().getOnlinePlayers()) {
            Location centerLocation = plugin.getWorldManager().getGameWorld().getHighestBlockAt(locations.get(index).getBlockX(), locations.get(index).getBlockZ()).getLocation();
            centerLocation.setX(locations.get(index).getBlockX() + 0.5);
            centerLocation.setY(centerLocation.getY() + 1);
            centerLocation.setZ(locations.get(index).getBlockZ() + 0.5);
            p.teleport(centerLocation);
            if (locations.size() >= ++index)
                index = 1;
        }
    }

    public void teleportPlayer(Player player) {
        ArrayList<Location> locations = plugin.getVoteManager().getVotedMap().getSpawnLocations();
        int index = new Random().nextInt(locations.size());
        Location centerLocation = plugin.getWorldManager().getGameWorld().getHighestBlockAt(locations.get(index).getBlockX(), locations.get(index).getBlockZ()).getLocation();
        centerLocation.setX(locations.get(index).getBlockX() + 0.5);
        centerLocation.setY(centerLocation.getY() + 1);
        centerLocation.setZ(locations.get(index).getBlockZ() + 0.5);
        player.teleport(centerLocation);
    }

    public void removePlayer(Player player) { gPlayerList.remove(player.getUniqueId().toString()); }

}
