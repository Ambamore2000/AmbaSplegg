package me.andrewjkim.ambasplegg.runnable;

import me.andrewjkim.ambasplegg.utils.GameManager;
import org.bukkit.Bukkit;

public class GameStartingRunnable implements Runnable {

    private GameManager gameManager;

    public GameStartingRunnable(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        int timer = 11 - gameManager.getSecondsPassed();
        //TODO cool countdown 5 4 3 2 1 go!
        if (timer == 0) {
            gameManager.setInGame();
        } else {
            Bukkit.broadcastMessage(String.valueOf(timer));
        }
        gameManager.addSecondPassed();
    }

}
