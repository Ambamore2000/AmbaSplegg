package me.andrewjkim.ambasplegg.utils;

import me.andrewjkim.ambasplegg.AmbaSplegg;
import me.andrewjkim.ambasplegg.enums.GameStatus;
import me.andrewjkim.ambasplegg.tasks.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GameManager {

    private final AmbaSplegg plugin;

    private GameStatus gameStatus;
    private final int minRequired;
    private final int gameDuration;

    private int task;
    private int expTask;

    private int timerCap;
    private int secondsPassed;

    public GameManager(AmbaSplegg plugin, int minRequired, int gameDuration) {
        this.plugin = plugin;
        this.minRequired = minRequired;
        this.gameDuration = gameDuration;

        this.task = -1;
        this.expTask = -1;

        this.timerCap = 0;
        this.secondsPassed = 0;

        setLobbyPending();
    }

    public int getMinRequired() { return minRequired; }
    public int getTimer() { return timerCap - secondsPassed; }
    public void addSecondPassed() { secondsPassed = secondsPassed + 1; }

    private void resetTask(GameStatus inputGameStatus, Runnable runnableClass, int inputTimerCap) {
        if (task != -1) plugin.getServer().getScheduler().cancelTask(task);
        if (expTask != -1) plugin.getServer().getScheduler().cancelTask(expTask);
        secondsPassed = -1;
        gameStatus = inputGameStatus;
        timerCap = inputTimerCap;

        expTask = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new ExpTimerRunnable(this), 0, 20);
        task = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, runnableClass, 0, 20);
    }

    public boolean isRunnableInitialized() { return getTimer() == timerCap; }

    public boolean isLobbyPending() {
        return (gameStatus.equals(GameStatus.LOBBY_PENDING));
    }
    public boolean isLobbyStarting() {
        return (gameStatus.equals(GameStatus.LOBBY_STARTING));
    }
    public boolean isInLobby() {
        return (isLobbyPending() || isLobbyStarting());
    }

    public boolean isGameStarting() {
        return (gameStatus.equals(GameStatus.GAME_STARTING));
    }
    public boolean isGameStarted() {
        return (gameStatus.equals(GameStatus.GAME_STARTED));
    }
    public boolean isGameFinished() {
        return (gameStatus.equals(GameStatus.GAME_FINISHED));
    }
    public boolean isInGame() { return (isGameStarting() || isGameStarted() || isGameFinished()); }


    public void setLobbyPending() {
        resetTask(GameStatus.LOBBY_PENDING, new LobbyPendingRunnable(this), 60);
    }
    public void setLobbyStarting() {
        resetTask(GameStatus.LOBBY_STARTING, new LobbyStartingRunnable(this), 60);
    }
    public void setGameStarting() {
        resetTask(GameStatus.GAME_STARTING, new GameStartingRunnable(this), 10);
    }
    public void setGameStarted() {
        resetTask(GameStatus.GAME_STARTED, new GameStartedRunnable(this), gameDuration);
    }
    public void setGameFinished() {
        resetTask(GameStatus.GAME_FINISHED, new GameFinishedRunnable(this), 20);
    }

    public AmbaSplegg getPlugin() {
        return plugin;
    }


}
