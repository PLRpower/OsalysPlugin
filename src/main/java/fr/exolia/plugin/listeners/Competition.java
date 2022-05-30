package fr.exolia.plugin.listeners;

import fr.exolia.plugin.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class Competition implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        Player killed = e.getEntity();
        Player killer = e.getEntity().getKiller();
        Main.getInstance().playerStats.addKills(killer);
        Main.getInstance().playerStats.addMorts(killed);
    }

}

