package fr.exolia.plugin.commands;

import fr.exolia.plugin.managers.PlayerManager;
import fr.exolia.plugin.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
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
            for (int i = 0; i < args.length; i++) {
                sb.append(args[i]);
                sb.append(" ");
            }
            String combinedArgs = sb.toString();
            Bukkit.getOnlinePlayers().stream()
                    .filter(players -> players.hasPermission("exolia.staff"))
                    .forEach(players -> players.sendMessage("§2StaffChat §a" + player.getName() + " §f» §b" + combinedArgs));
            return true;
        }

        return false;

    }
}