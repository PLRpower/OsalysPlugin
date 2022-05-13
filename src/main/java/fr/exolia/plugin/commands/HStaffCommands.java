package fr.exolia.plugin.commands;

import fr.exolia.plugin.Main;
import fr.exolia.plugin.database.Exolions;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HStaffCommands implements CommandExecutor {

    private final Main main = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        Player player = (Player)sender;
        if(!player.hasPermission(main.permissionHStaff)){
            player.sendMessage(main.prefixError + "Vous n'avez pas la permission d'exécuter cette commande !");
        }else{
            if(label.equalsIgnoreCase("exolionadmin")){
                if(args.length == 0){
                    player.sendMessage(main.prefixError + "Veuillez saisir un argument ! §6(give/remove/set)");
                    return false;
                }else{
                    Player target = Bukkit.getPlayer(args[1]);
                    if(target == null){
                        player.sendMessage(main.prefixError + "Ce joueur n'est pas connecté ou n'existe pas !");
                        return false;
                    }
                    if(args.length < 3){
                        player.sendMessage(main.prefixError + "Vous devez saisir un joueur ainsi qu'un montant.");
                        return false;
                    }else{
                        Exolions exolions = new Exolions(target);
                        if(args[0].equalsIgnoreCase("give")){
                            exolions.addCoins(Float.parseFloat(args[2]));
                            player.sendMessage(main.prefixInfo + "Vous avez correctement ajouté §b" + args[2] + " Exolions §7à §b" + target.getName() + "§7.");
                            target.sendMessage(main.prefixInfo + "Vous avez correctement reçu §b" + args[2] + " Exolions §7.");
                            return true;
                        }
                        else if(args[0].equalsIgnoreCase("remove")){
                            exolions.removeCoins(Float.parseFloat(args[2]));
                            player.sendMessage(main.prefixInfo + "Vous avez correctement retiré §b" + args[2] + " Exolions §7à §b" + target.getName() + "§7.");
                            target.sendMessage(main.prefixInfo + "Vous avez été débité de §b" + args[2] + " Exolions §7.");
                            return true;
                        }
                        else if(args[0].equalsIgnoreCase("set")){
                            exolions.setCoins(Float.parseFloat(args[2]));
                            player.sendMessage(main.prefixInfo + "Vous avez correctement retiré §b" + args[2] + " Exolions §7à §b" + target.getName() + "§7.");
                            target.sendMessage(main.prefixInfo + "Vous avez été débité de §b" + args[2] + " Exolions §7.");
                            return true;
                        }else{
                            player.sendMessage(main.prefixError + "Veuillez saisir un argument correct ! §6(give/remove/set)");
                            return false;
                        }
                    }
                }
            }

            if (label.equalsIgnoreCase("freco")){
                Bukkit.broadcastMessage("§f[§a+§f] " + player.getDisplayName());
                return true;
            }

            if (label.equalsIgnoreCase("fdeco")){
                Bukkit.broadcastMessage("§f[§c-§f] " + player.getDisplayName());
                return true;
            }
        }
        return false;
    }
}