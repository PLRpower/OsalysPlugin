package fr.osalys.plugin.listeners;

import fr.osalys.plugin.Main;
import fr.osalys.plugin.managers.ChatHistoryManager;
import fr.osalys.plugin.managers.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerEvents implements Listener {

    private final Main main;
    public PlayerEvents(Main main) {this.main = main;}

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent e) {
        main.getStats().removePlayer();
        Player player = e.getPlayer();
        if (PlayerManager.isInModerationMod(player)) {
            main.getPlayerManager().setModerationMod(player, false);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        main.playerStats.addKills(e.getEntity().getKiller());
        main.playerStats.addDeaths(e.getEntity());
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        main.getChatHistory().addMessage(new ChatHistoryManager(player.getUniqueId().toString(), player.getName(), e.getMessage()));
        if (!e.isCancelled()) {
            if (main.getStaffChat().contains(e.getPlayer().getUniqueId())) {
                e.setCancelled(true);
                main.getChatManager().sendMessageToStaff("§2StaffChat §a" + player.getName() + " §f» §b" + e.getMessage());
            }
        }
    }
}
