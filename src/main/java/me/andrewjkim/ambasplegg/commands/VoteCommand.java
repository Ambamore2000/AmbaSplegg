package me.andrewjkim.ambasplegg.commands;

import me.andrewjkim.ambasplegg.AmbaSplegg;
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
        if (args.length == 1) {
        }
        plugin.getMessageManager().printVoteMessage(player);
        return false;
    }

}
