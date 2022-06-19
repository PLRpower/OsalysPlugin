package fr.osalys.plugin;

import com.zaxxer.hikari.HikariDataSource;
import fr.osalys.plugin.commands.*;
import fr.osalys.plugin.database.*;
import fr.osalys.plugin.discord.Discord;
import fr.osalys.plugin.gui.ReportGui;
import fr.osalys.plugin.listeners.*;
import fr.osalys.plugin.managers.ChatManager;
import fr.osalys.plugin.managers.GuiManager;
import fr.osalys.plugin.managers.ModerationManager;
import fr.osalys.plugin.managers.PlayerManager;
import fr.osalys.plugin.tablist.TablistManager;
import fr.osalys.plugin.util.GuiBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main extends JavaPlugin implements Listener {

    public final String permissionStaff = "exolia.staff";
    public final String permissionHStaff = "exolia.hstaff";
    public final String permissionModerateur = "exolia.moderator";
    public final String permissionSupreme = "exolia.supreme";
    public final String permssionEmpereur = "exolia.empereur";
    public final String permissionSeigneur = "exolia.seigneur";
    public final String permissionMaitre = "exolia.maitre";
    public final String permissionChevalier = "exolia.chevalier";
    private final Map<Class<? extends GuiBuilder>, GuiBuilder> registeredMenus = new HashMap<>();
    private final ArrayList<UUID> moderators = new ArrayList<>();
    private final ArrayList<UUID> staffChat = new ArrayList<>();
    private final Map<UUID, Location> frozenPlayers = new HashMap<>();
    private final ArrayList<UUID> vanished = new ArrayList<>();
    private final FileConfiguration fileConfigurationMysql = YamlConfiguration.loadConfiguration(getFile("mysql"));
    private final FileConfiguration fileConfigurationDiscord = YamlConfiguration.loadConfiguration(getFile("discord"));
    public MySQL mysql;
    public MySQL mysql2;
    private TablistManager tablistManager;
    private PlayerStats playerStats;
    private ChatManager chatManager;
    private PlayerManager playerManager;
    private GuiManager guiManager;
    private ModerationManager moderationManager;
    private Reports reports;
    private Discord discord;
    private Stats stats;
    private Exolions exolions;
    private ChatHistory chatHistory;

    /**
     * Fonction apellée lors du démarage.
     */
    @Override
    public void onEnable() {
        createFileConfigurations();
        chatManager = new ChatManager(this);
        playerManager = new PlayerManager(this);
        playerStats = new PlayerStats(this);
        tablistManager = new TablistManager(this);
        guiManager = new GuiManager(this);
        moderationManager = new ModerationManager();
        reports = new Reports(this);
        stats = new Stats(this);
        exolions = new Exolions(this);
        chatHistory = new ChatHistory(this);
        discord = new Discord(this);
        try {
            getDiscord().initDiscord();
        } catch (LoginException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        getLogger().info(getChatManager().prefixInfo + "Activation du plugin en cours ...");
        registerCommands();
        registerEvents();
        initConnection();
        loadGui();
        getChatManager().autoBroadcast();
        getStats().setOnlinePlayers(getStats().getOnlinePlayers());
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            getLogger().warning("Impossible de trouver PlaceholderAPI! Le plugin est requis.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        if (Bukkit.getPluginManager().isPluginEnabled(this)) {
            getLogger().info(getChatManager().prefixAnnounce + "Le plugin s'est correctement activé.");
        }
    }

    /**
     * Fonction apellée lors de l'arêt.
     */
    @Override
    public void onDisable() {
        getLogger().info(getChatManager().prefixInfo + "Désactivation du plugin en cours ...");
        Bukkit.getOnlinePlayers().stream().filter(PlayerManager::isInModerationMod).forEach(p -> this.getPlayerManager().setModerationMod(p, false));
        getStats().setOnlinePlayers(0);
        getLogger().info(getChatManager().prefixAnnounce + "Le plugin s'est correctement désactivé.");
    }

    /**
     * Permet de connecter le plugin aux différentes bases de données.
     */
    private void initConnection() {
        HikariDataSource c1 = new HikariDataSource();
        c1.setDriverClassName("com.mysql.jdbc.Driver");
        c1.setUsername(fileConfigurationMysql.getString("mysql.user"));
        c1.setPassword(fileConfigurationMysql.getString("mysql.password"));
        c1.setJdbcUrl("jdbc:mysql://" + fileConfigurationMysql.getString("mysql.host") + ":" + fileConfigurationMysql.getString("mysql.port") + "/" + fileConfigurationMysql.getString("mysql.dbname") + "?autoReconnect=true");
        c1.setMaxLifetime(600000L);
        c1.setLeakDetectionThreshold(300000L);
        c1.setConnectionTimeout(1000L);
        mysql = new MySQL(c1);
        mysql.createTables();

        HikariDataSource c2 = new HikariDataSource();
        c2.setDriverClassName("com.mysql.jdbc.Driver");
        c2.setUsername(fileConfigurationMysql.getString("mysql2.user"));
        c2.setPassword(fileConfigurationMysql.getString("mysql2.password"));
        c2.setJdbcUrl("jdbc:mysql://" + fileConfigurationMysql.getString("mysql2.host") + ":" + fileConfigurationMysql.getString("mysql2.port") + "/" + fileConfigurationMysql.getString("mysql2.dbname") + "?autoReconnect=true");
        c2.setMaxLifetime(600000L);
        c2.setLeakDetectionThreshold(300000L);
        c2.setConnectionTimeout(1000L);
        mysql2 = new MySQL(c2);
    }

    /**
     * Permet d'activer tout les listeners.
     */
    private void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new OnBlockBreak(), this);
        pm.registerEvents(new OnBlockPlace(), this);
        pm.registerEvents(new OnChat(this), this);
        pm.registerEvents(new OnEntityDamage(), this);
        pm.registerEvents(new OnEntityDamageByEntity(), this);
        pm.registerEvents(new OnInteract(this), this);
        pm.registerEvents(new OnInteractWithEntity(this), this);
        pm.registerEvents(new OnInventoryClick(), this);
        pm.registerEvents(new OnItemDrop(), this);
        pm.registerEvents(new OnItemPickup(), this);
        pm.registerEvents(new OnJoin(this), this);
        pm.registerEvents(new OnMoveEvent(), this);
        pm.registerEvents(new OnPlayerDeath(this), this);
        pm.registerEvents(new OnQuit(this), this);
    }

    /**
     * Permet d'activer toutes les commandes.
     */
    private void registerCommands() {
        Objects.requireNonNull(getCommand("discord")).setExecutor(new AliasesCommands(this));
        Objects.requireNonNull(getCommand("hub")).setExecutor(new AliasesCommands(this));
        Objects.requireNonNull(getCommand("end")).setExecutor(new AliasesCommands(this));
        Objects.requireNonNull(getCommand("nether")).setExecutor(new AliasesCommands(this));
        Objects.requireNonNull(getCommand("website")).setExecutor(new WebsiteCommand(this));
        Objects.requireNonNull(getCommand("vote")).setExecutor(new VoteCommand(this));
        Objects.requireNonNull(getCommand("rules")).setExecutor(new RulesCommand(this));
        Objects.requireNonNull(getCommand("report")).setExecutor(new ReportCommand(this));
        Objects.requireNonNull(getCommand("moderationmod")).setExecutor(new ModerationModCommand(this));
        Objects.requireNonNull(getCommand("staffchat")).setExecutor(new StaffChatCommand(this));
        Objects.requireNonNull(getCommand("history")).setExecutor(new HistoryCommand(this));
        Objects.requireNonNull(getCommand("jm")).setExecutor(new JMCommand(this));
        Objects.requireNonNull(getCommand("clearchat")).setExecutor(new ClearChatCommand(this));
        Objects.requireNonNull(getCommand("exolions")).setExecutor(new ExolionsCommand(this));
        Objects.requireNonNull(getCommand("exolionadmin")).setExecutor(new ExolionAdminCommand(this));
        Objects.requireNonNull(getCommand("nightvision")).setExecutor(new NightVisionCommand(this));
        Objects.requireNonNull(getCommand("freeze")).setExecutor(new FreezeCommand(this));
        Objects.requireNonNull(getCommand("fake")).setExecutor(new FakeConnectionCommand(this));
        Objects.requireNonNull(getCommand("date")).setExecutor(new DateCommand());

        Objects.requireNonNull(getCommand("report")).setTabCompleter(new ReportCommand(this));
        Objects.requireNonNull(getCommand("history")).setTabCompleter(new HistoryCommand(this));
        Objects.requireNonNull(getCommand("jm")).setTabCompleter(new JMCommand(this));
        Objects.requireNonNull(getCommand("clearchat")).setTabCompleter(new ClearChatCommand(this));
        Objects.requireNonNull(getCommand("exolions")).setTabCompleter(new ExolionsCommand(this));
        Objects.requireNonNull(getCommand("exolionadmin")).setTabCompleter(new ExolionAdminCommand(this));
        Objects.requireNonNull(getCommand("nightvision")).setTabCompleter(new NightVisionCommand(this));
        Objects.requireNonNull(getCommand("freeze")).setTabCompleter(new FreezeCommand(this));
        Objects.requireNonNull(getCommand("fake")).setTabCompleter(new FakeConnectionCommand(this));
    }

    /**
     * Permet d'activer tout les menus.
     */
    private void loadGui() {
        getGuiManager().addMenu(new ReportGui(this));
    }

    private void createFileConfigurations() {
        createFile("mysql");
        createFile("discord");

        fileConfigurationMysql.addDefault("mysql.host", "127.0.0.1");
        fileConfigurationMysql.addDefault("mysql.user", "user");
        fileConfigurationMysql.addDefault("mysql.password", "password");
        fileConfigurationMysql.addDefault("mysql.dbname", "db");
        fileConfigurationMysql.addDefault("mysql.port", "3306");
        fileConfigurationMysql.addDefault("mysql2.host", "127.0.0.1");
        fileConfigurationMysql.addDefault("mysql2.user", "user");
        fileConfigurationMysql.addDefault("mysql2.password", "password");
        fileConfigurationMysql.addDefault("mysql2.dbname", "db");
        fileConfigurationMysql.addDefault("mysql2.port", "3306");

        fileConfigurationDiscord.addDefault("discord.token", "Token ici");

        fileConfigurationMysql.options().copyDefaults(true);
        fileConfigurationDiscord.options().copyDefaults(true);
        try {
            fileConfigurationMysql.save(getFile("mysql"));
            fileConfigurationDiscord.save(getFile("discord"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    /**
     * Getters
     */
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

    public FileConfiguration getFileConfigurationMysql() {
        return fileConfigurationMysql;
    }

    public FileConfiguration getFileConfigurationDiscord() {
        return fileConfigurationDiscord;
    }

    public TablistManager getTablistManager() {
        return tablistManager;
    }

    public PlayerStats getPlayer() {
        return playerStats;
    }

    public ModerationManager getModerationManager() {
        return moderationManager;
    }

    public PlayerStats getPlayerStats() {
        return playerStats;
    }

    public Discord getDiscord() {
        return discord;
    }
}