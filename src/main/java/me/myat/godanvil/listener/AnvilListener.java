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

import java.util.Map;

public class AnvilListener implements Listener {

    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent event) {

        AnvilInventory inv = event.getInventory();
        Location loc = inv.getLocation();

        if (loc == null) return;

        if (!GodAnvilPlugin.getInstance().getGodAnvils().contains(loc)) {
            return;
        }

        ItemStack left = inv.getItem(0);
        ItemStack right = inv.getItem(1);

        if (left == null || left.getType() == Material.AIR) return;
        if (right == null || right.getType() == Material.AIR) return;

        ItemStack result = left.clone();

        Map<Enchantment, Integer> leftEnchants = left.getEnchantments();
        Map<Enchantment, Integer> rightEnchants = right.getEnchantments();

        // merge left
        for (Map.Entry<Enchantment, Integer> e : leftEnchants.entrySet()) {
            result.addUnsafeEnchantment(e.getKey(), e.getValue());
        }

        // merge right
        for (Map.Entry<Enchantment, Integer> e : rightEnchants.entrySet()) {

            Enchantment ench = e.getKey();
            int level = e.getValue();

            // 🚫 ONLY forbidden combo: Silk Touch + Fortune
            if ((ench == Enchantment.SILK_TOUCH &&
                    result.containsEnchantment(Enchantment.FORTUNE)) ||
                (ench == Enchantment.FORTUNE &&
                    result.containsEnchantment(Enchantment.SILK_TOUCH))) {
                continue;
            }

            int existing = result.getEnchantmentLevel(ench);
            if (level > existing) {
                result.addUnsafeEnchantment(ench, level);
            }
        }

        event.setResult(result);

        GodAnvilPlugin.getInstance().getLogger().info(
                "God Anvil merge executed at "
                        + loc.getBlockX() + ", "
                        + loc.getBlockY() + ", "
                        + loc.getBlockZ()
        );
    }
}
