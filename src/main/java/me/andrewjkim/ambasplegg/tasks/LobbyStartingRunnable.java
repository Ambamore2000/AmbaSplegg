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
        if (Bukkit.getOnlinePlayers().size() < gameManager.getMinRequired()) { gameManager.setLobbyPending(); }
        int timer = gameManager.getTimer();


        if (timer == 15 || timer == 10 || (timer <= 5 && timer > 0)) {
            Bukkit.broadcastMessage("Game starting in " + timer + " seconds.");
        } else if (timer == 0) {
            gameManager.getPlugin().getWorldManager().initializeGameWorld("bigtrees");
            gameManager.setGameStarting();
        }


        gameManager.addSecondPassed();
    }

}
