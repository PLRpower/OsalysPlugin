package fr.exolia.plugin.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ChatManager {

    public void clearChatForPlayersOnly(){
        for(Player targetPlayers : Bukkit.getOnlinePlayers()){
            if(!targetPlayers.hasPermission("exolia.staff")){
                for (int x = 0; x <= 100; x++) {
                    targetPlayers.sendMessage(" ");
                }
            }
        }
    }

    public void clearChatForOnePlayer(Player player){
        for (int x = 0; x <= 100; x++) {
            player.sendMessage(" ");
        }
    }

    public void clearChatForAll(){
        for (int x = 0; x <= 100; x++) {
            Bukkit.broadcastMessage(" ");
        }
    }

    public void sendClearChatErrorMessage(Player player){
        player.sendMessage("§c§lExolia §8➜ §c/clearchat §7Clear le chat pour tout le monde");
        player.sendMessage("§c§lExolia §8➜ §c/clearchat <all> §7Clear le chat pour tout le monde");
        player.sendMessage("§c§lExolia §8➜ §c/clearchat <player> §7Clear le chat pour les joueurs uniquement");
        player.sendMessage("§c§lExolia §8➜ §c/clearchat <player> <Nom d'un joueur> §7Clear le chat pour un seul joueur");
    }

}
