package me.andrewjkim.ambasplegg.utils;

import me.andrewjkim.ambasplegg.AmbaSplegg;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;

public class MessageManager {

    AmbaSplegg plugin;

    private final String chatMessage;
    private final String prefix;
    private final String joinMessage;
    private final String quitMessage;
    private final List<String> waitingMessageList;
    private final String voteMessage;
    private final String voteSelectMessage;
    private final String voteOptionMessage;
    private final String votedMessage;
    private final List<String> startMessageList;
    private final String restartMessage;
    private final List<String> thanksMessageList;

    private final String eliminatedMessage;
    private final List<String> finishMessageList;

    public MessageManager(AmbaSplegg plugin) { this.plugin = plugin;
        chatMessage = getString("messages.chat");
        prefix = getString("messages.prefix");
        joinMessage = getString("messages.join");
        quitMessage = getString("messages.quit");
        waitingMessageList = getStringList("messages.waiting");
        voteMessage = getString("messages.vote");
        voteSelectMessage = getString("messages.voteselect");
        voteOptionMessage = getString("messages.voteoption");
        votedMessage = getString("messages.voted");
        startMessageList = getStringList("messages.start");
        restartMessage = getString("messages.restart");
        thanksMessageList = getStringList("messages.thanks");

        eliminatedMessage = getString("splegg-messages.eliminated"); //TODO Separate splegg messages.
        finishMessageList = getStringList("splegg-messages.finish");
    }

    private String getString(String path) { return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString(path))); }
    private List<String> getStringList(String path) {
        List<String> stringList = plugin.getConfig().getStringList(path);
        for (int x = 0; x < stringList.size(); x++) {
            stringList.set(x, ChatColor.translateAlternateColorCodes('&', stringList.get(x)));
        }
        return stringList;
    }

    private String replacePlaceholders(String message, String toReplace, String toString) {
        return message.replace(toReplace, toString);
    }

    private void printMessage(final String message) { Bukkit.broadcastMessage(prefix + message); }
    private void printMessage(Player player, final String message) { player.sendMessage(prefix + message); }
    private void printMessageList(final List<String> messageList) {
        for (String message : messageList) {
            Bukkit.broadcastMessage(StringUtils.center(message, 52));
        }
    }

    public String getChatMessageFormat(String message) { return replacePlaceholders(chatMessage, "%message%", message); }

    public void printJoinMessage(String playerJoinName) { printMessage(replacePlaceholders(joinMessage, "%player%", playerJoinName)); }
    public void printQuitMessage(String playerQuitName) { printMessage(replacePlaceholders(quitMessage, "%player%", playerQuitName)); }
    
    public void printWaitingMessageList(int amountWaiting, int amountNeeded, int timer) {
        for (String stringToPrint : waitingMessageList) {
            stringToPrint = replacePlaceholders(stringToPrint, "%amountWaiting%", String.valueOf(amountWaiting));
            if (amountNeeded < 0)
                stringToPrint = replacePlaceholders(stringToPrint, "%amountNeeded%", "0");
            else
                stringToPrint = replacePlaceholders(stringToPrint, "%amountNeeded%", String.valueOf(amountNeeded));
            if (timer == -1)
                stringToPrint = replacePlaceholders(stringToPrint, "%timer%", "N/a");
            else
                stringToPrint = replacePlaceholders(stringToPrint, "%timer%", String.valueOf(timer));
            printMessage(stringToPrint);
        }
    }

    public void printVoteMessage() { printMessage(voteMessage); printVoteOptionsMessageList(); }
    public void printVoteSelectMessage(Player player, Map map) {
        String stringToPrint = voteSelectMessage;
        stringToPrint = replacePlaceholders(stringToPrint, "%map%", map.getMapName());
        stringToPrint = replacePlaceholders(stringToPrint, "%amountVotes%", String.valueOf(plugin.getVoteManager().getVoteCollection().get(map).size()));
        printMessage(player, stringToPrint);
    }
    public void printVoteMessage(Player player) { printMessage(player, voteMessage); printVoteOptionsMessageList(player); }
    private void printVoteOptionsMessageList() {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            printVoteOptionsMessageList(player);
        }
    }
    private void printVoteOptionsMessageList(Player player) {
        Map votedMap = plugin.getVoteManager().getVotedMap();
        int voteId = 1;
        for (Map map : plugin.getVoteManager().getVoteCollection().keySet()) {
            String stringToPrint = voteOptionMessage;

            int amountVotes = plugin.getVoteManager().getVoteCollection().get(map).size();

            stringToPrint = replacePlaceholders(stringToPrint, "%#%", String.valueOf(voteId++));
            stringToPrint = replacePlaceholders(stringToPrint, "%map%", map.getMapName());
            stringToPrint = replacePlaceholders(stringToPrint, "%amountVotes%", String.valueOf(amountVotes));

            if (map.getMapName().equalsIgnoreCase(votedMap.getMapName()) && amountVotes > 0) {
                stringToPrint = stringToPrint.replace("§7", "§a");
            }

            printMessage(player, stringToPrint);
        }
    }

    public void printVotedMessage() { printMessage(replacePlaceholders(votedMessage, "%mapVoted%", plugin.getVoteManager().getVotedMap().getMapName())); }

    public void printStartMessageList() { printMessageList(startMessageList); }
    public void printRestartMessage() { printMessage(restartMessage); }
    public void printThanksMessageList() { printMessageList(thanksMessageList); }

    public void printEliminatedMessage(String eliminatedPlayer) { printMessage(replacePlaceholders(eliminatedMessage, "%player%", eliminatedPlayer)); }
    public void printFinishMessageList(String winner) {
        for (String stringToPrint : finishMessageList) {
            stringToPrint = replacePlaceholders(stringToPrint, "%winner%", winner);
            printMessage(stringToPrint);
        }
    }

}
