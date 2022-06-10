package fr.osalys.plugin.listeners;

import fr.osalys.plugin.managers.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class OnEntityDamage implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player damaged)) return;
        e.setCancelled(PlayerManager.isInModerationMod(damaged) || PlayerManager.isFreeze(damaged));

        if (e instanceof EntityDamageByEntityEvent ev) {
            e.setCancelled(ev.getEntity() instanceof Player && PlayerManager.isFreeze((Player) ev.getEntity()));
        }
    }
}
