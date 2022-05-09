package fr.exolia.plugin.listeners;

import fr.exolia.plugin.Main;
import fr.exolia.plugin.managers.PlayerManager;
import fr.exolia.plugin.managers.ReportManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.List;

public class ModItemsInteract implements Listener {

    private final Main main = Main.getInstance();

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent e){
        Player player = e.getPlayer();
        if(!PlayerManager.isInModerationMod(player)) return;
        if(!(e.getRightClicked() instanceof Player)) return;
        Player target = (Player) e.getRightClicked();
        if(e.getHand() != EquipmentSlot.HAND) return;

        e.setCancelled(true);

        switch(player.getInventory().getItemInMainHand().getType()){
            case PAPER:
                player.performCommand("invsee " + target.getName());
                break;

            case PACKED_ICE:
                if(main.getFrozenPlayers().containsKey(target.getUniqueId())){
                    main.getFrozenPlayers().remove(target.getUniqueId());
                    target.sendMessage(main.prefixInfo + "Vous avez été unfreeze par un modérateur");
                    player.sendMessage(main.prefixInfo + "Vous avez unfreeze" + target.getName());
                }else{
                    main.getFrozenPlayers().put(target.getUniqueId(), target.getLocation());
                    target.sendMessage(main.prefixInfo + "Vous avez été freeze par un modérateur");
                    player.sendMessage(main.prefixInfo + "Vous avez freeze" + target.getName());
                }
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
        switch (player.getInventory().getItemInMainHand().getType()){

            case FEATHER:
                PlayerManager mod = PlayerManager.getFromPlayer(player);
                mod.setVanished(!mod.isVanished());
                player.sendMessage(mod.isVanished() ? main.prefixInfo + "Vous êtes à présent invisible." : main.prefixInfo + "Vous êtes à présent visible");
                break;

            default:
                break;
        }
    }
}
