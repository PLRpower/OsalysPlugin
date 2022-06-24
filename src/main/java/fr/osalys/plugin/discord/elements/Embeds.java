package fr.osalys.plugin.discord.elements;

import fr.osalys.plugin.discord.Discord;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.AudioChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.awt.*;
import java.util.ArrayList;

public class Embeds {

    private final Discord discord;
    private final Color backgroundColor = Color.decode("#2F3136");
    private final Color discordColor = Color.decode("#5865F2");
    private final Color successColor = Color.decode("#3BA55D");
    private final Color errorColor = Color.decode("#C9963C");
    private final Color warningColor = Color.decode("#ED4245");
    private final MessageEmbed notInVoiceEmbed = new EmbedBuilder()
            .setColor(warningColor)
            .setTitle(":exclamation: Erreur")
            .setDescription("Merci de rejoindre un salon vocal custom avant d'ouvrir la configuration.").build();
    private final MessageEmbed errorEmbed = new EmbedBuilder()
            .setColor(warningColor)
            .setTitle(":exclamation: Erreur")
            .setDescription("Vous n'avez pas la permission d'exécuter cette commande.").build();
    private final MessageEmbed statutEmbed = new EmbedBuilder()
            .setColor(successColor)
            .setTitle(":white_check_mark: Le serveur est en ligne !")
            .setDescription("__Statut actuel:__ **on**\n" +
                    "Vous pouvez aller y jouer !").build();
    private final MessageEmbed helpInfoEmbed = new EmbedBuilder()
            .setColor(discordColor)
            .setTitle(":grey_question: Commandes informatives")
            .setDescription("""
                    > `/doc` : Affiche les informations générales sur le bot.
                    "> `/help` : Affiche les informations par rapport aux commandes du bot.""")
            .build();
    private final MessageEmbed helpMinecraftEmbed = new EmbedBuilder()
            .setColor(discordColor)
            .setTitle(":pick: Commandes pour Minecraft")
            .setDescription("""
                    > `/suggestion` : Permet de déposer une suggestion.
                    > `/statut` : Affiche le statut du serveur Minecraft (online/offline).
                    > `/traffic` : Affiche l'affluence du serveur dans un graphique.""").build();
    private final MessageEmbed helpMusicEmbed = new EmbedBuilder()
            .setColor(discordColor)
            .setTitle(":musical_note: Commandes pour la musique")
            .setDescription("""
                    > `/play` : Joue une musique.
                    > `/volume` : Change le volume de la musique. (0-100%)
                    > `/leave` : Le bot quitte le salon vocal.""").build();
    private final MessageEmbed helpSupportEmbed = new EmbedBuilder()
            .setColor(discordColor)
            .setTitle(":ticket: Commandes pour le support")
            .setDescription("""
                    > `/open` : Ouvre un ticket personnalisé.
                    "> `/close` : Ferme le ticket actuel.""").build();
    private final MessageEmbed helpBotEmbed = new EmbedBuilder()
            .setColor(discordColor)
            .setTitle(":robot: Commandes pour le bot")
            .setDescription("""
                    > `/config` : Configuration du bot.
                    > `/logs` : Affichage des logs du bot.
                    > `/setstatus` : Mise à jour du statut du serveur
                    > `/shutdown` : Extinction du bot.""").build();
    private final MessageEmbed publishedSuggestionEmbed = new EmbedBuilder()
            .setColor(successColor)
            .setTitle(":exclamation: Suggestion ajoutée !")
            .setDescription("Votre suggestion a correctement été publiée dans le salon <#986609839562162217> !").build();

    public Embeds(Discord discord) {
        this.discord = discord;
    }

    public final EmbedBuilder getDocumentationEmbed(int number) {

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(discordColor);
        embedBuilder.setTitle(":grey_question: Documentation du bot");

        switch (number) {
            case 1 -> {
                embedBuilder.setDescription("""
                        Bienvenue sur la documentation du bot **Osalys**!
                        Vous trouverez ici toutes les informations nécessaires sur le bot en général et l'utilisation de celui-ci. Je vous invite également à consulter `help` pour plus de détails sur les commandes du bot.

                        __Description:__
                        **Osalys Assistant** est un bot crée spécialement pour le serveur discord Osalys et a pour but de faciliter la gestion de celui-ci ainsi que d'aider au mieux les membres du serveur. Il a également été conçu afin de proposer aux membres d'Osalys des aides par rapport au serveur **Minecraft**.

                        __Utilisation générale:__
                        Il y a principalement deux modes d'utilisation des commandes du bot:
                        > Mode *simplifié*: Consiste à juste entrer le préfixe du bot suivi du nom de la commande dans un channel.""");
                embedBuilder.setFooter("Page 1/3");
                return embedBuilder;
            }
            case 2 -> {
                embedBuilder.setDescription("""
                        > *Mode rapide:* Consiste à entrer le préfixe du bot suivi de la commande souhaitée suivie des paramètres nécessaires.

                        __Mode simplifié:__
                        Pour le mode simplifié, il suffit d'entrer le préfixe avec le nom de la commande dans un channel.
                        *Exemple:* `!doc`
                        Si la commande ne recquiert aucun paramètre supplémentaire, un message sera envoyé sur le channel précisé avec le résultat de la commande. Si la commande comprend des paramètres supplémentaires, elle vous les demandera par l'intermédiaire de questions.

                        __Mode rapide:__
                        Le mode rapide consiste à entrer le préfixe avec le nom de la commande suivi des paramètres nécessaires.
                        *Exemple:* `!play` [musique]""");
                embedBuilder.setFooter("Page 2/3");
                return embedBuilder;
            }
            case 3 -> {
                embedBuilder.setDescription("""
                        **Attention:** En mode rapide, certaines commandes ne peuvent accepter tous leurs paramètres optionnels comme `add`. Si vous voulez avoir ces paramètres, il vous faudra utiliser le mode simplifié.

                        __Bugs et sécurité:__
                        Si vous détectez un **bug **ou une **faille de sécurité**, merci de prévenir le développeur du bot directement en MP. Bien évidemment, l'exploitation des ces failles est interdite et le coupable encourra de lourdes sanctions.

                        __Informations supplémentaires:__
                        > Version du bot: 0.1.3
                        > Développeur du bot: <@754080723882868776>
                        > Adresse mail d'Osalys: exolia.serveur@gmail.com""");
                embedBuilder.setFooter("Page 3/3");
                return embedBuilder;
            }
        }
        return embedBuilder;
    }

    public Discord getDiscord() {
        return discord;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public Color getDiscordColor() {
        return discordColor;
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

    public final MessageEmbed getAudioPanelEmbed(AudioChannel channel) {
        return new EmbedBuilder()
                .setColor(discordColor)
                .setTitle(":loud_sound: Configuration salon audio - " + channel.getName())
                .setDescription(
                        "Propriétaire : " + discord.getAudioManager().findOwner(channel)
                                + "\nStatut : " + discord.getAudioManager().statusName(channel)
                                + "\nLimite d'utilisateurs : " + discord.getAudioManager().limiteUsers((VoiceChannel) channel)
                                + "\nUtilisateurs whitelistés : " + channel.getPermissionContainer().getMemberPermissionOverrides().size()
                ).build();
    }

    public final MessageEmbed getAudioPanelEmbedWithCustomStatus(AudioChannel channel, String status) {
        return new EmbedBuilder()
                .setColor(discordColor)
                .setTitle(":loud_sound: Configuration salon audio - " + channel.getName())
                .setDescription(
                        "Propriétaire : " + discord.getAudioManager().findOwner(channel)
                                + "\nStatut : " + status
                                + "\nLimite d'utilisateurs : " + discord.getAudioManager().limiteUsers((VoiceChannel) channel)
                                + "\nUtilisateurs whitelistés : " + channel.getPermissionContainer().getMemberPermissionOverrides().size()
                ).build();
    }

    public final MessageEmbed getAudioPanelEmbedWithCustomUsers(AudioChannel channel, int number) {
        return new EmbedBuilder()
                .setColor(discordColor)
                .setTitle(":loud_sound: Configuration salon audio - " + channel.getName())
                .setDescription(
                        "Propriétaire : " + discord.getAudioManager().findOwner(channel)
                                + "\nStatut : " + discord.getAudioManager().statusName(channel)
                                + "\nLimite d'utilisateurs : " + number
                                + "\nUtilisateurs whitelistés : " + channel.getPermissionContainer().getMemberPermissionOverrides().size()
                ).build();
    }

    public final MessageEmbed getAudioPanelEmbedWithCustomName(AudioChannel channel, String name) {
        return new EmbedBuilder()
                .setColor(discordColor)
                .setTitle(":loud_sound: Configuration salon audio - " + name)
                .setDescription(
                        "Propriétaire : " + discord.getAudioManager().findOwner(channel)
                                + "\nStatut : " + discord.getAudioManager().statusName(channel)
                                + "\nLimite d'utilisateurs : " + discord.getAudioManager().limiteUsers((VoiceChannel) channel)
                                + "\nUtilisateurs whitelistés : " + channel.getPermissionContainer().getMemberPermissionOverrides().size()
                ).build();
    }

    public final MessageEmbed getAudioPanelEmbedWithCustomWhitelist(AudioChannel channel, int number) {
        return new EmbedBuilder()
                .setColor(discordColor)
                .setTitle(":loud_sound: Configuration salon audio - " + channel.getName())
                .setDescription(
                        "Propriétaire : " + discord.getAudioManager().findOwner(channel)
                                + "\nStatut : " + discord.getAudioManager().statusName(channel)
                                + "\nLimite d'utilisateurs : " + discord.getAudioManager().limiteUsers((VoiceChannel) channel)
                                + "\nUtilisateurs whitelistés : " + number
                ).build();
    }

    public final MessageEmbed getNamesEmbed(ArrayList<String> names, AudioChannel channel) {
        StringBuilder listString = new StringBuilder();
        for (String s : names) {
            listString.append(s).append("\n");
        }
        return new EmbedBuilder()
                .setColor(discordColor)
                .setTitle("Whitelist - " + channel.getName())
                .setDescription(listString).build();
    }

    public final MessageEmbed getSuggestionEmbed(User user, String title, String suggestion) {
        return new EmbedBuilder()
                .setAuthor(user.getName(), null, user.getEffectiveAvatarUrl())
                .setColor(discordColor)
                .setTitle(title)
                .setDescription(suggestion).build();
    }

    public MessageEmbed getErrorEmbed() {
        return errorEmbed;
    }

    public MessageEmbed getStatutEmbed() {
        return statutEmbed;
    }

    public MessageEmbed getHelpInfoEmbed() {
        return helpInfoEmbed;
    }

    public MessageEmbed getHelpMinecraftEmbed() {
        return helpMinecraftEmbed;
    }

    public MessageEmbed getHelpMusicEmbed() {
        return helpMusicEmbed;
    }

    public MessageEmbed getHelpSupportEmbed() {
        return helpSupportEmbed;
    }

    public MessageEmbed getHelpBotEmbed() {
        return helpBotEmbed;
    }

    public MessageEmbed getPublishedSuggestionEmbed() {
        return publishedSuggestionEmbed;
    }

    public MessageEmbed getNotInVoiceEmbed() {
        return notInVoiceEmbed;
    }
}
