package fr.osalys.plugin.managers;

import fr.osalys.plugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {

    private final Main main;
    private final Player player;

    public CommandManager(Player player, Main main) {
        this.main = main;
        this.player = player;
    }

    /**
     * Retourne la liste de tout les joueurs connectés (sans celui qui exécute la commande).
     */
    public List<String> getAllPlayers(boolean withMe) {
        List<String> playerNames = new ArrayList<>();
        Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
        Bukkit.getServer().getOnlinePlayers().toArray(players);
        for (Player player : players) {
            playerNames.add(player.getName());
        }
        if (!withMe) {
            playerNames.remove(player.getName());
        }
        return playerNames;
    }

    /**
     * Retourne la liste de tout les joueurs connectés (sans le staff et sans celui qui exécute la commande).
     */
    public List<String> getPlayersOnly(boolean withMe) {
        List<String> playerNames = new ArrayList<>();
        Bukkit.getOnlinePlayers().stream().filter(players -> !players.hasPermission(main.permissionStaff))
                .forEach(players -> playerNames.add(String.valueOf(players)));
        if (!withMe) {
            playerNames.remove(player.getName());
        }
        return playerNames;
    }

}
