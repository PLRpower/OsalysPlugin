package fr.exolia.plugin.listeners;

import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;

public class PlayerChat implements Listener {


    public void exampleJson(Player player) {
        IChatBaseComponent comp = ChatSerializer.a("{\"text\":\"Welcome to my server! \",\"extra\":[{\"text\":\"§bClick Here\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"§cThis §dIs §aSo §bCool!\"},\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/list\"}}]}");
        PacketPlayOutChat packet = new PacketPlayOutChat(comp);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }


    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
    }
}
