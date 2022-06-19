package fr.osalys.plugin.discord.listeners;

import fr.osalys.plugin.discord.Discord;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class OnGuildVoiceJoin extends ListenerAdapter {

    private final Discord discord;

    public OnGuildVoiceJoin(Discord discord) {
        this.discord = discord;
    }

    @Override
    public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event) {
        if (event.getChannelJoined().getId().equals("987648743778189373")) {
            event.getGuild().createVoiceChannel("Channel de " + event.getMember().getNickname(), event.getGuild().getCategoryById("986594441324294165"))
                    .queue(voiceChannel -> event.getGuild().moveVoiceMember(event.getMember(), voiceChannel).queue());
        }
    }
}
