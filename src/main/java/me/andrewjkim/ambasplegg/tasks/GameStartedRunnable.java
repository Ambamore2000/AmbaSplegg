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
        int timer = gameManager.getTimer();


        if (gameManager.isRunnableInitialized()) {
            gameManager.getPlugin().getMessageManager().printStartMessageList();
            fuckShitUp();
        } else if (timer == 500) {
            //TODO delete
            Bukkit.broadcastMessage(String.valueOf(timer));
            Bukkit.broadcastMessage("Automatically finish game @ 500");
            gameManager.setGameFinished();
        } else if (timer == 0) {
            gameManager.setGameFinished();
        }


        gameManager.addSecondPassed();
    }

    //TODO Make a better function to give players items. ItemManager??
    private void fuckShitUp() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.getInventory().addItem(new ItemStack(Material.IRON_SHOVEL));
        }
    }

}
