package fr.osalys.plugin.discord.listeners;

import fr.osalys.plugin.discord.Discord;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class OnSelectMenuInteraction extends ListenerAdapter {

    private final Discord discord;

    public OnSelectMenuInteraction(Discord discord) {
        this.discord = discord;
    }


    @Override
    public void onSelectMenuInteraction(SelectMenuInteractionEvent event) {
        if (event.getComponentId().equals("help")) {

            if (event.getValues().get(0).equals("help-info")) {
                MessageEmbed embedBuilder = new EmbedBuilder()
                        .setColor(discord.getDiscordColor())
                        .setTitle(":grey_question: **Commandes informatives**")
                        .setDescription("> `/doc` : Affiche les informations générales sur le bot.\n" +
                                "> `/help` : Affiche les informations par rapport aux commandes du bot.").build();
                event.editMessageEmbeds(embedBuilder).queue();
            }

            if (event.getValues().get(0).equals("help-minecraft")) {
                MessageEmbed embedBuilder = new EmbedBuilder()
                        .setColor(discord.getDiscordColor())
                        .setTitle(":pick: **Commandes pour Minecraft**")
                        .setDescription("> `/suggest` : Permet de déposer une suggestion.\n" +
                                "> `/status` : Affiche le statut du serveur Minecraft (online/offline).\n" +
                                "> `/traffic` : Affiche l'affluence du serveur dans un graphique.").build();
                event.editMessageEmbeds(embedBuilder).queue();
            }

            if (event.getValues().get(0).equals("help-music")) {
                MessageEmbed embedBuilder = new EmbedBuilder()
                        .setColor(discord.getDiscordColor())
                        .setTitle(":musical_note: **Commandes pour la musique**")
                        .setDescription("> `/play` : Joue une musique.\n" +
                                "> `/volume` : Change le volume de la musique. (0-100%)\n" +
                                "> `/leave` : Le bot quitte le salon vocal.").build();
                event.editMessageEmbeds(embedBuilder).queue();
            }

            if (event.getValues().get(0).equals("help-support")) {
                MessageEmbed embedBuilder = new EmbedBuilder()
                        .setColor(discord.getDiscordColor())
                        .setTitle(":ticket: **Commandes pour le support**")
                        .setDescription("> `/open` : Ouvre un ticket personnalisé.\n" +
                                "> `/close` : Ferme le ticket actuel.").build();
                event.editMessageEmbeds(embedBuilder).queue();
            }

            if (event.getValues().get(0).equals("help-bot")) {
                MessageEmbed embedBuilder = new EmbedBuilder()
                        .setColor(discord.getDiscordColor())
                        .setTitle(":robot:  Commandes pour le bot")
                        .setDescription("> `/config` : Configuration du bot.\n" +
                                "> `/logs` : Affichage des logs du bot.\n" +
                                "> `/setstatus` : Mise à jour du statut du serveur\n" +
                                "> `/shutdown` : Extinction du bot.").build();
                event.editMessageEmbeds(embedBuilder).queue();
            }
        }
    }
}
