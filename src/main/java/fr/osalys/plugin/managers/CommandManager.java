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
    public CommandManager(Main main) {
        this.main = main;
    }

    /**
     * Retourne la liste de tout les joueurs connectés (sans celui qui exécute la commande).
     *
     * @param sender Joueur qui exécute la commande
     */
    public List<String> getAllPlayers(String sender) {
        List<String> playerNames = new ArrayList<>();
        Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
        Bukkit.getServer().getOnlinePlayers().toArray(players);
        for (Player player : players) {
            playerNames.add(player.getName());
        }
        playerNames.remove(sender);
        return playerNames;
    }

    /**
     * Retourne la liste de tout les joueurs connectés (sans le staff et sans celui qui exécute la commande).
     *
     * @param sender Joueur qui exécute la commande
     */
    public List<String> getPlayersOnly(String sender) {
        List<String> playerNames = new ArrayList<>();
        Bukkit.getOnlinePlayers().stream().filter(players -> !players.hasPermission(main.permissionStaff))
                .forEach(players -> playerNames.add(String.valueOf(players)));
        playerNames.remove(sender);
        return playerNames;
    }

    /**
     * Permet de vérifier si un joueur à une permission.
     *
     * @param player     joueur à vérifier
     * @param permission permission à vérifier
     */
    public boolean isPermissed(Player player, String permission) {
        if(player.hasPermission(permission)) {
            return true;
        }
        player.sendMessage("Vous n'avez pas la permission d'exécuter cette commande !");
        return false;
    }

    public boolean isOnline(Player player, Player target) {
        if (target == null) {
            player.sendMessage(main.prefixError + "Ce joueur n'existe pas ou n'est pas connecté !");
            return false;
        }
        return true;
    }
}
