package fr.osalys.plugin.discord.listeners;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.AudioChannel;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class VoiceChatActions extends ListenerAdapter {

    @Override
    public void onGuildVoiceMove(@NotNull GuildVoiceMoveEvent event) {
        removeChannel((VoiceChannel) event.getChannelLeft(), event.getMember());
        createChannel(event.getChannelJoined(), event.getGuild(), event.getMember());
    }

    @Override
    public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event) {
        removeChannel((VoiceChannel) event.getChannelLeft(), event.getMember());
    }

    @Override
    public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event) {
        createChannel(event.getChannelJoined(), event.getGuild(), event.getMember());
    }

    private void removeChannel(VoiceChannel channelLeft, Member member) {
        if (!channelLeft.getId().equals("987648743778189373")) {
            if (channelLeft.getMembers().size() == 0) {
                channelLeft.delete().queue();
                return;
            }
            channelLeft.getManager().removePermissionOverride(member).queue();
            channelLeft.getManager().putMemberPermissionOverride(channelLeft.getMembers().get(0).getIdLong(), Permission.MANAGE_CHANNEL.getRawValue(), Permission.getRaw()).queue();
        }
    }

    private void createChannel(AudioChannel channelJoined, Guild guild, Member member) {
        if (channelJoined.getId().equals("987648743778189373")) {
            guild.createVoiceChannel("Channel de " + member.getUser().getName(), guild.getCategoryById("986594441324294165"))
                    .addMemberPermissionOverride(member.getIdLong(), Permission.MANAGE_CHANNEL.getRawValue(), Permission.getRaw())
                    .queue(voiceChannel -> guild.moveVoiceMember(member, voiceChannel).queue());
        }
    }
}
