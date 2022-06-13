package fr.osalys.plugin.managers;

import fr.osalys.plugin.Main;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ChatManager {

    private final Main main;
    int number = 1;

    public ChatManager(Main main) {
        this.main = main;
    }

    public final String prefixInfo = "§a§lExolia §8§l➜ §7";
    public final String prefixError = "§c§lExolia §8§l➜ §c";
    public final String prefixAnnounce = "§2§lExolia §8§l➜ §a";
    public final String errorNotValidPlayer = prefixError + "Ce joueur n'existe pas ou n'est pas connecté !";
    public final String errorNotValidInt = prefixError + "Veuillez saisir un montant valide.";
    public final String errorHasNotPermission = prefixError + "Vous n'avez pas la permission d'exécuter cette commande !";
    public final String errorNoSelectedPlayer = prefixError + "Veuillez saisir un joueur !";
    public final String errorNoSelectedInt = prefixError + "Veuillez saisir un montant !";
    public final String errorNotInstanceOfPlayer = prefixError + "Cette commande est uniquement exécutable par des joueurs.";
    public final String errorSamePlayerAsTarget = prefixError + "Vous ne pouvez pas exécuter cette commande sur vous-même !";

    /**
     * Permet de nettoyer le chat pour seulement un joueur.
     *
     * @param player joueur qui aura le chat nettoyé
     */
    public void clearChatForOnePlayer(Player player) {
        for (int x = 0; x <= 100; x++) {
            player.sendMessage(" ");
        }
        player.sendMessage(prefixAnnounce + "Ton chat vient d'être nettoyé  par un Administrateur.");
    }

    /**
     * Permet de nettoyer le chat pour tout le monde.
     */
    public void clearChatForAll() {
        for (int x = 0; x <= 100; x++) {
            Bukkit.broadcastMessage("");
        }
        Bukkit.broadcastMessage(prefixAnnounce + "Le chat vient d'être nettoyé par un Modérateur.");
    }

    /**
     * Permet de nettoyer le chat pour les joueurs seulement.
     */
    public void clearChatForPlayersOnly() {
        for (Player targetPlayers : Bukkit.getOnlinePlayers()) {
            if (!targetPlayers.hasPermission(main.permissionStaff)) {
                clearChatForOnePlayer(targetPlayers);
            }
        }
    }

    /**
     * Envoie un message à tout le staff en ligne.
     *
     * @param targetName joueur à signaler
     * @param reason     raison du signalement
     */
    public void sendReportToStaff(String targetName, String reason) {
        sendMessageToStaff("§cLe joueur §6" + targetName + " §ca été signalé pour : §6" + reason);
    }

    /**
     * Envoie un message à tout le staff en ligne.
     *
     * @param message texte à envoyer à tout les joueurs qui ont la permission {@link Main#permissionStaff}
     */
    public void sendMessageToStaff(String message) {
        Bukkit.broadcast(message, main.permissionStaff);
    }

    /**
     * Fonction qui envoie les messages d'astuces/d'informations automatiquement.
     */
    public void autoBroadcast() {
        TextComponent bar = new TextComponent("§7§m---------------------");
        String strBar = "§7§m---------------------";
        String tipsPrefix = "\n§2§lAstuce §7● §a";

        Bukkit.getScheduler().scheduleSyncRepeatingTask(main, () -> {

            number += 1;
            if (number == 10) {
                number = 1;
            }
            if (number == 1) {
                TextComponent weblink = new TextComponent(tipsPrefix + "Informes-toi des actualités et participes aux concours sur notre serveur Discord ! Tu peux également lier ton compte pour gagner 15.000$ §b/discord\n");
                weblink.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§bCliquez pour accéder au discord")));
                weblink.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.exolia.site"));
                Bukkit.getServer().spigot().broadcast(bar, weblink, bar);
            }
            if (number == 2) {
                TextComponent weblink = new TextComponent(tipsPrefix + "Vote régulièrement et gagne des récompenses uniques ! §b/vote\n");
                weblink.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§bCliquez pour accéder au site de vote")));
                weblink.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://exolia.site/vote"));
                Bukkit.getServer().spigot().broadcast(bar, weblink, bar);
            }
            if (number == 3) {
                TextComponent weblink = new TextComponent(tipsPrefix + "Le règlement du serveur est à respecter impérativement. §b/reglement\n");
                weblink.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§bCliquez pour accéder au site de vote")));
                weblink.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://exolia.site/p/reglement"));
                Bukkit.getServer().spigot().broadcast(bar, weblink, bar);
            }
            if (number == 4) {
                TextComponent weblink = new TextComponent(tipsPrefix + "Tu peux acheter des grades, kits, spawners, clés et bien plus encore sur notre boutique ! §b/boutique\n");
                weblink.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§bCliquez pour acheter des exolions")));
                weblink.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://exolia.site/shop"));
                Bukkit.getServer().spigot().broadcast(bar, weblink, bar);
            }
            if (number == 5) {
                TextComponent weblink = new TextComponent(tipsPrefix + "Obtiens des exolions et dévalises la boutique grâce au §b/exolion\n");
                weblink.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§bCliquez pour acheter des exolions")));
                weblink.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://exolia.site/shop"));
                Bukkit.getServer().spigot().broadcast(bar, weblink, bar);
            }
            if (number == 6) {
                Bukkit.broadcastMessage(strBar + tipsPrefix + "Envie de signaler un joueur pour son comportement ? §b/report\n" + strBar);
            }
            if (number == 7) {
                Bukkit.broadcastMessage(strBar + tipsPrefix + "Si tu possèdes des exolions, tu peux acheter des grades, kits, spawners, clés et bien plus encore sur la boutique en jeu ! §b/boutique\n" + strBar);
            }
            if (number == 8) {
                Bukkit.broadcastMessage(strBar + tipsPrefix + "Obtiens de nombreuses informations sur le serrveur avec §b/aide\n" + strBar);
            }
            if (number == 9) {
                Bukkit.broadcastMessage(strBar + tipsPrefix + "Effectue des échanges sécurisés grâce au §b/trade\n" + strBar);
            }
        }, 20L, 12000L);
    }
}
