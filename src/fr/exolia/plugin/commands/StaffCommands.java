package fr.exolia.plugin.commands;

import fr.exolia.plugin.managers.PlayerManager;
import fr.exolia.plugin.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.exolia.plugin.Main;
import org.bukkit.inventory.Inventory;

public class StaffCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.PrefixError + "Seul un joueur peut executer cette commande.");
            return false;
        }

        Player player = (Player) sender;

        if(label.equalsIgnoreCase("mod")){
            if(!player.hasPermission("exolia.moderateur")) {
                player.sendMessage(Main.PrefixError + "Vous n'avez pas la permission d'éxecuter cette commande !");
                return false;
            }

            if(PlayerManager.isInModerationMod(player)) {
                PlayerManager pm = PlayerManager.getFromPlayer(player);
                Main.getInstance().moderateurs.remove(player.getUniqueId());
                player.getInventory().clear();
                player.sendMessage(Main.PrefixInfo + "Vous n'êtes maintenant plus en mode modération");
                pm.GiveInventory();
                pm.destroy();
                return false;
            }

            PlayerManager pm = new PlayerManager(player);
            pm.init();
            Main.getInstance().moderateurs.add(player.getUniqueId());
            player.sendMessage( Main.PrefixInfo + "Vous êtes à présent en mode modération");
            pm.SaveInventory();
            player.setGameMode(GameMode.CREATIVE);

            ItemBuilder invSee = new ItemBuilder(Material.PAPER).setName("§aVoir l'inventaire").setLore("§7Clique droit sur un joueur", "§7pour voir son inventaire");
            ItemBuilder reports = new ItemBuilder(Material.FEATHER).setName("§aVanish");
            player.getInventory().setItem(0, invSee.toItemStack());
            player.getInventory().setItem(1, reports.toItemStack());
        }

        return false;

    }
}
