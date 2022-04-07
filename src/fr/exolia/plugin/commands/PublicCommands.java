package fr.exolia.plugin.commands;

import fr.exolia.plugin.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.inventory.Inventory;

public class PublicCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("§4Exolia §f» §cSeul un joueur peut executer cette commande.");
            return false;
        }

        Player player = (Player)sender;
        TextComponent bar = new TextComponent("§7§m---------------------");

        if(label.equalsIgnoreCase("report")){
            if(args.length != 1){
                player.sendMessage("§cVeuillez saisir le pseudo d'un joueur !");
                return false;
            }

            String targetName = args[0];

            if(Bukkit.getPlayer(targetName) == null){
                player.sendMessage("§cCe joueur n'est pas connecté ou n'existe pas !");
                return false;
            }

            Player target = Bukkit.getPlayer(targetName);

            Inventory inv = Bukkit.createInventory(null, 18, "§2Report §f» §a" + target.getName());

            inv.setItem(0, new ItemBuilder(Material.IRON_SWORD).setName("§cForceField").toItemStack());
            inv.setItem(1, new ItemBuilder(Material.BOW).setName("§cSpamBow").toItemStack());

            player.openInventory(inv);
        }


        if (label.equalsIgnoreCase("site")) {
            TextComponent weblink = new TextComponent("\n§aSite Web §2§l➤ §bexolia.site\n");
            weblink.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§bCliquez pour accéder au site").create()));
            weblink.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://exolia.site"));
            player.spigot().sendMessage(bar, weblink, bar);
            return true;
        }

        if (label.equalsIgnoreCase("vote")) {
            TextComponent weblink = new TextComponent("\n§aVoter pour exolia §2§l➤ §bexolia.site/vote\n");
            weblink.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§bCliquez pour voter").create()));
            weblink.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://exolia.site/vote"));
            player.spigot().sendMessage(bar, weblink, bar);
            return true;
        }

        if (label.equalsIgnoreCase("reglement")) {
            TextComponent weblink = new TextComponent("\n§aRéglement §2§l➤ §bexolia.site/p/reglement\n");
            weblink.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§bCliquez pour accèder au règlement du serveur.").create()));
            weblink.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://exolia.site/p/reglement"));
            player.spigot().sendMessage(bar, weblink, bar);
            player.sendMessage("https://exolia.site");
            return true;
        }

        if (label.equalsIgnoreCase("discord")) {
            player.performCommand("discordsrv link");
            return true;
        }

        if (label.equalsIgnoreCase("hub")) {
            player.performCommand("spawn");
            return true;
        }

        if (label.equalsIgnoreCase("hoteldevente")) {
            player.performCommand("ah");
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