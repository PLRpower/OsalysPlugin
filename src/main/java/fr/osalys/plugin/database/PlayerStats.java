package fr.osalys.plugin.database;

import fr.osalys.plugin.Main;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class PlayerStats {

    private final Main main;
    public PlayerStats(Main main) {this.main = main;}

    public float getKills(Player player) {
        return (float) main.getMySQL2().query("SELECT * FROM playerstats WHERE pseudo ='" + player.getName() + "'", rs -> {
            try {
                if (rs.next()) {
                    return rs.getFloat("kills");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return 0;
        });
    }

    public float getDeaths(Player player) {
        return (float) main.getMySQL2().query("SELECT * FROM playerstats WHERE pseudo ='" + player.getName() + "'", rs -> {
            try {
                if (rs.next()) {
                    return rs.getFloat("morts");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return 0;
        });
    }

    public void setKills(Player player, float kills) {
        main.getMySQL2().update("UPDATE playerstats SET kills ='" + kills + "' WHERE pseudo ='" + player.getName() + "'");
    }

    public void addKills(Player player) {
        setKills(player, getKills(player) + 1);
    }

    public void setDeaths(org.bukkit.entity.Player player, float deaths) {
        main.getMySQL2().update("UPDATE playerstats SET deaths ='" + deaths + "' WHERE pseudo ='" + player.getName() + "'");
    }

    public void addDeaths(Player player) {
        setDeaths(player, getDeaths(player) + 1);
    }

}
