package fr.exolia.plugin.managers;

import fr.exolia.plugin.Main;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ChatManager {

    public void clearChatForPlayersOnly(){
        for(Player targetPlayers : Bukkit.getOnlinePlayers()){
            if(!targetPlayers.hasPermission(Main.getInstance().permissionStaff)){
                for (int x = 0; x <= 100; x++) {
                    targetPlayers.sendMessage(" ");
                }
                targetPlayers.sendMessage(Main.getInstance().prefixAnnounce + "Le chat vient d'être nettoyé par un Administrateur.");
            }
        }
    }

    public void clearChatForOnePlayer(Player player){
        for (int x = 0; x <= 100; x++){
            player.sendMessage(" ");
        }
        player.sendMessage(Main.getInstance().prefixAnnounce + "Ton chat vient d'être nettoyé par §b" + player.getName() + "§a.");
    }

    public void clearChatForAll(){
        for (int x = 0; x <= 100; x++){
            Bukkit.broadcastMessage(" ");
        }
        Bukkit.broadcastMessage(Main.getInstance().prefixAnnounce + "Le chat vient d'être nettoyé par un Modérateur.");
    }

    public void sendClearChatErrorMessage(Player player){
        player.sendMessage(Main.getInstance().prefixError + "/clearchat §7Clear le chat pour tout le monde");
        player.sendMessage(Main.getInstance().prefixError + "/clearchat <all> §7Clear le chat pour tout le monde");
        player.sendMessage(Main.getInstance().prefixError + "/clearchat <player> §7Clear le chat pour les joueurs uniquement");
        player.sendMessage(Main.getInstance().prefixError + "/clearchat <player> <Nom d'un joueur> §7Clear le chat pour un seul joueur");
    }

    public void sendReportToStaff(String reason, String targetName){
        for(Player players : Bukkit.getOnlinePlayers()){
            if(players.hasPermission(Main.getInstance().permissionStaff)){
                players.sendMessage("§cLe joueur §6" + targetName + " §ca été signalé pour : §6" + reason);
            }
        }
    }

    public void sendMessageToStaff(Player player, String message){
        Bukkit.getOnlinePlayers().stream().filter(players -> players.hasPermission(Main.getInstance().permissionStaff)).forEach(players -> players.sendMessage("§2StaffChat §a" + player.getName() + " §f» §b" + message));
    }

    int number = 0;

    public void autoBroadcast() {
        TextComponent bar = new TextComponent("§7§m---------------------");
        String strBar = "§7§m---------------------";
        String tipsPrefix = "\n§2§lAstuce §7● §a";

        Bukkit.getScheduler().runTaskTimer(Main.getInstance(), () -> {

            number += 1;
            if(number == 10){
                number = 1;
            }
            if(number == 1){
                TextComponent weblink = new TextComponent(tipsPrefix + "Informes-toi des actualités et participes aux concours sur notre serveur Discord ! Tu peux également lier ton compte pour gagner 15.000$ §b/discord\n");
                weblink.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§bCliquez pour accéder au discord").create()));
                weblink.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.exolia.site"));
                Bukkit.getServer().spigot().broadcast(bar,  weblink, bar);
            }
            if(number == 2){
                TextComponent weblink = new TextComponent(tipsPrefix + "Vote régulièrement et gagne des récompenses uniques ! §b/vote\n");
                weblink.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§bCliquez pour accéder au site de vote").create()));
                weblink.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://exolia.site/vote"));
                Bukkit.getServer().spigot().broadcast(bar,  weblink, bar);
            }
            if(number == 3){
                TextComponent weblink = new TextComponent(tipsPrefix + "Le règlement du serveur est à respecter impérativement. §b/reglement\n");
                weblink.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§bCliquez pour accéder au site de vote").create()));
                weblink.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://exolia.site/p/reglement"));
                Bukkit.getServer().spigot().broadcast(bar, weblink, bar);
            }
            if(number == 4){
                TextComponent weblink = new TextComponent(tipsPrefix + "Tu peux acheter des grades, kits, spawners, clés et bien plus encore sur notre boutique ! §b/boutique\n");
                weblink.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§bCliquez pour acheter des exolions").create()));
                weblink.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://exolia.site/shop"));
                Bukkit.getServer().spigot().broadcast(bar, weblink, bar);
            }
            if(number == 5){
                TextComponent weblink = new TextComponent(tipsPrefix + "Obtiens des exolions et dévalises la boutique grâce au §b/exolion\n");
                weblink.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§bCliquez pour acheter des exolions").create()));
                weblink.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://exolia.site/shop"));
                Bukkit.getServer().spigot().broadcast(bar, weblink, bar);
            }
            if(number == 6){
                Bukkit.broadcastMessage(strBar + tipsPrefix + "Envie de signaler un joueur pour son comportement ? §b/report\n" + strBar);
            }
            if(number == 7){
                Bukkit.broadcastMessage(strBar + tipsPrefix + "Si tu possèdes des exolions, tu peux acheter des grades, kits, spawners, clés et bien plus encore sur la boutique en jeu ! §b/boutique\n" + strBar);
            }
            if(number == 8){
                Bukkit.broadcastMessage(strBar + tipsPrefix + "Obtiens de nombreuses informations sur le serrveur avec §b/aide\n" + strBar);
            }
            if(number == 9){
                Bukkit.broadcastMessage(strBar + tipsPrefix + "Effectue des échanges sécurisés grâce au §b/trade\n" + strBar);
            }
        }, 20L, 12000L);
    }
}
