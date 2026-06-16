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

        /*
         * BOOK + BOOK
         */
        if (left.getType() == Material.ENCHANTED_BOOK
                && right.getType() == Material.ENCHANTED_BOOK) {

            if (!(left.getItemMeta() instanceof EnchantmentStorageMeta leftMeta)) {
                return;
            }

            if (!(right.getItemMeta() instanceof EnchantmentStorageMeta rightMeta)) {
                return;
            }

            ItemStack result = new ItemStack(Material.ENCHANTED_BOOK);
            EnchantmentStorageMeta resultMeta =
                    (EnchantmentStorageMeta) result.getItemMeta();

            // Copy left book enchants.
            for (Map.Entry<Enchantment, Integer> entry :
                    leftMeta.getStoredEnchants().entrySet()) {

                resultMeta.addStoredEnchant(
                        entry.getKey(),
                        entry.getValue(),
                        true
                );
            }

            // Merge right book enchants.
            for (Map.Entry<Enchantment, Integer> entry :
                    rightMeta.getStoredEnchants().entrySet()) {

                Enchantment enchantment = entry.getKey();
                int level = entry.getValue();

                if (resultMeta.hasStoredEnchant(enchantment)) {
                    int current =
                            resultMeta.getStoredEnchantLevel(enchantment);

                    if (level > current) {
                        resultMeta.removeStoredEnchant(enchantment);
                        resultMeta.addStoredEnchant(
                                enchantment,
                                level,
                                true
                        );
                    }
                } else {
                    resultMeta.addStoredEnchant(
                            enchantment,
                            level,
                            true
                    );
                }
            }

            result.setItemMeta(resultMeta);
            event.setResult(result);

            return;
        }

        /*
         * ITEM + ENCHANTED BOOK
         */
        if (right.getType() == Material.ENCHANTED_BOOK) {

            if (!(right.getItemMeta() instanceof EnchantmentStorageMeta bookMeta)) {
                return;
            }

            ItemStack result = left.clone();

            for (Map.Entry<Enchantment, Integer> entry :
                    bookMeta.getStoredEnchants().entrySet()) {

                result.addUnsafeEnchantment(
                        entry.getKey(),
                        entry.getValue()
                );
            }

            event.setResult(result);
        }
    }
}
