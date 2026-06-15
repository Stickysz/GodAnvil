package me.myat.godanvil;

import org.bukkit.plugin.java.JavaPlugin;

public final class GodAnvilPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("GodAnvil has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("GodAnvil has been disabled!");
    }
}
