package fr.exolia.plugin.managers;

import fr.exolia.plugin.Main;
import fr.exolia.plugin.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerManager {

    public Main main = Main.getInstance();
    private final Plugin plugin = main;
    private final ItemStack[] items = new ItemStack[40];
    public ItemStack[] getItems() {
        return items;
    }

    public void SaveInventory(Player player){
        for (int slot = 0; slot < 36; slot++){
            ItemStack item = player.getInventory().getItem(slot);
            if (item != null){
                items[slot] = item;
            }
        }
        items[36] = player.getInventory().getHelmet();
        items[37] = player.getInventory().getChestplate();
        items[38] = player.getInventory().getLeggings();
        items[39] = player.getInventory().getBoots();
        player.getInventory().clear();
    }

    public void GiveInventory(Player player){
        player.getInventory().clear();
        for (int slot = 0; slot < 36; slot++){
            ItemStack item = items[slot];
            if (item != null) {
                player.getInventory().setItem(slot, item);
            }
        }
        player.getInventory().setHelmet(items[36]);
        player.getInventory().setChestplate(items[37]);
        player.getInventory().setLeggings(items[38]);
        player.getInventory().setBoots(items[39]);
    }

    public void setModerationMod(Player player, boolean moderationMod){
        if(moderationMod){
            Main.getInstance().getModerators().add(player.getUniqueId());
            SaveInventory(player);
            player.getInventory().setItem(0, new ItemBuilder(Material.PAPER).setName("§aVoir l'inventaire").setLore("§7Clique droit sur un joueur", "§7pour voir son inventaire").toItemStack());
            player.getInventory().setItem(1, new ItemBuilder(Material.FEATHER).setName("§aVanish").setLore("§7Clique droit pour", "§7activer/désactiver le vanish").toItemStack());
            player.getInventory().setItem(2, new ItemBuilder(Material.PACKED_ICE).setName("§aFreeze").setLore("§7Clique droit sur un joueur", "§7pour le rendre immobile").toItemStack());
            player.getInventory().setItem(3, new ItemBuilder(Material.BOOK).setName("§aReports").setLore("§7Clique droit sur un joueur", "§7pour voir ses reports").toItemStack());
            player.sendMessage( Main.getInstance().prefixInfo + "Mode modération §aactivé§7.");
        }else{
            Main.getInstance().getModerators().remove(player.getUniqueId());
            player.getInventory().clear();
            GiveInventory(player);
            player.sendMessage(Main.getInstance().prefixInfo + "Mode modération §cdésactivé§7.");
        }
    }

    public void setVanish(Player player, boolean vanish){
        if(vanish){
            Main.getInstance().getVanished().add(player.getUniqueId());
            player.performCommand("vanish on");
        }else{
            Main.getInstance().getVanished().remove(player.getUniqueId());
            player.performCommand("vanish off");
        }
    }

    public void setStaffChat(Player player, boolean staffChat){
        if(staffChat){
            Main.getInstance().getStaffChat().add(player.getUniqueId());
            player.sendMessage( Main.getInstance().prefixInfo + "StaffChat §aactivé§7.");
        }else{
            Main.getInstance().getStaffChat().remove(player.getUniqueId());
            player.sendMessage(Main.getInstance().prefixInfo + "StaffChat §cdésactivé§7.");
        }
    }

    public void setFreeze(Player target, Player player, boolean freeze){
        if(freeze){
            Main.getInstance().getFrozenPlayers().put(target.getUniqueId(), target.getLocation());
            target.sendMessage(Main.getInstance().prefixInfo + "Vous avez été §cimmobilisé §7par un modérateur");
            player.sendMessage(Main.getInstance().prefixInfo + "Vous avez §cimmobilisé §b" + target.getName());
        }else{
            Main.getInstance().getFrozenPlayers().remove(target.getUniqueId());
            target.sendMessage(Main.getInstance().prefixInfo + "Vous avez été §adésimmobilisé §7un modérateur");
            player.sendMessage(Main.getInstance().prefixInfo + "Vous avez §adésimmobilisé §b" + target.getName());
        }
    }

    public void setNightVision(Player player, boolean nightVision){
        if(nightVision){
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 100000,2, false, false));
            player.sendMessage(Main.getInstance().prefixInfo + "Vous avez §aactivé §7la vision nocturne.");
        }else{
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
            player.sendMessage(Main.getInstance().prefixInfo + "Vous avez §cdésactivé §7la vision nocturne.");
        }
    }

    public static boolean isInModerationMod(Player player) {
        return Main.getInstance().getModerators().contains(player.getUniqueId());
    }
    public static boolean isFreeze(Player player) {
        return Main.getInstance().getFrozenPlayers().containsKey(player.getUniqueId());
    }
    public static boolean isNightVision(Player player){
        return player.hasPotionEffect(PotionEffectType.NIGHT_VISION);
    }
    public static boolean isStaffChat(Player player) {
        return Main.getInstance().getStaffChat().contains(player.getUniqueId());
    }
    public static boolean isVanished(Player player) {return Main.getInstance().getVanished().contains(player.getUniqueId());}
}