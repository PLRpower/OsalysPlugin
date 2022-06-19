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

public class JMCommand implements CommandExecutor, TabCompleter {

    private final Main main;
    private final ChatManager chatManager;

    public JMCommand(Main main) {
        this.main = main;
        this.chatManager = main.getChatManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player player) {
            if (player.hasPermission(main.permissionStaff)) {
                if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        if (target != player) {
                            chatManager.sendMessageToStaff(player.getName() + " s'occupe de modérer de " + target.getName());
                            return true;
                        }
                        player.sendMessage(chatManager.errorSamePlayerAsTarget);
                        return false;
                    }
                    player.sendMessage(chatManager.errorNotValidPlayer);
                    return false;
                }
                player.sendMessage(chatManager.errorNoSelectedPlayer);
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
