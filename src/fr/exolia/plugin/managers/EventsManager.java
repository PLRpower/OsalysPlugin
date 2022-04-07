package fr.exolia.plugin.managers;

import fr.exolia.plugin.Main;
import fr.exolia.plugin.listeners.InventoryClick;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class EventsManager {

    public void registers() {
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new InventoryClick(), Main.getInstance());
    }
}
