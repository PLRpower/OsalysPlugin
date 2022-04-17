package fr.exolia.plugin;

import com.zaxxer.hikari.HikariDataSource;
import fr.exolia.plugin.commands.PublicCommands;
import fr.exolia.plugin.commands.StaffCommands;
import fr.exolia.plugin.database.MySQL;
import fr.exolia.plugin.database.Reports;
import fr.exolia.plugin.listeners.ModCancels;
import fr.exolia.plugin.listeners.ModItemsInteract;
import fr.exolia.plugin.listeners.PlayerChat;
import fr.exolia.plugin.listeners.ReportEvents;
import fr.exolia.plugin.managers.PlayerManager;
import fr.exolia.plugin.managers.Report;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class Main extends JavaPlugin {

    private static Main instance;

    private HikariDataSource connectionPool;
    private MySQL mysql;
    private Reports reports;

    public ArrayList<UUID> moderators = new ArrayList<>();
    public ArrayList<UUID> staffchat = new ArrayList<>();
    public HashMap<UUID, PlayerManager> players = new HashMap<>();
    private Map<UUID, Location> freezedPlayers = new HashMap<>();

    public static String PrefixInfo = "§a§lExolia §f§l» §7";
    public static String PrefixError = "§4§lExolia §f§l» §c";
    public static String PrefixAnnounce = "§2§lExolia §f§l» §a";

    @Override
    public void onEnable() {
        System.out.println(PrefixInfo + "Activation du plugin en cours ...");
        instance = this;
        reports = new Reports();
        initConnection();
        registerCommands();
        registerEvents();
        System.out.println(PrefixAnnounce + "Le plugin s'est correctement activé.");
    }

    private void initConnection() {
        connectionPool = new HikariDataSource();
        connectionPool.setDriverClassName("com.mysql.jdbc.Driver");
        connectionPool.setUsername("PLR");
        connectionPool.setPassword("@4qkVi06&");
        connectionPool.setJdbcUrl("jdbc:mysql://45.76.45.183:33ç06/site?autoReconnect=true");
        connectionPool.setMaxLifetime(600000L);
        connectionPool.setIdleTimeout(300000L);
        connectionPool.setLeakDetectionThreshold(300000L);
        connectionPool.setConnectionTimeout(1000L);
        mysql = new MySQL(connectionPool);
        mysql.createTables();
    }

    @Override
    public void onDisable() {
        System.out.println(PrefixInfo + "Désactivation du plugin en cours ...");
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
    }

    public static Main getInstance() {return instance;}
    public Reports getReports() {return reports;}
    public MySQL getMySQL() {return mysql;}
    public List<UUID> getModerators() {return moderators;}
    public Map<UUID, PlayerManager> getPlayers() {return players;}
    public Map<UUID, Location> getFrozenPlayers() {return freezedPlayers;}
    public boolean isFreeze(Player player) {return getFrozenPlayers().containsKey(player.getUniqueId());}
}