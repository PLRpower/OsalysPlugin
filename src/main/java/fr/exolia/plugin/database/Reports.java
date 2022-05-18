package fr.exolia.plugin.database;

import fr.exolia.plugin.Main;
import fr.exolia.plugin.managers.ReportManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Reports {

    private final Main main = Main.getInstance();
    private static final String TABLE = "reports";

    public void add(ReportManager reportManager){
        main.getMySQL().update("INSERT INTO " + TABLE + " (uuid, date, auteur, raison) VALUES (" +
                "'" + reportManager.getUUID() + "' ," +
                "'" + reportManager.getDate() + "' ," +
                "'" + reportManager.getAuthor() + "' ," +
                "'" + reportManager.getReason() + "')");
    }

    public void remove(int id){
        main.getMySQL().query("SELECT * FROM " + TABLE + " WHERE id='" + id + "'", rs -> {
            try {
                if(rs.next()) {
                    rs.deleteRow();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public ReportManager getReport(int id){
        main.getMySQL().query("SELECT * FROM " + TABLE + " WHERE id='" + id + "'", rs -> {
            try {
                if(rs.next()){
                    return new ReportManager(rs.getString("uuid"), rs.getString("date"),rs.getString("auteur"), rs.getString("raison") );
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
            return null;
        });
        return null;
    }

    public List<Integer> getIds(String uuid){
        List<Integer> ids = new ArrayList<>();
        main.getMySQL().query("SELECT * FROM " + TABLE + " WHERE uuid='" + uuid + "'", rs -> {
            try {
                while(rs.next()){
                    ids.add(rs.getInt("id"));}
            }catch (SQLException e){
                e.printStackTrace();
            }
        });
        return ids;
    }

    public List<ReportManager> getReports(String uuid){
        List<ReportManager> reportManagers = new ArrayList<>();
        Main.getInstance().getMySQL().query("SELECT * FROM " + TABLE + " WHERE uuid='" + uuid + "' ORDER BY id ASC", rs -> {
            try{
                while(rs.next()){
                    reportManagers.add(new ReportManager(rs.getString("uuid"), rs.getString("date"),rs.getString("auteur"), rs.getString("raison")));
                    }
            } catch (SQLException e){
                e.printStackTrace();
            }
        });
        return reportManagers;
    }
}
