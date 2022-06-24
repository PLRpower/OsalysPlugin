package fr.osalys.plugin.discord.elements;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Messages {

    private final List<String[]> messages = Arrays.asList(
            new String[]{"Salut :v: ", ":wave: Hey ! Comment vas-tu ?", "Coucou, comment ça va ? :smiley:", "Bonjour :grinning:"},
            new String[]{"Ça va bien :ok_hand:, et toi ? ", "Je vais très bien, merci ! :grin:", "C'est gentil de me demander, merci. Je vais bien :ok_hand:"},
            new String[]{"Il suffit de vous créer un compte et de télécharger le launcher sur notre site. Bon jeu ! :innocent:", "Vous être prêts à rejoindre l'aventure ? :magic_wand: C'est parti ! https://exolia.site/launcher "},
            new String[]{"Je vous invite à créer un ticket afin d'avoir un support efficace de notre équipe ! :ticket: ", ":question: Pour obtenir de l'aide, je vous invite à regarder notre FAQ dans le salon :"},
            new String[]{"Je suis votre assistant Osalys ! Je peux vous aider en cas de question ou de problème. :innocent: ", "Mon nom est l'Assistant Osalys. 15 lettres, 6 syllabes, mais 1 seule mission : vous aider :sunglasses: :muscle:"},
            new String[]{"Avec plaisir", "Il n'y a pas de quoi :blush:", "Tout le plaisir est pour moi :grinning:", "Avec plaisir. Je suis là si vous avez besoin de moi :grinning:"}
    );

    public String getAMessage(int maxAt) {
        Random random = new Random();
        int index = random.nextInt(messages.get(maxAt).length);
        return messages.get(maxAt)[index];
    }
}
