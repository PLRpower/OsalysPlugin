package fr.exolia.plugin.listeners;

import fr.exolia.plugin.Main;
import fr.exolia.plugin.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class PlayerJoin implements Listener {

    private final Main main = Main.getInstance();
    private final Plugin plugin = main;

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        main.stats.addPlayers(1);
        Player player = e.getPlayer();
        for(Player players : Bukkit.getOnlinePlayers()){
            if(PlayerManager.isInModerationMod(players)){
                PlayerManager pm = PlayerManager.getFromPlayer(players);
                if(pm.isVanished()){
                    player.hidePlayer(Bukkit.getPluginManager().getPlugin("Essentials"), players);
                }
            }
        }
        if(!player.hasPermission(main.permissionHStaff)){
            Bukkit.getScheduler().runTaskLater(this.plugin, () -> main.chatManager.clearChatForOnePlayer(player), 100);
        }
    }
}
