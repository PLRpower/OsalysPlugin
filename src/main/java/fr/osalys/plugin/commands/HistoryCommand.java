package fr.osalys.plugin.commands;

import fr.osalys.plugin.Main;
import fr.osalys.plugin.managers.ReportManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HistoryCommand implements CommandExecutor, TabCompleter {

    private final Main main;
    public HistoryCommand(Main main) {this.main = main;}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            return false;
        }
        if (!main.getCommandManager().isPermissed(player, main.permissionModerateur)) {
            return false;
        }

        if (args.length != 1) {
            player.sendMessage(main.prefixError + "Veuillez saisir le pseudo d'un joueur !");
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(main.prefixError + "Ce joueur n'est pas connecté ou n'existe pas !");
            return false;
        }

        List<ReportManager> reports = main.getReports().getReports(target.getUniqueId().toString());
        if (reports.isEmpty()) {
            player.sendMessage(main.prefixInfo + "Ce joueur n'a aucun signalement");
        } else {
            player.sendMessage(main.prefixInfo + "Voici la liste des signalements de §b" + target.getName() + "§7:");
            reports.forEach(r -> player.sendMessage("§f" + r.getDate() + "§fSignalé par :" + r.getAuthor() + " §fpour la raison :" + r.getReason()));
        }
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return null;
    }
}
