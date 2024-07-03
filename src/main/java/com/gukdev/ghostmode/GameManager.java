package com.gukdev.ghostmode;

import com.gukdev.ghostmode.bomb.Bomb;
import com.gukdev.ghostmode.teams.GhostTeam;
import com.gukdev.ghostmode.teams.HumanTeam;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameManager {
    private GhostTeam ghostTeam;
    private HumanTeam humanTeam;
    private boolean gameActive = false;
    private Bomb bomb;
    private List<Location> bombSites;

    public GameManager() {
        this.ghostTeam = new GhostTeam();
        this.humanTeam = new HumanTeam();
        this.bombSites = new ArrayList<>();
    }

    public void startGame() {
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        Collections.shuffle(players);
        int halfSize = players.size() / 2;

        for (int i = 0; i < players.size(); i++) {
            if (i < halfSize) {
                ghostTeam.addGhost(players.get(i));
            } else {
                humanTeam.addHuman(players.get(i));
            }
        }

        gameActive = true;
        Bukkit.broadcastMessage("Ghost Mode game started! Ghosts vs Humans!");
    }

    public void stopGame() {
        ghostTeam.clearTeam();
        humanTeam.clearTeam();
        gameActive = false;
        Bukkit.broadcastMessage("Ghost Mode game stopped!");
    }

    public boolean isGameActive() {
        return gameActive;
    }

    public void addBombSite(Location location) {
        bombSites.add(location);
    }

    public boolean isBombSite(Location location) {
        for (Location site : bombSites) {
            if (site.distance(location) < 2) {
                return true;
            }
        }
        return false;
    }

    public void plantBomb(Player player) {
        if (isGameActive() && isBombSite(player.getLocation())) {
            bomb = new Bomb(GhostModePlugin.getInstance(), player, player.getLocation());
            bomb.plantBomb();
        } else {
            player.sendMessage("You can only plant the bomb at designated sites!");
        }
    }

    public void defuseBomb(Player player) {
        if (isGameActive() && bomb != null && bomb.isPlanted()) {
            bomb.defuseBomb(player);
        } else {
            player.sendMessage("There is no bomb to defuse!");
        }
    }
}
