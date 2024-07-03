package com.gukdev.ghostmode.bomb;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Bomb {
    private JavaPlugin plugin;
    private Player planter;
    private boolean isPlanted = false;
    private Location bombLocation;
    private int timer = 30; // Bomb timer in seconds

    public Bomb(JavaPlugin plugin, Player planter, Location bombLocation) {
        this.plugin = plugin;
        this.planter = planter;
        this.bombLocation = bombLocation;
    }

    public void plantBomb() {
        isPlanted = true;
        Bukkit.broadcastMessage(planter.getName() + " has planted the bomb!");
        new BukkitRunnable() {
            @Override
            public void run() {
                if (isPlanted) {
                    Bukkit.broadcastMessage("Bomb exploded!");
                    // End game logic for Ghosts win
                }
            }
        }.runTaskLater(plugin, timer * 20L); // Convert seconds to ticks
    }

    public void defuseBomb(Player defuser) {
        if (isPlanted) {
            isPlanted = false;
            Bukkit.broadcastMessage(defuser.getName() + " has defused the bomb!");
            // End game logic for Humans win
        }
    }

    public boolean isPlanted() {
        return isPlanted;
    }

    public Location getBombLocation() {
        return bombLocation;
    }
}
