package fr.osalys.plugin.discord.elements;

import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Modal;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;

public class Modals {

    private final Modal suggestionModal = Modal.create("suggest", "Proposer une suggestion")
            .addActionRows(
                    ActionRow.of(TextInput.create("title", "Titre", TextInputStyle.SHORT).setPlaceholder("Titre de votre suggestion/idée ici ...").setMaxLength(30).build()),
                    ActionRow.of(TextInput.create("suggest", "Votre suggestion", TextInputStyle.PARAGRAPH).setPlaceholder("Proposez votre suggestion/idée ici ...").setMaxLength(1000).build())
            ).build();

    private final Modal audioLimitUsersModal = Modal.create("audioLimitUsers", "Définir la limite d'utilisateurs")
            .addActionRow(TextInput.create("number", "Nombre d'utilisateurs", TextInputStyle.SHORT).setPlaceholder("Séléctionnez un nombre").build())
            .build();

    private final Modal audioChangeNameModal = Modal.create("audioChangeName", "Changer le nom du salon vocal")
            .addActionRow(TextInput.create("name", "Nom du salon vocal", TextInputStyle.SHORT).setPlaceholder("Séléctionnez un nom").build())
            .build();

    private final Modal audioAddWhitelist = Modal.create("audioAddWhitelist", "Ajouter un membre")
            .addActionRow(TextInput.create("id", "Ajouter un utilisateur à la whitelist", TextInputStyle.SHORT).setPlaceholder("Séléctionnez un nom").build())
            .build();

    public Modal getSuggestionModal() {
        return suggestionModal;
    }

    public Modal getAudioLimitUsersModal() {
        return audioLimitUsersModal;
    }

    public Modal getAudioChangeNameModal() {
        return audioChangeNameModal;
    }

    public Modal getAudioAddWhitelist() {
        return audioAddWhitelist;
    }


}
