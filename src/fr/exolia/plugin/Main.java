package fr.exolia.plugin;

import fr.exolia.plugin.commands.PublicCommands;
import fr.exolia.plugin.mysql.MySQL;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.ArrayList;
import java.util.UUID;

public class Main extends JavaPlugin {

    private static Main instance;
    public MySQL mysql = new MySQL();
    public ArrayList<UUID> moderateurs = new ArrayList<>();
    public static String PrefixInfo = "§aExolia §f» §7";
    public static String PrefixError = "§2Exolia §f» §c";
    public static String PrefixAnnounce = "§2Exolia §f» §a";

    @Override
    public void onEnable() {
        System.out.println(PrefixInfo + "Activation du plugin en cours ...");
        instance = this;
        mysql.connect("45.76.45.183", 3306, "site", "PLR", "@4qkVi06&");
        getCommand("site").setExecutor(new PublicCommands());
        getCommand("vote").setExecutor(new PublicCommands());
        getCommand("reglement").setExecutor(new PublicCommands());
        getCommand("discord").setExecutor(new PublicCommands());
        getCommand("hub").setExecutor(new PublicCommands());
        getCommand("hoteldevente").setExecutor(new PublicCommands());
        System.out.println(PrefixAnnounce + "Le plugin s'est correctement activé.");
    }

    @Override
    public void onDisable() {
        System.out.println(PrefixInfo + "Désactivation du plugin en cours ...");
        mysql.disconnect();
        System.out.println(PrefixAnnounce + "Le plugin s'est correctement désactivé.");
    }


    public static Main getInstance() {
        return instance;
    }
}
