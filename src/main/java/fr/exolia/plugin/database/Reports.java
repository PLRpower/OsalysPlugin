package fr.exolia.plugin.database;

import fr.exolia.plugin.Main;
import fr.exolia.plugin.managers.Report;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Reports {

    private static final String TABLE = "reports";

    public void add(Report report) {
        Main.getInstance().getMySQL().update("INSERT INTO " + TABLE + " (uuid, date, auteur, raison) VALUES (" +
                "'" + report.getUUID().toString() + "' ," +
                "'" + report.getDate() + "' ," +
                "'" + report.getAuthor() + "' ," +
                "'" + report.getReason() + "')");
    }

    public void remove(int id) {
        Main.getInstance().getMySQL().query("SELECT * FROM " + TABLE + " WHERE `#`='" + id + "'", rs -> {
            try {
                if(rs.next()) {
                    rs.deleteRow();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public Report getReport(int id) {
        Main.getInstance().getMySQL().query("SELECT * FROM " + TABLE + " WHERE `#`='" + id + "'", rs -> {
            try {
                if(rs.next()) {
                    return new Report(UUID.fromString(rs.getString("uuid")), rs.getString("date"),rs.getString("auteur"), rs.getString("raison") );
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });
        return null;
    }

    public List<Integer> getFromUUID(UUID uuid) {
        List<Integer> ids = new ArrayList<>();

        Main.getInstance().getMySQL().query("SELECT * FROM " + TABLE + "WHERE uuid='" + uuid.toString() + "'", rs -> {
            try {
                while(rs.next()) {
                    ids.add(rs.getInt("#"));}
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return ids;
    }
}
