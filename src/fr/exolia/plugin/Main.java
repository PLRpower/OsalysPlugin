package fr.exolia.plugin;

import fr.exolia.plugin.commands.Commands;
import fr.exolia.plugin.commands.PublicCommands;
import fr.exolia.plugin.listeners.ModItemsInteract;
import fr.exolia.plugin.listeners.ReportEvents;
import fr.exolia.plugin.listeners.ModCancels;
import fr.exolia.plugin.managers.PlayerManager;
import fr.exolia.plugin.mysql.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Main extends JavaPlugin {

    private static Main instance;
    public MySQL mysql = new MySQL();
    public ArrayList<UUID> moderateurs = new ArrayList<>();
    public HashMap<UUID, PlayerManager> players = new HashMap<>();

    @Override
    public void onEnable() {
        System.out.println("§2Exolia §f» §aActivation du plugin en cours ...");
        instance = this;
        registerEvents();
        mysql.connect("45.76.45.183", 3306, "site", "PLR", "@4qkVi06&");
        getCommand("site").setExecutor(new PublicCommands());
        getCommand("vote").setExecutor(new PublicCommands());
        getCommand("reglement").setExecutor(new PublicCommands());
        getCommand("discord").setExecutor(new PublicCommands());
        getCommand("hub").setExecutor(new PublicCommands());
        getCommand("hoteldevente").setExecutor(new PublicCommands());
        getCommand("mod").setExecutor(new Commands());
        getCommand("report").setExecutor(new Commands());
        System.out.println("§2Exolia §f» §aLe plugin s'est correctement activé.");
    }

    @Override
    public void onDisable() {
        System.out.println("§2Exolia §f» §aDésactivation du plugin en cours ...");
        mysql.disconnect();
        System.out.println("§2Exolia §f» §aLe plugin s'est correctement désactivé.");
    }


    public static Main getInstance() {
        return instance;
    }

    private void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new ModItemsInteract(), this);
        pm.registerEvents(new ReportEvents(), this);
        pm.registerEvents(new ModCancels(), this);
    }
}
