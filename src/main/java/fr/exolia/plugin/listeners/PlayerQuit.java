package fr.exolia.plugin.listeners;

import fr.exolia.plugin.Main;
import fr.exolia.plugin.managers.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit  implements Listener {

    private final Main main = Main.getInstance();

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        main.stats.removePlayers(1);
        Player player = e.getPlayer();
        if(PlayerManager.isInModerationMod(player)){
            PlayerManager.getFromPlayer(player).destroyModerationMod();
        }
    }
}
