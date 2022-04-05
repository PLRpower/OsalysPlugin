package fr.exolia.plugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.exolia.plugin.Main;

public class StaffCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("§4Exolia §f>> §cSeul un joueur peut executer cette commande.");
            return false;
        }

        Player player = (Player)sender;

        if (label.equalsIgnoreCase("mod")) {
            if (!player.hasPermission("exolia.mod")) {
                player.sendMessage("§4Exolia §f §cVous n'avez pas la permission d'executer cette commande.");
                return false;
            }
            if (Main.getInstance().moderateurs.contains(player.getUniqueId())) {
                Main.getInstance().moderateurs.remove(player.getUniqueId());
                player.getInventory().clear();
                player.sendMessage("§4Exolia §f §cVous avez désactivé le mode modération.");
                return false;

            }

            Main.getInstance().moderateurs.add(player.getUniqueId());
            player.sendMessage("§2Exolia §f §aVous avez activé le mode modération.");
        }

        return false;

    }
}
