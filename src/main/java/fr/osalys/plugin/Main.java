package fr.osalys.plugin;

import com.zaxxer.hikari.HikariDataSource;
import fr.osalys.plugin.commands.*;
import fr.osalys.plugin.database.*;
import fr.osalys.plugin.gui.ReportGui;
import fr.osalys.plugin.listeners.ModEvents;
import fr.osalys.plugin.listeners.OnJoinEvent;
import fr.osalys.plugin.listeners.PlayerEvents;
import fr.osalys.plugin.managers.*;
import fr.osalys.plugin.tablist.TablistManager;
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

    public Map<Class<? extends GuiBuilder>, GuiBuilder> registeredMenus = new HashMap<>();
    public ArrayList<UUID> moderators = new ArrayList<>();
    public ArrayList<UUID> staffChat = new ArrayList<>();
    public Map<UUID, Location> frozenPlayers = new HashMap<>();
    public ArrayList<UUID> vanished = new ArrayList<>();
    public FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(getFile("mysql"));
    public TablistManager tablistManager;
    public boolean chatDisabled;
    public PlayerStats playerStats;
    public ChatManager chatManager;
    public PlayerManager playerManager;
    public GuiManager guiManager;
    public CommandManager commandManager;
    public ModerationManager moderationManager;
    public Reports reports;
    public Stats stats;
    public Exolions exolions;
    public ChatHistory chatHistory;
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
    public MySQL mysql;
    public MySQL mysql2;


    /**
     * Fonction apellée lors du démarage.
     */

    @Override
    public void onEnable() {
        playerManager = new PlayerManager(this);
        chatManager = new ChatManager(this);
        playerStats = new PlayerStats(this);
        tablistManager = new TablistManager(this);
        guiManager = new GuiManager(this);
        commandManager = new CommandManager(this);
        moderationManager = new ModerationManager(this);
        reports = new Reports(this);
        stats = new Stats(this);
        exolions = new Exolions(this);
        chatHistory = new ChatHistory(this);
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
     * Fonction apellée lors de l'arêt.
     */

    @Override
    public void onDisable() {
        getLogger().info(prefixInfo + "Désactivation du plugin en cours ...");
        Bukkit.getOnlinePlayers().stream().filter(PlayerManager::isInModerationMod).forEach(p -> this.getPlayerManager().setModerationMod(p, false));
        getStats().setOnlinePlayers(0);
        getLogger().info(prefixAnnounce + "Le plugin s'est correctement désactivé.");
    }

    /**
     * Permet de connecter le plugin aux différentes bases de données.
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
     * Permet d'activer tout les listeners.
     */

    private void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new ModEvents(this), this);
        pm.registerEvents(new PlayerEvents(this), this);
        pm.registerEvents(new OnJoinEvent(this), this);
    }

    /**
     * Permet d'activer toutes les commandes.
     */

    private void registerCommands() {
        Objects.requireNonNull(getCommand("discord")).setExecutor(new AliasesCommands());
        Objects.requireNonNull(getCommand("hub")).setExecutor(new AliasesCommands());
        Objects.requireNonNull(getCommand("end")).setExecutor(new AliasesCommands());
        Objects.requireNonNull(getCommand("nether")).setExecutor(new AliasesCommands());
        Objects.requireNonNull(getCommand("website")).setExecutor(new WebsiteCommand());
        Objects.requireNonNull(getCommand("vote")).setExecutor(new VoteCommand());
        Objects.requireNonNull(getCommand("rules")).setExecutor(new RulesCommand());
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
        Objects.requireNonNull(getCommand("moderationmod")).setTabCompleter(new ModerationModCommand(this));
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

    public CommandManager getCommandManager() {
        return commandManager;
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

    public FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }

    public TablistManager getTablistManager() {
        return tablistManager;
    }

    public PlayerStats getPlayer() {
        return playerStats;
    }

    public boolean isChatDisabled() {
        return chatDisabled;
    }

    public ModerationManager getModerationManager() {
        return moderationManager;
    }

}