package fr.exolia.plugin.listeners;

import fr.exolia.plugin.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;

public class ModItemsInteract implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent e) {
        Player player = e.getPlayer();
        if(!PlayerManager.isInModerationMod(player)) return;
        if(!(e.getRightClicked() instanceof Player)) return;
        Player target = (Player) e.getRightClicked();

        e.setCancelled(true);

        switch(player.getInventory().getItemInMainHand().getType()) {
            case PAPER:
                player.performCommand("invsee " + target.getName());
                break;

            default: break;
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if(!PlayerManager.isInModerationMod(player)) return;
        if(e.getAction() != Action.RIGHT_CLICK_BLOCK && e.getAction() != Action.RIGHT_CLICK_AIR) return;
        if(e.getHand() != EquipmentSlot.HAND) return;
        switch (player.getInventory().getItemInMainHand().getType()) {

            case FEATHER:
                PlayerManager mod = PlayerManager.getFromPlayer(player);
                mod.setVanished(!mod.isVanished());
                player.sendMessage(mod.isVanished() ? "§aVous êtes à présent invisible." : "§cVous êtes à présent visible");
                break;

            default:
                break;
        }

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        for(Player players : Bukkit.getOnlinePlayers()) {
            PlayerManager pm = PlayerManager.getFromPlayer(players);
            if(pm.isVanished()) {
                player.hidePlayer(players);
            }
        }
     }
}
