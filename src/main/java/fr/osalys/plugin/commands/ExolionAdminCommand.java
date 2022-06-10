package fr.osalys.plugin.commands;

import fr.osalys.plugin.Main;
import fr.osalys.plugin.database.Exolions;
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

public class ExolionAdminCommand implements CommandExecutor, TabCompleter {

    private final Exolions exolions;
    private Main main;

    {
        assert false;
        exolions = main.getExolions();
    }

    public ExolionAdminCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (!main.getCommandManager().isPermissed((Player) sender, main.permissionHStaff)) {
            return false;
        }

        if (args.length == 0) {
            sender.sendMessage(main.prefixError + "Veuillez saisir un argument §6(give/remove/set)§c !");
            return false;
        }

        Player target = Bukkit.getPlayer(args[1]);

        if (target == null) {
            sender.sendMessage(main.prefixError + "Ce joueur n'existe pas ou n'est pas connecté !");
            return false;
        }

        if (args.length < 3) {
            sender.sendMessage(main.prefixError + "Veuillez saisir un joueur ainsi qu'un montant !");
            return false;
        }

        if (!args[0].matches("-?\\d+")) {
            sender.sendMessage(main.prefixError + "Veuillez saisir un montant valide.");
            return false;
        }

        if (args[0].equalsIgnoreCase("give")) {
            exolions.addCoins(target, Float.parseFloat(args[2]));
            sender.sendMessage(main.prefixInfo + "Vous avez correctement ajouté §b" + args[2] + " Exolions §7à §b" + target.getName() + "§7.");
            target.sendMessage(main.prefixInfo + "Vous avez correctement reçu §b" + args[2] + " Exolions §7.");
            return true;

        } else if (args[0].equalsIgnoreCase("remove")) {
            exolions.removeCoins(target, Float.parseFloat(args[2]));
            sender.sendMessage(main.prefixInfo + "Vous avez correctement retiré §b" + args[2] + " Exolions §7à §b" + target.getName() + "§7.");
            target.sendMessage(main.prefixInfo + "Vous avez été débité de §b" + args[2] + " Exolions §7.");
            return true;

        } else if (args[0].equalsIgnoreCase("set")) {
            exolions.setCoins(target, Float.parseFloat(args[2]));
            sender.sendMessage(main.prefixInfo + "Vous avez correctement retiré §b" + args[2] + " Exolions §7à §b" + target.getName() + "§7.");
            target.sendMessage(main.prefixInfo + "Vous avez été débité de §b" + args[2] + " Exolions §7.");
            return true;

        } else {
            sender.sendMessage(main.prefixError + "Veuillez saisir un argument correct ! §6(give/remove/set)");
            return false;
        }
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> arguments = new ArrayList<>();
            arguments.add("give");
            arguments.add("remove");
            arguments.add("set");
            return arguments;
        }

        if (args.length == 2) {
            return getAllPlayers(sender.getName());
        }

        if (args.length == 3) {
            List<String> integer = new ArrayList<>();
            for (int i = 0; i < 5001; i++) {
                integer.add(String.valueOf(i));
            }
            return integer;
        }
        return null;
    }

    @NotNull
    private List<String> getAllPlayers(String sender) {
        List<String> playerNames = new ArrayList<>();
        Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
        Bukkit.getServer().getOnlinePlayers().toArray(players);
        for (Player player : players) {
            playerNames.add(player.getName());
        }
        playerNames.remove(sender);
        return playerNames;
    }
}