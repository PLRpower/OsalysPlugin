package fr.osalys.plugin.commands;

import fr.osalys.plugin.Main;
import fr.osalys.plugin.gui.ReportGui;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ReportCommand implements CommandExecutor, TabCompleter {

    private final Main main;
    public ReportCommand(Main main) {this.main = main;}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
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

        if (target == player) {
            player.sendMessage(main.prefixError + "Vous ne pouvez pas vous signaler vous-même !");
            return false;
        }

        if (target.hasPermission(main.permissionStaff)) {
            player.sendMessage(main.prefixError + "Vous ne pouvez pas signaler ce joueur.");
            return false;
        }

        main.getGuiManager().open(player, ReportGui.class);

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            return getPlayersOnly(sender.getName());
        }
        return null;
    }

    @NotNull
    private List<String> getPlayersOnly(String sender) {
        List<String> playerNames = new ArrayList<>();
        Bukkit.getOnlinePlayers().stream().filter(players -> !players.hasPermission(main.permissionStaff))
                .forEach(players -> playerNames.add(String.valueOf(players)));
        playerNames.remove(sender);
        return playerNames;
    }
}
