package fr.exolia.plugin.listeners;

import fr.exolia.plugin.managers.PlayerManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class ModCancels implements Listener {

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent e){
        if(!(e.getEntity() instanceof Player)) return;
        Player pickup = (Player) e.getEntity();
        e.setCancelled(PlayerManager.isInModerationMod(pickup) || PlayerManager.isFreeze(pickup));
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e){
        if(!(e.getEntity() instanceof Player)) return;
        Player damaged = (Player) e.getEntity();
        e.setCancelled(PlayerManager.isInModerationMod(damaged) ||  PlayerManager.isFreeze(damaged));

        if(e instanceof EntityDamageByEntityEvent){
            EntityDamageByEntityEvent ev = (EntityDamageByEntityEvent) e;
            e.setCancelled(ev.getEntity() instanceof Player &&  PlayerManager.isFreeze((Player) ev.getEntity()));
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e){
        if(!(e.getEntity() instanceof Player)) return;
        if(!(e.getDamager() instanceof Player)) return;
        Player damage = (Player) e.getDamager();
        if(PlayerManager.isInModerationMod(damage)){
            e.setCancelled(damage.getInventory().getItemInMainHand().getType() != Material.STICK);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        if( PlayerManager.isFreeze(e.getPlayer())){
            e.setTo(e.getFrom());
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {e.setCancelled(PlayerManager.isInModerationMod(e.getPlayer()) ||  PlayerManager.isFreeze(e.getPlayer()));}

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        e.setCancelled(PlayerManager.isFreeze(e.getPlayer()) || PlayerManager.isInModerationMod(e.getPlayer()) && e.getBlock().getType() == Material.PACKED_ICE);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {e.setCancelled(PlayerManager.isFreeze(e.getPlayer()));}

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {e.setCancelled(PlayerManager.isInModerationMod((Player) e.getWhoClicked()) || PlayerManager.isFreeze((Player) e.getWhoClicked()));}

}
