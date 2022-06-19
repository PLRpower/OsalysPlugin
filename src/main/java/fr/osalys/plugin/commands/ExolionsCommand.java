package fr.osalys.plugin.commands;

import fr.osalys.plugin.Main;
import fr.osalys.plugin.database.Exolions;
import fr.osalys.plugin.managers.ChatManager;
import fr.osalys.plugin.managers.CommandManager;
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

    private final Main main;
    private final ChatManager chatManager;
    private final Exolions exolions;

    public ExolionsCommand(Main main) {
        this.main = main;
        this.chatManager = main.getChatManager();
        this.exolions = main.getExolions();

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player player) {
            if (args.length == 0) {
                player.sendMessage("\n§7§m---------------------\n" + chatManager.prefixAnnounce + "Acheter des Exolions:\n \n§aTu as §b" + exolions.getCoins(player) + " Exolions\n ");
                TextComponent weblink = new TextComponent("§2➤ §aObtiens des §2Exolions §aen cliquant sur ce §2lien sécurisé §a:\n§b§lhttps://exolia.site/shop");
                weblink.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§bObtenir des Exolions")));
                weblink.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://exolia.site/shop"));
                player.spigot().sendMessage(weblink);
                player.sendMessage("§7§m---------------------");
                player.sendMessage("");
                return true;
            }

            if (args[0].equalsIgnoreCase("send")) {
                if (args.length == 3) {
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target != null) {
                        if (target != player) {
                            exolions.removeCoins(target, Integer.parseInt(args[2]));
                            exolions.addCoins(target, Integer.parseInt(args[2]));
                            player.sendMessage(chatManager.prefixInfo + "Vous avez correctement envoyé §b" + args[2] + " Exolions §7à §b" + target.getName() + "§7.");
                            target.sendMessage(chatManager.prefixInfo + "Vous avez reçu §b" + args[2] + " Exolions §7de §b" + player.getName() + "§7.");
                            return true;
                        }
                        player.sendMessage(chatManager.errorSamePlayerAsTarget);
                        return false;
                    }
                    player.sendMessage(chatManager.errorNotValidPlayer);
                    return false;
                }

                if (args.length == 1) {
                    player.sendMessage(chatManager.errorNoSelectedPlayer);
                    return false;
                }

                if (args.length == 2) {
                    player.sendMessage(chatManager.errorNoSelectedInt);
                    return false;
                }

            }
            player.sendMessage(chatManager.prefixError + "Utilisation : /exolions send <joueur> <nombre d'exolions>");
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
            List<String> arguments = new ArrayList<>();
            arguments.add("send");
            return arguments;
        }

        if (args.length == 2) {
            return commandManager.getAllPlayers(false);
        }
        return null;
    }

}
