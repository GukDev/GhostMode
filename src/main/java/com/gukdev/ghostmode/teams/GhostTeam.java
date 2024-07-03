package com.gukdev.ghostmode.teams;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.Set;

public class GhostTeam {
    private Set<Player> ghosts = new HashSet<>();

    public void addGhost(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
        ghosts.add(player);
    }

    public void clearTeam() {
        for (Player player : ghosts) {
            player.removePotionEffect(PotionEffectType.INVISIBILITY);
        }
        ghosts.clear();
    }
}


