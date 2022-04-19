package fr.exolia.plugin.commands;

import fr.exolia.plugin.managers.PlayerManager;
import fr.exolia.plugin.managers.Report;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.exolia.plugin.Main;

import java.util.List;

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
                PlayerManager.getFromPlayer(player).destroyModerationMod();
            } else {
                new PlayerManager(player).initModerationMod();
            }
            return true;
        }

        if(label.equalsIgnoreCase("sc")) {
            if(!player.hasPermission("exolia.staff")) {
                player.sendMessage(Main.PrefixError + "Vous n'avez pas la permission d'éxecuter cette commande !");
                return false;
            }

            if(args.length == 0) {
                if(PlayerManager.isInStaffChat(player)) {
                    Main.getInstance().staffchat.remove(player.getUniqueId());
                    player.sendMessage(Main.PrefixInfo + "StaffChat §cdésactivé§7.");
                    return false;
                }

                Main.getInstance().staffchat.add(player.getUniqueId());
                player.sendMessage( Main.PrefixInfo + "StaffChat §aactivé§7.");
                return false;
            }

            StringBuilder sb = new StringBuilder();
            for (String arg : args) {
                sb.append(arg);
                sb.append(" ");
            }
            String combinedArgs = sb.toString();
            Bukkit.getOnlinePlayers().stream().filter(players -> players.hasPermission("exolia.staff")).forEach(players -> players.sendMessage("§2StaffChat §a" + player.getName() + " §f» §b" + combinedArgs));
            return true;
        }

        if(label.equalsIgnoreCase("history")) {

            if(!player.hasPermission("exolia.staff")) {
                player.sendMessage(Main.PrefixError + "Vous n'avez pas la permission d'éxecuter cette commande !");
                return false;
            }

            if(args.length != 1) {
                player.sendMessage(Main.PrefixError + "Veuillez saisir le pseudo d'un joueur !");
                return false;
            }

            Player target = Bukkit.getPlayer(args[0]);
            List<Report> reports = Main.getInstance().getReports().getReports(target.getUniqueId().toString());

            if(reports.isEmpty()) {
                player.sendMessage(Main.PrefixError + "Ce joueur n'a aucun signalement");
            } else {
                player.sendMessage(Main.PrefixInfo + "Voici la liste des signalements de §b" + target.getName() + "§7:");
                reports.forEach(r -> player.sendMessage("§f" + r.getDate() + "§fSignalé par :" + r.getAuthor() + " §fpour la raison :" + r.getReason()));
            }
        }


        return false;

    }
}