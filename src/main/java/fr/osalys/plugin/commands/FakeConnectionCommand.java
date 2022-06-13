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

import java.util.List;

public class FakeConnectionCommand implements CommandExecutor, TabCompleter {

    private final Main main;
    private final ChatManager chatManager;

    public FakeConnectionCommand(Main main) {
        this.main = main;
        this.chatManager = main.getChatManager();

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player player) {
            if (player.hasPermission(main.permissionHStaff)) {
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("reco")) {
                        Bukkit.broadcastMessage("§f[§a+§f] " + player.getDisplayName());
                        return true;
                    }

                    if (args[0].equalsIgnoreCase("deco")) {
                        Bukkit.broadcastMessage("§f[§c-§f] " + player.getDisplayName());
                        return true;
                    }
                    player.sendMessage(chatManager.prefixError + "Veuillez saisir un argument valide ! §6(reco/deco)");
                    return false;
                }
                player.sendMessage(chatManager.prefixError + "Veuillez saisir un argument ! §6(reco/deco)");
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
