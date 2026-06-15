package me.myat.godanvil.storage;

import me.myat.godanvil.GodAnvilPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class GodAnvilStorage {

    private final GodAnvilPlugin plugin;
    private final File file;
    private final YamlConfiguration config;

    public GodAnvilStorage(GodAnvilPlugin plugin) {
        this.plugin = plugin;

        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        this.file = new File(plugin.getDataFolder(), "godanvils.yml");
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public void save() {
        config.set("anvils", null);

        int i = 0;
        for (Location loc : plugin.getGodAnvils()) {
            String path = "anvils." + i;
            config.set(path + ".world", loc.getWorld().getName());
            config.set(path + ".x", loc.getBlockX());
            config.set(path + ".y", loc.getBlockY());
            config.set(path + ".z", loc.getBlockZ());
            i++;
        }

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        plugin.getGodAnvils().clear();

        if (!config.contains("anvils")) {
            return;
        }

        for (String key : config.getConfigurationSection("anvils").getKeys(false)) {
            String worldName = config.getString("anvils." + key + ".world");
            World world = Bukkit.getWorld(worldName);

            if (world == null) {
                continue;
            }

            int x = config.getInt("anvils." + key + ".x");
            int y = config.getInt("anvils." + key + ".y");
            int z = config.getInt("anvils." + key + ".z");

            plugin.getGodAnvils().add(new Location(world, x, y, z));
        }
    }
}
