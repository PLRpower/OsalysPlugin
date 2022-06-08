package fr.osalys.plugin;

import com.zaxxer.hikari.HikariDataSource;
import fr.osalys.plugin.commands.HStaffCommands;
import fr.osalys.plugin.commands.PublicCommands;
import fr.osalys.plugin.commands.StaffCommands;
import fr.osalys.plugin.database.*;
import fr.osalys.plugin.gui.ReportGui;
import fr.osalys.plugin.listeners.ModEvents;
import fr.osalys.plugin.listeners.PlayerEvents;
import fr.osalys.plugin.managers.ChatManager;
import fr.osalys.plugin.managers.GuiManager;
import fr.osalys.plugin.managers.PlayerManager;
import fr.osalys.plugin.util.GuiBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main extends JavaPlugin implements Listener {

    private static Main instance;
    private final Map<Class<? extends GuiBuilder>, GuiBuilder> registeredMenus = new HashMap<>();
    private final ArrayList<UUID> moderators = new ArrayList<>();
    private final ArrayList<UUID> staffChat = new ArrayList<>();
    private final Map<UUID, Location> frozenPlayers = new HashMap<>();
    private final ArrayList<UUID> vanished = new ArrayList<>();
    private final FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(getFile("mysql"));
    public PlayerStats playerStats = new PlayerStats();
    public ChatManager chatManager = new ChatManager();
    public PlayerManager playerManager = new PlayerManager();
    public GuiManager guiManager = new GuiManager();
    public Reports reports = new Reports();
    public Stats stats = new Stats();
    public Exolions exolions = new Exolions();
    public ChatHistory chatHistory = new ChatHistory();
    public String prefixInfo = "§a§lExolia §8§l➜ §7";
    public String prefixError = "§c§lExolia §8§l➜ §c";
    public String prefixAnnounce = "§2§lExolia §8§l➜ §a";
    public String permissionStaff = "exolia.staff";
    public String permissionHStaff = "exolia.hstaff";
    public String permissionModerateur = "exolia.moderator";
    public String permissionSupreme = "exolia.supreme";
    public String permssionEmpereur = "exolia.empereur";
    public String permissionSeigneur = "exolia.seigneur";
    public String permissionMaitre = "exolia.maitre";
    public String permissionChevalier = "exolia.chevalier";
    private MySQL mysql;
    private MySQL mysql2;

    /**
     * <hr>
     * <br>
     * Initialisation des getters afin de pouvoir les utiliser dans d'autres classes.
     */

    public static Main getInstance() {
        return instance;
    }

    /**
     * <hr>
     * <br>
     * Fonction apellée lors du démarage.
     */

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info(prefixInfo + "Activation du plugin en cours ...");
        registerCommands();
        createFile("mysql");
        fileConfiguration.addDefault("mysql.host", "127.0.0.1");
        fileConfiguration.addDefault("mysql.user", "user");
        fileConfiguration.addDefault("mysql.password", "password");
        fileConfiguration.addDefault("mysql.dbname", "db");
        fileConfiguration.addDefault("mysql.port", "3306");

        fileConfiguration.addDefault("mysql2.host", "127.0.0.1");
        fileConfiguration.addDefault("mysql2.user", "user");
        fileConfiguration.addDefault("mysql2.password", "password");
        fileConfiguration.addDefault("mysql2.dbname", "db");
        fileConfiguration.addDefault("mysql2.port", "3306");

        fileConfiguration.options().copyDefaults(true);
        try {
            fileConfiguration.save(getFile("mysql"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        registerEvents();
        initConnection();
        getChatManager().autoBroadcast();
        loadGui();
        getStats().setOnlinePlayers(getStats().getOnlinePlayers());
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            getLogger().warning("Impossible de trouver PlaceholderAPI! Le plugin est requis.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        if (Bukkit.getPluginManager().isPluginEnabled(this)) {
            getLogger().info(prefixAnnounce + "Le plugin s'est correctement activé.");
        }
    }

    /**
     * <hr>
     * <br>
     * Fonction apellée lors de l'arêt.
     */

    @Override
    public void onDisable() {
        getLogger().info(prefixInfo + "Désactivation du plugin en cours ...");
        Bukkit.getOnlinePlayers().stream().filter(PlayerManager::isInModerationMod).forEach(p -> Main.getInstance().getPlayerManager().setModerationMod(p, false));
        getStats().setOnlinePlayers(0);
        getLogger().info(prefixAnnounce + "Le plugin s'est correctement désactivé.");
    }

    /**
     * <hr>
     * <br>
     * Permet de connecter le plugin aux différentes bases de données.
     * <br>
     * <br>
     * /!\ Ne pas partagez ce code avec n'importe qui !
     */

    private void initConnection() {
        HikariDataSource c1 = new HikariDataSource();
        c1.setDriverClassName("com.mysql.jdbc.Driver");
        c1.setUsername(fileConfiguration.getString("mysql.user"));
        c1.setPassword(fileConfiguration.getString("mysql.password"));
        c1.setJdbcUrl("jdbc:mysql://" + fileConfiguration.getString("mysql.host") + ":" + fileConfiguration.getString("mysql.port") + "/" + fileConfiguration.getString("mysql.dbname") + "?autoReconnect=true");
        c1.setMaxLifetime(600000L);
        c1.setLeakDetectionThreshold(300000L);
        c1.setConnectionTimeout(1000L);
        mysql = new MySQL(c1);
        mysql.createTables();

        HikariDataSource c2 = new HikariDataSource();
        c2.setDriverClassName("com.mysql.jdbc.Driver");
        c2.setUsername(fileConfiguration.getString("mysql2.user"));
        c2.setPassword(fileConfiguration.getString("mysql2.password"));
        c2.setJdbcUrl("jdbc:mysql://" + fileConfiguration.getString("mysql2.host") + ":" + fileConfiguration.getString("mysql2.port") + "/" + fileConfiguration.getString("mysql2.dbname") + "?autoReconnect=true");
        c2.setMaxLifetime(600000L);
        c2.setLeakDetectionThreshold(300000L);
        c2.setConnectionTimeout(1000L);
        mysql2 = new MySQL(c2);
    }

    /**
     * <hr>
     * <br>
     * Permet d'activer tout les listeners.
     */

    private void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new ModEvents(), this);
        pm.registerEvents(new ModEvents(), this);
        pm.registerEvents(new PlayerEvents(), this);
        pm.registerEvents(new GuiManager(), this);
    }

    /**
     * <hr>
     * <br>
     * Permet d'activer toutes les commandes.
     */

    private void registerCommands() {
        Objects.requireNonNull(getCommand("site")).setExecutor(new PublicCommands());
        Objects.requireNonNull(getCommand("vote")).setExecutor(new PublicCommands());
        Objects.requireNonNull(getCommand("reglement")).setExecutor(new PublicCommands());
        Objects.requireNonNull(getCommand("discord")).setExecutor(new PublicCommands());
        Objects.requireNonNull(getCommand("hub")).setExecutor(new PublicCommands());
        Objects.requireNonNull(getCommand("report")).setExecutor(new PublicCommands());
        Objects.requireNonNull(getCommand("mod")).setExecutor(new StaffCommands());
        Objects.requireNonNull(getCommand("sc")).setExecutor(new StaffCommands());
        Objects.requireNonNull(getCommand("history")).setExecutor(new StaffCommands());
        Objects.requireNonNull(getCommand("jm")).setExecutor(new StaffCommands());
        Objects.requireNonNull(getCommand("clearchat")).setExecutor(new StaffCommands());
        Objects.requireNonNull(getCommand("exolion")).setExecutor(new PublicCommands());
        Objects.requireNonNull(getCommand("exolionadmin")).setExecutor(new HStaffCommands());
        Objects.requireNonNull(getCommand("nv")).setExecutor(new PublicCommands());
        Objects.requireNonNull(getCommand("freeze")).setExecutor(new StaffCommands());
        Objects.requireNonNull(getCommand("freco")).setExecutor(new HStaffCommands());
        Objects.requireNonNull(getCommand("fdeco")).setExecutor(new HStaffCommands());
        Objects.requireNonNull(getCommand("date")).setExecutor(new PublicCommands());
    }

    /**
     * <hr>
     * <br>
     * Permet d'activer tout les menus.
     */

    private void loadGui() {
        getGuiManager().addMenu(new ReportGui());
    }

    private void createFile(String fileName) {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        File file = new File(getDataFolder(), fileName + ".yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public File getFile(String fileName) {
        return new File(getDataFolder(), fileName + ".yml");
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }

    public Map<Class<? extends GuiBuilder>, GuiBuilder> getRegisteredMenus() {
        return registeredMenus;
    }

    public MySQL getMySQL() {
        return mysql;
    }

    public MySQL getMySQL2() {
        return mysql2;
    }

    public ChatManager getChatManager() {
        return chatManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public Reports getReports() {
        return reports;
    }

    public List<UUID> getModerators() {
        return moderators;
    }

    public List<UUID> getStaffChat() {
        return staffChat;
    }

    public Map<UUID, Location> getFrozenPlayers() {
        return frozenPlayers;
    }

    public List<UUID> getVanished() {
        return vanished;
    }

    public Stats getStats() {
        return stats;
    }

    public Exolions getExolions() {
        return exolions;
    }

    public ChatHistory getChatHistory() {
        return chatHistory;
    }

    public PlayerStats getPlayer() {
        return playerStats;
    }
}