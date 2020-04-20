package me.andrewjkim.ambasplegg.utils;

import me.andrewjkim.ambasplegg.AmbaSplegg;
import org.bukkit.entity.Player;

import java.util.*;

public class VoteManager {

    //Finish MapsManager

    AmbaSplegg plugin;

    private HashMap<Map, ArrayList<String>> voteCollection;

    public VoteManager(AmbaSplegg plugin, int amountOfOptions) {
        this.plugin = plugin;
        initializeVoteOptions(amountOfOptions);
    }

    public void initializeVoteOptions(int amountOfOptions) {
        Random rand = new Random();

        voteCollection = new HashMap<>();

        while (plugin.getMapsManager().getMapList().size() > amountOfOptions)
            plugin.getMapsManager().getMapList().remove(rand.nextInt(plugin.getMapsManager().getMapList().size()));

        for (Map map : plugin.getMapsManager().getMapList()) { voteCollection.put(map, new ArrayList<>()); }
    }

    public Map getMapByIndex(int index) { return (Map) voteCollection.keySet().toArray()[index-1]; }

    public boolean isValidIndex(int userIndexInput) { return voteCollection.size() >= userIndexInput && userIndexInput > 0;}

    public void playerSetVote(Player player, int userIndexInput) {
        for (Map map : voteCollection.keySet()) { voteCollection.get(map).remove(player.getUniqueId().toString()); }
        Map votedMap = getMapByIndex(userIndexInput);
        ArrayList<String> votedMapPlayers = voteCollection.get(votedMap);
        votedMapPlayers.add(player.getUniqueId().toString());
        voteCollection.put(getMapByIndex(userIndexInput), votedMapPlayers);
    }

    public Map getVotedMap() {
        Map votedMap = (Map) voteCollection.keySet().toArray()[new Random().nextInt(voteCollection.keySet().size())];
        int amountOfVotes = 0;
        for (Map map : voteCollection.keySet()) {
            if (voteCollection.get(map).size() > amountOfVotes) {
                votedMap = map;
                amountOfVotes = voteCollection.get(map).size();
            }
        }
        return votedMap;
    }

    public HashMap<Map, ArrayList<String>> getVoteCollection() { return voteCollection; }

}
