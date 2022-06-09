package fr.osalys.plugin.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RulesCommand implements CommandExecutor {

    private final TextComponent bar = new TextComponent("§7§m---------------------");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            return false;
        }

        TextComponent weblink = new TextComponent("\n§aRéglement §2§l➤ §bexolia.site/p/reglement\n");
        weblink.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§bCliquez pour accèder au règlement")));
        weblink.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://exolia.site/p/reglement"));
        player.spigot().sendMessage(bar, weblink, bar);
        return true;
    }
}
