package fr.osalys.plugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AliasesCommands implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            return false;
        }

        if (label.equalsIgnoreCase("discord")) {
            player.performCommand("discordsrv link");
            return true;
        }

        if (label.equalsIgnoreCase("hub")) {
            player.performCommand("spawn");
            return true;
        }

        if (label.equalsIgnoreCase("end")) {
            player.performCommand("warp end");
            return true;
        }

        if (label.equalsIgnoreCase("nether")) {
            player.performCommand("warp nether");
            return true;
        }

        return false;
    }
}
