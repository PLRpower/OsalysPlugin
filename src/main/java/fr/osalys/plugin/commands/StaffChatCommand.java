package fr.osalys.plugin.commands;

import fr.osalys.plugin.Main;
import fr.osalys.plugin.managers.ChatManager;
import fr.osalys.plugin.managers.PlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StaffChatCommand implements CommandExecutor {

    private final Main main;
    private final ChatManager chatManager;
    private final PlayerManager playerManager;

    public StaffChatCommand(Main main) {
        this.main = main;
        this.chatManager = main.getChatManager();
        this.playerManager = main.getPlayerManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player player) {
            if (player.hasPermission(main.permissionStaff)) {
                if (args.length == 0) {
                    playerManager.setStaffChat(player, !PlayerManager.isStaffChat(player));
                    return true;
                }

                StringBuilder sb = new StringBuilder();
                for (String arg : args) sb.append(arg).append(" ");
                chatManager.sendMessageToStaff("§2StaffChat §a" + player.getName() + " §f» §b" + sb);
                return true;
            }
            player.sendMessage(chatManager.errorHasNotPermission);
            return false;
        }
        sender.sendMessage(chatManager.errorNotInstanceOfPlayer);
        return false;
    }
}
