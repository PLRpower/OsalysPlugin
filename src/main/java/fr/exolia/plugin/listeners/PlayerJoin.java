package fr.exolia.plugin.listeners;

import fr.exolia.plugin.Main;
import fr.exolia.plugin.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class PlayerJoin implements Listener {

    private final Main main = Main.getInstance();
    private final Plugin plugin = main;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e){
       // main.getStats().addPlayers(1);
        Player player = e.getPlayer();
        if(PlayerManager.isInModerationMod(player)){
            e.setJoinMessage("hey!");
            main.getPlayerManager().setVanish(player, true);
        }
        if(!player.hasPermission(main.permissionHStaff)){
            Bukkit.getScheduler().runTaskLater(this.plugin, () -> main.getChatManager().clearChatForOnePlayer(player), 100);
        }
    }
}
