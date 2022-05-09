package fr.exolia.plugin.database;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.function.Function;

public class MySQL {

    private final HikariDataSource connectionPool;
    public MySQL(HikariDataSource connectionPool) {
        this.connectionPool = connectionPool;
    }

    private Connection getConnection() throws SQLException{
        return connectionPool.getConnection();
    }

    public void createTables(){
        update("CREATE TABLE IF NOT EXISTS reports (" +
                "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "uuid VARCHAR(255), " +
                "date DATETIME, " +
                "auteur VARCHAR(255), " +
                "raison VARCHAR(255))");
    }

    public void update(String qry){
        try (Connection c = getConnection();
             PreparedStatement s = c.prepareStatement(qry)){
            s.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public Object query(String qry, Function<ResultSet, Object> consumer){
        try (Connection c = getConnection();
             PreparedStatement s = c.prepareStatement(qry);
             ResultSet rs = s.executeQuery()){
            return consumer.apply(rs);
        }catch(SQLException e){
            throw new IllegalStateException(e.getMessage());
        }
    }

    public void query(String qry, Consumer<ResultSet> consumer){
        try (Connection c = getConnection();
             PreparedStatement s = c.prepareStatement(qry);
             ResultSet rs = s.executeQuery()){
            consumer.accept(rs);
        }catch(SQLException e){
            throw new IllegalStateException(e.getMessage());
        }
    }

}
