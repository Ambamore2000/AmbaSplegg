package me.andrewjkim.ambasplegg.utils;

import me.andrewjkim.ambasplegg.AmbaSplegg;
import me.andrewjkim.ambasplegg.enums.GameStatus;
import me.andrewjkim.ambasplegg.runnable.GameStartingRunnable;
import me.andrewjkim.ambasplegg.runnable.InGameRunnable;
import me.andrewjkim.ambasplegg.runnable.LobbyPendingRunnable;
import me.andrewjkim.ambasplegg.runnable.LobbyStartingRunnable;
import org.bukkit.Bukkit;

public class GameManager {

    private AmbaSplegg plugin;

    private GameStatus gameStatus;
    private int minRequired;
    private int gameDuration;

    private int task;
    private int secondsPassed;

    public GameManager(AmbaSplegg plugin, int minRequired, int gameDuration) {
        this.plugin = plugin;
        this.minRequired = minRequired;
        this.gameDuration = gameDuration;

        this.task = -1;
        this.secondsPassed = 0;

        setLobbyPending();
    }


    public GameStatus getGameStatus() { return gameStatus; }
    public int getMinRequired() { return minRequired; }
    public int getSecondsPassed() { return secondsPassed; }

    public void addSecondPassed() { secondsPassed++; }

    private void stopCurrentTask() { if (task != -1) Bukkit.getScheduler().cancelTask(task); }

    public void setLobbyPending() {
        stopCurrentTask();
        gameStatus = GameStatus.LOBBY_PENDING;
        secondsPassed = 0;

        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new LobbyPendingRunnable(this), 20L, 20*20L);
    }

    public void setLobbyStarting() {
        stopCurrentTask();
        gameStatus = GameStatus.LOBBY_STARTING;
        secondsPassed = 0;

        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new LobbyStartingRunnable(this), 20L, 15*20L);
    }

    public void setGameStarting() {
        stopCurrentTask();
        gameStatus = GameStatus.GAME_STARTING;
        secondsPassed = 0;

        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new GameStartingRunnable(this), 20L, 10*20L);
    }

    public void setInGame() {
        stopCurrentTask();
        gameStatus = GameStatus.IN_GAME;
        secondsPassed = 0;

        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new InGameRunnable(this), 20L, gameDuration*20L);
    }


}
