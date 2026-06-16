package me.myat.godanvil.listener;

import me.myat.godanvil.GodAnvilPlugin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.Map;

public class AnvilListener implements Listener {

    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent event) {

        AnvilInventory inventory = event.getInventory();
        Location location = inventory.getLocation();

        if (location == null) {
            GodAnvilPlugin.getInstance().getLogger().info("[DEBUG] Anvil location is null.");
            return;
        }

        if (!GodAnvilPlugin.getInstance().getGodAnvils().contains(location)) {
            return;
        }

        GodAnvilPlugin.getInstance().getLogger().info("[DEBUG] God Anvil detected.");

        ItemStack left = inventory.getItem(0);
        ItemStack right = inventory.getItem(1);

        GodAnvilPlugin.getInstance().getLogger().info(
                "[DEBUG] Left slot: " +
                        (left == null ? "null" : left.getType().toString())
        );

        GodAnvilPlugin.getInstance().getLogger().info(
                "[DEBUG] Right slot: " +
                        (right == null ? "null" : right.getType().toString())
        );

        if (left == null || left.getType() == Material.AIR) {
            GodAnvilPlugin.getInstance().getLogger().info("[DEBUG] Left slot invalid.");
            event.setResult(null);
            return;
        }

        if (right == null || right.getType() == Material.AIR) {
            GodAnvilPlugin.getInstance().getLogger().info("[DEBUG] Right slot invalid.");
            event.setResult(null);
            return;
        }

        if (right.getType() != Material.ENCHANTED_BOOK) {
            GodAnvilPlugin.getInstance().getLogger().info("[DEBUG] Right item is not an enchanted book.");
            event.setResult(null);
            return;
        }

        if (!(right.getItemMeta() instanceof EnchantmentStorageMeta bookMeta)) {
            GodAnvilPlugin.getInstance().getLogger().info("[DEBUG] Failed to read book meta.");
            event.setResult(null);
            return;
        }

        ItemStack result = left.clone();

        for (Map.Entry<Enchantment, Integer> entry : bookMeta.getStoredEnchants().entrySet()) {
            GodAnvilPlugin.getInstance().getLogger().info(
                    "[DEBUG] Applying "
                            + entry.getKey().getKey().getKey()
                            + " "
                            + entry.getValue()
            );

            result.addUnsafeEnchantment(
                    entry.getKey(),
                    entry.getValue()
            );
        }

        GodAnvilPlugin.getInstance().getLogger().info("[DEBUG] Setting custom result.");
        event.setResult(result);
    }
}
