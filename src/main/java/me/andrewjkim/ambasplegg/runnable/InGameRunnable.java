package me.andrewjkim.ambasplegg.runnable;

import me.andrewjkim.ambasplegg.utils.GameManager;

public class InGameRunnable implements Runnable {

    GameManager gameManager;

    public InGameRunnable(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void run() {

    }
}
