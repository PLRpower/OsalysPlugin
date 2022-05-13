package fr.exolia.plugin.database;

import fr.exolia.plugin.Main;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class Exolions {
    private final Player player;
    private final Main main = Main.getInstance();

    public Exolions(Player player) {
        this.player = player;
    }

    public float getCoins(){
        return (float) main.getMySQL2().query("SELECT * FROM users WHERE pseudo ='" + player.getName() + "'", rs -> {
            try{
                if(rs.next()){
                    return rs.getFloat("money");
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
            return 0;
        });
    }

    public void setCoins(float coins){
        main.getMySQL2().update("UPDATE users SET money ='" + coins + "' WHERE pseudo ='" + player.getName() + "'");
    }

    public void addCoins(float coins) {
        setCoins(getCoins() + coins);
    }

    public void removeCoins(float coins) {
        setCoins(getCoins() < coins ? 0 : (getCoins() - coins));
    }

}
