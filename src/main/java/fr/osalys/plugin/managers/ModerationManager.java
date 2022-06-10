package fr.osalys.plugin.managers;

import fr.osalys.plugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;

import static org.bukkit.Bukkit.getServer;

public class ModerationManager {

    private final Main main;
    public boolean chatDisabled = false;

    public ModerationManager(Main main) {
        this.main = main;
    }

    public void kickPlayer(Player player, String reason) {
        player.kickPlayer(reason);
    }

    public void kickAll(String reason) {
        getServer().getOnlinePlayers().forEach(player -> player.kickPlayer(reason));
    }

    public void kickNonPermissed(String permission, String reason) {
        getServer().getOnlinePlayers().stream()
                .filter(player -> !player.hasPermission(permission))
                .forEach(player -> player.kickPlayer(reason));
    }

    public void setMaintenance(boolean maintenance, String reason) {
        if (maintenance) {
            getServer().setWhitelist(true);
            getServer().getOnlinePlayers().stream()
                    .filter(player -> !player.isOp() && !player.isWhitelisted())
                    .forEach(player -> player.kickPlayer(reason));
        } else {
            getServer().setWhitelist(false);
        }
    }

    public void muteChat(boolean muteChat) {
        chatDisabled = muteChat;
        if (muteChat) {
            Bukkit.broadcastMessage("Le chat à été désactivé");
        } else {
            Bukkit.broadcastMessage("Le chat à été activé");
        }
    }

    public void banIp(Player player) {
        if (player.isOnline()) {
            getServer().banIP(Objects.requireNonNull(player.getAddress()).toString());
        } else {
            getServer().banIP(player.getName());
        }
    }

    public void banPlayer(Player player) {

    }

}
