package fr.osalys.plugin.listeners;

import fr.osalys.plugin.Main;
import fr.osalys.plugin.managers.ChatHistoryManager;
import fr.osalys.plugin.managers.PlayerManager;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class PlayerEvents implements Listener {

    private final Main main = Main.getInstance();
    private final Plugin plugin = main;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e){
        main.getStats().addPlayer();
        Player player = e.getPlayer();
        e.setJoinMessage(PlaceholderAPI.setPlaceholders(e.getPlayer(), "%player_ping% &aà rejoins le serveur! They are rank &f%vault_rank%"));
        if(PlayerManager.isInModerationMod(player)){
            main.getPlayerManager().setVanish(player, true);
        }
        if(!player.hasPermission(main.permissionHStaff)){
            Bukkit.getScheduler().runTaskLater(this.plugin, () -> main.getChatManager().clearChatForOnePlayer(player), 100);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent e){
        main.getStats().removePlayer();
        Player player = e.getPlayer();
        if(PlayerManager.isInModerationMod(player)){
            main.getPlayerManager().setModerationMod(player, false);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        Main.getInstance().playerStats.addKills(e.getEntity().getKiller());
        Main.getInstance().playerStats.addDeaths(e.getEntity());
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player player = e.getPlayer();
        main.getChatHistory().addMessage(new ChatHistoryManager(player.getUniqueId().toString(), player.getName(), e.getMessage()));
        if(!e.isCancelled()){
            if(main.getStaffChat().contains(e.getPlayer().getUniqueId())){
                e.setCancelled(true);
                main.getChatManager().sendMessageToStaff("§2StaffChat §a" + player.getName() + " §f» §b" + e.getMessage());
            }
        }
    }
}
