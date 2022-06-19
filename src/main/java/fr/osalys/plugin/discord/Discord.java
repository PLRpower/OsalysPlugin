package fr.osalys.plugin.discord;

import fr.osalys.plugin.Main;
import fr.osalys.plugin.discord.listeners.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import javax.security.auth.login.LoginException;
import java.awt.*;

public class Discord extends ListenerAdapter {

    private static Main main;
    private final Color backgroundColor = Color.decode("#2F3136");
    private final Color discordColor = Color.decode("#5865F2");
    private final Color successColor = Color.decode("#3BA55D");
    private final Color errorColor = Color.decode("#C9963C");
    private final Color warningColor = Color.decode("#ED4245");
    private final MessageEmbed errorEmbed = new EmbedBuilder()
            .setColor(getWarningColor())
            .setTitle(":exclamation: Erreur")
            .setDescription("Vous n'avez pas la permission d'exécuter cette commande.").build();

    public Discord(Main main) {
        Discord.main = main;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public Color getSuccessColor() {
        return successColor;
    }

    public Color getErrorColor() {
        return errorColor;
    }

    public Color getWarningColor() {
        return warningColor;
    }

    public Color getDiscordColor() {
        return discordColor;
    }

    public Main getMain() {
        return main;
    }

    public MessageEmbed getErrorEmbed() {
        return errorEmbed;
    }

    public void initDiscord() throws LoginException, InterruptedException {
        JDA jda = JDABuilder.createDefault(main.getFileConfigurationDiscord().getString("discord.token"))
                .setActivity(Activity.watching("/help"))
                .addEventListeners(new OnSlashCommandInteraction(this))
                .addEventListeners(new OnSelectMenuInteraction(this))
                .addEventListeners(new OnModalInteraction(this))
                .addEventListeners(new OnGuildVoiceJoin(this))
                .addEventListeners(new OnGuildVoiceLeave(this))
                .addEventListeners(new OnGuildVoiceMove(this))
                .addEventListeners(new OnMessageReceived(this))
                .build().awaitReady();

        Guild testServer = jda.getGuildById("986594440770625587");
        assert testServer != null;
        testServer.upsertCommand("aide", "Affiche les informations par rapport aux commandes du bot.").queue();
        testServer.upsertCommand("help", "Affiche les informations par rapport aux commandes du bot.").queue();
        testServer.upsertCommand("suggestion", "Proposer une suggestion.").queue();
        testServer.upsertCommand("clear", "Supprimer des messages.")
                .addOption(OptionType.INTEGER, "combien", "Combien de messages voulez-vous supprimer ?", true).queue();
        testServer.upsertCommand("statut", "Permet d'affiche le statut actuel du serveur.").queue();

    }
}
