package fr.osalys.plugin.discord.listeners;

import fr.osalys.plugin.discord.Discord;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class OnButtonInteraction extends ListenerAdapter {

    private final Discord discord;

    public OnButtonInteraction(Discord discord) {
        this.discord = discord;
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        switch (Objects.requireNonNull(event.getButton().getId())) {
            case "docNext" -> {
                String[] args = Objects.requireNonNull(Objects.requireNonNull(event.getMessage().getEmbeds().get(0).getFooter()).getText()).split("[ /]");
                int pageNumber = Integer.parseInt(args[1]);
                event.editMessageEmbeds(discord.getEmbeds().getDocumentationEmbed(pageNumber + 1).build()).setActionRow(discord.getButtons().getDocumentationButtons(pageNumber + 1)).queue();
            }
            case "docPrevious" -> {
                String[] args = Objects.requireNonNull(Objects.requireNonNull(event.getMessage().getEmbeds().get(0).getFooter()).getText()).split("[ /]");

                int pageNumber = Integer.parseInt(args[1]);
                event.editMessageEmbeds(discord.getEmbeds().getDocumentationEmbed(pageNumber - 1).build()).setActionRow(discord.getButtons().getDocumentationButtons(pageNumber - 1)).queue();
            }

            case "audioViewWhitelist" -> {
                VoiceChannel channel = (VoiceChannel) Objects.requireNonNull(Objects.requireNonNull(event.getMember()).getVoiceState()).getChannel();
                ArrayList<String> list = new ArrayList<>();
                for (int i = 0; i < Objects.requireNonNull(channel).getPermissionContainer().getMemberPermissionOverrides().size(); i++) {
                    list.add("<@" + channel.getPermissionContainer().getMemberPermissionOverrides().get(i).getId() + ">");
                }
                event.replyEmbeds(discord.getEmbeds().getNamesEmbed(list, channel)).setEphemeral(true).queue();
            }
            case "audioSetUserLimit" -> event.replyModal(discord.getModals().getAudioLimitUsersModal()).queue();
            case "audioChangeName" -> event.replyModal(discord.getModals().getAudioChangeNameModal()).queue();
            case "audioPublic" -> {
                VoiceChannel channel = (VoiceChannel) Objects.requireNonNull(Objects.requireNonNull(event.getMember()).getVoiceState()).getChannel();
                assert channel != null;
                channel.getManager().removePermissionOverride(channel.getGuild().getPublicRole().getIdLong()).queue();
                event.editMessageEmbeds(discord.getEmbeds().getAudioPanelEmbedWithCustomStatus(channel, "Public :green_circle:"))
                        .setActionRow(discord.getButtons().getAudioPanelButtons(false)).queue();
            }

            case "audioPrivate" -> {
                VoiceChannel channel = (VoiceChannel) Objects.requireNonNull(Objects.requireNonNull(event.getMember()).getVoiceState()).getChannel();
                assert channel != null;
                channel.getManager().putRolePermissionOverride(channel.getGuild().getPublicRole().getIdLong(), Permission.getRaw(), Permission.VIEW_CHANNEL.getRawValue() + Permission.VOICE_CONNECT.getRawValue()).queue();
                event.editMessageEmbeds(discord.getEmbeds().getAudioPanelEmbedWithCustomStatus(channel, "Privé :red_circle:")).setActionRow(discord.getButtons().getAudioPanelButtons(true)).queue();
            }
            case "audioAddWhitelist" -> event.replyModal(discord.getModals().getAudioAddWhitelist()).queue();

        }
    }
}
