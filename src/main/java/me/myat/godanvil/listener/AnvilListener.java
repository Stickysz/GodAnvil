package me.myat.godanvil.listener;

import me.myat.godanvil.GodAnvilPlugin;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;

public class AnvilListener implements Listener {

    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent event) {

        Location location = event.getInventory().getLocation();

        if (location == null) {
            return;
        }

        if (!GodAnvilPlugin.getInstance().getGodAnvils().contains(location)) {
            return;
        }

        GodAnvilPlugin.getInstance().getLogger().info(
                "God Anvil used at "
                        + location.getBlockX() + ", "
                        + location.getBlockY() + ", "
                        + location.getBlockZ()
        );
    }
}
