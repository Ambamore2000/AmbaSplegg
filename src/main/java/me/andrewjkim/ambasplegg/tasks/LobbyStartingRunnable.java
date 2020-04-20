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
        if (gameManager.getPlugin().getServer().getOnlinePlayers().size() < gameManager.getMinRequired()) { gameManager.setLobbyPending(); }
        int timer = gameManager.getTimer();

        if (timer % 30 == 0 && timer > 0) {
            int amountWaiting = gameManager.getPlugin().getServer().getOnlinePlayers().size();
            int playerNeededCount = gameManager.getMinRequired() - amountWaiting;
            gameManager.getPlugin().getMessageManager().printWaitingMessageList(amountWaiting, playerNeededCount, timer);
        } else if (timer % 20 == 0 && timer > 0) {
            gameManager.getPlugin().getMessageManager().printVoteMessage();
        } else if (timer == 0) {
            gameManager.getPlugin().getWorldManager().initializeGameWorld(gameManager.getPlugin().getVoteManager().getVotedMap().getFileName());
            gameManager.setGameStarting();
        }


        gameManager.addSecondPassed();
    }

}
