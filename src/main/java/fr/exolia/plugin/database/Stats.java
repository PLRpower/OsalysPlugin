package fr.exolia.plugin.database;

import fr.exolia.plugin.Main;
import org.bukkit.Bukkit;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Stats {

    public Integer getOnlinePlayers(){
        return Bukkit.getOnlinePlayers().size();
    }

    public void setOnlinePlayers(Integer players){
        Main.getInstance().getMySQL().update("INSERT INTO stats (date, players) VALUES (" +
                "'" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "' ," +
                "'" + players + "')");
    }

    public void addPlayer(){
        setOnlinePlayers(getOnlinePlayers());
    }

    public void removePlayer(){
        setOnlinePlayers(getOnlinePlayers()-1);
    }
}
