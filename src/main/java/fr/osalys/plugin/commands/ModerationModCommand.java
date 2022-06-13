package fr.osalys.plugin.commands;

import fr.osalys.plugin.Main;
import fr.osalys.plugin.managers.ChatManager;
import fr.osalys.plugin.managers.PlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ModerationModCommand implements CommandExecutor {

    private final Main main;
    private final ChatManager chatManager;
    private final PlayerManager playerManager;

    public ModerationModCommand(Main main) {
        this.main = main;
        this.chatManager = main.getChatManager();
        this.playerManager = main.getPlayerManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player player) {
            if (player.hasPermission(main.permissionModerateur)) {
                playerManager.setModerationMod(player, !PlayerManager.isInModerationMod(player));
                return true;
            }
            player.sendMessage(chatManager.errorHasNotPermission);
            return false;
        }
        sender.sendMessage(chatManager.errorNotInstanceOfPlayer);
        return false;
    }
}
