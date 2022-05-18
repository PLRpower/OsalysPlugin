package fr.exolia.plugin;

import com.zaxxer.hikari.HikariDataSource;
import fr.exolia.plugin.commands.HStaffCommands;
import fr.exolia.plugin.commands.PublicCommands;
import fr.exolia.plugin.commands.StaffCommands;
import fr.exolia.plugin.database.MySQL;
import fr.exolia.plugin.database.Reports;
import fr.exolia.plugin.gui.ReportGui;
import fr.exolia.plugin.listeners.*;
import fr.exolia.plugin.managers.AutoBroadcast;
import fr.exolia.plugin.managers.ChatManager;
import fr.exolia.plugin.managers.GuiManager;
import fr.exolia.plugin.managers.PlayerManager;
import fr.exolia.plugin.database.Stats;
import fr.exolia.plugin.util.GuiBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.*;

public class Main extends JavaPlugin {

    private static Main instance;

    public ChatManager chatManager = new ChatManager();
    public Stats stats = new Stats();
    public PlayerManager playerManager = new PlayerManager();
    public Reports reports = new Reports();

    private MySQL mysql;
    private MySQL mysql2;

    private final GuiManager guiManager = new GuiManager();
    private final Map<Class<? extends GuiBuilder>, GuiBuilder> registeredMenus = new HashMap<>();
    private final ArrayList<UUID> moderators = new ArrayList<>();
    private final ArrayList<UUID> staffChat = new ArrayList<>();
    private final Map<UUID, Location> frozenPlayers = new HashMap<>();
    private final ArrayList<UUID> vanished = new ArrayList<>();

    public String prefixInfo = "§a§lExolia §8§l➜ §7";
    public String prefixError = "§c§lExolia §8§l➜ §c";
    public String prefixAnnounce = "§2§lExolia §8§l➜ §a";

    public String permissionStaff = "exolia.staff";
    public String permissionHStaff = "exolia.hstaff";
    public String permissionModerator = "exolia.moderator";


    /**<hr>
     * <br>
     * Fonction apellée lors du démarage.
     */

    @Override
    public void onEnable(){
        getLogger().info(prefixInfo + "Activation du plugin en cours ...");
        instance = this;
        initConnection();
        registerCommands();
        registerEvents();
        //loadGui();
        getChatManager().autoBroadcast();
        new AutoBroadcast().Minuterie();
        //getStats().setOnlinePlayers(0);
        getLogger().info(prefixAnnounce + "Le plugin s'est correctement activé.");
    }

    /**<hr>
     * <br>
     * Fonction apellée lors de l'arêt.
     */

    @Override
    public void onDisable(){
        getLogger().info(prefixInfo + "Désactivation du plugin en cours ...");
        Bukkit.getOnlinePlayers().stream().filter(PlayerManager::isInModerationMod).forEach(p -> Main.getInstance().getPlayerManager().setModerationMod(p, false));
        //getStats().setOnlinePlayers(0);
        getLogger().info(prefixAnnounce + "Le plugin s'est correctement désactivé.");
    }

    /**<hr>
     * <br>
     * Permet de connecter le plugin aux différentes bases de données.
     * <br>
     * <br>
     * /!\ Ne pas partagez ce code avec n'importe qui !
     */

    private void initConnection(){
        HikariDataSource c1 = new HikariDataSource();
        c1.setDriverClassName("com.mysql.jdbc.Driver");
        c1.setUsername("u12749_Y7S0i006dF");
        c1.setPassword("NMv22^!45sUrNexU!.asU19b");
        c1.setJdbcUrl("jdbc:mysql://45.140.165.82:3306/s12749_serveur?autoReconnect=true");
        c1.setMaxLifetime(600000L);
        c1.setIdleTimeout(300000L);
        c1.setLeakDetectionThreshold(300000L);
        c1.setConnectionTimeout(1000L);
        mysql = new MySQL(c1);
        mysql.createTables();

        HikariDataSource c2 = new HikariDataSource();
        c2.setDriverClassName("com.mysql.jdbc.Driver");
        c2.setUsername("PLR");
        c2.setPassword("60xxdZ$40");
        c2.setJdbcUrl("jdbc:mysql://45.76.45.183:3306/exolia_website?autoReconnect=true");
        c2.setMaxLifetime(600000L);
        c2.setIdleTimeout(300000L);
        c2.setLeakDetectionThreshold(300000L);
        c2.setConnectionTimeout(1000L);
        mysql2 = new MySQL(c2);
    }

    /**<hr>
     * <br>
     * Permet d'activer tout les listeners.
     */

    private void registerEvents(){
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new ModItemsInteract(), this);
        pm.registerEvents(new ModCancels(), this);
        pm.registerEvents(new PlayerChat(), this);
        pm.registerEvents(new PlayerQuit(), this);
        pm.registerEvents(new PlayerJoin(), this);
        pm.registerEvents(new GuiManager(), this);
    }

    /**<hr>
     * <br>
     * Permet d'activer toutes les commandes.
     */

    private void registerCommands(){
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
        getCommand("freeze").setExecutor(new StaffCommands());
        getCommand("freco").setExecutor(new HStaffCommands());
        getCommand("fdeco").setExecutor(new HStaffCommands());
        getCommand("date").setExecutor(new PublicCommands());
    }

    /**<hr>
     * <br>
     * Permet d'activer tout les menus.
     */

    private void loadGui(){
        getGuiManager().addMenu(new ReportGui());
    }

    /**<hr>
     * <br>
     * Initialisation des getters afin de pouvoir les utiliser dans d'autres classes.
     */

    public static Main getInstance() {
        return instance;
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
}