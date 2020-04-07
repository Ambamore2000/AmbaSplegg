package me.andrewjkim.ambasplegg;

import me.andrewjkim.ambasplegg.events.LobbyJoinEvent;
import me.andrewjkim.ambasplegg.events.LobbyQuitEvent;
import me.andrewjkim.ambasplegg.events.RespawnEvent;
import me.andrewjkim.ambasplegg.utils.GameManager;
import me.andrewjkim.ambasplegg.utils.Schematic;
import net.minecraft.server.v1_15_R1.NBTCompressedStreamTools;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileInputStream;
import java.util.Random;

public class AmbaSplegg extends JavaPlugin {

    private GameManager gameManager;

    @Override
    public void onEnable() {
        initializeWorld();

        registerClasses();
        registerEvents();
        registerCommands();
    }

    private void registerClasses() {
        gameManager = new GameManager(this, 1, 60*10);
    }


    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new LobbyJoinEvent(this), this);
        getServer().getPluginManager().registerEvents(new LobbyQuitEvent(this), this);
        getServer().getPluginManager().registerEvents(new RespawnEvent(this), this);
    }


    private void registerCommands() {

    }

    private void deleteWorld(File path) {
        if(path.exists()) {
            File files[] = path.listFiles();
            for(int i=0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteWorld(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
    }

    private void initializeWorld() {

        Bukkit.unloadWorld("world", true);

        World gameWorld = Bukkit.getWorld("game_world");

        try {
            deleteWorld(gameWorld.getWorldFolder());
        } catch (NullPointerException ex) { /* do nothing */ }

        WorldCreator world = new WorldCreator("game_world");
        world.type(WorldType.FLAT);
        world.generator(new ChunkGenerator() {
            @Override
            public ChunkData generateChunkData(World world, Random random, int cx, int cz, ChunkGenerator.BiomeGrid biome) {
                ChunkData chunkData = createChunkData(world);
                for (int x = 0; x <= 15; x++) {
                    for (int z = 0; z <= 15; z++) {
                        biome.setBiome(x, z, Biome.PLAINS);
                    }
                }
                return chunkData;
            }
        });
        world.createWorld();

        gameWorld = Bukkit.getWorld("game_world");

        gameWorld.getBlockAt(0, 98, 0).setType(Material.BEDROCK);
        gameWorld.setSpawnLocation(0, 100, 0);
        loadSchematic("lobby");
    }

    //TODO get Schematic to work properly.
    public Schematic loadSchematic(String name) {
        if (!name.endsWith(".schematic"))
            name = name + ".schematic";
        System.out.println(getDataFolder());
        File file = new File(getDataFolder() + "/schematics/" + name);
        if (!file.exists())
            return null;
        try {
            FileInputStream stream = new FileInputStream(file);
            NBTTagCompound nbtdata = NBTCompressedStreamTools.a(stream);

            short width = nbtdata.getShort("Width");
            short height = nbtdata.getShort("Height");
            short length = nbtdata.getShort("Length");

            byte[] blocks = nbtdata.getByteArray("Blocks");
            byte[] data = nbtdata.getByteArray("Data");

            byte[] addId = new byte[0];

            if (nbtdata.hasKey("AddBlocks")) {
                addId = nbtdata.getByteArray("AddBlocks");
            }

            short[] sblocks = new short[blocks.length];
            for (int index = 0; index < blocks.length; index++) {
                if ((index >> 1) >= addId.length) {
                    sblocks[index] = (short) (blocks[index] & 0xFF);
                } else {
                    if ((index & 1) == 0) {
                        sblocks[index] = (short) (((addId[index >> 1] & 0x0F) << 8) + (blocks[index] & 0xFF));
                    } else {
                        sblocks[index] = (short) (((addId[index >> 1] & 0xF0) << 4) + (blocks[index] & 0xFF));
                    }
                }
            }

            stream.close();
            return new Schematic(name, sblocks, data, width, length, height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GameManager getGameManager() { return gameManager; }

    @Override
    public void onDisable() {

    }

}
