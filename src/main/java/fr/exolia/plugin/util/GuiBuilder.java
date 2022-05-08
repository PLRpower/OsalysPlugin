package fr.exolia.plugin.util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface GuiBuilder {
    String name();
    int getSize();
    void contents(Player player, Inventory inv);
    void onClick(Player player, Inventory inv, ItemStack current, int slot);
}