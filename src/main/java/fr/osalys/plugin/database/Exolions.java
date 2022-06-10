package fr.osalys.plugin.database;

import fr.osalys.plugin.Main;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class Exolions {

    private final Main main;

    public Exolions(Main main) {
        this.main = main;
    }

    public float getCoins(Player player) {
        return (float) main.getMySQL2().query("SELECT * FROM users WHERE pseudo ='" + player.getName() + "'", rs -> {
            try {
                if (rs.next()) {
                    return rs.getFloat("money");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return 0;
        });
    }

    public void setCoins(Player player, float coins) {
        main.getMySQL2().update("UPDATE users SET money ='" + coins + "' WHERE pseudo ='" + player.getName() + "'");
    }

    public void addCoins(Player player, float coins) {
        setCoins(player, getCoins(player) + coins);
    }

    public void removeCoins(Player player, float coins) {
        setCoins(player, getCoins(player) < coins ? 0 : (getCoins(player) - coins));
    }

}
