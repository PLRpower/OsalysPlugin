package fr.exolia.plugin;

import fr.exolia.plugin.commands.PublicCommands;
import fr.exolia.plugin.listeners.PlayerChat;
import fr.exolia.plugin.mysql.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.UUID;

public class Main extends JavaPlugin {

    private static Main instance;
    public MySQL mysql = new MySQL();
    public ArrayList<UUID> moderateurs = new ArrayList<>();

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
        System.out.println("§2Exolia §f» §aLe plugin s'est correctement activé.");
    }

    private void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerChat(), this);
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
}
