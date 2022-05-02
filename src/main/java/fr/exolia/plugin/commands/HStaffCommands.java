package fr.exolia.plugin.commands;

import fr.exolia.plugin.Main;
import fr.exolia.plugin.managers.Exolions;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HStaffCommands implements CommandExecutor {

    private final Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player)sender;
        if(!player.hasPermission("exolia.hstaff")) {
            player.sendMessage(main.PrefixError + "Vous n'avez pas la permission d'exécuter cette commande !");
            return false;
        }

        if(label.equalsIgnoreCase("exolionadmin")) {

            if(args.length == 0) {
                player.sendMessage(main.PrefixError + "Veuillez saisir un argument ! §6(give/remove/set)");
            }else{

                Player target = Bukkit.getPlayer(args[1]);
                Exolions exolions = new Exolions(target);

                if(target == null){
                    player.sendMessage(main.PrefixError + "Ce joueur n'est pas connecté ou n'existe pas !");
                    return false;
                }

                if(args.length < 3) {
                    player.sendMessage(main.PrefixError + "Vous devez saisir un joueur ainsi qu'un montant.");
                }else{
                    if(args[0].equalsIgnoreCase("give")) {
                        exolions.addCoins(Integer.parseInt(args[2]));
                    }else if(args[0].equalsIgnoreCase("remove")){
                        exolions.removeCoins(Integer.parseInt(args[2]));
                    }else if(args[0].equalsIgnoreCase("set")){
                        exolions.setCoins(Integer.parseInt(args[2]));
                    }else{
                        player.sendMessage(main.PrefixError + "Veuillez saisir un argument correct ! §6(give/remove/set)");
                        return false;
                    }
                }
                return false;
            }
        }
        return false;
    }
}