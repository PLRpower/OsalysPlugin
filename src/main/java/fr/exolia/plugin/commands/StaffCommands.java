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

    private final Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(main.PrefixError + "Seul un joueur peut executer cette commande.");
            return false;
        }

        Player player = (Player) sender;

        if(!player.hasPermission("exolia.staff")) {
            player.sendMessage(main.PrefixError + "Vous n'avez pas la permission d'éxecuter cette commande !");
        }else{
            if(label.equalsIgnoreCase("mod")) {

                if(!player.hasPermission("exolia.moderateur")) {
                    player.sendMessage(main.PrefixError + "Vous n'avez pas la permission d'éxecuter cette commande !");
                    return false;
                }

                if(PlayerManager.isInModerationMod(player)) {
                    PlayerManager.getFromPlayer(player).destroyModerationMod();
                } else {
                    new PlayerManager(player).initModerationMod();
                }
                return true;
            }

            if(label.equalsIgnoreCase("freeze")) {

                if(!player.hasPermission("exolia.moderateur")) {
                    player.sendMessage(main.PrefixError + "Vous n'avez pas la permission d'éxecuter cette commande !");
                    return false;
                }

                if(args.length != 1) {
                    player.sendMessage(main.PrefixError + "Veuillez saisir un joueur !");
                    return false;
                }

                Player target = Bukkit.getPlayer(args[0]);

                if(target == null){
                    player.sendMessage(main.PrefixError + "Ce joueur n'est pas connecté ou n'existe pas !");
                    return false;
                }

                if(PlayerManager.isFreeze(target)) {
                    main.getFrozenPlayers().remove(target.getUniqueId());
                    target.sendMessage(main.PrefixInfo + "Vous avez été unfreeze par un modérateur");
                    player.sendMessage(main.PrefixInfo + "Vous avez unfreeze" + target.getName());
                } else {
                    main.getFrozenPlayers().put(target.getUniqueId(), target.getLocation());
                    target.sendMessage(main.PrefixInfo + "Vous avez été freeze par un modérateur");
                    player.sendMessage(main.PrefixInfo + "Vous avez freeze" + target.getName());
                }
                return true;
            }

            if(label.equalsIgnoreCase("sc")) {

                if(args.length == 0) {
                    if(main.staffchat.contains(player.getUniqueId())) {
                        PlayerManager.getFromPlayer(player).destroyStaffChat();
                    } else {
                        new PlayerManager(player).initStaffChat();
                    }
                    return true;
                }

                StringBuilder sb = new StringBuilder();
                for (String arg : args) {sb.append(arg); sb.append(" ");}
                String combinedArgs = sb.toString();
                Bukkit.getOnlinePlayers().stream().filter(players -> players.hasPermission("exolia.staff")).forEach(players -> players.sendMessage("§2StaffChat §a" + player.getName() + " §f» §b" + combinedArgs));
                return true;
            }

            if(label.equalsIgnoreCase("history")) {

                if(args.length != 1) {
                    player.sendMessage(main.PrefixError + "Veuillez saisir le pseudo d'un joueur !");
                    return false;
                }

                Player target = Bukkit.getPlayer(args[0]);
                List<Report> reports = main.getReports().getReports(target.getUniqueId().toString());
                if(reports.isEmpty()) {
                    player.sendMessage(main.PrefixError + "Ce joueur n'a aucun signalement");
                } else {
                    player.sendMessage(main.PrefixInfo + "Voici la liste des signalements de §b" + target.getName() + "§7:");
                    reports.forEach(r -> player.sendMessage("§f" + r.getDate() + "§fSignalé par :" + r.getAuthor() + " §fpour la raison :" + r.getReason()));
                }
                return true;
            }

            if(label.equalsIgnoreCase("jm")) {

                if(args.length != 1){
                    player.sendMessage(main.PrefixError + "Veuillez saisir le pseudo d'un joueur !");
                    return false;
                }

                Player target = Bukkit.getPlayer(args[0]);
                if(target == null){
                    player.sendMessage(main.PrefixError + "Ce joueur n'est pas connecté ou n'existe pas !");
                    return false;
                }

                if(target == player) {
                    player.sendMessage(main.PrefixError + "Vous ne pouvez pas vous occuper de vous-même !");
                    return false;
                }

                Bukkit.getOnlinePlayers().stream().filter(players -> players.hasPermission("exolia.staff")).forEach(players -> players.sendMessage(player.getName()+" s'occupe de modérer de " + target.getName()));
                return true;
            }

            if(label.equalsIgnoreCase("clearchat")) {
                if(args.length == 0){
                    main.chatManager.clearChatForAll();
                    Bukkit.broadcastMessage("§c§lExolia §8➜ §cLe chat vient d'être nettoyé par un Administrateur.");
                }else{
                    if(args.length == 1){
                        if(args[0].equalsIgnoreCase("player")){
                            for(Player players : Bukkit.getOnlinePlayers()){
                                if(!players.hasPermission("exolia.staff")){
                                    main.chatManager.clearChatForPlayersOnly();
                                    players.sendMessage("§c§lExolia §8➜ §cLe chat vient d'être nettoyé par un Administrateur.");
                                }
                            }
                        }else if(args[0].equalsIgnoreCase("all")){
                            main.chatManager.clearChatForAll();
                            Bukkit.broadcastMessage("§c§lExolia §8➜ §cLe chat vient d'être nettoyé par un Administrateur.");
                        }else{
                            main.chatManager.sendClearChatErrorMessage(player);
                            return false;
                        }
                    }else if(args.length == 2){
                        Player target = Bukkit.getPlayerExact(args[1]);

                        if(target == null){
                            player.sendMessage("§c§lExolia §8➜ §cCe joueur n'existe pas ou n'est pas connecté.");
                            return false;
                        }

                        if(args[0].equalsIgnoreCase("player")){
                            main.chatManager.clearChatForOnePlayer(target);
                            target.sendMessage("§c§lExolia §8➜ §cTon chat vient d'être nettoyé par §f" + player.getName() + "§c.");
                        }else{
                            main.chatManager.sendClearChatErrorMessage(player);
                            return false;
                        }
                    }else{
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