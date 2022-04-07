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

        if(label.equalsIgnoreCase("mod")){
            if(!player.hasPermission("moderation.mod")){
                player.sendMessage("§cVous n'avez pas la permission d'éxecuter cette commande !");
                return false;
            }

            if(Main.getInstance().moderateurs.contains(player.getUniqueId())){
                PlayerManager pm = PlayerManager.getFromPlayer(player);

                Main.getInstance().moderateurs.remove(player.getUniqueId());
                player.getInventory().clear();
                player.sendMessage("§cVous n'êtes maintenant plus en mode modération");
                pm.giveInventory();
                pm.destroy();
                return false;
            }

            PlayerManager pm = new PlayerManager(player);
            pm.init();

            Main.getInstance().moderateurs.add(player.getUniqueId());
            player.sendMessage("§aVous êtes à présent en mode modération");
            pm.saveInventory();
        }

        return false;

    }
}
