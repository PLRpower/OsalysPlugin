package fr.osalys.plugin.listeners;

import fr.osalys.plugin.Main;
import fr.osalys.plugin.managers.PlayerManager;
import fr.osalys.plugin.managers.ReportManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.List;

public class ModEvents implements Listener {

    private final Main main = Main.getInstance();

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent e){
        if(!(e.getEntity() instanceof Player pickup)) return;
        e.setCancelled(PlayerManager.isInModerationMod(pickup) || PlayerManager.isFreeze(pickup));
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e){
        if(!(e.getEntity() instanceof Player damaged)) return;
        e.setCancelled(PlayerManager.isInModerationMod(damaged) ||  PlayerManager.isFreeze(damaged));

        if(e instanceof EntityDamageByEntityEvent ev){
            e.setCancelled(ev.getEntity() instanceof Player &&  PlayerManager.isFreeze((Player) ev.getEntity()));
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e){
        if(!(e.getEntity() instanceof Player)) return;
        if(!(e.getDamager() instanceof Player damage)) return;
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
    public void onItemDrop(PlayerDropItemEvent e) {
        e.setCancelled(PlayerManager.isInModerationMod(e.getPlayer()) ||  PlayerManager.isFreeze(e.getPlayer()));
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        e.setCancelled(PlayerManager.isFreeze(e.getPlayer()) || PlayerManager.isInModerationMod(e.getPlayer()) && e.getBlock().getType() == Material.PACKED_ICE);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {e.setCancelled(PlayerManager.isFreeze(e.getPlayer()));}

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        e.setCancelled(PlayerManager.isInModerationMod((Player) e.getWhoClicked()) || PlayerManager.isFreeze((Player) e.getWhoClicked()));
    }

    @EventHandler
    public void onInteractWithEntity(PlayerInteractEntityEvent e){
        Player player = e.getPlayer();
        if(!PlayerManager.isInModerationMod(player)) return;
        if(!(e.getRightClicked() instanceof Player target)) return;
        if(e.getHand() != EquipmentSlot.HAND) return;
        e.setCancelled(true);
        switch(player.getInventory().getItemInMainHand().getType()){
            case PAPER:
                player.performCommand("invsee " + target.getName());
                break;

            case PACKED_ICE:
                main.getPlayerManager().setFreeze(target, player, !PlayerManager.isFreeze(target));
                break;

            case BOOK:
                List<ReportManager> reports = main.getReports().getReports(target.getUniqueId().toString());

                if(reports.isEmpty()){
                    player.sendMessage(main.prefixError + "Ce joueur n'a aucun signalement");
                }else{
                    player.sendMessage(main.prefixInfo + "Voici la liste des signalements de §b" + target.getName() + "§7:");
                    reports.forEach(r -> player.sendMessage("§f" + r.getDate() + "§fSignalé par :" + r.getAuthor() + " §fpour la raison :" + r.getReason()));
                }

                break;

            default: break;
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player player = e.getPlayer();
        if(!PlayerManager.isInModerationMod(player)) return;
        if(e.getAction() != Action.RIGHT_CLICK_BLOCK && e.getAction() != Action.RIGHT_CLICK_AIR) return;
        if(e.getHand() != EquipmentSlot.HAND) return;
        switch(player.getInventory().getItemInMainHand().getType()){

            case FEATHER:
                main.getPlayerManager().setVanish(player, !PlayerManager.isVanished(player));
                break;

            case PACKED_ICE:
                return;

            default:
                break;
        }
    }
}
