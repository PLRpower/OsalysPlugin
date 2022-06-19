package fr.osalys.plugin.discord.listeners;

import fr.osalys.plugin.discord.Discord;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class OnGuildVoiceMove extends ListenerAdapter {

    private final Discord discord;

    public OnGuildVoiceMove(Discord discord) {
        this.discord = discord;
    }

    @Override
    public void onGuildVoiceMove(@NotNull GuildVoiceMoveEvent event) {
        if (!event.getChannelLeft().getId().equals("987648743778189373")) {
            if (event.getChannelLeft().getMembers().size() == 0) {
                event.getChannelLeft().delete().queue();
            }
        }

        if (event.getChannelJoined().getId().equals("987648743778189373")) {
            event.getGuild().createVoiceChannel("Channel de " + event.getMember().getNickname(), event.getGuild().getCategoryById("986594441324294165"))
                    .queue(voiceChannel -> event.getGuild().moveVoiceMember(event.getMember(), voiceChannel).queue());
        }
    }
}
