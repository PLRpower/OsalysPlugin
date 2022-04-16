package fr.exolia.plugin.listeners;

import fr.exolia.plugin.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChat implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        if(e.getMessage().startsWith("$") && player.hasPermission("exolia.staff")) {
            e.setCancelled(true);
            Bukkit.getOnlinePlayers().stream()
                    .filter(players -> players.hasPermission("exolia.staff"))
                    .forEach(players -> players.sendMessage("§2StaffChat §a" + player.getName() + " §f» §b" + e.getMessage().substring(1)));
        }

        if(PlayerManager.isInStaffChat(player)) {
            e.setCancelled(true);
            Bukkit.getOnlinePlayers().stream()
                    .filter(players -> players.hasPermission("exolia.staff"))
                    .forEach(players -> players.sendMessage("§2StaffChat §a" + player.getName() + " §f» §b" + e.getMessage()));
        }
    }

}