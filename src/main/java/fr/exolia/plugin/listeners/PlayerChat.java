package fr.exolia.plugin.listeners;

import fr.exolia.plugin.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChat implements Listener {

    private final Main main = Main.getInstance();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player player = e.getPlayer();
        if(!e.isCancelled()){
            if(e.getMessage().startsWith("$") && player.hasPermission("exolia.staff")){
                e.setCancelled(true);
                main.chatManager.sendMessageToStaff(player, e.getMessage().substring(1));
                return;
            }
            if(main.getStaffChat().contains(e.getPlayer().getUniqueId())){
                e.setCancelled(true);
                main.chatManager.sendMessageToStaff(player, e.getMessage());
            }
        }
    }
}
