package fr.exolia.plugin.managers;

import fr.exolia.plugin.Main;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import java.util.Random;

public class AutoBroadcast {

    private final Main main = Main.getInstance();

    int number = 0;

    public void Minuterie() {
        Bukkit.getScheduler().runTaskTimer(main, () -> {
            number += 1;
            if (number == 11){number = 0;}
            Broadcast(number);
        }, 20L, 12000L);
    }

    public void Broadcast(Integer number){
        TextComponent bar = new TextComponent("§7§m---------------------");
        String strBar = "§7§m---------------------";
        String tipsPrefix = "\n§2§lAstuce §7● §a";
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
    }
}

