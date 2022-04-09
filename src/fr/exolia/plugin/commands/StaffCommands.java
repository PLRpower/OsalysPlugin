package fr.exolia.plugin.commands;

import fr.exolia.plugin.managers.PlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.exolia.plugin.Main;

public class StaffCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("§4Exolia §f>> §cSeul un joueur peut executer cette commande.");
            return false;
        }

        Player player = (Player)sender;

        return false;

    }
}
