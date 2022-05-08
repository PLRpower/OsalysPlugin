package fr.exolia.plugin;

import com.zaxxer.hikari.HikariDataSource;
import fr.exolia.plugin.commands.HStaffCommands;
import fr.exolia.plugin.commands.PublicCommands;
import fr.exolia.plugin.commands.StaffCommands;
import fr.exolia.plugin.database.MySQL;
import fr.exolia.plugin.database.Reports;
import fr.exolia.plugin.gui.ReportGui;
import fr.exolia.plugin.listeners.*;
import fr.exolia.plugin.managers.ChatManager;
import fr.exolia.plugin.managers.GuiManager;
import fr.exolia.plugin.managers.PlayerManager;
import fr.exolia.plugin.util.GuiBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.*;

public class Main extends JavaPlugin {

    private static Main instance;

    private MySQL mysql;
    private MySQL mysql2;

    private final Reports reports = new Reports();
    public final ChatManager chatManager = new ChatManager();

    public ArrayList<UUID> moderators = new ArrayList<>();
    public ArrayList<UUID> staffchat = new ArrayList<>();
    public HashMap<UUID, PlayerManager> players = new HashMap<>();
    private final Map<UUID, Location> freezedPlayers = new HashMap<>();

    private GuiManager guiManager;
    private Map<Class<? extends GuiBuilder>, GuiBuilder> registeredMenus;

    public String PrefixInfo = "§a§lExolia §f§l➜ §7";
    public String PrefixError = "§c§lExolia §f§l➜ §c";
    public String PrefixAnnounce = "§a§lExolia §f§l➜ §a";

    @Override
    public void onEnable() {
        getLogger().info(PrefixInfo + "Activation du plugin en cours ...");
        instance = this;
        initConnection();
        registerCommands();
        registerEvents();
        loadGui();
        getLogger().info(PrefixAnnounce + "Le plugin s'est correctement activé.");
    }

    private void initConnection() {
        HikariDataSource c1 = new HikariDataSource();
        c1.setDriverClassName("com.mysql.jdbc.Driver");
        c1.setUsername("PLR");
        c1.setPassword("@4qkVi06&");
        c1.setJdbcUrl("jdbc:mysql://45.76.45.183:3306/site?autoReconnect=true");
        c1.setMaxLifetime(600000L);
        c1.setIdleTimeout(300000L);
        c1.setLeakDetectionThreshold(300000L);
        c1.setConnectionTimeout(1000L);
        mysql = new MySQL(c1);
        mysql.createTables();

        HikariDataSource c2 = new HikariDataSource();
        c2.setDriverClassName("com.mysql.jdbc.Driver");
        c2.setUsername("u12749_OwD4gPJ6L7");
        c2.setPassword("FMavi6!z^X@n.cd4XlEehhwL");
        c2.setJdbcUrl("jdbc:mysql://45.140.165.82:3306/s12749_site?autoReconnect=true");
        c2.setMaxLifetime(600000L);
        c2.setIdleTimeout(300000L);
        c2.setLeakDetectionThreshold(300000L);
        c2.setConnectionTimeout(1000L);
        mysql2 = new MySQL(c2);
    }

    @Override
    public void onDisable() {
        getLogger().info(PrefixInfo + "Désactivation du plugin en cours ...");
        Bukkit.getOnlinePlayers().stream().filter(PlayerManager::isInModerationMod).forEach(p -> PlayerManager.getFromPlayer(p).destroyModerationMod());
        getLogger().info(PrefixAnnounce + "Le plugin s'est correctement désactivé.");
    }

    private void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new ModItemsInteract(), this);
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
        getCommand("report").setExecutor(new PublicCommands());
        getCommand("mod").setExecutor(new StaffCommands());
        getCommand("sc").setExecutor(new StaffCommands());
        getCommand("history").setExecutor(new StaffCommands());
        getCommand("jm").setExecutor(new StaffCommands());
        getCommand("clearchat").setExecutor(new StaffCommands());
        getCommand("exolion").setExecutor(new PublicCommands());
        getCommand("exolionadmin").setExecutor(new HStaffCommands());
        getCommand("nv").setExecutor(new PublicCommands());
        getCommand("freco").setExecutor(new HStaffCommands());
        getCommand("fdeco").setExecutor(new HStaffCommands());
    }




    public static Main getInstance() {return instance;}

    private void loadGui() {
        guiManager = new GuiManager();
        Bukkit.getPluginManager().registerEvents(guiManager, this);
        registeredMenus = new HashMap<>();
        guiManager.addMenu(new ReportGui());

    }

    public GuiManager getGuiManager() {
        return guiManager;
    }
    public Map<Class<? extends GuiBuilder>, GuiBuilder> getRegisteredMenus() {
        return registeredMenus;
    }

    public MySQL getMySQL() {return mysql;}
    public MySQL getMySQL2() {return mysql2;}

    public Reports getReports() {return reports;}

    public Map<UUID, PlayerManager> getPlayers() {return players;}
    public List<UUID> getModerators() {return moderators;}
    public List<UUID> getStaffChat() {return staffchat;}
    public Map<UUID, Location> getFrozenPlayers() {return freezedPlayers;}
}