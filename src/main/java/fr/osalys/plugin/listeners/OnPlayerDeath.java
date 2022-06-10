package fr.osalys.plugin.listeners;

import fr.osalys.plugin.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class OnPlayerDeath implements Listener {

    private final Main main;
    public OnPlayerDeath(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        main.playerStats.addKills(e.getEntity().getKiller());
        main.playerStats.addDeaths(e.getEntity());
    }

}
