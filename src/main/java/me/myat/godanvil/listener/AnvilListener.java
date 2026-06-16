package me.myat.godanvil.listener;

import me.myat.godanvil.GodAnvilPlugin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.enchantments.Enchantment;

import java.util.Map;

public class AnvilListener implements Listener {

    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent event) {

        AnvilInventory inventory = event.getInventory();
        Location location = inventory.getLocation();

        if (location == null) {
            return;
        }

        if (!GodAnvilPlugin.getInstance().getGodAnvils().contains(location)) {
            return;
        }

        ItemStack left = inventory.getFirstItem();
        ItemStack right = inventory.getSecondItem();

        if (left == null || left.getType() == Material.AIR) {
            return;
        }

        if (right == null || right.getType() == Material.AIR) {
            return;
        }

        // For the first implementation, only handle enchanted books.
        if (right.getType() != Material.ENCHANTED_BOOK) {
            return;
        }

        if (!(right.getItemMeta() instanceof EnchantmentStorageMeta bookMeta)) {
            return;
        }

        ItemStack result = left.clone();

        for (Map.Entry<Enchantment, Integer> entry : bookMeta.getStoredEnchants().entrySet()) {
            result.addUnsafeEnchantment(
                    entry.getKey(),
                    entry.getValue()
            );
        }

        event.setResult(result);

        GodAnvilPlugin.getInstance().getLogger().info(
                "Generated custom God Anvil result."
        );
    }
}
