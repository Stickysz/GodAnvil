package me.myat.godanvil.listener;

import me.myat.godanvil.GodAnvilPlugin;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class BlockListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        ItemStack item = event.getItemInHand();

        if (!item.hasItemMeta()) {
            return;
        }

        ItemMeta meta = item.getItemMeta();

        if (meta.getPersistentDataContainer().has(
                CraftListener.GOD_ANVIL_KEY,
                PersistentDataType.BOOLEAN
        )) {
            Location location = event.getBlockPlaced().getLocation();
            GodAnvilPlugin.getInstance().getGodAnvils().add(location);
            GodAnvilPlugin.getInstance().getStorage().save();
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Location location = block.getLocation();

        if (!GodAnvilPlugin.getInstance().getGodAnvils().contains(location)) {
            return;
        }

        event.setDropItems(false);

        GodAnvilPlugin.getInstance().getGodAnvils().remove(location);
        GodAnvilPlugin.getInstance().getStorage().save();

        block.getWorld().dropItemNaturally(
                location,
                CraftListener.createGodAnvil()
        );
    }
}
