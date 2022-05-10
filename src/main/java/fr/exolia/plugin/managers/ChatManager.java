package fr.exolia.plugin.managers;

import fr.exolia.plugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ChatManager {

    public void clearChatForPlayersOnly(){
        for(Player targetPlayers : Bukkit.getOnlinePlayers()){
            if(!targetPlayers.hasPermission(Main.getInstance().permissionStaff)){
                for (int x = 0; x <= 100; x++) {
                    targetPlayers.sendMessage(" ");
                }
                targetPlayers.sendMessage(Main.getInstance().prefixAnnounce + "Le chat vient d'être nettoyé par un Administrateur.");
            }
        }
    }

    public void clearChatForOnePlayer(Player player){
        for (int x = 0; x <= 100; x++){
            player.sendMessage(" ");
        }
        player.sendMessage(Main.getInstance().prefixAnnounce + "Ton chat vient d'être nettoyé par §b" + player.getName() + "§a.");
    }

    public void clearChatForAll(){
        for (int x = 0; x <= 100; x++){
            Bukkit.broadcastMessage(" ");
        }
        Bukkit.broadcastMessage(Main.getInstance().prefixAnnounce + "Le chat vient d'être nettoyé par un Modérateur.");
    }

    public void sendClearChatErrorMessage(Player player){
        player.sendMessage(Main.getInstance().prefixError + "/clearchat §7Clear le chat pour tout le monde");
        player.sendMessage(Main.getInstance().prefixError + "/clearchat <all> §7Clear le chat pour tout le monde");
        player.sendMessage(Main.getInstance().prefixError + "/clearchat <player> §7Clear le chat pour les joueurs uniquement");
        player.sendMessage(Main.getInstance().prefixError + "/clearchat <player> <Nom d'un joueur> §7Clear le chat pour un seul joueur");
    }

    public void sendReportToStaff(String reason, String targetName){
        for(Player players : Bukkit.getOnlinePlayers()){
            if(players.hasPermission(Main.getInstance().permissionStaff)){
                players.sendMessage("§cLe joueur §6" + targetName + " §ca été signalé pour : §6" + reason);
            }
        }
    }


    public void sendMessageToStaff(Player player, String message){
        Bukkit.getOnlinePlayers().stream().filter(players -> players.hasPermission(Main.getInstance().permissionStaff)).forEach(players -> players.sendMessage("§2StaffChat §a" + player.getName() + " §f» §b" + message));
    }
}
