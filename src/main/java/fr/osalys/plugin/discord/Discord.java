package fr.osalys.plugin.discord;

import fr.osalys.plugin.Main;
import fr.osalys.plugin.discord.elements.*;
import fr.osalys.plugin.discord.listeners.*;
import fr.osalys.plugin.discord.managers.AudioManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import javax.security.auth.login.LoginException;

public class Discord extends ListenerAdapter {

    private static Main main;

    public Discord(Main main) {
        Discord.main = main;
    }

    private Buttons buttons;
    private Embeds embeds;
    private Messages messages;
    private Modals modals;
    private Menus menus;

    private AudioManager audioManager;


    public void initDiscord() throws LoginException, InterruptedException {
        buttons = new Buttons();
        embeds = new Embeds(this);
        messages = new Messages();
        modals = new Modals();
        menus = new Menus();

        audioManager = new AudioManager();

        JDA jda = JDABuilder.createDefault(main.getFileConfigurationDiscord().getString("discord.token"))
                .setActivity(Activity.watching("/help"))
                .addEventListeners(new OnSlashCommandInteraction(this))
                .addEventListeners(new OnSelectMenuInteraction(this))
                .addEventListeners(new OnModalInteraction(this))
                .addEventListeners(new OnMessageReceived(this))
                .addEventListeners(new VoiceChatActions())
                .addEventListeners(new OnButtonInteraction(this))
                .build().awaitReady();

        Guild testServer = jda.getGuildById("986594440770625587");
        assert testServer != null;
        testServer.upsertCommand("aide", "Affiche les informations par rapport aux commandes du bot.").queue();
        testServer.upsertCommand("help", "Affiche les informations par rapport aux commandes du bot.").queue();
        testServer.upsertCommand("suggestion", "Proposer une suggestion.").queue();
        testServer.upsertCommand("clear", "Supprimer des messages.")
                .addOption(OptionType.INTEGER, "combien", "Combien de messages voulez-vous supprimer ?", true).queue();
        testServer.upsertCommand("statut", "Affiche le statut actuel du serveur.").queue();
        testServer.upsertCommand("blague", "Affiche une balgue aléatoire.")
                .addOption(OptionType.STRING, "type", "Quel type de blague voulez-vous ?", false).queue();
        testServer.upsertCommand("ip", "Affiche une balgue aléatoire.").queue();
        testServer.upsertCommand("ip", "Affiche une balgue aléatoire.").queue();
        testServer.upsertCommand("doc", "Affiche les informations générales du bot.").queue();
        testServer.upsertCommand("audio", "Gère ton channel vocal comme tu le souhaites.").queue();

    }

    public Main getMain() {
        return main;
    }

    public Buttons getButtons() {
        return buttons;
    }

    public Embeds getEmbeds() {
        return embeds;
    }

    public Messages getMessages() {
        return messages;
    }

    public Modals getModals() {
        return modals;
    }

    public Menus getMenus() {
        return menus;
    }

    public AudioManager getAudioManager() {
        return audioManager;
    }
}
