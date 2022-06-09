package fr.osalys.plugin.listeners;

import fr.osalys.plugin.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class OnPlayerChatEvent implements Listener {

    private final Main main;
    public OnPlayerChatEvent(Main main) {this.main = main;}

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        if (main.chatDisabled) {
            event.setCancelled(true);
        }
    }
}
