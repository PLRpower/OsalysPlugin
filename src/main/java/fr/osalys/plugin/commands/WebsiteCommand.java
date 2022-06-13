package fr.osalys.plugin.commands;

import fr.osalys.plugin.Main;
import fr.osalys.plugin.managers.ChatManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WebsiteCommand implements CommandExecutor {

    private final TextComponent bar = new TextComponent("§7§m---------------------");
    private final ChatManager chatManager;

    public WebsiteCommand(Main main) {
        this.chatManager = main.getChatManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player player) {
            TextComponent weblink = new TextComponent("\n§aSite Web §2§l➤ §bexolia.site\n");
            weblink.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§bCliquez pour accéder au site")));
            weblink.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://exolia.site"));
            player.spigot().sendMessage(bar, weblink, bar);
            return true;
        }
        sender.sendMessage(chatManager.errorNotInstanceOfPlayer);
        return false;
    }
}
