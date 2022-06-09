package fr.osalys.plugin.managers;

import fr.osalys.plugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ModerationManager {

    private final Main main;
    public ModerationManager(Main main) {this.main = main;}

    public void kickPlayer(Player player, String reason) {
        player.kickPlayer(reason);
    }

    public void kickAll(String reason) {
        Bukkit.getServer().getOnlinePlayers().forEach(player -> player.kickPlayer(reason));
    }

    public void kickNonPermissed(String permission, String reason) {
        Bukkit.getServer().getOnlinePlayers().stream()
                .filter(player -> !player.hasPermission(permission))
                .forEach(player -> player.kickPlayer(reason));
    }

    public void setMaintenance(boolean maintenance, String reason) {
        if (maintenance) {
            Bukkit.getServer().setWhitelist(true);
            Bukkit.getServer().getOnlinePlayers().stream()
                    .filter(player -> !player.isOp() && !player.isWhitelisted())
                    .forEach(player -> player.kickPlayer(reason));
        } else {
            Bukkit.getServer().setWhitelist(false);
        }
    }

    public void muteChat(boolean muteChat) {
        main.chatDisabled = muteChat;
        if(muteChat) {
            Bukkit.broadcastMessage("Le chat à été désactivé");
        } else {
            Bukkit.broadcastMessage("Le chat à été activé");
        }
    }
}
