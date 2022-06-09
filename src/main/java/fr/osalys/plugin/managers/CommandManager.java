package fr.osalys.plugin.managers;

import fr.osalys.plugin.Main;
import org.bukkit.entity.Player;

public class CommandManager {

    private final Main main;
    public CommandManager(Main main) {this.main = main;}

    /**
     * Permet de vérifier si un joueur à une permission
     *
     * @param player     joueur à vérifier
     * @param permission permission à vérifier
     */
    public boolean isPermissed(Player player, String permission) {
        if (!player.hasPermission(permission)) {
            player.sendMessage(main.prefixError + "Vous n'avez pas la permission d'exécuter cette commande !");
            return false;
        }
        return true;
    }
}
