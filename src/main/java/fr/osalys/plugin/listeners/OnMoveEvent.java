package fr.osalys.plugin.listeners;

import fr.osalys.plugin.managers.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class OnMoveEvent implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (PlayerManager.isFreeze(e.getPlayer())) {
            e.setTo(e.getFrom());
        }
    }

}
