package me.andrewjkim.ambasplegg.commands;

import me.andrewjkim.ambasplegg.AmbaSplegg;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VoteCommand implements CommandExecutor {

    AmbaSplegg plugin;

    public VoteCommand(AmbaSplegg plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if (args.length == 1 && args[0].matches("^[1-9]\\d*$")) {
            int index = Integer.parseInt(args[0]);
            if (plugin.getVoteManager().isValidIndex(index)) {
                plugin.getVoteManager().playerSetVote(player, index);
                plugin.getMessageManager().printVoteSelectMessage(player, plugin.getVoteManager().getMapByIndex(index));
                return true;
            }
        }
        plugin.getMessageManager().printVoteMessage(player);
        return false;
    }

}
