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
        if (Bukkit.getOnlinePlayers().size() >= gameManager.getMinRequired()) { gameManager.setLobbyStarting(); }
        int timer = gameManager.getTimer();

        //TODO Make shit last longer
        if (gameManager.isRunnableInitialized()) {
            gameManager.getPlugin().getMessageManager().printVoteMessage();
        } else if (timer == 10) {
            int amountWaiting = Bukkit.getOnlinePlayers().size();
            int playerNeededCount = gameManager.getMinRequired() - amountWaiting;
            gameManager.getPlugin().getMessageManager().printWaitingMessageList(amountWaiting, playerNeededCount, gameManager.getMinRequired());
        } else if (timer == 0) {
            gameManager.setLobbyPending();
        }


        gameManager.addSecondPassed();
    }

}
