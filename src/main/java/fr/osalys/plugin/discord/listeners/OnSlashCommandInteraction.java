package fr.osalys.plugin.discord.listeners;

import fr.osalys.plugin.discord.Discord;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.AudioChannel;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

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
            event.replyEmbeds(discord.getEmbeds().getHelpInfoEmbed()).addActionRow(discord.getMenus().getHelpMenu()).setEphemeral(true).queue();
            return;
        }

        if (event.getName().equals("suggestion")) {
            event.replyModal(discord.getModals().getSuggestionModal()).queue();
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
            event.replyEmbeds(discord.getEmbeds().getErrorEmbed()).setEphemeral(true).queue();
            return;
        }
        if (event.getName().equals("statut")) {
            event.replyEmbeds(discord.getEmbeds().getStatutEmbed()).queue();
        }

        if (event.getName().equals("doc")) {
            event.replyEmbeds(discord.getEmbeds().getDocumentationEmbed(1).build()).addActionRow(discord.getButtons().getDocumentationButtons(1)).setEphemeral(true).queue();
        }

        if (event.getName().equals("audio")) {
            if (Objects.requireNonNull(Objects.requireNonNull(event.getMember()).getVoiceState()).inAudioChannel()) {
                AudioChannel audioChannel = Objects.requireNonNull(Objects.requireNonNull(event.getMember()).getVoiceState()).getChannel();
                assert audioChannel != null;
                if (event.getMember().hasPermission(audioChannel, Permission.MANAGE_CHANNEL)) {
                    event.replyEmbeds(discord.getEmbeds().getAudioPanelEmbed(audioChannel))
                            .addActionRow(discord.getButtons().getAudioPanelButtons(audioChannel))
                            .addActionRow(discord.getButtons().getAudioPanelButtons2()).setEphemeral(true).queue();
                    return;
                }
            }
            event.replyEmbeds(discord.getEmbeds().getNotInVoiceEmbed()).setEphemeral(true).queue();
        }
    }
}
