package fr.exolia.plugin.managers;

import fr.exolia.plugin.Main;
import fr.exolia.plugin.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerManager {

    private final Main main = Main.getInstance();
    private final Player player;
    private final ItemStack[] items = new ItemStack[40];
    private boolean vanished;

    public PlayerManager(Player player){
        this.player = player;
        vanished = false;
    }

    public ItemStack[] getItems() {
        return items;
    }

    public void SaveInventory(){
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

    public void GiveInventory(){
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

    public void initModerationMod(){
        main.getPlayers().put(player.getUniqueId(), this);
        main.getModerators().add(player.getUniqueId());
        player.sendMessage( main.prefixInfo + "Mode modération §aactivé§7.");
        SaveInventory();

        ItemBuilder invSee = new ItemBuilder(Material.PAPER).setName("§aVoir l'inventaire").setLore("§7Clique droit sur un joueur", "§7pour voir son inventaire");
        ItemBuilder vanish = new ItemBuilder(Material.FEATHER).setName("§aVanish").setLore("§7Clique droit pour", "§7activer/désactiver le vanish");
        ItemBuilder freeze = new ItemBuilder(Material.PACKED_ICE).setName("§aFreeze").setLore("§7Clique droit sur un joueur", "§7pour le rendre immobile");
        ItemBuilder reports = new ItemBuilder(Material.BOOK).setName("§aReports").setLore("§7Clique droit sur un joueur", "§7pour voir ses reports");
        player.getInventory().setItem(0, invSee.toItemStack());
        player.getInventory().setItem(1, vanish.toItemStack());
        player.getInventory().setItem(2, freeze.toItemStack());
        player.getInventory().setItem(3, reports.toItemStack());
    }

    public void destroyModerationMod(){
        main.getPlayers().remove(player.getUniqueId());
        main.getModerators().remove(player.getUniqueId());
        player.getInventory().clear();
        player.sendMessage(main.prefixInfo + "Mode modération §cdésactivé§7.");
        GiveInventory();
        setVanished(false);
    }

    public void setVanished(boolean vanished){
        this.vanished = vanished;
        if(vanished){
            Bukkit.getOnlinePlayers().forEach(players -> players.hidePlayer(Bukkit.getPluginManager().getPlugin("Essentials"), player));
        } else {
            Bukkit.getOnlinePlayers().forEach(players -> players.showPlayer(Bukkit.getPluginManager().getPlugin("Essentials"), player));
        }
    }

    public void initStaffChat(){
        main.getPlayers().put(player.getUniqueId(), this);
        main.getStaffChat().add(player.getUniqueId());
        player.sendMessage( main.prefixInfo + "StaffChat §aactivé§7.");
    }

    public void destroyStaffChat(){
        main.getPlayers().remove(player.getUniqueId());
        main.getStaffChat().remove(player.getUniqueId());
        player.sendMessage(main.prefixInfo + "StaffChat §cdésactivé§7.");
    }

    public void initFreeze(Player target){
        main.getFrozenPlayers().put(target.getUniqueId(), target.getLocation());
        target.sendMessage(main.prefixInfo + "Vous avez été immobilisé par un modérateur");
        player.sendMessage(main.prefixInfo + "Vous avez immobilisé " + target.getName());
    }

    public void destoryFreeze(Player target){
        main.getFrozenPlayers().remove(target.getUniqueId());
        target.sendMessage(main.prefixInfo + "Vous avez été désimmobilisé un modérateur");
        player.sendMessage(main.prefixInfo + "Vous avez désimmobilisé " + target.getName());
    }

    public void initNightVision(){
        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 10000,2, false, false));
        player.sendMessage(main.prefixInfo + "Vous avez §aactivé §7la vision nocturne.");
    }

    public void destroyNightVision(){
        player.removePotionEffect(PotionEffectType.NIGHT_VISION);
        player.sendMessage(main.prefixInfo + "Vous avez §aactivé §7la vision nocturne.");
    }

    public static PlayerManager getFromPlayer(Player player) {return Main.getInstance().getPlayers().get(player.getUniqueId());}
    public static boolean isInModerationMod(Player player) {return Main.getInstance().getModerators().contains(player.getUniqueId());}
    public static boolean isFreeze(Player player) {return Main.getInstance().getFrozenPlayers().containsKey(player.getUniqueId());}
    public static boolean isNightVision(Player player) {return player.hasPotionEffect(PotionEffectType.NIGHT_VISION);}
    public boolean isVanished() {return vanished;}
}