package fr.osalys.plugin.gui;

import fr.osalys.plugin.Main;
import fr.osalys.plugin.managers.ReportManager;
import fr.osalys.plugin.util.GuiBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ReportGui implements GuiBuilder {

    private final Main main;
    public ReportGui(Main main) {this.main = main;}
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
        Player target = Bukkit.getPlayer(String.valueOf(player));
        String reason = Objects.requireNonNull(current.getItemMeta()).getDisplayName();
        switch (current.getType()) {
            case IRON_SWORD:
                if (reportCooldown.containsKey(player)) {
                    long time = (System.currentTimeMillis() - reportCooldown.get(player)) / 1000;
                    if (time < 120) {
                        player.sendMessage(main.prefixError + "Merci de patienter entre chaque signalement !");
                        player.closeInventory();
                        return;
                    } else {
                        reportCooldown.remove(player);
                    }
                }

                if (target == null) {
                    player.closeInventory();
                    player.sendMessage(main.prefixError + "Vous ne pouvez pas signaler ce joueur car il s'est déconnecté");
                }

                assert target != null;
                main.getReports().add(new ReportManager(target.getUniqueId().toString(), player.getName(), reason));
                main.getChatManager().sendReportToStaff(target.getName(), reason);
                reportCooldown.put(player, System.currentTimeMillis());

                player.closeInventory();
                player.sendMessage(main.prefixInfo + "Vous avez bien signalé ce joueur !");
                break;
            default:
                break;
        }
    }
}