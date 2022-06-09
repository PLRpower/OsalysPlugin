package fr.osalys.plugin.commands;

import fr.osalys.plugin.Main;
import fr.osalys.plugin.database.Exolions;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
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

public class ExolionsCommand implements CommandExecutor, TabCompleter {

    private Main main;
    public ExolionsCommand(Main main) {this.main = main;}
    private final Exolions exolions; {assert false; exolions = main.getExolions();}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            return false;
        }

        if (args.length == 0) {
            player.sendMessage("\n§7§m---------------------\n" + main.prefixAnnounce + "Acheter des Exolions:\n \n§aTu as §b" + exolions.getCoins(player) + " Exolions\n ");
            TextComponent weblink = new TextComponent("§2➤ §aObtiens des §2Exolions §aen cliquant sur ce §2lien sécurisé §a:\n§b§lhttps://exolia.site/shop");
            weblink.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§bObtenir des Exolions")));
            weblink.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://exolia.site/shop"));
            player.spigot().sendMessage(weblink);
            player.sendMessage("§7§m---------------------");
            player.sendMessage("");
            return true;
        }

        if (!args[0].equalsIgnoreCase("send")) {
            player.sendMessage(main.prefixError + "Utilisation : /exolions send <joueur> <nombre d'exolions>");
            return false;
        }

        if (args.length == 1) {
            player.sendMessage(main.prefixError + "Veuillez saisir un joueur à qui envoyer les exolions !");
            return false;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            player.sendMessage(main.prefixError + "Ce joueur n'est pas connecté ou n'existe pas !");
            return false;
        }

        if (target == player) {
            player.sendMessage(main.prefixError + "Vous ne pouvez pas vous envoyer des exolions à vous-même !");
            return false;
        }

        if (args.length == 2) {
            player.sendMessage(main.prefixError + "Veuillez saisir un nombre d'exolions à envoyer !");
            return false;
        }

        exolions.removeCoins(target, Integer.parseInt(args[2]));
        exolions.addCoins(target, Integer.parseInt(args[2]));
        player.sendMessage(main.prefixInfo + "Vous avez correctement envoyé §b" + args[2] + " Exolions §7à §b" + target.getName() + "§7.");
        target.sendMessage(main.prefixInfo + "Vous avez reçu §b" + args[2] + " Exolions §7de §b" + player.getName() + "§7.");

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {

        if (args.length == 1) {
            List<String> arguments = new ArrayList<>();
            arguments.add("send");
            return arguments;
        }

        if (args.length == 2) {
            return getAllPlayers(sender.getName());
        }
        return null;
    }

    @NotNull
    private List<String> getAllPlayers(String sender) {
        List<String> playerNames = new ArrayList<>();
        Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
        Bukkit.getServer().getOnlinePlayers().toArray(players);
        for (Player player : players) {
            playerNames.add(player.getName());
        }
        playerNames.remove(sender);
        return playerNames;
    }
}
