package fr.osalys.plugin.managers;

import fr.osalys.plugin.Main;
import fr.osalys.plugin.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerManager {


    private static Main main;
    private final ChatManager chatManager;
    private final ItemStack[] items = new ItemStack[40];

    public PlayerManager(Main main) {
        PlayerManager.main = main;
        this.chatManager = main.getChatManager();
    }

    /**
     * Permet de savoir si un joueur est en mode modération.
     *
     * @param player joueur à vérifier
     */
    public static boolean isInModerationMod(Player player) {
        return main.getModerators().contains(player.getUniqueId());
    }

    /**
     * Permet de savoir si un joueur est immobilisé.
     *
     * @param player joueur à vérifier
     */
    public static boolean isFreeze(Player player) {
        return main.getFrozenPlayers().containsKey(player.getUniqueId());
    }

    /**
     * Permet de savoir si un joueur à la vision nocturne.
     *
     * @param player joueur à vérifier
     */
    public static boolean isNightVision(Player player) {
        return player.hasPotionEffect(PotionEffectType.NIGHT_VISION);
    }

    /**
     * Permet de savoir si un joueur est en mode StaffChat.
     *
     * @param player joueur à vérifier
     */
    public static boolean isStaffChat(Player player) {
        return main.getStaffChat().contains(player.getUniqueId());
    }

    /**
     * Permet de savoir si un joueur est invisible.
     *
     * @param player joueur à vérifier
     */
    public static boolean isVanished(Player player) {
        return main.getVanished().contains(player.getUniqueId());
    }

    public ItemStack[] getItems() {
        return items;
    }

    /**
     * Permet de sauvegarder l'inventaire d'un joueur.
     *
     * @param player joueur à sauvegarder
     */
    public void SaveInventory(Player player) {
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

    /**
     * Permet de redonner l'inventaire sauvegardé à un joueur.
     *
     * @param player joueur à regive
     */
    public void GiveInventory(Player player) {
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

    /**
     * Permet d'activer/désactiver le mode modération.
     *
     * @param player        joueur à modifier
     * @param moderationMod Activer/désactiver le mode
     */
    public void setModerationMod(Player player, boolean moderationMod) {
        if (moderationMod) {
            main.getModerators().add(player.getUniqueId());
            SaveInventory(player);
            player.getInventory().setItem(0, new ItemBuilder(Material.PAPER).setName("§aVoir l'inventaire").setLore("§7Clique droit sur un joueur", "§7pour voir son inventaire").toItemStack());
            player.getInventory().setItem(1, new ItemBuilder(Material.FEATHER).setName("§aVanish").setLore("§7Clique droit pour", "§7activer/désactiver le vanish").toItemStack());
            player.getInventory().setItem(2, new ItemBuilder(Material.PACKED_ICE).setName("§aFreeze").setLore("§7Clique droit sur un joueur", "§7pour le rendre immobile").toItemStack());
            player.getInventory().setItem(3, new ItemBuilder(Material.BOOK).setName("§aReports").setLore("§7Clique droit sur un joueur", "§7pour voir ses reports").toItemStack());
            player.sendMessage(chatManager.prefixInfo + "Mode modération §aactivé§7.");
        } else {
            main.getModerators().remove(player.getUniqueId());
            player.getInventory().clear();
            GiveInventory(player);
            player.sendMessage(chatManager.prefixInfo + "Mode modération §cdésactivé§7.");
        }
    }

    /**
     * Permet de rendre invisible/visible un joueur.
     *
     * @param player joueur à modifier
     * @param vanish Activer/désactiver le mode
     */
    public void setVanish(Player player, boolean vanish) {
        if (vanish) {
            main.getVanished().add(player.getUniqueId());
            player.performCommand("vanish on");
        } else {
            main.getVanished().remove(player.getUniqueId());
            player.performCommand("vanish off");
        }
    }

    /**
     * Permet d'activer/désactiver le mode StaffChat.
     *
     * @param player    joueur à mettre dans le mode
     * @param staffChat Activer/désactiver le mode
     */
    public void setStaffChat(Player player, boolean staffChat) {
        if (staffChat) {
            main.getStaffChat().add(player.getUniqueId());
            player.sendMessage(chatManager.prefixInfo + "StaffChat §aactivé§7.");
        } else {
            main.getStaffChat().remove(player.getUniqueId());
            player.sendMessage(chatManager.prefixInfo + "StaffChat §cdésactivé§7.");
        }
    }

    /**
     * Permet d'immobiliser ou non un joueur.
     *
     * @param player joueur à mettre dans le mode
     * @param freeze Activer/désactiver le mode
     */
    public void setFreeze(Player target, Player player, boolean freeze) {
        if (freeze) {
            main.getFrozenPlayers().put(target.getUniqueId(), target.getLocation());
            target.sendMessage(chatManager.prefixInfo + "Vous avez été §cimmobilisé §7par un modérateur");
            player.sendMessage(chatManager.prefixInfo + "Vous avez §cimmobilisé §b" + target.getName());
        } else {
            main.getFrozenPlayers().remove(target.getUniqueId());
            target.sendMessage(chatManager.prefixInfo + "Vous avez été §adésimmobilisé §7un modérateur");
            player.sendMessage(chatManager.prefixInfo + "Vous avez §adésimmobilisé §b" + target.getName());
        }
    }

    /**
     * Permet d'activer/désactiver le vision nocturne.
     *
     * @param player      joueur à mettre dans le mode
     * @param nightVision Activer/désactiver le mode
     */
    public void setNightVision(Player player, boolean nightVision) {
        if (nightVision) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 100000, 2, false, false));
            player.sendMessage(chatManager.prefixInfo + "Vous avez §aactivé §7la vision nocturne.");
        } else {
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
            player.sendMessage(chatManager.prefixInfo + "Vous avez §cdésactivé §7la vision nocturne.");
        }
    }
}