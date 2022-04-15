package fr.exolia.plugin.commands;

import fr.exolia.plugin.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.exolia.plugin.Main;


public class StaffCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.PrefixError + "Seul un joueur peut executer cette commande.");
            return false;
        }

        Player player = (Player) sender;

        if(label.equalsIgnoreCase("mod")){
            if(!player.hasPermission("exolia.moderateur")) {
                player.sendMessage(Main.PrefixError + "Vous n'avez pas la permission d'éxecuter cette commande !");
                return false;
            }

            if(PlayerManager.isInModerationMod(player)) {
                PlayerManager.getFromPlayer(player).destroy();
            } else {
                new PlayerManager(player).init();
            }
        }

        if(label.equalsIgnoreCase("sc")) {
            if (!player.hasPermission("exolia.staff")) {
                player.sendMessage(Main.PrefixError + "Vous n'avez pas la permission d'éxecuter cette commande !");
                return false;
            }
            if (args.length == 0) {
                if (PlayerManager.isInStaffChat(player)) {
                    Main.getInstance().staffchat.remove(player.getUniqueId());
                    player.sendMessage(Main.PrefixInfo + "StaffChat §cdésactivé§7.");
                    return false;
                }

                Main.getInstance().staffchat.add(player.getUniqueId());
                player.sendMessage(Main.PrefixInfo + "StaffChat §aactivé§7.");
                return false;
            }
        }

        // commande : /jm ******************************************************************************************

        if(label.equalsIgnoreCase("jm")){

            if(!player.hasPermission("exolia.moderateur")) {
                player.sendMessage(Main.PrefixError + "Vous n'avez pas la permission d'éxecuter cette commande !");
                return false;
            }

            if(args.length != 1){
                player.sendMessage(Main.PrefixError + "Veuillez saisir le pseudo d'un joueur !");
                return false;
            }

            String targetName = args[0];
            if(Bukkit.getPlayer(targetName) == null){
                player.sendMessage(Main.PrefixError + "Ce joueur n'est pas connecté ou n'existe pas !");
                return false;
            }

            if(Bukkit.getPlayer(targetName) == player) {
                player.sendMessage(Main.PrefixError + "Vous ne pouvez pas vous occuper de vous-même !");
                return false;
            }

            Bukkit.getOnlinePlayers().stream()
                    .filter(players -> players.hasPermission("exolia.staff"))
                    .forEach(players -> players.sendMessage(player.getName()+"s'occupe de modérer de" + targetName));
        }

        if(label.equalsIgnoreCase("clearchat")) {

            if (!player.hasPermission("exolia.moderateur")) {
                for (int x = 0; x <= 100; x++) {
                    Bukkit.broadcastMessage("");
                    if (x == 100)
                        Bukkit.broadcastMessage("Le chat a bien été supprimé");
                }
            else {
                    player.sendMessage("Tu n'as pas la permission de faire cette commande");
                }
            }
        }

    // *********************************************************************************************************

        return false;

    }
}