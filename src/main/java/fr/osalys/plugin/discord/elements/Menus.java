package fr.osalys.plugin.discord.elements;

import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;

public class Menus {

    private final SelectMenu helpMenu = SelectMenu.create("help")
            .setPlaceholder("Fais un choix")
            .setRequiredRange(1, 1)
            .addOption("Commandes informatives", "help-info", "", Emoji.fromUnicode("❔"))
            .addOption("Commandes pour Minecraft", "help-minecraft", "", Emoji.fromUnicode("⛏"))
            .addOption("Commandes pour la musique", "help-music", "", Emoji.fromUnicode("\uD83C\uDFB5"))
            .addOption("Commandes pour le support", "help-support", "", Emoji.fromUnicode("\uD83C\uDFAB"))
            .addOption("Commandes pour le bot", "help-bot", "", Emoji.fromUnicode("\uD83E\uDD16"))
            .build();

    public SelectMenu getHelpMenu() {
        return helpMenu;
    }
}
