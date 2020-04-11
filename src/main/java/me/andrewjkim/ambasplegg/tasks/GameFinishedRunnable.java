package me.andrewjkim.ambasplegg.tasks;

import me.andrewjkim.ambasplegg.utils.GameManager;
import org.bukkit.Bukkit;

public class GameFinishedRunnable implements Runnable {

    GameManager gameManager;

    public GameFinishedRunnable(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        int timer = 20 - gameManager.getSecondsPassed();
        if (timer == 0) {
            //gameManager.getPlugin().getServer().shutdown();
        } else {
            Bukkit.broadcastMessage(String.valueOf(timer));
        }
        gameManager.addSecondPassed();
    }
}
