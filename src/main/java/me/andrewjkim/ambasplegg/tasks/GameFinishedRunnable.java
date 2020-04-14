package me.andrewjkim.ambasplegg.tasks;

import me.andrewjkim.ambasplegg.utils.GameManager;
import org.bukkit.Bukkit;

public class GameFinishedRunnable implements Runnable {

    GameManager gameManager;

    public GameFinishedRunnable(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        int timer = gameManager.getTimer();


        if (gameManager.isRunnableInitialized()) {
            if (Bukkit.getOnlinePlayers().size() > 0)
                gameManager.getPlugin().getMessageManager().printFinishMessageList(gameManager.getPlugin().getgPlayerManager().getWinner().getDisplayName());
        } else if (timer == 10) {
            gameManager.getPlugin().getMessageManager().printRestartMessage();
        } else if (timer == 0) {
            gameManager.getPlugin().getMessageManager().printThanksMessageList();
            gameManager.getPlugin().getServer().shutdown();
        }


        gameManager.addSecondPassed();
    }
}
