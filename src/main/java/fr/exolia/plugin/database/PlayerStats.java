package fr.exolia.plugin.database;

import fr.exolia.plugin.Main;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class PlayerStats {

    public float getKills(Player player){
        return (float) Main.getInstance().getMySQL2().query("SELECT * FROM playerstats WHERE pseudo ='" + player.getName() + "'", rs -> {
            try{
                if(rs.next()){
                    return rs.getFloat("kills");
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
            return 0;
        });
    }


    public void setKills(Player player, float kills){
        Main.getInstance().getMySQL2().update("UPDATE playerstats SET kills ='" + kills + "' WHERE pseudo ='" + player.getName() + "'");
    }

    public void addKills(Player player) {
        setKills(player, getKills(player) + 1);
    }

    public void removeKills(Player player) {
        setKills(player, getKills(player) - 1);
    }

    public float getMorts(Player player){
        return (float) Main.getInstance().getMySQL2().query("SELECT * FROM playerstats WHERE pseudo ='" + player.getName() + "'", rs -> {
            try{
                if(rs.next()){
                    return rs.getFloat("morts");
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
            return 0;
        });
    }


    public void setMorts(Player player, float morts){
        Main.getInstance().getMySQL2().update("UPDATE playerstats SET kills ='" + morts + "' WHERE pseudo ='" + player.getName() + "'");
    }

    public void addMorts(Player player) {
        setMorts(player, getMorts(player) + 1);
    }

    public void removeMorts(Player player) {
        setMorts(player, getMorts(player) - 1);
    }

}
