package me.andrewjkim.ambasplegg.utils;

import me.andrewjkim.ambasplegg.AmbaSplegg;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MessageManager {

    AmbaSplegg plugin;

    private final String prefix;
    private final String joinMessage;
    private final String quitMessage;
    private final List<String> waitingMessageList;
    private final String voteMessage;
    private final String voteOptionMessage;
    private final String votedMessage;
    private final List<String> startMessageList;
    private final String restartMessage;
    private final List<String> thanksMessageList;

    private final String eliminatedMessage;
    private final List<String> finishMessageList;

    public MessageManager(AmbaSplegg plugin) { this.plugin = plugin;
        prefix = getString("messages.prefix");
        joinMessage = getString("messages.join");
        quitMessage = getString("messages.quit");
        waitingMessageList = getStringList("messages.waiting");
        voteMessage = getString("messages.vote");
        voteOptionMessage = getString("messages.voteoption");
        votedMessage = getString("messages.voted");
        startMessageList = getStringList("messages.start");
        restartMessage = getString("messages.restart");
        thanksMessageList = getStringList("messages.thanks");

        eliminatedMessage = getString("splegg-messages.eliminated");//TODO Separate splegg-messages
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

    private String replacePlaceholders(String message, String toReplace, String toString) { return message.replace(toReplace, toString); }
    private List<String> replaceListPlaceholders(List<String> messageList, Map<String, String> stringsToReplace) {
        for (int x = 0; x < messageList.size(); x++) {
            for (String toReplace : stringsToReplace.keySet()) {
                messageList.set(x, replacePlaceholders(messageList.get(x), toReplace, stringsToReplace.get(toReplace)));
            }
        }
        return messageList;
    }
    private void printMessage(final String message) { Bukkit.broadcastMessage(prefix + message); }
    private void printMessageList(final List<String> messageList) { for (String string : messageList) { printMessage(string); } }

    final public void printJoinMessage(String playerJoinName) { printMessage(replacePlaceholders(joinMessage, "%player%", playerJoinName)); }
    final public void printQuitMessage(String playerQuitName) { printMessage(replacePlaceholders(quitMessage, "%player%", playerQuitName)); }
    
    final public void printWaitingMessageList(int amountWaiting, int amountNeeded, int timer) {
        Map<String, String> stringsToReplace = new HashMap<>();
        stringsToReplace.put("%amountWaiting%", String.valueOf(amountWaiting));
        stringsToReplace.put("%amountNeeded%", String.valueOf(amountNeeded));
        stringsToReplace.put("%timer%", String.valueOf(timer));
        printMessageList(replaceListPlaceholders(waitingMessageList, stringsToReplace));
    }

    final public void printVoteMessage() { printMessage(voteMessage); }
    final public void printVoteOptionsMessageList() {
        //TODO Make Vote Manager (get vote map)
        Map<String, Integer> voteOptions = new HashMap<>();
        int voteId = 1;
        for (String map : voteOptions.keySet()) {
            String stringToPrint = voteMessage;
            stringToPrint = replacePlaceholders(stringToPrint, "%#%", String.valueOf( voteId++));
            stringToPrint = replacePlaceholders(stringToPrint, "%map%", map);
            stringToPrint = replacePlaceholders(stringToPrint, "%#%", String.valueOf(voteOptions.get(map)));
        }
    }
    final public void printVotedMessage(String mapVoted) { printMessage(replacePlaceholders(votedMessage, "%mapVoted%", mapVoted)); }

    final public void printStartMessageList() { printMessageList(startMessageList); }
    final public void printRestartMessage() { printMessage(restartMessage); }
    final public void printThanksMessageList() { printMessageList(thanksMessageList); }

    final public void printEliminatedMessage() { printMessage(eliminatedMessage); }
    final public void printFinishMessageList() { printMessageList(finishMessageList); }

}
