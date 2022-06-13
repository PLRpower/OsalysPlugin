package fr.osalys.plugin.commands;

import fr.osalys.plugin.Main;
import fr.osalys.plugin.database.Exolions;
import fr.osalys.plugin.managers.ChatManager;
import fr.osalys.plugin.managers.CommandManager;
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

    private final Main main;
    private final ChatManager chatManager;
    private final Exolions exolions;

    public ExolionAdminCommand(Main main) {
        this.main = main;
        this.chatManager = main.getChatManager();
        this.exolions = main.getExolions();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (sender.hasPermission(main.permissionHStaff)) {
            if (args.length == 3) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null) {
                    if ((args[0]).matches("-?\\d+")) {
                        if (args[0].equalsIgnoreCase("give")) {
                            exolions.addCoins(target, Float.parseFloat(args[2]));
                            sender.sendMessage(chatManager.prefixInfo + "Vous avez correctement ajouté §b" + args[2] + " Exolions §7à §b" + target.getName() + "§7.");
                            target.sendMessage(chatManager.prefixInfo + "Vous avez correctement reçu §b" + args[2] + " Exolions §7.");
                            return true;
                        }

                        if (args[0].equalsIgnoreCase("remove")) {
                            exolions.removeCoins(target, Float.parseFloat(args[2]));
                            sender.sendMessage(chatManager.prefixInfo + "Vous avez correctement retiré §b" + args[2] + " Exolions §7à §b" + target.getName() + "§7.");
                            target.sendMessage(chatManager.prefixInfo + "Vous avez été débité de §b" + args[2] + " Exolions §7.");
                            return true;
                        }

                        if (args[0].equalsIgnoreCase("set")) {
                            exolions.setCoins(target, Float.parseFloat(args[2]));
                            sender.sendMessage(chatManager.prefixInfo + "Vous avez correctement retiré §b" + args[2] + " Exolions §7à §b" + target.getName() + "§7.");
                            target.sendMessage(chatManager.prefixInfo + "Vous avez été débité de §b" + args[2] + " Exolions §7.");
                            return true;
                        }
                        sender.sendMessage(chatManager.prefixError + "Veuillez saisir un argument correct ! §6(give/remove/set)");
                        return false;
                    }
                    sender.sendMessage(chatManager.errorNotValidInt);
                    return false;
                }
                sender.sendMessage(chatManager.errorNotValidPlayer);
                return false;
            } if (args.length == 0) {
                sender.sendMessage(chatManager.prefixError + "Veuillez saisir un argument §6(give/remove/set)§c !");
            }

            if (args.length == 1) {
                sender.sendMessage(chatManager.errorNoSelectedPlayer);
            }

            if (args.length == 2) {
                sender.sendMessage(chatManager.errorNoSelectedInt);
            }
            return false;
        }
        sender.sendMessage(chatManager.errorNotInstanceOfPlayer);
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        CommandManager commandManager = new CommandManager((Player) sender, main);


        if (args.length == 1) {
            List<String> arguments = new ArrayList<>();
            arguments.add("give");
            arguments.add("remove");
            arguments.add("set");
            return arguments;
        }

        if (args.length == 2) {
            return commandManager.getAllPlayers();
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
}