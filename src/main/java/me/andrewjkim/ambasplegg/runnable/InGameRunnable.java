package me.andrewjkim.ambasplegg.runnable;

import me.andrewjkim.ambasplegg.utils.GameManager;
import org.bukkit.Bukkit;

public class InGameRunnable implements Runnable {

    GameManager gameManager;

    public InGameRunnable(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        int timer = gameManager.getGameDuration() - gameManager.getSecondsPassed();
        if (timer == 0) {
            gameManager.setInGame();
        } else {
            Bukkit.broadcastMessage(String.valueOf(timer));
        }
        gameManager.addSecondPassed();
    }
}
