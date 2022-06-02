package fr.osalys.plugin.database;

import fr.osalys.plugin.Main;

import java.sql.SQLException;

public class PlayerStats {

    public float getKills(org.bukkit.entity.Player player){
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

    public float getDeaths(org.bukkit.entity.Player player){
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

    public void setKills(org.bukkit.entity.Player player, float kills){
        Main.getInstance().getMySQL2().update("UPDATE playerstats SET kills ='" + kills + "' WHERE pseudo ='" + player.getName() + "'");
    }

    public void addKills(org.bukkit.entity.Player player) {
        setKills(player, getKills(player) + 1);
    }

    public void setDeaths(org.bukkit.entity.Player player, float deaths){
        Main.getInstance().getMySQL2().update("UPDATE playerstats SET deaths ='" + deaths + "' WHERE pseudo ='" + player.getName() + "'");
    }

    public void addDeaths(org.bukkit.entity.Player player) {
        setDeaths(player, getDeaths(player) + 1);
    }

}
