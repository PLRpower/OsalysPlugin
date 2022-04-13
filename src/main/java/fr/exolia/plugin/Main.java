package fr.exolia.plugin;

import fr.exolia.plugin.commands.PublicCommands;
import fr.exolia.plugin.commands.StaffCommands;
import fr.exolia.plugin.listeners.ModCancels;
import fr.exolia.plugin.listeners.ModItemsInteract;
import fr.exolia.plugin.listeners.PlayerChat;
import fr.exolia.plugin.listeners.ReportEvents;
import fr.exolia.plugin.managers.PlayerManager;
import fr.exolia.plugin.mysql.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class Main extends JavaPlugin {

    private static Main instance;

    public MySQL mysql = new MySQL();
    public ArrayList<UUID> moderators = new ArrayList<>();
    public ArrayList<UUID> staffchat = new ArrayList<>();
    public HashMap<UUID, PlayerManager> players = new HashMap<>();
    private Map<UUID, Location> freezedPlayers = new HashMap<>();
    public static String PrefixInfo = "§a§lExolia §f§l» §7";
    public static String PrefixError = "§4§lExolia §f§l» §c";
    public static String PrefixAnnounce = "§2§lExolia §f§l» §a";

    public static Main getInstance() {return instance;}

    @Override
    public void onEnable() {
        System.out.println(PrefixInfo + "Activation du plugin en cours ...");
        instance = this;
        registerCommands();
        registerEvents();
        mysql.connect("45.76.45.183", 3306, "site", "PLR", "@4qkVi06&");
        System.out.println(PrefixAnnounce + "Le plugin s'est correctement activé.");
    }

    @Override
    public void onDisable() {
        System.out.println(PrefixInfo + "Désactivation du plugin en cours ...");
        mysql.disconnect();
        System.out.println(PrefixAnnounce + "Le plugin s'est correctement désactivé.");
    }

    private void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new ModItemsInteract(), this);
        pm.registerEvents(new ReportEvents(), this);
        pm.registerEvents(new ModCancels(), this);
        pm.registerEvents(new PlayerChat(), this);
    }

    private void registerCommands() {
        getCommand("site").setExecutor(new PublicCommands());
        getCommand("vote").setExecutor(new PublicCommands());
        getCommand("reglement").setExecutor(new PublicCommands());
        getCommand("discord").setExecutor(new PublicCommands());
        getCommand("hub").setExecutor(new PublicCommands());
        getCommand("hoteldevente").setExecutor(new PublicCommands());
        getCommand("report").setExecutor(new PublicCommands());
        getCommand("mod").setExecutor(new StaffCommands());
        getCommand("sc").setExecutor(new StaffCommands());
        getCommand("jm").setExecutor(new StaffCommands());
    }

    public List<UUID> getModerators() {return moderators;}
    public Map<UUID, PlayerManager> getPlayers() {return players;}
    public Map<UUID, Location> getFrozenPlayers() {return freezedPlayers;}
    /*
    TODO
     */
    public boolean isFreeze(Player player) {
        return getFrozenPlayers().containsKey(player.getUniqueId());
    }
}