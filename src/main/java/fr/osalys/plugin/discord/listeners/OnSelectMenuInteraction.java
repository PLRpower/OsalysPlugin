package fr.osalys.plugin.discord.listeners;

import fr.osalys.plugin.discord.Discord;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class OnSelectMenuInteraction extends ListenerAdapter {

    private final Discord discord;
    public OnSelectMenuInteraction(Discord discord) {
        this.discord = discord;
    }

    @Override
    public void onSelectMenuInteraction(SelectMenuInteractionEvent event) {
        if (event.getComponentId().equals("help")) {

            if (event.getValues().get(0).equals("help-info")) {
                event.editMessageEmbeds(discord.getEmbeds().getHelpInfoEmbed()).queue();
                return;
            }

            if (event.getValues().get(0).equals("help-minecraft")) {
                event.editMessageEmbeds(discord.getEmbeds().getHelpMinecraftEmbed()).queue();
                return;
            }

            if (event.getValues().get(0).equals("help-music")) {
                event.editMessageEmbeds(discord.getEmbeds().getHelpMusicEmbed()).queue();
                return;
            }

            if (event.getValues().get(0).equals("help-support")) {
                event.editMessageEmbeds(discord.getEmbeds().getHelpSupportEmbed()).queue();
                return;
            }

            if (event.getValues().get(0).equals("help-bot")) {
                event.editMessageEmbeds(discord.getEmbeds().getHelpBotEmbed()).queue();
            }
        }
    }
}
