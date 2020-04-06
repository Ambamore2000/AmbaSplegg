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

    public int getMinRequired() { return minRequired; }
    public int getSecondsPassed() { return secondsPassed; }
    public int getGameDuration() { return gameDuration; }

    public void addSecondPassed() { secondsPassed++; }

    private void resetTask(GameStatus inputGameStatus) { if (task != -1) Bukkit.getScheduler().cancelTask(task); secondsPassed = 0; gameStatus = inputGameStatus; }


    /**
     * Sets game status to lobby pending. (Waiting for enough players to join)
     */
    public void setLobbyPending() {
        resetTask(GameStatus.LOBBY_PENDING);

        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new LobbyPendingRunnable(this), 0, 20);
    }

    /**
     * Sets game status to lobby starting. (Enough players to start the game)
     */

    public void setLobbyStarting() {
        resetTask(GameStatus.LOBBY_STARTING);

        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new LobbyStartingRunnable(this), 0, 20);
    }

    /**
     * Sets game status to game starting. (Short countdown for game to start)
     */

    public void setGameStarting() {
        resetTask(GameStatus.GAME_STARTING);

        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new GameStartingRunnable(this), 0, 20);
    }

    /**
     * Sets game status to in game. (Game has begun)
     */

    public void setInGame() {
        resetTask(GameStatus.IN_GAME);

        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new InGameRunnable(this), 0, 20);
    }

    //TODO Should I make a setCompleteGame function...


}
