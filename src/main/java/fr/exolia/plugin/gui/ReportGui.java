package fr.exolia.plugin.gui;

import fr.exolia.plugin.Main;
import fr.exolia.plugin.managers.GuiManager;
import fr.exolia.plugin.managers.Report;
import fr.exolia.plugin.util.GuiBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ReportGui implements GuiBuilder {

    /**
     * En cours de création ...
     */

    private final Main main = Main.getInstance();
    private final Map<Player, Long> reportCooldown = new HashMap<>();

    @Override
    public String name() {
        return "Report";
    }

    @Override
    public int getSize() {
        return 27;
    }

    @Override
    public void contents(Player player, Inventory inv) {
        inv = main.getGuiManager().addBorder(inv, getSize());
        inv.setItem(12, new ItemStack(Material.IRON_SWORD));
    }

    @Override
    public void onClick(Player player, Inventory inv, ItemStack current, int slot) {
        Player target = Bukkit.getPlayer("PLR_Power");
        String reason = current.getItemMeta().getDisplayName();
        switch(current.getType()){
            case IRON_SWORD:
                if(reportCooldown.containsKey(player)) {
                    long time = (System.currentTimeMillis() - reportCooldown.get(player)) / 1000;
                    if(time < 120) {
                        player.sendMessage(main.PrefixError + "Merci de patienter entre chaque signalement !");
                        player.closeInventory();
                        return;
                    } else {
                        reportCooldown.remove(player);
                    }
                }

                if(target == null) {
                    player.closeInventory();
                    player.sendMessage(main.PrefixError + "Vous ne pouvez pas signaler ce joueur car il s'est déconnecté");
                }

                player.closeInventory();
                player.sendMessage("§aVous avez bien signalé ce joueur !");

                assert target != null;
                Main.getInstance().getReports().add(new Report(target.getUniqueId().toString(), player.getName(), reason.substring(2)));
                main.chatManager.sendReportToStaff(reason, target.getName());
                reportCooldown.put(player, System.currentTimeMillis());
                break;
            default: break;
        }
    }
}