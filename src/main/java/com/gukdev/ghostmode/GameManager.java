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
    private final GhostTeam ghostTeam;
    private final HumanTeam humanTeam;
    private final ConfigManager configManager;
    private boolean gameActive = false;
    private Bomb bomb;
    private final List<Location> bombSites;

    public GameManager(ConfigManager configManager) {
        this.ghostTeam = new GhostTeam();
        this.humanTeam = new HumanTeam();
        this.configManager = configManager;
        this.bombSites = new ArrayList<>();
    }

    public void startGame() {
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        Collections.shuffle(players);
        int halfSize = players.size() / 2;
        int ghostTeamSize = Math.min(halfSize, configManager.getGhostTeamSize());
        int humanTeamSize = Math.min(halfSize, configManager.getHumanTeamSize());

        for (int i = 0; i < players.size(); i++) {
            if (i < ghostTeamSize) {
                ghostTeam.addGhost(players.get(i));
            } else if (i < ghostTeamSize + humanTeamSize) {
                humanTeam.addHuman(players.get(i));
            }
        }

        gameActive = true;
        Bukkit.broadcastMessage(configManager.getMessage("game_started"));
    }

    public void stopGame() {
        ghostTeam.clearTeam();
        humanTeam.clearTeam();
        gameActive = false;
        Bukkit.broadcastMessage(configManager.getMessage("game_stopped"));
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
            Bukkit.broadcastMessage(configManager.getMessage("bomb_planted").replace("%player%", player.getName()));
        } else {
            player.sendMessage(configManager.getMessage("not_bomb_site"));
        }
    }

    public void defuseBomb(Player player) {
        if (isGameActive() && bomb != null && bomb.isPlanted()) {
            bomb.defuseBomb(player);
            Bukkit.broadcastMessage(configManager.getMessage("bomb_defused").replace("%player%", player.getName()));
        } else {
            player.sendMessage(configManager.getMessage("no_bomb_to_defuse"));
        }
    }
}
