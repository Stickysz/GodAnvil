package me.myat.godanvil.listener;

import me.myat.godanvil.GodAnvilPlugin;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class CraftListener {

    public static final NamespacedKey GOD_ANVIL_KEY =
            new NamespacedKey(GodAnvilPlugin.getInstance(), "god_anvil");

    public static ItemStack createGodAnvil() {
        ItemStack item = new ItemStack(Material.ANVIL);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("§6God Anvil");
        meta.getPersistentDataContainer().set(
                GOD_ANVIL_KEY,
                PersistentDataType.BOOLEAN,
                true
        );

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);

        return item;
    }

    public static void registerRecipe() {
        NamespacedKey recipeKey = new NamespacedKey(
                GodAnvilPlugin.getInstance(),
                "god_anvil_recipe"
        );

        ShapedRecipe recipe = new ShapedRecipe(recipeKey, createGodAnvil());

        recipe.shape(
                "DDD",
                "DAD",
                "DDD"
        );

        recipe.setIngredient('D', Material.DIAMOND);
        recipe.setIngredient('A', Material.ANVIL);

        GodAnvilPlugin.getInstance().getServer().addRecipe(recipe);
    }
}
