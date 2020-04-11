package me.andrewjkim.ambasplegg.tasks;

import me.andrewjkim.ambasplegg.utils.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GameStartingRunnable implements Runnable {

    private GameManager gameManager;

    public GameStartingRunnable(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        int timer = 11 - gameManager.getSecondsPassed();
        if (timer == 0) {
            gameManager.setGameStarted();
        } else {
            //TODO cool countdown 5 4 3 2 1 go!
            Bukkit.broadcastMessage(String.valueOf(timer));
        }
        gameManager.addSecondPassed();
    }

}
