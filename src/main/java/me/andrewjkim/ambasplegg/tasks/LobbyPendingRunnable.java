package me.andrewjkim.ambasplegg.tasks;


import me.andrewjkim.ambasplegg.utils.GameManager;
import org.bukkit.Bukkit;

public class LobbyPendingRunnable implements Runnable {

    private GameManager gameManager;

    public LobbyPendingRunnable(GameManager gameManager) {
        this.gameManager = gameManager;
    }


    @Override
    public void run() {
        if (Bukkit.getOnlinePlayers().size() >= gameManager.getMinRequired()) {
            gameManager.setLobbyStarting();
        } else {
            //TODO change 11 and 21 to 30 and 61.
            if (gameManager.getSecondsPassed() == 11) {
                int amountWaiting = Bukkit.getOnlinePlayers().size();
                int playerNeededCount = gameManager.getMinRequired() - amountWaiting;
                gameManager.getPlugin().getMessageManager().printWaitingMessageList(amountWaiting, playerNeededCount, gameManager.getMinRequired());
            }
            if (gameManager.getSecondsPassed() == 21)
                gameManager.setLobbyPending();
        }
        gameManager.addSecondPassed();
    }

}
