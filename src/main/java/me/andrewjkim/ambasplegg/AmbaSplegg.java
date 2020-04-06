package me.andrewjkim.ambasplegg;

import me.andrewjkim.ambasplegg.utils.GameManager;
import org.bukkit.plugin.java.JavaPlugin;

public class AmbaSplegg extends JavaPlugin {

    private GameManager gameManager;

    @Override
    public void onEnable() {
        registerClasses();
        registerEvents();
        registerCommands();
    }

    private void registerClasses() {
        gameManager = new GameManager(this, 1, 60*10);
    }


    private void registerEvents() {

    }


    private void registerCommands() {

    }



    @Override
    public void onDisable() {

    }

}
