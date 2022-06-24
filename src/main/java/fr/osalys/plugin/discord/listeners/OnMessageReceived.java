package fr.osalys.plugin.discord.listeners;

import fr.osalys.plugin.discord.Discord;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OnMessageReceived extends ListenerAdapter {

    private final Discord discord;
    public OnMessageReceived(Discord discord) {
        this.discord = discord;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getMessage().getMentions().isMentioned(User.fromId("981947225984876584"))) {
            String toLowerCase = event.getMessage().getContentRaw().toLowerCase();
            String[] splitMessage = toLowerCase.split("[-?=,;' ]");
            List<Float> percentage = new ArrayList<>();
            percentage.add(messageProbability(splitMessage, Arrays.asList("bonjour", "salut", "hey", "yo", "hello", "coucou", "bonsoir", "yoo")));
            percentage.add(messageProbability(splitMessage, Arrays.asList("comment", "ça", "va", "cv", "cela", "bien", "votre", "journée")));
            percentage.add(messageProbability(splitMessage, Arrays.asList("ip", "connecter", "jouer", "comment", "quelle", "connexion", "adresse", "serveur", "server", "rejoindre")));
            percentage.add(messageProbability(splitMessage, Arrays.asList("comment", "faire", "problème", "question", "comment", "connexion", "erreur", "aide")));
            percentage.add(messageProbability(splitMessage, Arrays.asList("qui", "est", "tu", "vous", "es", "comment", "tu", "apelle", "appelle", "apele", "nom", "ton", "vous", "votre", "prénom")));
            percentage.add(messageProbability(splitMessage, Arrays.asList("merci", "remercie", "cimer", "mercii")));

            int maxAt = 0;
            for (int i = 0; i < percentage.size(); i++) {
                maxAt = percentage.get(i) > percentage.get(maxAt) ? i : maxAt;
            }

            if (percentage.get(maxAt) > 10) {
                event.getChannel().sendMessage(discord.getMessages().getAMessage(maxAt)).queue();
                return;
            }
            event.getChannel().sendMessage("Je n'ai pas compris votre demande, désolé !").queue();
        }
    }

    public float messageProbability(String[] userMessage, List<String> recognised_words) {
        float messageCertainly = 0;
        for (String word : userMessage) {
            if (recognised_words.contains(word)) {
                messageCertainly += 1;
            }
        }
        float percentage = messageCertainly / userMessage.length;
        return percentage * 100;
    }

}
