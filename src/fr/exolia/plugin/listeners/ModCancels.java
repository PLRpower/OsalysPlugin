package fr.exolia.plugin.listeners;

import fr.exolia.plugin.managers.PlayerManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ModCancels implements Listener {

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        e.setCancelled(PlayerManager.isInModerationMod(e.getPlayer()));
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent e) {
        if(!(e.getEntity() instanceof Player)) return;
        Player pickup = (Player) e.getEntity();
        e.setCancelled(PlayerManager.isInModerationMod(pickup));
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if(!(e.getEntity() instanceof Player)) return;
        e.setCancelled(PlayerManager.isInModerationMod((Player) e.getEntity()));
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if(!(e.getEntity() instanceof Player)) return;
        if(!(e.getDamager() instanceof Player)) return;
        Player damage = (Player) e.getDamager();
        if(PlayerManager.isInModerationMod((damage))) {
            e.setCancelled(damage.getInventory().getItemInMainHand().getType() != Material.STICK);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        e.setCancelled(PlayerManager.isInModerationMod((Player) e.getWhoClicked()));
    }
}