package me.myat.godanvil;

import me.myat.godanvil.listener.CraftListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class GodAnvilPlugin extends JavaPlugin {

    private static GodAnvilPlugin instance;

    @Override
    public void onEnable() {
        instance = this;

        CraftListener.registerRecipe();

        getLogger().info("GodAnvil has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("GodAnvil has been disabled!");
    }

    public static GodAnvilPlugin getInstance() {
        return instance;
    }
}
