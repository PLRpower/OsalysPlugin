package fr.osalys.plugin.listeners;

import fr.osalys.plugin.managers.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class OnItemPickup implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onItemPickup(EntityPickupItemEvent e) {
        if (!(e.getEntity() instanceof Player pickup)) return;
        e.setCancelled(PlayerManager.isInModerationMod(pickup) || PlayerManager.isFreeze(pickup));
    }
}
