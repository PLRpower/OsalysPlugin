package fr.osalys.plugin.commands;

import fr.osalys.plugin.managers.PlayerManager;
import fr.osalys.plugin.managers.ReportManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.osalys.plugin.Main;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StaffCommands implements CommandExecutor {

    private final Main main = Main.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {

        Player player = (Player) sender;
        if (!player.hasPermission(main.permissionStaff)) {
            player.sendMessage(main.prefixError + "Vous n'avez pas la permission d'éxecuter cette commande !");
            return false;
        } else {

            if (label.equalsIgnoreCase("mod")) {

                if (!player.hasPermission(main.permissionModerateur)) {
                    player.sendMessage(main.prefixError + "Vous n'avez pas la permission d'éxecuter cette commande !");
                    return false;
                }

                main.getPlayerManager().setModerationMod(player, !PlayerManager.isInModerationMod(player));
                return true;
            }

            if (label.equalsIgnoreCase("freeze")) {

                if (!player.hasPermission(main.permissionModerateur)) {
                    player.sendMessage(main.prefixError + "Vous n'avez pas la permission d'éxecuter cette commande !");
                    return false;
                }

                if (args.length != 1) {
                    player.sendMessage(main.prefixError + "Veuillez saisir un joueur !");
                    return false;
                }

                Player target = Bukkit.getPlayer(args[0]);

                if (target == null) {
                    player.sendMessage(main.prefixError + "Ce joueur n'est pas connecté ou n'existe pas !");
                    return false;
                }

                main.getPlayerManager().setFreeze(target, player, !PlayerManager.isFreeze(target));
                return true;
            }

            if (label.equalsIgnoreCase("sc")) {

                if (args.length == 0) {
                    main.getPlayerManager().setStaffChat(player, !PlayerManager.isStaffChat(player));
                    return true;
                }

                StringBuilder sb = new StringBuilder();
                for (String arg : args) {
                    sb.append(arg);
                    sb.append(" ");
                }
                main.getChatManager().sendMessageToStaff("§2StaffChat §a" + player.getName() + " §f» §b" + sb);
                return true;
            }

            if (label.equalsIgnoreCase("history")) {

                if (args.length != 1) {
                    player.sendMessage(main.prefixError + "Veuillez saisir le pseudo d'un joueur !");
                    return false;
                }

                Player target = Bukkit.getPlayer(args[0]);
                assert target != null;
                List<ReportManager> reports = main.getReports().getReports(target.getUniqueId().toString());
                if (reports.isEmpty()) {
                    player.sendMessage(main.prefixInfo + "Ce joueur n'a aucun signalement");
                } else {
                    player.sendMessage(main.prefixInfo + "Voici la liste des signalements de §b" + target.getName() + "§7:");
                    reports.forEach(r -> player.sendMessage("§f" + r.getDate() + "§fSignalé par :" + r.getAuthor() + " §fpour la raison :" + r.getReason()));
                }
                return true;
            }

            if (label.equalsIgnoreCase("jm")) {

                if (args.length != 1) {
                    player.sendMessage(main.prefixError + "Veuillez saisir le pseudo d'un joueur !");
                    return false;
                }

                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    player.sendMessage(main.prefixError + "Ce joueur n'est pas connecté ou n'existe pas !");
                    return false;
                }

                if (target == player) {
                    player.sendMessage(main.prefixError + "Vous ne pouvez pas vous occuper de vous-même !");
                    return false;
                }

                main.getChatManager().sendMessageToStaff(player.getName() + " s'occupe de modérer de " + target.getName());
                return true;
            }

            if (label.equalsIgnoreCase("clearchat")) {
                if (args.length == 0) {
                    main.getChatManager().clearChatForAll();
                    return true;
                }

                if (args.length == 1){
                    if(args[0].equalsIgnoreCase("player")){
                        main.getChatManager().clearChatForPlayersOnly();
                    } else {
                        Player target = Bukkit.getPlayerExact(args[0]);
                        if (target == null) {
                            player.sendMessage(main.prefixError + "Ce joueur n'existe pas ou n'est pas connecté.");
                            return false;
                        }

                        main.getChatManager().clearChatForOnePlayer(target);
                        return true;
                    }
                } else {
                    main.getChatManager().sendClearChatErrorMessage(player);
                    return false;
                }
            }
        }
        return false;
    }
}