package me.andrewjkim.ambasplegg.runnable;


import me.andrewjkim.ambasplegg.utils.GameManager;
import org.bukkit.Bukkit;

public class LobbyPendingRunnable implements Runnable {

    private GameManager gameManager;

    public LobbyPendingRunnable(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        gameManager.addSecondPassed();
        if (Bukkit.getOnlinePlayers().size() >= gameManager.getMinRequired()) {
            gameManager.setLobbyStarting();
        } else {

            if (gameManager.getSecondsPassed() % 10 == 0) {
                //TODO Config Message
                Bukkit.broadcastMessage("");
            }

            if (gameManager.getSecondsPassed() == 19)
            gameManager.setLobbyPending();
        }
    }

}
