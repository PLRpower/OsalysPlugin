package fr.osalys.plugin.listeners;

import fr.osalys.plugin.managers.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class OnInventoryClick implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        e.setCancelled(PlayerManager.isInModerationMod((Player) e.getWhoClicked()) || PlayerManager.isFreeze((Player) e.getWhoClicked()));
    }
}
