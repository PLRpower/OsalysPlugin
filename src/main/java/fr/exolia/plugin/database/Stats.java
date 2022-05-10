package fr.exolia.plugin.database;

import fr.exolia.plugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

public class Stats {

    private final Main main = Main.getInstance();

    public Integer getOnlinePlayers(){
        final Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        return players.size();
    }

    public void setOnlinePlayers(Integer players){
        main.getMySQL2().update("INSERT INTO discord_traffic (date, time, players) VALUES (" +
                "'" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "' ," +
                "'" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "' ," +
                "'" + players + "')");
    }

    public void addPlayers(Integer players) {
        setOnlinePlayers(getOnlinePlayers() + players);
    }

    public void removePlayers(Integer players){
        setOnlinePlayers(getOnlinePlayers() < players ? 0 : (getOnlinePlayers() - players));
    }

}
