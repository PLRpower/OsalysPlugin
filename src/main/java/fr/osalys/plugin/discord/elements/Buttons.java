package fr.osalys.plugin.discord.elements;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.AudioChannel;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.ArrayList;
import java.util.Arrays;

public class Buttons {

    public ArrayList<Button> getDocumentationButtons(int pageNumber) {

        Button nextButton = (pageNumber == 3) ? Button.primary("docNext", Emoji.fromUnicode("▶")).asDisabled() : Button.primary("docNext", Emoji.fromUnicode("▶"));
        Button previousButton = (pageNumber == 1) ? Button.primary("docPrevious", Emoji.fromUnicode("◀")).asDisabled() : Button.primary("docPrevious", Emoji.fromUnicode("◀"));
        return new ArrayList<>(Arrays.asList(previousButton, nextButton));
    }

    public ArrayList<Button> getAudioPanelButtons(AudioChannel channel) {
        ArrayList<Button> list = new ArrayList<>();

        if (channel.getGuild().getPublicRole().hasPermission(channel, Permission.VIEW_CHANNEL)) {
            list.add(Button.danger("audioPrivate", "Rendre privé"));
        } else {
            list.add(Button.success("audioPublic", "Rendre public"));
        }
        list.add(Button.primary("audioChangeName", "Changer le nom"));
        list.add(Button.primary("audioSetUserLimit", "Changer la limite d'utilisateurs"));
        list.add(Button.secondary("audioViewWhitelist", "Voir la whitelist"));
        list.add(Button.success("audioAddWhitelist", "Ajouter un utilisateur à la whitelist"));
        return list;
    }

    public ArrayList<Button> getAudioPanelButtons2() {
        ArrayList<Button> list = new ArrayList<>();
        list.add(Button.secondary("audioViewWhitelist", "Voir la whitelist"));
        list.add(Button.success("audioAddWhitelist", "Ajouter un utilisateur à la whitelist"));
        list.add(Button.success("audioRemoveWhitelist", "Retirer un utilisateur de la whitelist"));
        return list;
    }

    public ArrayList<Button> getAudioPanelButtons(boolean publique) {
        ArrayList<Button> list = new ArrayList<>();

        if (publique) {
            list.add(Button.success("audioPublic", "Rendre public"));
        } else {
            list.add(Button.danger("audioPrivate", "Rendre privé"));
        }
        list.add(Button.primary("audioChangeName", "Changer le nom"));
        list.add(Button.primary("audioSetUserLimit", "Changer la limite d'utilisateurs"));
        list.add(Button.secondary("audioViewWhitelist", "Voir la whitelist"));
        list.add(Button.success("audioAddWhitelist", "Ajouter un utilisateur à la whitelist"));
        return list;
    }
}
