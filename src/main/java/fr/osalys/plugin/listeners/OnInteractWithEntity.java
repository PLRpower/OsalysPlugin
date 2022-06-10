package fr.osalys.plugin.listeners;

import fr.osalys.plugin.Main;
import fr.osalys.plugin.managers.PlayerManager;
import fr.osalys.plugin.managers.ReportManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.List;

public class OnInteractWithEntity implements Listener {

    private final Main main;
    public OnInteractWithEntity(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onInteractWithEntity(PlayerInteractEntityEvent e) {
        Player player = e.getPlayer();
        if (!PlayerManager.isInModerationMod(player)) return;
        if (!(e.getRightClicked() instanceof Player target)) return;
        if (e.getHand() != EquipmentSlot.HAND) return;
        e.setCancelled(true);
        switch (player.getInventory().getItemInMainHand().getType()) {
            case PAPER:
                player.performCommand("invsee " + target.getName());
                break;

            case PACKED_ICE:
                main.getPlayerManager().setFreeze(target, player, !PlayerManager.isFreeze(target));
                break;

            case BOOK:
                List<ReportManager> reports = main.getReports().getReports(target.getUniqueId().toString());

                if (reports.isEmpty()) {
                    player.sendMessage(main.prefixError + "Ce joueur n'a aucun signalement");
                } else {
                    player.sendMessage(main.prefixInfo + "Voici la liste des signalements de §b" + target.getName() + "§7:");
                    reports.forEach(r -> player.sendMessage("§f" + r.getDate() + "§fSignalé par :" + r.getAuthor() + " §fpour la raison :" + r.getReason()));
                }

                break;

            default:
                break;
        }
    }
}
