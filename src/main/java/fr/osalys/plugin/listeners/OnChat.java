package fr.osalys.plugin.listeners;

import fr.osalys.plugin.Main;
import fr.osalys.plugin.managers.ChatHistoryManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class OnChat implements Listener {

    private final Main main;
    public OnChat(Main main) {
        this.main = main;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        if (main.getModerationManager().chatDisabled) {
            e.setCancelled(true);
        }
        main.getChatHistory().addMessage(new ChatHistoryManager(player.getUniqueId().toString(), player.getName(), e.getMessage()));
        if (!e.isCancelled()) {
            if (main.getStaffChat().contains(e.getPlayer().getUniqueId())) {
                e.setCancelled(true);
                main.getChatManager().sendMessageToStaff("§2StaffChat §a" + player.getName() + " §f» §b" + e.getMessage());
            }
        }
    }
}
