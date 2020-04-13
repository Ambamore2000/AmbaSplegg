package me.andrewjkim.ambasplegg.tasks;

import me.andrewjkim.ambasplegg.utils.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ExpTimerRunnable implements Runnable {

    private GameManager gameManager;

    public ExpTimerRunnable(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        if (gameManager.isWaiting())
            setExpTimer(0);
        else
            setExpTimer(gameManager.getTimer());
    }

    private void setExpTimer(int timer) {
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            p.setLevel(timer);
        }
    }
}
