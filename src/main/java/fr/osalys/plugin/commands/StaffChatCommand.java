package fr.osalys.plugin.commands;

import fr.osalys.plugin.Main;
import fr.osalys.plugin.managers.PlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StaffChatCommand implements CommandExecutor {

    private final Main main;
    public StaffChatCommand(Main main) {this.main = main;}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            return false;
        }
        if (!main.getCommandManager().isPermissed(player, main.permissionStaff)) {
            return false;
        }

        if (args.length == 0) {
            main.getPlayerManager().setStaffChat(player, !PlayerManager.isStaffChat(player));
            return true;
        }

        StringBuilder sb = new StringBuilder();
        for (String arg : args) {
            sb.append(arg).append(" ");
        }
        main.getChatManager().sendMessageToStaff("§2StaffChat §a" + player.getName() + " §f» §b" + sb);
        return true;
    }
}
