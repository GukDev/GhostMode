package com.gukdev.ghostmode.teams;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.Set;

public class HumanTeam {
    private Set<Player> humans = new HashSet<>();

    public void addHuman(Player player) {
        player.removePotionEffect(PotionEffectType.INVISIBILITY);
        humans.add(player);
    }

    public void clearTeam() {
        humans.clear();
    }
}