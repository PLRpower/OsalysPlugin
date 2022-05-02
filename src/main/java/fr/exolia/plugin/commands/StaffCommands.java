package fr.exolia.plugin.commands;

import fr.exolia.plugin.managers.PlayerManager;
import fr.exolia.plugin.managers.Report;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
            return false;
        }

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
            for (int x = 0; x <= 100; x++) {
                Bukkit.broadcastMessage("");
            }
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("nv") && sender instanceof Player) {
            if (!player.hasPermission("nightvision.use")) {
                player.sendMessage(Main.PrefixError + "Tu n'as pas la permission d'utiliser cette commande !");
                return true;
            }
            if (args.length == 0) {
                if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                    player.sendMessage("Night Vision désactivé");
                    player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                    return true;
                }
                player.sendMessage("Night Vision activé");
                player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 9000000, 2));
                return true;
            }
            if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null || !target.isOnline()) {
                    player.sendMessage(Main.PrefixError + "Ce joueur n'est pas connecté ou n'existe pas !");
                    return true;
                }
                if (!player.hasPermission("nightvision.player")) {
                    player.sendMessage(Main.PrefixError + "Tu n'as pas la permission d'utiliser cette commande !");
                    return true;
                }
                if (target.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                    target.sendMessage("Night Vision désactivé");
                    target.removePotionEffect(PotionEffectType.NIGHT_VISION);
                    return true;
                }
                target.sendMessage("Night Vision désactivé");
                target.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 9000000, 2));
                return true;
            }
            if (args.length != 1 && args.length != 0) {
                player.sendMessage(Main.PrefixError + "/nv <player>");
                return true;
            }
        }

        return false;
    }
}