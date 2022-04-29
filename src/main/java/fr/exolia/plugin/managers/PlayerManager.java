package fr.exolia.plugin.managers;

import fr.exolia.plugin.Main;
import fr.exolia.plugin.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerManager {

    private final Player player;
    private final ItemStack[] items = new ItemStack[40];
    private boolean vanished;

    public PlayerManager(Player player) {
        this.player = player;
        vanished = false;
    }

    public ItemStack[] getItems() { return items; }

    public void SaveInventory() {
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

    public void GiveInventory() {
        player.getInventory().clear();

        for (int slot = 0; slot < 36; slot++) {
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


    public void initModerationMod() {
        Main.getInstance().getPlayers().put(player.getUniqueId(), this);
        Main.getInstance().getModerators().add(player.getUniqueId());
        player.sendMessage( Main.PrefixInfo + "Mode modération §aactivé§7.");
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

    public void destroyModerationMod() {
        Main.getInstance().getPlayers().remove(player.getUniqueId());
        Main.getInstance().getModerators().remove(player.getUniqueId());
        player.getInventory().clear();
        player.sendMessage(Main.PrefixInfo + "Mode modération §cdésactivé§7.");
        GiveInventory();
        setVanished(false);
    }

    public void setVanished(boolean vanished) {
        this.vanished = vanished;
        if(vanished) {
            Bukkit.getOnlinePlayers().forEach(players -> players.hidePlayer(Bukkit.getPluginManager().getPlugin("Essentials"), player));
        } else {
            Bukkit.getOnlinePlayers().forEach(players -> players.showPlayer(Bukkit.getPluginManager().getPlugin("Essentials"), player));
        }
    }

    public void initStaffChat() {
        Main.getInstance().getPlayers().put(player.getUniqueId(), this);
        Main.getInstance().getStaffChat().add(player.getUniqueId());
        player.sendMessage( Main.PrefixInfo + "StaffChat §aactivé§7.");
    }

    public void destroyStaffChat() {
        Main.getInstance().getPlayers().remove(player.getUniqueId());
        Main.getInstance().getStaffChat().remove(player.getUniqueId());
        player.sendMessage(Main.PrefixInfo + "StaffChat §cdésactivé§7.");
    }

    public static PlayerManager getFromPlayer(Player player) {return Main.getInstance().getPlayers().get(player.getUniqueId());}

    public static boolean isInModerationMod(Player player) {return Main.getInstance().getModerators().contains(player.getUniqueId());}

    public static boolean isInStaffChat(Player player) {return Main.getInstance().getStaffChat().contains(player.getUniqueId());}

    public static boolean isFreeze(Player player) {return Main.getInstance().getFrozenPlayers().containsKey(player.getUniqueId());}

    public boolean isVanished() {return vanished;}
}