package fr.osalys.plugin.listeners;

import fr.osalys.plugin.managers.PlayerManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class OnBlockPlace implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        e.setCancelled(PlayerManager.isFreeze(e.getPlayer()) || PlayerManager.isInModerationMod(e.getPlayer()) && e.getBlock().getType() == Material.PACKED_ICE);
    }

}
