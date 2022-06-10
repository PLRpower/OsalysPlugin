package fr.osalys.plugin.commands;

import fr.osalys.plugin.Main;
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

    public NightVisionCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            return false;
        }
        if (!main.getCommandManager().isPermissed(player, main.permssionEmpereur)) {
            return false;
        }

        if (args.length == 0) {
            main.getPlayerManager().setNightVision(player, !PlayerManager.isNightVision(player));
            return true;
        }

        if (args.length == 1) {

            Player target = Bukkit.getPlayer(args[0]);

            if (!player.hasPermission(main.permissionModerateur)) {
                player.sendMessage(main.prefixError + "Tu n'as pas la permission d'utiliser cette commande !");
                return false;
            }

            if (target == null) {
                player.sendMessage(main.prefixError + "Ce joueur n'est pas connecté ou n'existe pas !");
                return false;
            }
            main.getPlayerManager().setNightVision(target, !PlayerManager.isNightVision(target));
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return null;
    }
}
