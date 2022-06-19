package fr.osalys.plugin.listeners;

import fr.osalys.plugin.Main;
import fr.osalys.plugin.managers.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnQuit implements Listener {

    private final Main main;

    public OnQuit(Main main) {
        this.main = main;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent e) {
        main.getStats().removePlayer();
        Player player = e.getPlayer();
        if (PlayerManager.isInModerationMod(player)) {
            main.getPlayerManager().setModerationMod(player, false);
        }
    }
}
