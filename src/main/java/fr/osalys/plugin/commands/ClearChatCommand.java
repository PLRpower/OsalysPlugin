package fr.osalys.plugin.commands;

import fr.osalys.plugin.Main;
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

public class ClearChatCommand implements CommandExecutor, TabCompleter {

    private final Main main;
    private final ChatManager chatManager;

    public ClearChatCommand(Main main) {
        this.main = main;
        this.chatManager = main.getChatManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player player) {
            if (player.hasPermission(main.permissionStaff)) {
                if (args.length >= 1) {
                    if (args[0].equalsIgnoreCase("all")) {
                        chatManager.clearChatForAll();
                        return true;
                    }

                    if (args[0].equalsIgnoreCase("player")) {
                        if (args.length == 1) {
                            chatManager.clearChatForPlayersOnly();
                            return true;
                        }
                        Player target = Bukkit.getPlayerExact(args[1]);
                        if (target != null) {
                            chatManager.clearChatForOnePlayer(target);
                            return true;
                        }
                        player.sendMessage(chatManager.errorNotValidPlayer);
                        return false;
                    }
                }
                player.sendMessage(chatManager.clearchatUtilisation);
                return false;
            }
            player.sendMessage(chatManager.errorHasNotPermission);
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
            arguments.add("all");
            arguments.add("player");
            return arguments;
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("player")) {
                return commandManager.getPlayersOnly(true);
            }
        }

        return null;
    }
}
