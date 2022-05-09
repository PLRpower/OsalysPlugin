package fr.exolia.plugin.database;

import fr.exolia.plugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

public class Stats {

    private final Main main = Main.getInstance();

    public Integer getOnlinePlayers(){
        final Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        return players.size();
    }

    public void setOnlinePlayers(Integer players){
        main.getMySQL2().update("INSERT INTO discord_traffic (date, time, players) VALUES (" +
                "'" + LocalDate.now() + "' ," +
                "'" + LocalTime.now() + "' ," +
                "'" + players + "')");
    }

    public void addPlayers(Integer players) {
        setOnlinePlayers(getOnlinePlayers() + players);
    }

    public void removePlayers(Integer players){
        setOnlinePlayers(getOnlinePlayers() < players ? 0 : (getOnlinePlayers() - players));
    }

}
