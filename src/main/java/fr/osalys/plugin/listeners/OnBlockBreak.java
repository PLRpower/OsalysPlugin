package fr.osalys.plugin.listeners;

import fr.osalys.plugin.managers.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class OnBlockBreak implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        e.setCancelled(PlayerManager.isFreeze(e.getPlayer()));
    }

}
