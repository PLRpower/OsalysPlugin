package fr.osalys.plugin.managers;

import fr.osalys.plugin.Main;
import fr.osalys.plugin.util.GuiBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;

public class GuiManager implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event){

        Player player = (Player) event.getWhoClicked();
        ItemStack current = event.getCurrentItem();

        if(event.getCurrentItem() == null) return;

        Main.getInstance().getRegisteredMenus().values().stream()
                .filter(menu -> event.getView().getTitle().equalsIgnoreCase(menu.name()))
                .forEach(menu -> {
                    menu.onClick(player, event.getInventory(), current, event.getSlot());
                    event.setCancelled(true);
                });
    }

    public void addMenu(GuiBuilder m){
        Main.getInstance().getRegisteredMenus().put(m.getClass(), m);
    }

    public void open(Player player, Class<? extends GuiBuilder> gClass){

        if(!Main.getInstance().getRegisteredMenus().containsKey(gClass)) return;

        GuiBuilder menu = Main.getInstance().getRegisteredMenus().get(gClass);
        Inventory inv = Bukkit.createInventory(null, menu.getSize(), menu.name());
        menu.contents(player, inv);
        new BukkitRunnable() {

            @Override
            public void run() {
                player.openInventory(inv);
            }

        }.runTaskLater(Main.getInstance(), 1);

    }

    public Inventory addBorder(Inventory inventory, Integer size){
        if(size == 27){
            ItemStack border = new ItemStack(Material.GREEN_STAINED_GLASS_PANE, 1);
            List<Integer> slots = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26);
            slots.forEach(slot -> inventory.setItem(slot, border));
        }
        return inventory;
    }

}