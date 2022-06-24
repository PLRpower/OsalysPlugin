package fr.osalys.plugin.discord.managers;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.AudioChannel;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;

public class AudioManager {

    public String findOwner(AudioChannel channel) {
        for (Member member : channel.getMembers()) {
            if (member.hasPermission(channel, Permission.MANAGE_CHANNEL)) {
                return member.getAsMention();
            }
        }
        return channel.getMembers().get(0).getAsMention();
    }

    public String statusName(AudioChannel channel) {
        if (!channel.getGuild().getPublicRole().hasPermission(channel, Permission.VIEW_CHANNEL)) {
            return "Privé :red_circle:";
        }
        return "Public :green_circle:";
    }

    public String limiteUsers(VoiceChannel channel) {
        if (channel.getUserLimit() == 0) {
            return "Illimité";
        }
        return String.valueOf(channel.getUserLimit());
    }
}
