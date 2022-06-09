package fr.osalys.plugin.tablist;

import fr.osalys.plugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class TablistManager {

    private final Main main;
    public TablistManager(Main main) {this.main = main;}

    public void setPlayerList(Player player) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(main, new Runnable() {
            @Override
            public void run() {

            }
        }, 10, 40);
        player.setPlayerListHeaderFooter("qzdqzdzqdqz \n petit test", "zqdzqd \n coucou");
    }

    public void setPlayerTeams(Player player) {
        Scoreboard scoreboard = player.getScoreboard();

        Team yoo = scoreboard.getTeam("aadmin");
        Team dev = scoreboard.getTeam("bdev");
        Team modo = scoreboard.getTeam("cmodo");
        Team modojoueur = scoreboard.getTeam("dmodojoueur");
        Team guide = scoreboard.getTeam("eguide");
        Team influenceur = scoreboard.getTeam("finfluenceur");
        Team supreme = scoreboard.getTeam("gsupreme");
        Team empereur = scoreboard.getTeam("hempereur");
        Team seigneur = scoreboard.getTeam("iseigneur");
        Team maitre = scoreboard.getTeam("jmaitre");
        Team chevalier = scoreboard.getTeam("kchevalier");
        Team pop = scoreboard.getTeam("ljoueur");

        if (yoo == null) {
            yoo = scoreboard.registerNewTeam("aadmin");
        }
        if (dev == null) {
            dev = scoreboard.registerNewTeam("bdev");
        }
        if (modo == null) {
            modo = scoreboard.registerNewTeam("cmodo");
        }
        if (modojoueur == null) {
            modojoueur = scoreboard.registerNewTeam("dmodojoueur");
        }
        if (guide == null) {
            guide = scoreboard.registerNewTeam("eguide");
        }
        if (influenceur == null) {
            influenceur = scoreboard.registerNewTeam("finfluenceur");
        }
        if (supreme == null) {
            supreme = scoreboard.registerNewTeam("gsupreme");
        }
        if (empereur == null) {
            empereur = scoreboard.registerNewTeam("hempereur");
        }
        if (seigneur == null) {
            seigneur = scoreboard.registerNewTeam("iseigneur");
        }
        if (maitre == null) {
            maitre = scoreboard.registerNewTeam("jmaitre");
        }
        if (chevalier == null) {
            chevalier = scoreboard.registerNewTeam("kchevalier");
        }
        if (pop == null) {
            pop = scoreboard.registerNewTeam("ljoueur");
        }


        yoo.setDisplayName("§6✦ §c§lAdmin §6✦ §8➜ §c§l");
        dev.setDisplayName("&6✦ &c&lDev &6✦ &8➜ &c&l");
        modo.setDisplayName("&e✦ &2&lModo &e✦ &8➜ &2&l");
        modojoueur.setDisplayName("&e✧ &3&lModo-J &e✧ &8➜ &3&l");
        guide.setDisplayName("&e✧ &b&lGuide &e✧ &8➜ &b");
        influenceur.setDisplayName("&f✪ &5&lInfluenceur &f✪ &8➜ &5&l");
        supreme.setDisplayName("&a❖ &a&lSuprême &a❖ &8➜ &a&l");
        empereur.setDisplayName("&f❖ &5&lEmpereur &f❖ &8➜ &5&l");
        seigneur.setDisplayName("&f● &6&lSeigneur &f● &8➜ &6&l");
        maitre.setDisplayName("&f● &dMaître &f● &8➜ &d");
        chevalier.setDisplayName("&f● &eChevalier &f● &8➜ &e");
        pop.setDisplayName("§f● §7Osalien §f● §8➜ ");

        for (Player target : Bukkit.getOnlinePlayers()) {

            if (target.isOp()) {
                yoo.addEntry(target.getName());
                continue;
            }

            pop.addEntry(target.getName());
        }
    }
}
