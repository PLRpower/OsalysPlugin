package fr.osalys.plugin.listeners;

import fr.osalys.plugin.Main;
import fr.osalys.plugin.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.net.http.WebSocket;

public class OnJoin implements WebSocket.Listener, Listener {

    private final Main main;

    public OnJoin(Main main) {
        this.main = main;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        main.getTablistManager().setPlayerList(player);
        main.getStats().addPlayer();
        if (PlayerManager.isInModerationMod(player)) {
            main.getPlayerManager().setVanish(player, true);
        }
        if (!player.hasPermission(main.permissionHStaff)) {
            Bukkit.getScheduler().runTaskLater(this.main, () -> main.getChatManager().clearChatForOnePlayer(player), 100);
        }
        main.getTablistManager().setPlayerList(player);
        main.getTablistManager().setPlayerTeams(player);
    }
}
