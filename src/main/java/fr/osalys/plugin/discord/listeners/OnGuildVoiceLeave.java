package fr.osalys.plugin.discord.listeners;

import fr.osalys.plugin.discord.Discord;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class OnGuildVoiceLeave extends ListenerAdapter {

    private final Discord discord;

    public OnGuildVoiceLeave(Discord discord) {
        this.discord = discord;
    }

    @Override
    public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event) {
        if (!event.getChannelLeft().getId().equals("987648743778189373")) {
            if (event.getChannelLeft().getMembers().size() == 0) {
                event.getChannelLeft().delete().queue();
            }
        }
    }
}
