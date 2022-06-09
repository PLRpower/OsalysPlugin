package fr.osalys.plugin.database;

import fr.osalys.plugin.Main;
import fr.osalys.plugin.managers.ChatHistoryManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChatHistory {

    private static final String TABLE = "messages";
    private final Main main;
    public ChatHistory(Main main) {this.main = main;}

    public void addMessage(ChatHistoryManager chatHistoryManager) {
        main.getMySQL().update("INSERT INTO " + TABLE + " (uuid, player, message, date) VALUES (" +
                "'" + chatHistoryManager.getUUID() + "' ," +
                "'" + chatHistoryManager.getPlayer() + "' ," +
                "'" + chatHistoryManager.getMessage() + "' ," +
                "'" + chatHistoryManager.getDate() + "')");
    }

    public ChatHistoryManager getMessage(int id) {
        main.getMySQL().query("SELECT * FROM " + TABLE + " WHERE id='" + id + "'", rs -> {
            try {
                if (rs.next()) {
                    return new ChatHistoryManager(rs.getString("uuid"), rs.getString("player"), rs.getString("message"), rs.getString("date"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });
        return null;
    }

    public List<Integer> getIds(String uuid) {
        List<Integer> ids = new ArrayList<>();
        main.getMySQL().query("SELECT * FROM " + TABLE + " WHERE uuid='" + uuid + "'", rs -> {
            try {
                while (rs.next()) {
                    ids.add(rs.getInt("id"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        return ids;
    }

    public List<ChatHistoryManager> getMessages(String uuid) {
        List<ChatHistoryManager> chatHistoryManagers = new ArrayList<>();
        main.getMySQL().query("SELECT * FROM " + TABLE + " WHERE uuid='" + uuid + "' ORDER BY id ASC", rs -> {
            try {
                while (rs.next()) {
                    chatHistoryManagers.add(new ChatHistoryManager(rs.getString("uuid"), rs.getString("player"), rs.getString("message"), rs.getString("date")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        return chatHistoryManagers;
    }
}
