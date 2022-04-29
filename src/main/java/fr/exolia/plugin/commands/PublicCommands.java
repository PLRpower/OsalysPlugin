package fr.exolia.plugin.commands;

import fr.exolia.plugin.Main;
import fr.exolia.plugin.managers.Exolions;
import fr.exolia.plugin.managers.PlayerManager;
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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PublicCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Main.PrefixError + "Seul un joueur peut executer cette commande.");
            return false;
        }

        Player player = (Player)sender;
        TextComponent bar = new TextComponent("§7§m---------------------");

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

        if(label.equalsIgnoreCase("report")) {

            if(args.length != 1){
                player.sendMessage(Main.PrefixError + "Veuillez saisir le pseudo d'un joueur !");
                return false;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if(target == null){
                player.sendMessage(Main.PrefixError + "Ce joueur n'est pas connecté ou n'existe pas !");
                return false;
            }

            if(target == player) {
                player.sendMessage(Main.PrefixError + "Vous ne pouvez pas vous signaler vous-même !");
                return false;
            }

            Inventory inv = Bukkit.createInventory(null, 18, "§bReport: §c" + target.getName());
            inv.setItem(0, new ItemBuilder(Material.IRON_SWORD).setName("§cForceField").toItemStack());
            inv.setItem(1, new ItemBuilder(Material.BOW).setName("§cSpamBow").toItemStack());
            player.openInventory(inv);
            return true;
        }

        if(label.equalsIgnoreCase("exolions")) {


            if(args.length == 0) {
                Exolions exolions = new Exolions(player);
                player.sendMessage("\n§7§m---------------------\n" + Main.PrefixAnnounce + "Acheter des Exolions:\n \n§aTu as §b" + exolions.getCoins() + " Exolions\n ");
                TextComponent weblink = new TextComponent("§2➤ §aObtiens des §2Exolions §aen cliquant sur ce §2lien sécurisé §a:\n§b§lhttps://exolia.site/shop");
                weblink.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§bObtenir des Exolions").create()));
                weblink.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://exolia.site/shop"));
                player.spigot().sendMessage(weblink);
                player.sendMessage("§7§m---------------------");
                player.sendMessage("");
                return true;
            }

            if(!args[0].equalsIgnoreCase("send")) {
                player.sendMessage(Main.PrefixError + "Utilisation : /exolions send <joueur> <nombre d'exolions>");
                return false;
            }

            if(args.length == 1) {
                player.sendMessage(Main.PrefixError + "Veuillez saisir un joueur à qui envoyer les exolions !");
                return false;
            }

            Player target = Bukkit.getPlayer(args[1]);
            if(target == null) {
                player.sendMessage(Main.PrefixError + "Ce joueur n'est pas connecté ou n'existe pas !");
                return false;
            }

            if(target == player) {
                player.sendMessage(Main.PrefixError + "Vous ne pouvez pas vous envoyer des exolions à vous-même !");
                return false;
            }

            if(args.length == 2) {
                player.sendMessage(Main.PrefixError + "Veuillez saisir un nombre d'exolions à envoyer !");
                return false;
            }

            Exolions exolionsplayer = new Exolions(player);
            Exolions exolionstarget = new Exolions(target);
            exolionsplayer.removeCoins(Integer.parseInt(args[2]));
            exolionstarget.addCoins(Integer.parseInt(args[2]));

            player.sendMessage(Main.PrefixInfo + "Vous avez correctement envoyé §b" + args[2] + " Exolions §7à §b" + target.getName() + "§7.");
            target.sendMessage("Vous avez reçu §b" + args[2] + " Exolions §7de §b" + player.getName() + "§7.");

            return true;
        }

        if(label.equalsIgnoreCase("nv")) {
            if(player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                player.sendMessage(Main.PrefixInfo + "Vous avez §aactivé §7la vision nocturne.");
            } else {
                player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 10000,2, false, false));
                player.sendMessage(Main.PrefixInfo + "Vous avez §aactivé §7la vision nocturne.");
            }
            return true;
        }


        return false;
    }

}