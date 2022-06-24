package fr.osalys.plugin.discord.listeners;

import fr.osalys.plugin.discord.Discord;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.VoiceChannel;
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
        if (event.getModalId().equals("suggest")) {
            Objects.requireNonNull(Objects.requireNonNull(event.getGuild()).getTextChannelById("986609839562162217"))
                    .sendMessageEmbeds(discord.getEmbeds().getSuggestionEmbed(
                            event.getUser(),
                            Objects.requireNonNull(event.getValue("title")).getAsString(),
                            Objects.requireNonNull(event.getValue("suggest")).getAsString()))
                    .queue(message -> {
                        message.addReaction("✅").queue();
                        message.addReaction("❌").queue();
                    });
            event.replyEmbeds(discord.getEmbeds().getPublishedSuggestionEmbed()).setEphemeral(true).queue();
        }

        if (event.getModalId().equals("audioLimitUsers")) {
            VoiceChannel channel = (VoiceChannel) Objects.requireNonNull(Objects.requireNonNull(event.getMember()).getVoiceState()).getChannel();
            assert channel != null;
            int number = Integer.parseInt(Objects.requireNonNull(event.getValue("number")).getAsString());
            if (number < 1) {
                number = 0;
            }
            if (number > 99) {
                number = 99;
            }
            channel.getManager().setUserLimit(number).queue();
            event.editMessageEmbeds(discord.getEmbeds().getAudioPanelEmbedWithCustomUsers(channel, number)).queue();
        }

        if (event.getModalId().equals("audioChangeName")) {
            VoiceChannel channel = (VoiceChannel) Objects.requireNonNull(Objects.requireNonNull(event.getMember()).getVoiceState()).getChannel();
            assert channel != null;
            String name = Objects.requireNonNull(event.getValue("name")).getAsString();
            channel.getManager().setName(name).queue();
            event.editMessageEmbeds(discord.getEmbeds().getAudioPanelEmbedWithCustomName(channel, name)).queue();
        }

        if (event.getModalId().equals("audioAddWhitelist")) {
            VoiceChannel channel = (VoiceChannel) Objects.requireNonNull(Objects.requireNonNull(event.getMember()).getVoiceState()).getChannel();
            assert channel != null;
            long id = Long.parseLong(Objects.requireNonNull(event.getValue("id")).getAsString());
            channel.getManager().putMemberPermissionOverride(id, Permission.MANAGE_CHANNEL.getRawValue(), Permission.getRaw()).queue();
            event.editMessageEmbeds(discord.getEmbeds().getAudioPanelEmbedWithCustomWhitelist(channel, channel.getPermissionContainer().getMemberPermissionOverrides().size() + 1)).queue();
        }
    }
}