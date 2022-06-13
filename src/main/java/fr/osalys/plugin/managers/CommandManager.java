package fr.osalys.plugin.managers;

import fr.osalys.plugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public List<String> getAllPlayers() {
        List<String> playerNames = new ArrayList<>();
        Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
        Bukkit.getServer().getOnlinePlayers().toArray(players);
        for (Player player : players) {
            playerNames.add(player.getName());
        }
        playerNames.remove(player.getName());
        return playerNames;
    }

    /**
     * Retourne la liste de tout les joueurs connectés (sans le staff et sans celui qui exécute la commande).
     */
    public List<String> getPlayersOnly() {
        List<String> playerNames = new ArrayList<>();
        Bukkit.getOnlinePlayers().stream().filter(players -> !players.hasPermission(main.permissionStaff))
                .forEach(players -> playerNames.add(String.valueOf(players)));
        playerNames.remove(player.getName());
        return playerNames;
    }

}
