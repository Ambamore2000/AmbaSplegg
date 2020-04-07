package me.andrewjkim.ambasplegg.tasks;

import me.andrewjkim.ambasplegg.utils.GameManager;
import org.bukkit.Bukkit;

public class LobbyStartingRunnable implements Runnable {

    private GameManager gameManager;

    public LobbyStartingRunnable(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        if (Bukkit.getOnlinePlayers().size() >= gameManager.getMinRequired()) {
            //TODO config timer times? Probably not.
            int timer = 16 - gameManager.getSecondsPassed();
            if (timer == 0) {
                gameManager.setGameStarting();
            } else if (timer == 15 || timer == 10 || timer <= 5) {
                Bukkit.broadcastMessage("Game starting in " + timer + " seconds.");
            }
        } else {
            gameManager.setLobbyPending();
        }
        gameManager.addSecondPassed();
    }

}