package fr.exolia.plugin;

import com.zaxxer.hikari.HikariDataSource;
import fr.exolia.plugin.commands.HStaffCommands;
import fr.exolia.plugin.commands.PublicCommands;
import fr.exolia.plugin.commands.StaffCommands;
import fr.exolia.plugin.database.MySQL;
import fr.exolia.plugin.database.Reports;
import fr.exolia.plugin.listeners.*;
import fr.exolia.plugin.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.*;

public class Main extends JavaPlugin {

    private static Main instance;

    private MySQL mysql;
    private MySQL mysql2;

    private Reports reports;

    public ArrayList<UUID> moderators = new ArrayList<>();
    public ArrayList<UUID> staffchat = new ArrayList<>();
    public HashMap<UUID, PlayerManager> players = new HashMap<>();
    private final Map<UUID, Location> freezedPlayers = new HashMap<>();

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
        HikariDataSource connectionpool = new HikariDataSource();
        connectionpool.setDriverClassName("com.mysql.jdbc.Driver");
        connectionpool.setUsername("PLR");
        connectionpool.setPassword("@4qkVi06&");
        connectionpool.setJdbcUrl("jdbc:mysql://45.76.45.183:3306/site?autoReconnect=true");
        connectionpool.setMaxLifetime(600000L);
        connectionpool.setIdleTimeout(300000L);
        connectionpool.setLeakDetectionThreshold(300000L);
        connectionpool.setConnectionTimeout(1000L);
        mysql = new MySQL(connectionpool);
        mysql.createTables();

        HikariDataSource connectionpool2 = new HikariDataSource();
        connectionpool2.setDriverClassName("com.mysql.jdbc.Driver");
        connectionpool2.setUsername("u12749_OwD4gPJ6L7");
        connectionpool2.setPassword("FMavi6!z^X@n.cd4XlEehhwL");
        connectionpool2.setJdbcUrl("jdbc:mysql://45.140.165.82:3306/s12749_site?autoReconnect=true");
        connectionpool2.setMaxLifetime(600000L);
        connectionpool2.setIdleTimeout(300000L);
        connectionpool2.setLeakDetectionThreshold(300000L);
        connectionpool2.setConnectionTimeout(1000L);
        mysql2 = new MySQL(connectionpool2);
    }

    @Override
    public void onDisable() {
        System.out.println(PrefixInfo + "Désactivation du plugin en cours ...");
        Bukkit.getOnlinePlayers().stream().filter(PlayerManager::isInModerationMod).forEach(p -> PlayerManager.getFromPlayer(p).destroyModerationMod());
        System.out.println(PrefixAnnounce + "Le plugin s'est correctement désactivé.");
    }

    private void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new ModItemsInteract(), this);
        pm.registerEvents(new ReportEvents(), this);
        pm.registerEvents(new ModCancels(), this);
        pm.registerEvents(new PlayerChat(), this);
        pm.registerEvents(new PlayerQuit(), this);
        pm.registerEvents(new PlayerJoin(), this);
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
        getCommand("history").setExecutor(new StaffCommands());
        getCommand("jm").setExecutor(new StaffCommands());
        getCommand("clearchat").setExecutor(new StaffCommands());
        getCommand("exolion").setExecutor(new PublicCommands());
        getCommand("exolionadmin").setExecutor(new HStaffCommands());
    }

    public static Main getInstance() {return instance;}
    public Reports getReports() {return reports;}
    public MySQL getMySQL() {return mysql;}
    public MySQL getMySQL2() {return mysql2;}
    public List<UUID> getModerators() {return moderators;}
    public Map<UUID, PlayerManager> getPlayers() {return players;}
    public Map<UUID, Location> getFrozenPlayers() {return freezedPlayers;}
    public boolean isFreeze(Player player) {return getFrozenPlayers().containsKey(player.getUniqueId());}
}