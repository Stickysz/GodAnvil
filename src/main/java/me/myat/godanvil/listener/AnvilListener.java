package me.myat.godanvil.listener;

import me.myat.godanvil.GodAnvilPlugin;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.InventoryHolder;

public class AnvilListener implements Listener {

    @EventHandler
    public void onAnvilOpen(InventoryOpenEvent event) {

        if (!(event.getPlayer() instanceof Player player)) {
            return;
        }

        if (!(event.getInventory() instanceof AnvilInventory)) {
            return;
        }

        InventoryHolder holder = event.getInventory().getHolder();

        if (!(holder instanceof Block block)) {
            return;
        }

        Location location = block.getLocation();

        if (GodAnvilPlugin.getInstance().getGodAnvils().contains(location)) {
            GodAnvilPlugin.getInstance().getLogger().info(
                    player.getName() + " opened a God Anvil at "
                            + location.getBlockX() + ", "
                            + location.getBlockY() + ", "
                            + location.getBlockZ()
            );
        }
    }
}
