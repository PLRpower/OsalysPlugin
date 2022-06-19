package fr.osalys.plugin.discord.listeners;

import fr.osalys.plugin.discord.Discord;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Modal;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;

import java.util.List;
import java.util.Objects;

public class OnSlashCommandInteraction extends ListenerAdapter {

    private final Discord discord;

    public OnSlashCommandInteraction(Discord discord) {
        this.discord = discord;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if (event.getName().equals("help") || event.getName().equals("aide")) {

            MessageEmbed embedBuilder = new EmbedBuilder()
                    .setColor(discord.getDiscordColor())
                    .setTitle(":grey_question: **Commandes informatives**")
                    .setDescription("> `/doc` : Affiche les informations générales sur le bot.\n" +
                            "> `/help` : Affiche les informations par rapport aux commandes du bot.").build();

            SelectMenu selectMenu = SelectMenu.create("help")
                    .setPlaceholder("Fais un choix")
                    .setRequiredRange(1, 1)
                    .addOption("Commandes informatives", "help-info", "", Emoji.fromUnicode("❔"))
                    .addOption("Commandes pour Minecraft", "help-minecraft", "", Emoji.fromUnicode("⛏"))
                    .addOption("Commandes pour la musique", "help-music", "", Emoji.fromUnicode("\uD83C\uDFB5"))
                    .addOption("Commandes pour le support", "help-support", "", Emoji.fromUnicode("\uD83C\uDFAB"))
                    .addOption("Commandes pour le bot", "help-bot", "", Emoji.fromUnicode("\uD83E\uDD16"))
                    .build();

            event.replyEmbeds(embedBuilder)
                    .addActionRow(selectMenu)
                    .setEphemeral(true)
                    .queue();
            return;
        }

        if (event.getName().equals("suggestion")) {
            TextInput suggestion = TextInput.create("suggest", "Votre suggestion", TextInputStyle.PARAGRAPH)
                    .setPlaceholder("Proposez votre suggestion ici ...")
                    .setMaxLength(1000)
                    .build();

            Modal modal = Modal.create("suggest-modal", "Proposer une suggestion")
                    .addActionRows(ActionRow.of(suggestion))
                    .build();

            event.replyModal(modal).queue();
            return;
        }

        if (event.getName().equals("clear")) {
            if (Objects.requireNonNull(event.getMember()).hasPermission(Permission.MESSAGE_MANAGE)) {
                List<Message> messages = event.getChannel().getHistory().retrievePast(Objects.requireNonNull(event.getOption("combien")).getAsInt()).complete();
                event.getTextChannel().deleteMessages(messages).queue();

                event.reply("Vous avez correctement supprimé " + Objects.requireNonNull(event.getOption("combien")).getAsInt() + " messages.")
                        .setEphemeral(true)
                        .queue();
                return;
            }
            event.replyEmbeds(discord.getErrorEmbed()).setEphemeral(true).queue();
        }

        if (event.getName().equals("statut")) {
            MessageEmbed embedBuilder = new EmbedBuilder()
                    .setColor(discord.getDiscordColor())
                    .setTitle(":white_check_mark: Le serveur est en ligne !")
                    .setDescription("__Statut actuel:__ **on**\n" +
                            "Vous pouvez aller y jouer !").build();
            event.replyEmbeds(embedBuilder).queue();
        }
    }
}
