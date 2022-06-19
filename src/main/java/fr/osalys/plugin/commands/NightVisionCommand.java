package fr.osalys.plugin.commands;

import fr.osalys.plugin.Main;
import fr.osalys.plugin.managers.ChatManager;
import fr.osalys.plugin.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NightVisionCommand implements CommandExecutor, TabCompleter {

    private final Main main;
    private final ChatManager chatManager;
    private final PlayerManager playerManager;

    public NightVisionCommand(Main main) {
        this.main = main;
        this.chatManager = main.getChatManager();
        this.playerManager = main.getPlayerManager();

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player player) {
            if (player.hasPermission(main.permssionEmpereur)) {
                if (args.length == 0) {
                    playerManager.setNightVision(player, !PlayerManager.isNightVision(player));
                    return true;
                }
                if (player.hasPermission(main.permissionModerateur)) {
                    if (args.length == 1) {
                        Player target = Bukkit.getPlayer(args[0]);
                        if (target != null) {
                            playerManager.setNightVision(target, !PlayerManager.isNightVision(target));
                            return true;
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
