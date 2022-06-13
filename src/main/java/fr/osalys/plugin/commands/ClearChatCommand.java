package fr.osalys.plugin.commands;

import fr.osalys.plugin.Main;
import fr.osalys.plugin.managers.ChatManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
                if (args.length == 0) {
                    chatManager.clearChatForAll();
                    return true;
                }

                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("player")) {
                        chatManager.clearChatForPlayersOnly();
                        return true;
                    }

                    Player target = Bukkit.getPlayerExact(args[0]);
                    if (target != null) {
                        chatManager.clearChatForOnePlayer(target);
                        return true;
                    }
                    player.sendMessage(chatManager.errorNotValidPlayer);
                    return false;
                }
                player.sendMessage(chatManager.prefixInfo + "§b/clearchat §7Clear le chat pour tout le monde"
                        + "\n§b/clearchat player §7Clear le chat pour les joueurs uniquement"
                        + "\n§b/clearchat <nom d'un joueur> §7Clear le chat pour un seul joueur");
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
        return null;
    }
}
