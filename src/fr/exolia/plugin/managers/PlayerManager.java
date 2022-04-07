package fr.exolia.plugin.managers;

import fr.exolia.plugin.Main;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerManager {

    private Player player;
    private ItemStack[] items = new ItemStack[40];

    public PlayerManager(Player player){
        this.player = player;
    }

    public void init(){
        Main.getInstance().players.put(player.getUniqueId(), this);
    }

    public void destroy(){
        Main.getInstance().players.remove(player.getUniqueId());
    }

    public static PlayerManager getFromPlayer(Player player){
        return Main.getInstance().players.get(player.getUniqueId());
    }

    public ItemStack[] getItems() {
        return items;
    }

    public void saveInventory(){
        for(int slot = 0; slot < 36; slot++){
            ItemStack item = player.getInventory().getItem(slot);
            if(item != null){
                items[slot] = item;
            }
        }

        items[36] = player.getInventory().getHelmet();
        items[37] = player.getInventory().getChestplate();
        items[38] = player.getInventory().getLeggings();
        items[39] = player.getInventory().getBoots();

        player.getInventory().clear();
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
    }

    public void giveInventory(){
        player.getInventory().clear();

        for(int slot = 0; slot < 36; slot++){
            ItemStack item = items[slot];
            if(item != null){
                player.getInventory().setItem(slot, item);
            }
        }

        player.getInventory().setHelmet(items[36]);
        player.getInventory().setChestplate(items[37]);
        player.getInventory().setLeggings(items[38]);
        player.getInventory().setBoots(items[39]);
    }
}