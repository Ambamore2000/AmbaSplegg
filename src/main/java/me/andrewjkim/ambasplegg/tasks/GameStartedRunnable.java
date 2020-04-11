package me.andrewjkim.ambasplegg.tasks;

import me.andrewjkim.ambasplegg.utils.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GameStartedRunnable implements Runnable {

    GameManager gameManager;

    public GameStartedRunnable(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        int timer = gameManager.getGameDuration() - gameManager.getSecondsPassed();
        if (timer == 0) {
            gameManager.setGameFinished();
        } else {
            Bukkit.broadcastMessage(String.valueOf(timer));
            Bukkit.broadcastMessage("Automatic shutdown @ 300");
            //TODO delete
            if (timer == 300)
                gameManager.getPlugin().getServer().shutdown();
        }
        gameManager.addSecondPassed();
    }

}
