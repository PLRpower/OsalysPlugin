package fr.exolia.plugin.listeners;

import fr.exolia.plugin.Main;
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
        if(e.getHand() != EquipmentSlot.HAND) return;

        e.setCancelled(true);

        switch(player.getInventory().getItemInMainHand().getType()) {
            case PAPER:
                player.performCommand("invsee " + target.getName());
                break;

            case PACKED_ICE:
                if(Main.getInstance().getFrozenPlayers().containsKey(target.getUniqueId())){
                    Main.getInstance().getFrozenPlayers().remove(target.getUniqueId());
                    target.sendMessage(Main.PrefixInfo + "Vous avez été unfreeze par un modérateur");
                    player.sendMessage(Main.PrefixInfo + "Vous avez unfreeze" + target.getName());
                } else {
                    Main.getInstance().getFrozenPlayers().put(target.getUniqueId(), target.getLocation());
                    target.sendMessage(Main.PrefixInfo + "Vous avez été freeze par un modérateur");
                    player.sendMessage(Main.PrefixInfo + "Vous avez freeze" + target.getName());
                }
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
                player.sendMessage(mod.isVanished() ? Main.PrefixInfo + "Vous êtes à présent invisible." : Main.PrefixInfo + "Vous êtes à présent visible");
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
