package fr.exolia.plugin.managers;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatHistoryManager {
    private String uuid;
    private String player;
    private String date;
    private String message;

    public ChatHistoryManager(String uuid, String player, String date, String message){
        this.uuid = uuid;
        this.player = player;
        this.date = date;
        this.message = message;
    }

    public ChatHistoryManager(String uuid, String player, String message){
        this(uuid, player, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), message);
    }

    public ChatHistoryManager(String uniqueId) {}

    public String getUUID() {
        return uuid;
    }

    public String getPlayer() {
        return player;
    }

    public String getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }


}
