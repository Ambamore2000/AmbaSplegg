package me.andrewjkim.ambasplegg.runnable;


import me.andrewjkim.ambasplegg.utils.GameManager;
import org.bukkit.Bukkit;

public class LobbyPendingRunnable implements Runnable {

    private GameManager gameManager;

    public LobbyPendingRunnable(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    //TODO change 15 and 31 to 30 and 61.

    @Override
    public void run() {
        if (Bukkit.getOnlinePlayers().size() >= gameManager.getMinRequired()) {
            gameManager.setLobbyStarting();
        } else {
            if (gameManager.getSecondsPassed() % 15 == 0) {
                //TODO Config Message

                int playerNeededCount = gameManager.getMinRequired() - Bukkit.getOnlinePlayers().size();

                Bukkit.broadcastMessage("Waiting for " + playerNeededCount + " more players.");
            }
            if (gameManager.getSecondsPassed() == 31)
                gameManager.setLobbyPending();
        }
        gameManager.addSecondPassed();
    }

}
