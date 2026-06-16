package me.myat.godanvil.listener;

import me.myat.godanvil.GodAnvilPlugin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Tag;
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

        // Empty slots
        if (left == null || left.getType() == Material.AIR) {
            event.setResult(null);
            return;
        }

        if (right == null || right.getType() == Material.AIR) {
            event.setResult(null);
            return;
        }

        // Left side must NOT be a book.
        if (left.getType() == Material.ENCHANTED_BOOK) {
            event.setResult(null);
            return;
        }

        // Right side MUST be an enchanted book.
        if (right.getType() != Material.ENCHANTED_BOOK) {
            event.setResult(null);
            return;
        }

        // Left item must be enchantable.
        if (!isEnchantable(left)) {
            event.setResult(null);
            return;
        }

        if (!(right.getItemMeta() instanceof EnchantmentStorageMeta bookMeta)) {
            event.setResult(null);
            return;
        }

        ItemStack result = left.clone();

        // Apply every enchantment from the book.
        for (Map.Entry<Enchantment, Integer> entry : bookMeta.getStoredEnchants().entrySet()) {
            result.addUnsafeEnchantment(
                    entry.getKey(),
                    entry.getValue()
            );
        }

        event.setResult(result);
    }

    private boolean isEnchantable(ItemStack item) {

        Material type = item.getType();

        // Armor
        if (Tag.ITEMS_HEAD_ARMOR.isTagged(type)
                || Tag.ITEMS_CHEST_ARMOR.isTagged(type)
                || Tag.ITEMS_LEG_ARMOR.isTagged(type)
                || Tag.ITEMS_FOOT_ARMOR.isTagged(type)) {
            return true;
        }

        // Tools and weapons
        switch (type) {
            case WOODEN_SWORD:
            case STONE_SWORD:
            case IRON_SWORD:
            case GOLDEN_SWORD:
            case DIAMOND_SWORD:
            case NETHERITE_SWORD:

            case WOODEN_AXE:
            case STONE_AXE:
            case IRON_AXE:
            case GOLDEN_AXE:
            case DIAMOND_AXE:
            case NETHERITE_AXE:

            case WOODEN_PICKAXE:
            case STONE_PICKAXE:
            case IRON_PICKAXE:
            case GOLDEN_PICKAXE:
            case DIAMOND_PICKAXE:
            case NETHERITE_PICKAXE:

            case WOODEN_SHOVEL:
            case STONE_SHOVEL:
            case IRON_SHOVEL:
            case GOLDEN_SHOVEL:
            case DIAMOND_SHOVEL:
            case NETHERITE_SHOVEL:

            case WOODEN_HOE:
            case STONE_HOE:
            case IRON_HOE:
            case GOLDEN_HOE:
            case DIAMOND_HOE:
            case NETHERITE_HOE:

            case BOW:
            case CROSSBOW:
            case TRIDENT:
            case MACE:
            case FISHING_ROD:
            case SHEARS:
            case SHIELD:
            case ELYTRA:
                return true;

            default:
                return false;
        }
    }
}
