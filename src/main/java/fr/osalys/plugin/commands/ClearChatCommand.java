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

    private Main main;
    public ClearChatCommand(Main main) {this.main = main;}
    private final ChatManager chatManager; {assert false; chatManager = main.getChatManager();}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            return false;
        }
        if (!main.getCommandManager().isPermissed(player, main.permissionModerateur)) {
            return false;
        }

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
            if (target == null) {
                player.sendMessage(main.prefixError + "Ce joueur n'existe pas ou n'est pas connecté !");
                return false;
            }

            chatManager.clearChatForOnePlayer(target);
            return true;

        } else {
            chatManager.sendClearChatErrorMessage(player);
            return false;
        }
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return null;
    }
}
