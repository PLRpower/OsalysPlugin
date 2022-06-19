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
    List<String> messages = Arrays.asList(
            "Salut, bienvenue sur Osalys !",
            "Cela va bien, et toi ?",
            "Pour jouer sur Osalys, il suffit de te créer un compte, puis de télécharger le launcher sur notre site : <https://exolia.site>. Bon jeu !",
            "Tu rencontre un problème, tu as une question ? Créer un ticket afin d'avoir un support rapide de notre équipe !",
            "");

    public OnMessageReceived(Discord discord) {
        this.discord = discord;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (!event.getAuthor().getId().equals("981947225984876584")) {
            if (event.getMessage().getMentions().isMentioned(User.fromId("981947225984876584"))) {
                event.getChannel().sendMessage("Test !").queue();
            }
            String toLowerCase = event.getMessage().getContentRaw().toLowerCase();
            String[] splitMessage = toLowerCase.split("[?=,;' ]");

            List<Float> percentage = new ArrayList<>();
            percentage.add(messageProbability(splitMessage, Arrays.asList("bonjour", "salut", "hey", "yo", "hello", "coucou")));
            percentage.add(messageProbability(splitMessage, Arrays.asList("comment", "ça", "va", "cv", "cela")));
            percentage.add(messageProbability(splitMessage, Arrays.asList("ip", "connecter", "jouer", "comment", "quelle", "connexion", "adresse")));
            percentage.add(messageProbability(splitMessage, Arrays.asList("problème", "question", "comment", "connexion", "erreur")));
            percentage.add(messageProbability(splitMessage, Arrays.asList("qui", "es", "est", "tu")));
            percentage.add(messageProbability(splitMessage, Arrays.asList("qui", "es", "est", "tu")));

            int maxAt = 0;
            for (int i = 0; i < percentage.size(); i++) {
                maxAt = percentage.get(i) > percentage.get(maxAt) ? i : maxAt;
            }

            event.getChannel().sendMessage(messages.get(maxAt)).queue();
        }
    }

    public float messageProbability(String[] userMessage, List<String> recognised_words) {
        float messageCertainly = 0;
        for (String word : userMessage) {
            if (recognised_words.contains(word)) {
                messageCertainly += 1;
            }
        }
        float percentage = messageCertainly / recognised_words.size();
        return percentage * 100;
    }

}
