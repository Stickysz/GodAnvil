package me.myat.godanvil;

import me.myat.godanvil.listener.BlockListener;
import me.myat.godanvil.listener.CraftListener;
import me.myat.godanvil.storage.GodAnvilStorage;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public final class GodAnvilPlugin extends JavaPlugin {

    private static GodAnvilPlugin instance;

    private final Set<Location> godAnvils = new HashSet<>();
    private GodAnvilStorage storage;

    @Override
    public void onEnable() {
        instance = this;

        storage = new GodAnvilStorage(this);
        storage.load();

        CraftListener.registerRecipe();

        getServer().getPluginManager().registerEvents(
                new BlockListener(),
                this
        );

        getLogger().info("GodAnvil has been enabled!");
    }

    @Override
    public void onDisable() {
        storage.save();
        getLogger().info("GodAnvil has been disabled!");
    }

    public static GodAnvilPlugin getInstance() {
        return instance;
    }

    public Set<Location> getGodAnvils() {
        return godAnvils;
    }

    public GodAnvilStorage getStorage() {
        return storage;
    }
}
