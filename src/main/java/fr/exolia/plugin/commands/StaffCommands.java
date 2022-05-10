package fr.exolia.plugin.commands;

import fr.exolia.plugin.managers.PlayerManager;
import fr.exolia.plugin.managers.ReportManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.exolia.plugin.Main;

import java.util.List;

public class StaffCommands implements CommandExecutor {

    private final Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){

        Player player = (Player) sender;
        if(!player.hasPermission(main.permissionStaff)){
            player.sendMessage(main.prefixError + "Vous n'avez pas la permission d'éxecuter cette commande !");
            return false;
        }else{
            if(label.equalsIgnoreCase("mod")){

                if(!player.hasPermission(main.permissionModerator)) {
                    player.sendMessage(main.prefixError + "Vous n'avez pas la permission d'éxecuter cette commande !");
                    return false;
                }

                if(PlayerManager.isInModerationMod(player)){
                    main.getPlayerManager().destroyModerationMod(player);
                } else {
                    main.getPlayerManager().initModerationMod(player);
                }
                return true;
            }

            if(label.equalsIgnoreCase("freeze")){

                if(!player.hasPermission(main.permissionModerator)){
                    player.sendMessage(main.prefixError + "Vous n'avez pas la permission d'éxecuter cette commande !");
                    return false;
                }

                if(args.length != 1){
                    player.sendMessage(main.prefixError + "Veuillez saisir un joueur !");
                    return false;
                }

                Player target = Bukkit.getPlayer(args[0]);

                if(target == null){
                    player.sendMessage(main.prefixError + "Ce joueur n'est pas connecté ou n'existe pas !");
                    return false;
                }

                main.getPlayerManager().setFreeze(target, player, PlayerManager.isFreeze(target));
                return true;
            }

            if(label.equalsIgnoreCase("sc")){

                if(args.length == 0) {
                    main.getPlayerManager().setStaffChat(player, PlayerManager.isStaffChat(player));
                    return true;
                }

                StringBuilder sb = new StringBuilder();
                for (String arg : args) {sb.append(arg); sb.append(" ");}
                main.chatManager.sendMessageToStaff(player, sb.toString());
                return true;
            }

            if(label.equalsIgnoreCase("history")){

                if(args.length != 1) {
                    player.sendMessage(main.prefixError + "Veuillez saisir le pseudo d'un joueur !");
                    return false;
                }

                Player target = Bukkit.getPlayer(args[0]);
                List<ReportManager> reports = main.getReports().getReports(target.getUniqueId().toString());
                if(reports.isEmpty()) {
                    player.sendMessage(main.prefixError + "Ce joueur n'a aucun signalement");
                } else {
                    player.sendMessage(main.prefixInfo + "Voici la liste des signalements de §b" + target.getName() + "§7:");
                    reports.forEach(r -> player.sendMessage("§f" + r.getDate() + "§fSignalé par :" + r.getAuthor() + " §fpour la raison :" + r.getReason()));
                }
                return true;
            }

            if(label.equalsIgnoreCase("jm")){

                if(args.length != 1){
                    player.sendMessage(main.prefixError + "Veuillez saisir le pseudo d'un joueur !");
                    return false;
                }

                Player target = Bukkit.getPlayer(args[0]);
                if(target == null){
                    player.sendMessage(main.prefixError + "Ce joueur n'est pas connecté ou n'existe pas !");
                    return false;
                }

                if(target == player) {
                    player.sendMessage(main.prefixError + "Vous ne pouvez pas vous occuper de vous-même !");
                    return false;
                }

                main.chatManager.sendMessageToStaff(player, " s'occupe de modérer de " + target.getName());
                return true;
            }

            if(label.equalsIgnoreCase("clearchat")){
                if(args.length == 0){
                    main.chatManager.clearChatForAll();
                    Bukkit.broadcastMessage(main.prefixAnnounce + "Le chat vient d'être nettoyé par un Modérateur.");
                    return true;
                } else {
                    if(args.length == 1){
                        if(args[0].equalsIgnoreCase("player")){
                            for(Player players : Bukkit.getOnlinePlayers()){
                                if(!players.hasPermission(main.permissionStaff)){
                                    main.chatManager.clearChatForPlayersOnly();
                                    players.sendMessage(main.prefixAnnounce + "Le chat vient d'être nettoyé par un Administrateur.");
                                    return true;
                                }
                            }
                        } else if(args[0].equalsIgnoreCase("all")){
                            main.chatManager.clearChatForAll();
                            Bukkit.broadcastMessage(main.prefixAnnounce + "Le chat vient d'être nettoyé par un Administrateur.");
                            return true;
                        } else {
                            main.chatManager.sendClearChatErrorMessage(player);
                            return false;
                        }
                    } else if(args.length == 2){
                        Player target = Bukkit.getPlayerExact(args[1]);

                        if(target == null){
                            player.sendMessage(main.prefixError + "Ce joueur n'existe pas ou n'est pas connecté.");
                            return false;
                        }

                        if(args[0].equalsIgnoreCase("player")){
                            main.chatManager.clearChatForOnePlayer(target);
                            target.sendMessage(main.prefixAnnounce + "Ton chat vient d'être nettoyé par §b" + player.getName() + "§a.");
                            return true;
                        } else {
                            main.chatManager.sendClearChatErrorMessage(player);
                            return false;
                        }
                    } else {
                        main.chatManager.sendClearChatErrorMessage(player);
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
}