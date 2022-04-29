package fr.exolia.plugin.commands;

import fr.exolia.plugin.Main;
import fr.exolia.plugin.managers.Exolions;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HStaffCommands implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player)sender;
        if(!player.hasPermission("exolia.hstaff")) {
            player.sendMessage(Main.PrefixError + "Vous n'avez pas la permission d'éxecuter cette commande !");
            return false;
        }

        if(label.equalsIgnoreCase("exolionadmin")) {

            if(args.length == 0) {
                player.sendMessage(Main.PrefixError + "Veuillez saisir un argument ! §6(give/remove/set)");
                return false;
            }

            if(!args[0].equalsIgnoreCase("give") && !args[0].equalsIgnoreCase("remove") && !args[0].equalsIgnoreCase("set")) {
                player.sendMessage(Main.PrefixError + "Veuillez saisir un argument correct ! §6(give/remove/set)");
                return false;
            }

            if(args.length == 1) {
                player.sendMessage(Main.PrefixError + "Veuillez saisir un joueur !");
                return false;
            }

            Player target = Bukkit.getPlayer(args[1]);
            if(target == null){
                player.sendMessage(Main.PrefixError + "Ce joueur n'est pas connecté ou n'existe pas !");
                return false;
            }

            if(args.length == 2) {
                player.sendMessage(Main.PrefixError + "Veuillez saisir un nombre d'exolions !");
                return false;
            }

            Exolions exolions = new Exolions(target);

            if(args[0].equalsIgnoreCase("give")) {
                exolions.addCoins(Integer.parseInt(args[2]));
            }

            if(args[0].equalsIgnoreCase("remove")) {
                exolions.removeCoins(Integer.parseInt(args[2]));
            }

            if(args[0].equalsIgnoreCase("set")) {
                exolions.setCoins(Integer.parseInt(args[2]));
            }
            return true;

        }

        return false;
    }
}
