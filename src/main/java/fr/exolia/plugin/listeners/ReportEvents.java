package fr.exolia.plugin.listeners;

import fr.exolia.plugin.Main;
import fr.exolia.plugin.managers.Report;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashMap;
import java.util.Map;

public class ReportEvents implements Listener {

    private final Main main = Main.getInstance();

    private final Map<Player, Long> reportCooldown = new HashMap<>();
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(!e.getInventory().getTitle().contains("Report")) return;
        if(e.getCurrentItem() == null) return;
        if(!e.getCurrentItem().hasItemMeta()) return;

        Player player = (Player) e.getWhoClicked();
        Player target = Bukkit.getPlayer(e.getInventory().getName().substring(12));
        String reason = e.getCurrentItem().getItemMeta().getDisplayName();

        if(reportCooldown.containsKey(player)) {
            long time = (System.currentTimeMillis() - reportCooldown.get(player)) / 1000;

            if(time < 120) {
                player.sendMessage(main.PrefixError + "Merci de patienter entre chaque signalement !");
                player.closeInventory();
                return;
            } else {
                reportCooldown.remove(player);
            }
        }

        if(target == null) {
            player.closeInventory();
            player.sendMessage(main.PrefixError + "Vous ne pouvez pas signaler ce joueur car il s'est déconnecté");
        }

        e.setCancelled(true);

        player.closeInventory();
        player.sendMessage("§aVous avez bien signalé ce joueur !");

        assert target != null;
        Main.getInstance().getReports().add(new Report(target.getUniqueId().toString(), player.getName(), reason.substring(2)));
        sendToMods(reason, target.getName());
        reportCooldown.put(player, System.currentTimeMillis());
    }

    private void sendToMods(String reason, String targetName) {
        for(Player players : Bukkit.getOnlinePlayers()){
            if(players.hasPermission("exolia.staff")){
                players.sendMessage("§cLe joueur §6" + targetName + " §ca été signalé pour : §6" + reason);
            }
        }
    }
}