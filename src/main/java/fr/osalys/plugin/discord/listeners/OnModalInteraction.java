package fr.osalys.plugin.discord.listeners;

import fr.osalys.plugin.discord.Discord;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class OnModalInteraction extends ListenerAdapter {

    private final Discord discord;

    public OnModalInteraction(Discord discord) {
        this.discord = discord;
    }


    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        if (event.getModalId().equals("suggest-modal")) {
            MessageEmbed embedBuilder = new EmbedBuilder()
                    .setAuthor(event.getUser().getName(), null, event.getUser().getEffectiveAvatarUrl())
                    .setColor(discord.getDiscordColor())
                    .setTitle("Nouvelle suggestion !")
                    .setDescription(Objects.requireNonNull(event.getValue("suggest")).getAsString()).build();

            Objects.requireNonNull(Objects.requireNonNull(event.getGuild()).getTextChannelById("986609839562162217")).sendMessageEmbeds(embedBuilder).queue(message -> {
                message.addReaction("✅").queue();
                message.addReaction("❌").queue();
            });

            MessageEmbed publishedSuggestionEmbed = new EmbedBuilder()
                    .setColor(discord.getSuccessColor())
                    .setTitle(":exclamation: Suggestion ajoutée !")
                    .setDescription("Votre suggestion a correctement été publiée dans le salon <#986609839562162217> !").build();

            event.replyEmbeds(publishedSuggestionEmbed).setEphemeral(true).queue();
        }
    }
}