package me.myat.godanvil.listener;

import me.myat.godanvil.GodAnvilPlugin;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.InventoryView;

public class AnvilListener implements Listener {

    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent event) {

        InventoryView view = event.getView();
        Block block = view.getTopInventory().getLocation() != null
                ? view.getTopInventory().getLocation().getBlock()
                : null;

        if (block == null) {
            return;
        }

        Location location = block.getLocation();

        if (!GodAnvilPlugin.getInstance().getGodAnvils().contains(location)) {
            return;
        }

        GodAnvilPlugin.getInstance().getLogger().info(
                "GOD ANVIL DETECTED at "
                        + location.getBlockX() + ", "
                        + location.getBlockY() + ", "
                        + location.getBlockZ()
        );
    }
}
