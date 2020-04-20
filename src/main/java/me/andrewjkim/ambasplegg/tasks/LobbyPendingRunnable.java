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
        if (gameManager.getPlugin().getServer().getOnlinePlayers().size() >= gameManager.getMinRequired()) { gameManager.setLobbyStarting(); }
        int timer = gameManager.getTimer();

        if (timer % 30 == 0 && timer > 0) {
            int amountWaiting = gameManager.getPlugin().getServer().getOnlinePlayers().size();
            int playerNeededCount = gameManager.getMinRequired() - amountWaiting;
            gameManager.getPlugin().getMessageManager().printWaitingMessageList(amountWaiting, playerNeededCount, -1);
        } else if (timer % 20 == 0 && timer > 0) {
            gameManager.getPlugin().getMessageManager().printVoteMessage();
        } else if (timer == 0) {
            gameManager.setLobbyPending();
        }


        gameManager.addSecondPassed();
    }

}
