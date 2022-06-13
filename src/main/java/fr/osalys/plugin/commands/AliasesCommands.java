package fr.osalys.plugin.commands;

import fr.osalys.plugin.Main;
import fr.osalys.plugin.managers.ChatManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AliasesCommands implements CommandExecutor {

    private final ChatManager chatManager;

    public AliasesCommands(Main main) {
        this.chatManager = main.getChatManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player player) {
            if (label.equalsIgnoreCase("discord")) {
                player.performCommand("discordsrv link");
            }

            if (label.equalsIgnoreCase("hub")) {
                player.performCommand("spawn");
            }

            if (label.equalsIgnoreCase("end")) {
                player.performCommand("warp end");
            }

            if (label.equalsIgnoreCase("nether")) {
                player.performCommand("warp nether");
            }
            return true;
        }
        sender.sendMessage(chatManager.errorNotInstanceOfPlayer);
        return false;
    }
}
