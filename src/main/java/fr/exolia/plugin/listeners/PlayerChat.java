package fr.exolia.plugin.listeners;

import fr.exolia.plugin.Main;
import fr.exolia.plugin.managers.ChatHistoryManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChat implements Listener {

    private final Main main = Main.getInstance();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player player = e.getPlayer();
        main.getChatHistory().addMessage(new ChatHistoryManager(player.getUniqueId().toString(), player.getName(), e.getMessage()));
        if(!e.isCancelled()){
            if(e.getMessage().startsWith("$") && player.hasPermission("exolia.staff")){
                e.setCancelled(true);
                main.getChatManager().sendMessageToStaff(player, e.getMessage().substring(1));
                return;
            }
            if(main.getStaffChat().contains(e.getPlayer().getUniqueId())){
                e.setCancelled(true);
                main.getChatManager().sendMessageToStaff(player, e.getMessage());
            }
        }
    }
}
