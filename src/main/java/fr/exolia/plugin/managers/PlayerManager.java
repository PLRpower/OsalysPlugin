package fr.exolia.plugin.managers;

import fr.exolia.plugin.Main;
import fr.exolia.plugin.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerManager {

    private final Main main = Main.getInstance();
    private final ItemStack[] items = new ItemStack[40];
    private final Plugin plugin = main;

    public ItemStack[] getItems() {
        return items;
    }

    public void SaveInventory(Player player){
        for (int slot = 0; slot < 36; slot++) {
            ItemStack item = player.getInventory().getItem(slot);
            if (item != null) {
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

    public void initModerationMod(Player player){
        main.getModerators().add(player.getUniqueId());
        player.sendMessage( main.prefixInfo + "Mode modération §aactivé§7.");
        SaveInventory(player);

        ItemBuilder invSee = new ItemBuilder(Material.PAPER).setName("§aVoir l'inventaire").setLore("§7Clique droit sur un joueur", "§7pour voir son inventaire");
        ItemBuilder vanish = new ItemBuilder(Material.FEATHER).setName("§aVanish").setLore("§7Clique droit pour", "§7activer/désactiver le vanish");
        ItemBuilder freeze = new ItemBuilder(Material.PACKED_ICE).setName("§aFreeze").setLore("§7Clique droit sur un joueur", "§7pour le rendre immobile");
        ItemBuilder reports = new ItemBuilder(Material.BOOK).setName("§aReports").setLore("§7Clique droit sur un joueur", "§7pour voir ses reports");
        player.getInventory().setItem(0, invSee.toItemStack());
        player.getInventory().setItem(1, vanish.toItemStack());
        player.getInventory().setItem(2, freeze.toItemStack());
        player.getInventory().setItem(3, reports.toItemStack());
    }

    public void destroyModerationMod(Player player){
        main.getModerators().remove(player.getUniqueId());
        player.getInventory().clear();
        player.sendMessage(main.prefixInfo + "Mode modération §cdésactivé§7.");
        GiveInventory(player);
    }


    public void initVanish(Player player){
        main.getVanished().add(player.getUniqueId());
    }

    public void destroyVanish(Player player){
        main.getVanished().remove(player.getUniqueId());
    }

    public void initStaffChat(Player player){
        main.getStaffChat().add(player.getUniqueId());
        player.sendMessage( main.prefixInfo + "StaffChat §aactivé§7.");
    }

    public void destroyStaffChat(Player player){
        main.getStaffChat().remove(player.getUniqueId());
        player.sendMessage(main.prefixInfo + "StaffChat §cdésactivé§7.");
    }

    public void initFreeze(Player target, Player player){
        main.getFrozenPlayers().put(target.getUniqueId(), target.getLocation());
        target.sendMessage(main.prefixInfo + "Vous avez été §cimmobilisé §7par un modérateur");
        player.sendMessage(main.prefixInfo + "Vous avez §cimmobilisé §b" + target.getName());
    }

    public void destoryFreeze(Player target, Player player){
        main.getFrozenPlayers().remove(target.getUniqueId());
        target.sendMessage(main.prefixInfo + "Vous avez été §adésimmobilisé §7un modérateur");
        player.sendMessage(main.prefixInfo + "Vous avez §adésimmobilisé §b" + target.getName());
    }

    public void initNightVision(Player player){
        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 10000,2, false, false));
        player.sendMessage(main.prefixInfo + "Vous avez §aactivé §7la vision nocturne.");
    }

    public void destroyNightVision(Player player){
        player.removePotionEffect(PotionEffectType.NIGHT_VISION);
        player.sendMessage(main.prefixInfo + "Vous avez §cdésactivé §7la vision nocturne.");
    }


    public static boolean isInModerationMod(Player player) {
        return Main.getInstance().getModerators().contains(player.getUniqueId());
    }
    public static boolean isFreeze(Player player) {
        return Main.getInstance().getFrozenPlayers().containsKey(player.getUniqueId());
    }
    public static boolean isNightVision(Player player) {
        return player.hasPotionEffect(PotionEffectType.NIGHT_VISION);
    }
    public static boolean isStaffChat(Player player) {
        return Main.getInstance().getStaffChat().contains(player.getUniqueId());
    }
    public static boolean isVanished(Player player) {return Main.getInstance().getVanished().contains(player.getUniqueId());}
}