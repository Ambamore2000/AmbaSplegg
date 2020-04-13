package me.andrewjkim.ambasplegg.tasks;

import me.andrewjkim.ambasplegg.utils.GameManager;
import org.bukkit.Bukkit;

public class VoteMessageRunnable implements Runnable {

    private GameManager gameManager;

    public VoteMessageRunnable(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        //TODO Change 16 to %20?
        if (gameManager.getSecondsPassed() == 16) gameManager.getPlugin().getMessageManager().printVoteMessage();
    }
}
