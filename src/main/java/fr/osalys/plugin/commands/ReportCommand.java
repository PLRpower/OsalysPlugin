package fr.osalys.plugin.commands;

import fr.osalys.plugin.Main;
import fr.osalys.plugin.gui.ReportGui;
import fr.osalys.plugin.managers.ChatManager;
import fr.osalys.plugin.managers.CommandManager;
import fr.osalys.plugin.managers.GuiManager;
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

public class ReportCommand implements CommandExecutor, TabCompleter {

    private final Main main;
    private final ChatManager chatManager;
    private final GuiManager guiManager;

    public ReportCommand(Main main) {
        this.main = main;
        this.chatManager = main.getChatManager();
        this.guiManager = main.getGuiManager();

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player player) {
            if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    if (target != player) {
                        if (!target.hasPermission(main.permissionStaff)) {
                            guiManager.open(player, ReportGui.class);
                            return true;
                        }
                        player.sendMessage(chatManager.prefixError + "Vous ne pouvez pas signaler ce joueur.");
                        return false;
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
        sender.sendMessage(chatManager.errorNotInstanceOfPlayer);
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {

        CommandManager commandManager = new CommandManager((Player) sender, main);
        if (args.length == 1) {
            return commandManager.getPlayersOnly();
        }
        return null;
    }
}
