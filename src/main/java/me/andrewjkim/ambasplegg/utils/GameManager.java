package me.andrewjkim.ambasplegg.utils;

import me.andrewjkim.ambasplegg.AmbaSplegg;
import me.andrewjkim.ambasplegg.enums.GameStatus;
import me.andrewjkim.ambasplegg.tasks.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GameManager {

    private AmbaSplegg plugin;

    private GameStatus gameStatus;
    private int minRequired;
    private int gameDuration;

    private int task;
    private int voteMessageTask;
    private int expTimerTask;

    private int timer;
    private int secondsPassed;

    public GameManager(AmbaSplegg plugin, int minRequired, int gameDuration) {
        this.plugin = plugin;
        this.minRequired = minRequired;
        this.gameDuration = gameDuration;

        this.task = -1;
        this.expTimerTask = -1;

        this.timer = 0;
        this.secondsPassed = 0;

        setLobbyPending();
    }

    public int getMinRequired() { return minRequired; }

    public int getSecondsPassed() { return secondsPassed; }
    public int getTimer() { return timer; }

    public int getGameDuration() { return gameDuration; }
    public GameStatus getGameStatus() { return gameStatus; }

    public void addSecondPassed() { secondsPassed++; }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    private void resetTask(GameStatus inputGameStatus) { if (task != -1) Bukkit.getScheduler().cancelTask(task); secondsPassed = 0; gameStatus = inputGameStatus; }
    private void resetExpTask() { if (expTimerTask != -1) Bukkit.getScheduler().cancelTask(expTimerTask); }

    public boolean isWaiting() {
        return (gameStatus.equals(GameStatus.LOBBY_PENDING));
    }
    public boolean isStarting() {
        return (gameStatus.equals(GameStatus.LOBBY_STARTING));
    }
    public boolean isInLobby() {
        return (isWaiting() || isStarting());
    }
    public boolean isInGame() {
        return (gameStatus.equals(GameStatus.GAME_STARTED)
                || gameStatus.equals(GameStatus.GAME_STARTING));
    }

    /**
     * Sets game status to lobby pending. (Waiting for enough players to join)
     */
    public void setLobbyPending() {
        resetTask(GameStatus.LOBBY_PENDING);
        resetExpTask();

        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new LobbyPendingRunnable(this), 0, 20);
        voteMessageTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new VoteMessageRunnable(this), 0, 20);
    }

    /**
     * Sets game status to lobby starting. (Enough players to start the game)
     */

    public void setLobbyStarting() {
        resetTask(GameStatus.LOBBY_STARTING);

        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new LobbyStartingRunnable(this), 0, 20);
        expTimerTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new ExpTimerRunnable(this), 0, 20);
    }

    /**
     * Sets game status to game starting. (Short countdown for game to start)
     */

    public void setGameStarting() {
        resetTask(GameStatus.GAME_STARTING);
        Bukkit.getScheduler().cancelTask(voteMessageTask);
        plugin.getMessageManager().printVotedMessage();
        plugin.getgPlayerManager().initializegPlayerList();

        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new GameStartingRunnable(this), 100, 20);
    }

    /**
     * Sets game status to game started. (Give players items)
     */

    public void setGameStarted() {
        resetTask(GameStatus.GAME_STARTED);
        plugin.getMessageManager().printStartMessageList();
        fuckShitUp();

        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new GameStartedRunnable(this), 0, 20);
    }

    /**
     * Sets game status to game finished. (Game has finished)
     */

    public void setGameFinished() {
        resetTask(GameStatus.GAME_FINISHED);
        plugin.getMessageManager().printFinishMessageList(plugin.getgPlayerManager().getWinner().getDisplayName());

        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new GameFinishedRunnable(this), 0, 20);
    }

    //TODO Make a better function to give players items.
    private void fuckShitUp() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.getInventory().addItem(new ItemStack(Material.IRON_SHOVEL));
        }
    }

    public AmbaSplegg getPlugin() {
        return plugin;
    }


}
