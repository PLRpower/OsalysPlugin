package fr.exolia.plugin.listeners;

import fr.exolia.plugin.Main;
import fr.exolia.plugin.managers.PlayerManager;
import me.clip.placeholderapi.PlaceholderAPI;
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
       main.getStats().addPlayer();
        Player player = e.getPlayer();
        //e.setJoinMessage(PlaceholderAPI.setPlaceholders(e.getPlayer(), "%player_ping% &aà rejoins le serveur! They are rank &f%vault_rank%"));
        if(PlayerManager.isInModerationMod(player)){
            main.getPlayerManager().setVanish(player, true);
        }
        if(!player.hasPermission(main.permissionHStaff)){
            Bukkit.getScheduler().runTaskLater(this.plugin, () -> main.getChatManager().clearChatForOnePlayer(player), 100);
        }
    }
}
