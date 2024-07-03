package com.gukdev.ghostmode;

import com.gukdev.ghostmode.teams.GhostTeam;
import com.gukdev.ghostmode.teams.HumanTeam;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameManager {
    private GhostTeam ghostTeam;
    private HumanTeam humanTeam;
    private boolean gameActive = false;

    public GameManager() {
        this.ghostTeam = new GhostTeam();
        this.humanTeam = new HumanTeam();
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
}
